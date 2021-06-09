package fun.mactavish.mplayer;

import fun.mactavish.mplayer.event.MediaPlayOrPauseEvent;
import fun.mactavish.mplayer.event.MediaSkipTimeEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KeyboardShortcuts extends HashMap<KeyCombination, Runnable> {
    @Autowired
    public KeyboardShortcuts(ApplicationEventPublisher eventPublisher) {
        Map<KeyCombination, Object> m = Map.of(new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.CONTROL_DOWN), new MediaSkipTimeEvent(5000), new KeyCodeCombination(KeyCode.LEFT, KeyCombination.CONTROL_DOWN), new MediaSkipTimeEvent(-5000), new KeyCodeCombination(KeyCode.SPACE, KeyCombination.CONTROL_DOWN), MediaPlayOrPauseEvent.INSTANCE);

        m.forEach((keyCombination, event) -> put(keyCombination, () -> eventPublisher.publishEvent(event)));
    }
}
