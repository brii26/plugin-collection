package tit.labpro.plugins.clock;

import javafx.application.Platform;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockBackend extends Thread {

    public interface ClockUpdateListener {
        void onTimeUpdate(String time);
    }

    private final ClockUpdateListener listener;
    private volatile boolean running = true;

    public ClockBackend(ClockUpdateListener listener) {
        this.listener = listener;
    }

    private void updateTime() {
        String timeStr = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        Platform.runLater(() -> listener.onTimeUpdate(timeStr));
    }

    @Override
    public void run() {
        while (running) {
            updateTime();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                running = false;
                break;
            }
        }
    }

    public void startClock() {
        this.start();
    }

    public void stopClock() {
        running = false;
        this.interrupt();
    }
}
