package browsers.interfaces;

import browsers.beans.ProductInfoBean;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.List;

public interface FinishLoadProcessInteface {

    boolean htmlLoadingOverCanProcess(FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser);
    boolean htmlLoadingOverProcess(
            List<ProductInfoBean> productInfoBeans,
            FinishLoadingEvent event,
            String url,
            DOMDocument domDocument,
            BrowsersInterface browser);

}
