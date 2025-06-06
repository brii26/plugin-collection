package tit.labpro.plugins.clock;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockBackend {

    public interface ClockUpdateListener {
        void onTimeUpdate(String time);
    }

    private final ClockUpdateListener listener;
    private final Timeline timeline;

    public ClockBackend(ClockUpdateListener listener) {
        this.listener = listener;
        this.timeline = new Timeline(
            new KeyFrame(Duration.seconds(1), e -> updateTime())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    private void updateTime() {
        String timeStr = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        listener.onTimeUpdate(timeStr);
    }

    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }
}
