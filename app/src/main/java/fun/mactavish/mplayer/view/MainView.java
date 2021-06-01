package fun.mactavish.mplayer.view;

import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainView extends BorderPane {
    @Autowired
    MainView(MediaScreen screen, ControlPanel controlPanel) {
        setCenter(screen);
        setBottom(controlPanel);
    }
}
