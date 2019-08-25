package browsers.impls;

import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.FinishLoadProcessInteface;
import browsers.interfaces.ProductPicProcessInterface;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.List;

public class ProductPicFinishLoadProcess implements FinishLoadProcessInteface {

    private int scrollCount = 0;
    private int orderPicIndex = 0;
    private ProductPicProcessInterface tMallPicProcess = new TmallProductPicFinishLoadProcess();
    private ProductPicProcessInterface taoBaoPicProcess = new TaoBaoProductPicFinishLoadProcess();
    private ProductPicProcessInterface currentProcess;

    @Override
    public boolean canProcess(FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {

        if (tMallPicProcess.htmlLoadingOverCanProcess(event, url, domDocument, browser)) {
            currentProcess = tMallPicProcess;
        } else if (taoBaoPicProcess.htmlLoadingOverCanProcess(event, url, domDocument, browser)) {
            currentProcess = taoBaoPicProcess;
        } else {
            return false;
        }
        BrowserUtils.log(currentProcess.getClass().getSimpleName() + "开始查找图片：" + url);
        return true;
    }

    @Override
    public boolean process(List<ProductInfoBean> productInfoBeans, FinishLoadingEvent event, String validatedURL, DOMDocument document, BrowsersInterface browser) {
        new Thread(() -> {


            scrollCount = 0;
            int scrollIndex = 395;
            do {
                try {
                    scrollIndex += 50;
                    browser.executeJavaScriptAndReturnValue("window.scrollTo({top: " + scrollIndex +",behavior: \"smooth\" });");
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ++scrollCount;
                if (scrollIndex > 10) {
                    BrowserUtils.log("商品详情页，页面滚动第" + scrollIndex + "次");
                }
            } while (currentProcess.canFindPicElement(document));



            ProductInfoBean productInfoBean = null;
            try {
                productInfoBean = productInfoBeans.get(orderPicIndex);
                currentProcess.process(productInfoBean, event, validatedURL, document, browser);
                ++orderPicIndex;
            } catch (Exception e) {
                productInfoBeans.remove(orderPicIndex);
                BrowserUtils.logErroLine();
                e.printStackTrace();
                BrowserUtils.log("查找商品图片异常：" + (productInfoBean == null ? "商品为null" : productInfoBean.getProductName()));
                BrowserUtils.logErroLine();
            }



            if (orderPicIndex < productInfoBeans.size()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                browser.loadURL(productInfoBeans.get(orderPicIndex).getBuyUrl());
            } else {
                orderPicIndex = 0;
                String s = new Gson().toJson(productInfoBeans);
                BrowserUtils.logLine();
                BrowserUtils.log("图像查找完成了：");
                BrowserUtils.log(s);
                BrowserUtils.logLine();
                FileUtils.fileLinesWrite(FileUtils.FILE_PATH_JSON, s, false);
            }


        }).start();

        return true;
    }

}
