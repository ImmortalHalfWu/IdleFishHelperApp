package browsers.impls.yhqAndPic;

import browsers.interfaces.BrowsersInterface;
import browsers.queues.NewLoadHtmlRequestQueue;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

public class LoginLoadProcess implements NewLoadHtmlRequestQueue.LoadHtmlProcess {


    private static LoginLoadProcess instance;

    public static LoginLoadProcess getInstance() {
        if (instance == null) {
            synchronized (LoginLoadProcess.class) {
                if (instance == null) {
                    instance = new LoginLoadProcess();
                }
            }
        }
        return instance;
    }

    @Override
    public String getUrl() {
        return "https://login.taobao.com";
    }

    @Override
    public boolean canProcess(String url) {
        return url.startsWith("https://login.taobao.com");
    }

    @Override
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {

//        BrowserUtils.log(domDocument.getDocumentElement().getInnerText());
        DOMElement element = domDocument.findElement(By.id("sufei-dialog-close"));
        if (element != null) {
            element.click();
        }

        return true;
    }
}
