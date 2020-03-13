package browsers.queues;

import Utils.FileUtils;
import Utils.ThreadPoolManager;
import com.teamdev.jxbrowser.chromium.Browser;

import java.util.concurrent.LinkedBlockingQueue;

public class BrowserQueue implements Runnable{

    private volatile static BrowserQueue instance;

    private final LinkedBlockingQueue<Browser> browsers;
    private boolean isRunning;

    public static BrowserQueue instance() {
        if (instance == null) {
            synchronized (BrowserQueue.class) {
                if (instance == null) {
                    instance = new BrowserQueue();
                }
            }
        }
        return instance;
    }

    public BrowserQueue() {
        browsers = new LinkedBlockingQueue<>();
        start();
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

    @Override
    public void run() {
        try {
            isRunning = true;
            while (true) {

                Browser pollBrowser = browsers.take();

                try {

                    NewLoadHtmlRequestQueue.LoadHtmlProcess loadHtmlProcess = NewLoadHtmlRequestQueue.instance().get();
                    MLoadFinishAdapter mLoadFinishAdapter = MLoadFinishAdapter.newInstance(pollBrowser, loadHtmlProcess);
                    pollBrowser.addLoadListener(mLoadFinishAdapter);
                    if (FileUtils.isEmpty(loadHtmlProcess.getUrl())) {
                        browsers.put(pollBrowser);
                    } else {
                        pollBrowser.loadURL(loadHtmlProcess.getUrl());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        browsers.put(pollBrowser);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        isRunning = false;
        start();
    }

    private void start() {
        if (isRunning) {
            return;
        }
        ThreadPoolManager.init().post(this);
    }

}
