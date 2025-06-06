package tit.labpro.plugins.clock;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import tit.labpro.core.api.Plugin;
import tit.labpro.core.api.PluginTemplate;
import tit.labpro.core.api.ResizablePlugin;

public class ClockPlugin extends PluginTemplate implements Plugin, ResizablePlugin, ClockBackend.ClockUpdateListener {

    private final Label clockLabel;
    private final ClockBackend backend;

    public ClockPlugin() {
        clockLabel = new Label("00:00:00");
        clockLabel.setFont(Font.font("Consolas", 40));
        getChildren().add(clockLabel);
        setSize(250, 150);
        setLayoutX(100);
        setLayoutY(100);
        backend = new ClockBackend(this);
        enableResizeAndDrag();
    }


    @Override
    public Parent getUI() {
        return this;
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

    @Override
    public void setSize(double width, double height) {
        setPrefSize(width, height);
    }
}
