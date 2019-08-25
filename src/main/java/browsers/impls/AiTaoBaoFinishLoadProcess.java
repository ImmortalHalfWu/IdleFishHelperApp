package browsers.impls;

import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.FinishLoadProcessInteface;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.List;

public class AiTaoBaoFinishLoadProcess implements FinishLoadProcessInteface {
    @Override
    public boolean canProcess(FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {
        return url.startsWith("https://ai.taobao.com");
    }

    @Override
    public boolean process(List<ProductInfoBean> productInfoBeans, FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {
        browser.goBack();
        return true;
    }
}
