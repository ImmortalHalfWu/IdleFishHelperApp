package browsers.queues;

import browsers.interfaces.BrowsersInterface;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.concurrent.LinkedBlockingQueue;

public class NewLoadHtmlRequestQueue {

    private volatile static NewLoadHtmlRequestQueue instance;

    private final LinkedBlockingQueue<LoadHtmlProcess> process;

    public static NewLoadHtmlRequestQueue instance() {
        if (instance == null) {
            synchronized (NewLoadHtmlRequestQueue.class) {
                if (instance == null) {
                    instance = new NewLoadHtmlRequestQueue();
                }
            }
        }
        return instance;
    }

    private NewLoadHtmlRequestQueue() {
        process = new LinkedBlockingQueue<>();
    }

    public void put(LoadHtmlProcess runnable) {
        try {
            process.put(runnable);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public LoadHtmlProcess get() {
        try {
            return process.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int size() {
        return process.size();
    }

    public interface LoadHtmlProcess {

        String getUrl();

        boolean canProcess(String url);

        boolean process(
                FinishLoadingEvent event,
                String resultUrl,
                DOMDocument domDocument,
                BrowsersInterface browser);

    }

}
//ScheduledExecutorService