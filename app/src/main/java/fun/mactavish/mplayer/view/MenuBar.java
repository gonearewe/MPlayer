package fun.mactavish.mplayer.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MenuBar extends javafx.scene.control.MenuBar {
    @Autowired
    MenuBar(FileMenu fileMenu, PlaybackMenu playbackMenu) {
        getMenus().addAll(fileMenu, playbackMenu);
    }
}

