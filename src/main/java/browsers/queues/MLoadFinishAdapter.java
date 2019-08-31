package browsers.queues;

import browsers.interfaces.BrowsersInterface;
import com.sun.istack.internal.NotNull;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;
import com.teamdev.jxbrowser.chromium.events.LoadAdapter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MLoadFinishAdapter extends LoadAdapter implements BrowsersInterface {

    /**
     * 全局通用browser数据梳理
     */
    private final static List<NewLoadHtmlRequestQueue.LoadHtmlProcess> overallSituationLoadHtmlProcess = new CopyOnWriteArrayList<>();

    public static MLoadFinishAdapter newInstance(
            @NotNull Browser browser,
            @NotNull NewLoadHtmlRequestQueue.LoadHtmlProcess loadHtmlProcess) {
        return new MLoadFinishAdapter(browser, loadHtmlProcess);
    }

    public static void registerStaticLoadHtmlProcee(NewLoadHtmlRequestQueue.LoadHtmlProcess loadHtmlProcess) {
        if (loadHtmlProcess != null && !overallSituationLoadHtmlProcess.contains(loadHtmlProcess)) {
            overallSituationLoadHtmlProcess.add(loadHtmlProcess);
        }
    }

    private Browser browser;
    private NewLoadHtmlRequestQueue.LoadHtmlProcess loadHtmlProcess;

    private MLoadFinishAdapter(Browser browser, @NotNull NewLoadHtmlRequestQueue.LoadHtmlProcess loadHtmlProcess) {
        this.browser = browser;
        this.loadHtmlProcess = loadHtmlProcess;
    }

    @Override
    public void onFinishLoadingFrame(FinishLoadingEvent event) {
        if (browser == null) {
            return;
        }

        try {

            if (loadHtmlProcess == null) {
                situationProcess(event);
                return;
            }

            if (!loadHtmlProcess.canProcess(event.getValidatedURL())) {
                situationProcess(event);
                return;
            }

            if (browser.isDisposed()) {
                loadURL(loadHtmlProcess);
            } else {
                loadHtmlProcess.process(event, event.getValidatedURL(), browser.getDocument(), this);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        browser.removeLoadListener(this);
        BrowserQueue.instance().put(browser);
        browser = null;

    }

    private void situationProcess(FinishLoadingEvent event) {

        for (NewLoadHtmlRequestQueue.LoadHtmlProcess process :
                overallSituationLoadHtmlProcess) {
            if (process.canProcess(event.getValidatedURL()) &&
                process.process(event, event.getValidatedURL(), browser.getDocument(), this)) {
                return;
            }
        }

    }


    @Override
    public void loadURL(NewLoadHtmlRequestQueue.LoadHtmlProcess url) {
        if (url != null) {
            NewLoadHtmlRequestQueue.instance().put(url);
        }
    }

    @Override
    public DOMDocument getDOMDocument() {
        return browser.getDocument();
    }

    @Override
    public void executeJavaScriptAndReturnValue(String js) {
        browser.executeJavaScriptAndReturnValue(js);
    }

    @Override
    public void goBack() {
        browser.goBack();
    }

}