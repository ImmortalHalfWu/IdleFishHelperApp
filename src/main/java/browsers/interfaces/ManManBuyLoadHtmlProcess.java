package browsers.interfaces;

import browsers.beans.ProductInfoBean;
import browsers.exceptions.ManLoadHtmlException;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

public interface ManManBuyLoadHtmlProcess {
    String getUrl();

    boolean canProcess(String url);

    ProductInfoBean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) throws ManLoadHtmlException;
}
