package tit.labpro.plugins.clock;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ClockPluginMain extends Application {

    private ClockPlugin clockPlugin;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();

        clockPlugin = new ClockPlugin();
        clockPlugin.onActivate();

        root.getChildren().add(clockPlugin.getUI());

        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
