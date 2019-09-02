package browsers.impls.manAll;

import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.queues.NewLoadHtmlRequestQueue;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ManManBuyAllPageProcess implements NewLoadHtmlRequestQueue.LoadHtmlProcess {

    private Set<ProductInfoBean> productInfoBeanLinkedHashSet;
    private final static String PROCESS_URL = "http://baicai.manmanbuy.com/Default.aspx?PageID=";
    private String mUrl;
    private int page;
    private static int all;

    public ManManBuyAllPageProcess(int page) {
        this.page = page;
        this.mUrl = PROCESS_URL + page;
        productInfoBeanLinkedHashSet = new HashSet<>();
    }

    @Override
    public boolean canProcess(String url) {
        return mUrl.equals(url);
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {
        BrowserUtils.log(page);

        DOMElement container = domDocument.findElement(By.id("container"));

        List<DOMNode> children = container.getChildren();
        for (DOMNode node :
                children) {

            ProductInfoBean manManBuyProductInfo = BrowserUtils.findManManBuyProductInfo(node);
            if (manManBuyProductInfo != null) {
                productInfoBeanLinkedHashSet.add(manManBuyProductInfo);
            }

        }
        BrowserUtils.log(page + "over" + ++all);
        ManManBuyAllModel.instance().addManManBuyProducts(productInfoBeanLinkedHashSet);

        return true;
    }
}
