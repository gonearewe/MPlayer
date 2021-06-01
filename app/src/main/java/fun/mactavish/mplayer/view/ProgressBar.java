package fun.mactavish.mplayer.view;

import fun.mactavish.mplayer.event.MediaPositionUpdateEvent;
import fun.mactavish.mplayer.event.ProgressPositionUpdateEvent;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class ProgressBar extends Slider {
    private static final double maxValue = 1000.0;
    private final Logger logger = LoggerFactory.getLogger(ProgressBar.class);
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    ProgressBar() {
        super(0.0, maxValue, 0.0);
        addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            eventPublisher.publishEvent(new ProgressPositionUpdateEvent((float) (getValue() / maxValue)));
        });
        // valueProperty().addListener((observable, oldValue, newValue) -> {
        //     eventPublisher.publishEvent(new ProgressPositionUpdateEvent((float) (newValue.floatValue() / maxValue)));
        // });
    }

    @EventListener
    public void onMediaPositionUpdate(MediaPositionUpdateEvent event) {
        setValue(event.getNormalizedPosition() * maxValue);
    }
}
