package browsers.queues;

import com.teamdev.jxbrowser.chromium.Browser;

public class WorkQueueManager {

    private volatile static WorkQueueManager workQueueManager;

    public WorkQueueManager instance() {
        if (workQueueManager == null) {
            synchronized (WorkQueueManager.class) {
                if (workQueueManager == null) {
                    workQueueManager = new WorkQueueManager();
                }
            }
        }
        return workQueueManager;
    }

    public void putBrowser(Browser browser) {
        if (browser == null) {
            return;
        }
        BrowserQueue.instance().put(browser);
    }

    public Browser getBrowser() {
        return BrowserQueue.instance().get();
    }

    public void putWorkRunnable(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        LoadHtmlQueue.instance().put(runnable);
    }

}
