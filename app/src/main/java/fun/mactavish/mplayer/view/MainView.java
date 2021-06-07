package fun.mactavish.mplayer.view;

import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainView extends BorderPane {
    @Autowired
    MainView(MenuBar menuBar, MediaScreen screen, ControlPanel controlPanel) {
        setTop(menuBar);
        setCenter(screen);
        // Avoid overlapping of the screen.
        screen.setMinSize(0, 0);
        setBottom(controlPanel);
    }
}
