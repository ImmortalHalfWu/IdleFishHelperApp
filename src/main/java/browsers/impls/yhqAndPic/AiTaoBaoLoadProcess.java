package browsers.impls.yhqAndPic;

import browsers.interfaces.BrowsersInterface;
import browsers.queues.NewLoadHtmlRequestQueue;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

public class AiTaoBaoLoadProcess implements NewLoadHtmlRequestQueue.LoadHtmlProcess {

    private static AiTaoBaoLoadProcess instance;

    public static AiTaoBaoLoadProcess getInstance() {
        if (instance == null) {
            synchronized (AiTaoBaoLoadProcess.class) {
                if (instance == null) {
                    instance = new AiTaoBaoLoadProcess();
                }
            }
        }
        return instance;
    }

    @Override
    public String getUrl() {
        return "";
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("https://ai.taobao.com");
    }

    @Override
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {
        browser.goBack();
        return true;
    }
}
