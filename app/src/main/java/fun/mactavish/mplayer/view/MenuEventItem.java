package fun.mactavish.mplayer.view;


import javafx.scene.control.MenuItem;
import org.springframework.context.ApplicationEventPublisher;

class MenuEventItem extends MenuItem {
    MenuEventItem(String name, Object event, ApplicationEventPublisher eventPublisher) {
        super(name);
        setOnAction(e -> eventPublisher.publishEvent(event));
    }
}
