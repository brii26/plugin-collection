package tit.labpro.plugins.clock;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import tit.labpro.core.api.Plugin;

public class ClockPlugin implements Plugin, ClockBackend.ClockUpdateListener {
    private final StackPane root;
    private final Label clockLabel;
    private final ClockBackend backend;

    public ClockPlugin() {
        root = new StackPane();

        clockLabel = new Label();
        clockLabel.setFont(Font.font("Consolas", 40));
        clockLabel.setStyle(
            "-fx-border-color: darkslategray; " +
            "-fx-border-width: 3; " +
            "-fx-border-radius: 10; " +
            "-fx-padding: 20; " +
            "-fx-background-color: linear-gradient(to bottom, #f0f4f7, #d9e2ec); " +
            "-fx-background-radius: 10;"
        );

        root.getChildren().add(clockLabel);

        backend = new ClockBackend(this);
    }

    @Override
    public Parent getUI() {
        return root;
    }

    @Override
    public void onActivate() {
        backend.start();
    }

    @Override
    public void onDeactivate() {
        backend.stop();
    }

    @Override
    public void onTimeUpdate(String time) {
        clockLabel.setText(time);
    }
}
