package fun.mactavish.mplayer.view;

import fun.mactavish.mplayer.event.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import javax.annotation.PreDestroy;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

@Component
public class MediaScreen extends BorderPane {
    private final Logger logger = LoggerFactory.getLogger(MediaScreen.class);
    private EmbeddedMediaPlayer player;
    private ImageView imageView = new ImageView();
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    MediaScreen(@Value("${file}") String url) {
        this.imageView.fitWidthProperty().bind(this.widthProperty());
        this.imageView.fitHeightProperty().bind(this.heightProperty());
        setCenter(this.imageView);

        var factory = new MediaPlayerFactory();
        this.player = factory.mediaPlayers().newEmbeddedMediaPlayer();
        factory.release();
        player.videoSurface().set(videoSurfaceForImageView(this.imageView));

        player.media().startPaused(url);
    }

    @Scheduled(fixedRate = 1000)
    private void updateProgressBar() {
        eventPublisher.publishEvent(new MediaPositionUpdateEvent(player.status().position()));
    }

    @PreDestroy
    private void destroy() {
        player.release();
    }

    @EventListener(MediaPlayEvent.class)
    public void play() {
        player.controls().play();
    }

    @EventListener(MediaPauseEvent.class)
    public void pause() {
        player.controls().pause();
    }

    @EventListener(MediaStopEvent.class)
    public void stop() {
        player.controls().stop();
    }

    @EventListener
    public void onProgressPositionUpdate(ProgressPositionUpdateEvent event) {
        player.controls().setPosition(event.getNormalizedPosition());
    }
}
