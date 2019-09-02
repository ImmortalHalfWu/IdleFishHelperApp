package Utils;

import java.util.concurrent.*;

public class ThreadPoolManager {

    private static ThreadPoolManager threadPoolManager;

    public static ThreadPoolManager instance() {
        if (threadPoolManager == null) {
            synchronized (ThreadPoolManager.class) {
                if (threadPoolManager == null) {
                    threadPoolManager = new ThreadPoolManager();
                }
            }
        }
        return threadPoolManager;
    }




    private final ScheduledExecutorService executorService;

    private ThreadPoolManager() {
        executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    }

    public void postDelayForS(Runnable task, long time) {
        postDelayForMS(task, time * 1000);
    }

    public void postDelayForS(Runnable task, float time) {
        postDelayForMS(task, (int)(time * 1000));
    }

    public void postDelayForMS(Runnable task, long time) {
        if (task == null) {
            return;
        }
        time = time < 0 ? 0 : time;
        executorService.schedule(task, time, TimeUnit.MILLISECONDS);
    }

    public void post(Runnable task) {
        postDelayForS(task, 0);
    }

}
