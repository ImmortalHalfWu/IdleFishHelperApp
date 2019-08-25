package browsers.impls;

import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.FinishLoadProcessInteface;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.List;

public class LoginFinishLoadProcess implements FinishLoadProcessInteface {
    @Override
    public boolean canProcess(FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {
        return url.startsWith("https://login.taobao.com");
    }

    @Override
    public boolean process(List<ProductInfoBean> productInfoBeans, FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {
        domDocument.findElement(By.id("sufei-dialog-close")).click();
        return true;
    }
}
