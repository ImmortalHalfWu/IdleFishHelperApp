package browsers.impls.man.manAll;

import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.impls.man.ManManBuyBaseProcess;
import browsers.impls.man.ManManBuyCallBack;
import browsers.interfaces.BrowsersInterface;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


class ManManBuyAllPageProcess extends ManManBuyBaseProcess {

    private Set<ProductInfoBean> productInfoBeanLinkedHashSet;
    private final static String PROCESS_URL = "http://baicai.manmanbuy.com/Default.aspx?PageID=";
    private String mUrl;
    private int page;
    private static int all;

    ManManBuyAllPageProcess(int page, ManManBuyCallBack callBack) {
        super(callBack);
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
    protected boolean process(ManManBuyCallBack callBack, FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {
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
