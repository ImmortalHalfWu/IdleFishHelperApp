package browsers.impls.manAll;

import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.FinishLoadProcessInterface;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.List;

public class AiTaoBaoFinishLoadProcess implements FinishLoadProcessInterface {
    @Override
    public boolean canProcess(FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {
        return url.startsWith("https://ai.taobao.com");
    }

    @Override
    public boolean process(String productInfoSavePath, List<ProductInfoBean> productInfoBeans, FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {
        return false;
    }

    @Override
    public boolean productIsLoadComplete() {
        return false;
    }
}
