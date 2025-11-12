package game;

import game.JFrame_UI.SimpleInterface;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class GameScheduler {
    private static GameScheduler instance = null;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> task;
    private GameEngine engine = GameEngine.getInstance();
    private long tickIntervalMillis = 500; // default to 500 milliseconds

    private GameScheduler() {}

    public static GameScheduler getInstance() {
        if (instance == null) {
            instance = new GameScheduler();
        }
        return instance;
    }

    public void start() {
        if (task == null || task.isCancelled()) {
            task = executor.scheduleAtFixedRate(() -> {
                engine.tick();

            }, 0, tickIntervalMillis, TimeUnit.MILLISECONDS);
        }
    }

    public void pause() {
        if (task != null && !task.isCancelled()) {
            task.cancel(false);
        }
    }

    public void resume() {
        start(); // just restarts the scheduled task
    }

    public void stop() {
        executor.shutdownNow();
    }
}
