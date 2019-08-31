package browsers.queues;

import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.ProductPicProcessInterface;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

public class ProductPicLoadProcess implements NewLoadHtmlRequestQueue.LoadHtmlProcess {

    private String url;
    private ProductInfoBean productInfoBean;
    private ProductPicProcessInterface currentProcess;
    protected volatile static int loadIndex = 0;

    public ProductPicLoadProcess(ProductInfoBean productInfoBean) {
        this.url = productInfoBean.getBuyUrl();
        this.productInfoBean = productInfoBean;
    }


    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public boolean canProcess(String mURl) {
        if ((mURl.startsWith("https://detail.tmall.com") || url.startsWith("https://detail.yao.95095.com"))) {
            currentProcess = new TmallProductPicFinishLoadProcess();
            return true;
        }

        if ((mURl.startsWith("https://item.taobao.com/item.htm"))) {
            currentProcess = new TaoBaoProductPicFinishLoadProcess();
            return true;
        }
        currentProcess = null;
        return false;
    }

    @Override
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {
        if (currentProcess == null) {
            return false;
        }

        int scrollCount = 1;
        int scrollIndex = 395;
        try {
//            while (true) {
//
//                scrollIndex += 50;
//                browser.executeJavaScriptAndReturnValue("window.scrollTo({top: " + scrollIndex +",behavior: \"smooth\" });");
//                try {
//                    Thread.sleep(4000);
//                }catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if (++scrollCount > 10) {
//                    BrowserUtils.log("商品详情页，页面滚动第" + scrollIndex + "次");
//                    return false;
//                }
//
//                if (currentProcess.canFindPicElement(browser.getDOMDocument())) {
//                    break;
//                } else {
//                    try {
//                        Thread.sleep(3500);
//                    }catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }

            do {
                try {
                    scrollIndex += 400;
                    browser.executeJavaScriptAndReturnValue("window.scrollTo({top: " + scrollIndex +",behavior: \"smooth\" });");
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                BrowserUtils.log("商品详情页，页面滚动第" + scrollCount + "次");
                if (++scrollCount > 10) {
                    return false;
                }
            } while (currentProcess.canFindPicElement(browser.getDOMDocument()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            return currentProcess.process(productInfoBean, event, url, domDocument, browser);
        } catch (Exception e) {
            e.printStackTrace();
            BrowserUtils.log("图像抓取异常：" + url);
        }
        return false;
    }
}
