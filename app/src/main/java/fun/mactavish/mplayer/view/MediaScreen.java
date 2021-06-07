package fun.mactavish.mplayer.view;

import fun.mactavish.mplayer.event.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MarqueePosition;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

@Component
public class MediaScreen extends BorderPane {
    private final Logger logger = LoggerFactory.getLogger(MediaScreen.class);
    private EmbeddedMediaPlayer player;
    private ImageView imageView = new ImageView();
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    MediaScreen() {
        this.imageView.fitWidthProperty().bind(this.widthProperty());
        this.imageView.fitHeightProperty().bind(this.heightProperty());
        setCenter(this.imageView);

        var factory = new MediaPlayerFactory();
        this.player = factory.mediaPlayers().newEmbeddedMediaPlayer();
        factory.release();
        player.videoSurface().set(videoSurfaceForImageView(this.imageView));
    }

    @Scheduled(fixedRate = 200)
    private void publishMediaStatus() {
        eventPublisher.publishEvent(new MediaStatusUpdateEvent(player.status().position(), player.status().isPlaying()));
    }

    @PreDestroy
    private void destroy() {
        player.release();
    }

    @EventListener
    public void start(MediaStartEvent event) {
        player.controls().stop();
        player.media().startPaused(event.getUrl());
    }

    @EventListener(MediaPlayOrPauseEvent.class)
    public void playOrPause() {
        if (player.status().isPlaying()) {
            player.controls().pause();
        } else {
            player.controls().play();
        }
    }

    @EventListener(MediaStopEvent.class)
    public void stop() {
        player.controls().stop();
    }

    @EventListener
    public void onProgressPositionUpdate(ProgressPositionUpdateEvent event) {
        player.controls().setPosition(event.getNormalizedPosition());
        showMarquee();
    }

    @EventListener
    public void onMediaSkipTime(MediaSkipTimeEvent event) {
        player.controls().skipTime(event.getTimeMs());
        showMarquee();
    }

    private void showMarquee() {
        Executors.newSingleThreadExecutor().execute(() -> {
            long timeS = player.status().time() / 1000;
            player.marquee().setText(String.format("%02d:%02d", timeS / 60, timeS % 60));
            player.marquee().setPosition(MarqueePosition.TOP_RIGHT);
            player.marquee().enable(true);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException ignored) {
            }
            player.marquee().enable(false);
        });
    }
}
