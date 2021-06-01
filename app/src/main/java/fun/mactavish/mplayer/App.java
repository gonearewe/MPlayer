package fun.mactavish.mplayer;

import fun.mactavish.mplayer.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class App extends Application {
    public App() {
    }

    private final Logger logger = LoggerFactory.getLogger(App.class);
    private ConfigurableApplicationContext context;

    public static void main(String[] commandLineArgs) {
        // launch JavaFX
        launch(App.class, commandLineArgs);
    }

    @Override
    public void init() {
        // initialize Spring Boot
        String[] commandLineArgs = getParameters().getRaw().toArray(new String[0]);
        this.context = SpringApplication.run(App.class, commandLineArgs);
    }

    @Override
    public void start(Stage primaryStage) {
        // var root = new BorderPane();
        // var videoImageView = new ImageView();
        // root.setCenter(videoImageView);
        // videoImageView.fitWidthProperty().bind(root.widthProperty());
        // videoImageView.fitHeightProperty().bind(root.heightProperty());
        Scene scene = new Scene(context.getBean(MainView.class), 1920, 1080);

        // set JMetro theme
        new JMetro(Style.LIGHT).setScene(scene);

        primaryStage.setTitle("MPlayer");
        primaryStage.setScene(scene);
        primaryStage.show();

        // play media whose url comes from properties
    }

    @Override
    public void stop() {
        context.close();
    }
}
