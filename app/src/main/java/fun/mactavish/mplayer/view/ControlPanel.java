package fun.mactavish.mplayer.view;

import fun.mactavish.mplayer.event.MediaPauseEvent;
import fun.mactavish.mplayer.event.MediaPlayEvent;
import fun.mactavish.mplayer.event.MediaStopEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ControlPanel extends BorderPane {
    private final Logger logger = LoggerFactory.getLogger(ControlPanel.class);
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    ControlPanel(ProgressBar progressBar) {
        setTop(progressBar);
        var buttons = new ButtonBar();

        var playOrPause = new Button("Play");
        playOrPause.setOnMouseClicked(new EventHandler<>() {
            private boolean playing = false;

            @Override
            public void handle(final MouseEvent event) {
                if (playing) {
                    eventPublisher.publishEvent(MediaPauseEvent.INSTANCE);
                    playOrPause.setText("Play");
                } else {
                    eventPublisher.publishEvent(MediaPlayEvent.INSTANCE);
                    playOrPause.setText("Pause");
                }
                playing = !playing;
            }
        });

        var stop = new Button("Stop");
        stop.setOnMouseClicked(e -> eventPublisher.publishEvent(MediaStopEvent.INSTANCE));

        buttons.getButtons().add(playOrPause);
        buttons.getButtons().add(stop);
        setCenter(buttons);
    }
}
