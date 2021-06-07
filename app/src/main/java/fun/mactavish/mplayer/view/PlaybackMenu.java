package fun.mactavish.mplayer.view;

import fun.mactavish.mplayer.event.MediaPlayOrPauseEvent;
import fun.mactavish.mplayer.event.MediaSkipTimeEvent;
import fun.mactavish.mplayer.event.MediaStopEvent;
import javafx.scene.control.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class PlaybackMenu extends Menu {
    @Autowired
    PlaybackMenu(ApplicationEventPublisher publisher) {
        super("Playback");
        getItems().addAll(new MenuEventItem("Play/Pause", MediaPlayOrPauseEvent.INSTANCE, publisher), new MenuEventItem("Stop", MediaStopEvent.INSTANCE, publisher), new MenuEventItem("Jump Forward", new MediaSkipTimeEvent(5000), publisher), new MenuEventItem("Jump Backward", new MediaSkipTimeEvent(-5000), publisher));
    }
}
