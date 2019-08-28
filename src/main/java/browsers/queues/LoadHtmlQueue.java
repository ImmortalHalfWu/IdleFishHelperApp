package browsers.queues;

import java.util.concurrent.LinkedBlockingQueue;

class LoadHtmlQueue extends Thread {

    private volatile static LoadHtmlQueue instance;

    private final LinkedBlockingQueue<Runnable> runnables;
    private boolean running = true;

    public static LoadHtmlQueue instance() {
        if (instance == null) {
            synchronized (LoadHtmlQueue.class) {
                if (instance == null) {
                    instance = new LoadHtmlQueue();
                }
            }
        }
        return instance;
    }

    private LoadHtmlQueue() {
        runnables = new LinkedBlockingQueue<>();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Runnable poll = runnables.poll();
                if (poll == null) {
                    continue;
                }
                poll.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void put(Runnable runnable) {
        try {
            runnables.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stopRun() {
        running = false;
    }
}
