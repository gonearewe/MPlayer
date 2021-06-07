package fun.mactavish.mplayer.view;

import fun.mactavish.mplayer.event.MediaStartEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
class FileMenu extends Menu {
    @Autowired
    FileMenu(Stage primaryStage, ApplicationEventPublisher eventPublisher) {
        super("File");
        getItems().addAll(new MenuItem("Open File") {
            {
                // 不是结点
                // addEventHandler(MouseEvent.MOUSE_PRESSED,event -> {
                //     System.out.println(primaryStage);
                //     new FileChooser().showOpenDialog(primaryStage);
                // });
                setOnAction(action -> {
                    var f = new FileChooser().showOpenDialog(primaryStage);
                    eventPublisher.publishEvent(new MediaStartEvent(f.getAbsolutePath()));
                });
            }
        }, new MenuItem("Open Folder") {

        });
    }
}
