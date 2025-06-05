package tit.labpro.plugins.clock;

import javafx.application.Platform;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ClockBackend implements Runnable {
    private volatile boolean running = false;
    private Thread thread;
    private final ClockUpdateListener listener;

    public interface ClockUpdateListener {
        void onTimeUpdate(String time);
    }

    public ClockBackend(ClockUpdateListener listener) {
        this.listener = listener;
    }

    public void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "ClockBackendThread");
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        running = false;
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public void run() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        while (running) {
            String currentTime = LocalTime.now().format(formatter);
            Platform.runLater(() -> listener.onTimeUpdate(currentTime));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                running = false;
            }
        }
    }
}
