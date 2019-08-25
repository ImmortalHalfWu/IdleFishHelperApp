package browsers.interfaces;

import browsers.beans.ProductInfoBean;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

public interface ProductPicProcessInterface {

    boolean canFindPicElement(DOMDocument domDocument);
    boolean htmlLoadingOverCanProcess(FinishLoadingEvent event, String validatedURL, DOMDocument domDocument, BrowsersInterface browser);
    boolean process(ProductInfoBean productInfoBean, FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser);

}
