package browsers.queues;

import com.teamdev.jxbrowser.chromium.Browser;

import java.util.concurrent.LinkedBlockingQueue;

class BrowserQueue {

    private volatile static BrowserQueue instance;

    private final LinkedBlockingQueue<Browser> browsers;

    public static BrowserQueue instance() {
        if (instance == null) {
            synchronized (BrowserQueue.class) {
                if (instance == null) {
                    return instance;
                }
            }
        }
        return instance;
    }

    public BrowserQueue() {
        browsers = new LinkedBlockingQueue<>();
    }

    public void put(Browser browser) {
        if (browser != null) {
            try {
                browsers.put(browser);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Browser get() {
        return browsers.poll();
    }

}
