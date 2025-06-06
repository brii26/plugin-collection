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

        // Set posisi awal dan size default (bisa load dari config)
        clockPlugin.setSize(250, 150);
        clockPlugin.getUI().setLayoutX(100);
        clockPlugin.getUI().setLayoutY(100);

        root.getChildren().add(clockPlugin.getUI());

        // Activate plugin
        clockPlugin.onActivate();

        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Clock Plugin Main Test");
        stage.show();

        // On close, simpan posisi & size (contoh sederhana)
        stage.setOnCloseRequest(e -> {
            double x = clockPlugin.getUI().getLayoutX();
            double y = clockPlugin.getUI().getLayoutY();
            double w = clockPlugin.getWidth();
            double h = clockPlugin.getHeight();
            System.out.println("Save position & size: " + x + ", " + y + ", " + w + ", " + h);
            // Simpan ke file/config sesuai kebutuhan
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
