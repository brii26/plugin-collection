package tit.labpro.plugins.clock;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import tit.labpro.core.api.*;

public class ClockPlugin extends PluginTemplate implements Plugin, ClockBackend.ClockUpdateListener, ResizablePlugin {
    private final Label clockLabel;
    private final ClockBackend backend;
    

    public ClockPlugin() {
        backend = new ClockBackend(this);
        clockLabel = new Label("00:00");
        clockLabel.setFont(Font.font("Consolas", 40));
        getChildren().add(clockLabel);
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
