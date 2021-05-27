package fun.mactavish.mplayer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

import java.util.Objects;

import static uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurfaceFactory.videoSurfaceForImageView;

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
        var root = new BorderPane();
        var videoImageView = new ImageView();
        root.setCenter(videoImageView);
        videoImageView.fitWidthProperty().bind(root.widthProperty());
        videoImageView.fitHeightProperty().bind(root.heightProperty());
        Scene scene = new Scene(root, 1920, 1080);

        // set JMetro theme
        new JMetro(Style.LIGHT).setScene(scene);

        // mount media player
        EmbeddedMediaPlayer mediaPlayer = this.context.getBean(EmbeddedMediaPlayer.class);
        mediaPlayer.videoSurface().set(videoSurfaceForImageView(videoImageView));

        primaryStage.setTitle("MPlayer");
        primaryStage.setScene(scene);
        primaryStage.show();

        // play media whose url comes from properties
        mediaPlayer.media().play(Objects.requireNonNull(context.getEnvironment().getProperty("file")));
    }

    @Override
    public void stop() {
        context.close();
    }
}

@Configuration
class VlcjConfiguration {
    @Bean(destroyMethod = "release")
    EmbeddedMediaPlayer mediaPlayer() {
        var factory = new MediaPlayerFactory();
        var player = factory.mediaPlayers().newEmbeddedMediaPlayer();
        factory.release();
        return player;
    }
}
