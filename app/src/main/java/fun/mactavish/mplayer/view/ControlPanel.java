package fun.mactavish.mplayer.view;

import fun.mactavish.mplayer.event.MediaPlayOrPauseEvent;
import fun.mactavish.mplayer.event.MediaStatusUpdateEvent;
import fun.mactavish.mplayer.event.MediaStopEvent;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ControlPanel extends BorderPane {
    private final Logger logger = LoggerFactory.getLogger(ControlPanel.class);
    private final Button playOrPauseButton = new Button("Play");
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    ControlPanel(ProgressBar progressBar) {
        playOrPauseButton.setOnMouseClicked(e -> eventPublisher.publishEvent(MediaPlayOrPauseEvent.INSTANCE));

        var stopButton = new Button("Stop");
        stopButton.setOnMouseClicked(e -> eventPublisher.publishEvent(MediaStopEvent.INSTANCE));

        var buttons = new HBox(playOrPauseButton, stopButton);
        buttons.setAlignment(Pos.BASELINE_CENTER);
        buttons.setSpacing(100);

        setTop(progressBar);
        setCenter(buttons);
    }

    @EventListener
    public void onMediaStatusUpdate(MediaStatusUpdateEvent event) {
        String text = event.isPlaying() ? "Pause" : "Play";
        Platform.runLater(() -> playOrPauseButton.setText(text));
    }
}
