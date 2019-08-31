package browsers.queues;

import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.List;

public class ManManBuyAllEndPageProcess extends ManManBuyAllPageProcess {
    public ManManBuyAllEndPageProcess(int page) {
        super(page);
    }

    @Override
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {

        boolean process = super.process(event, resultUrl, domDocument, browser);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int maxProductCount = ManManBuyModel.instance().getMaxProductCount();
        int productCurrentCount = ManManBuyModel.instance().getProductCurrentCount();
        BrowserUtils.log("慢慢买主页爬取完，目前数量：" + productCurrentCount + "——总量：" + maxProductCount);

//        if (maxProductCount != productCurrentCount) {
//            return process;
//        }

        if (productCurrentCount <= 0) {
            return process;
        }

        List<ProductInfoBean> allProduct = ManManBuyModel.instance().getAllProduct();

        BrowserUtils.sortProductForPriceAndOrderNum(allProduct);

        if (productCurrentCount > 300) {
            List<ProductInfoBean> productInfoBeans = allProduct.subList(300, productCurrentCount);
            ManManBuyModel.instance().removeAllProduct(productInfoBeans);
        }
        BrowserUtils.log(allProduct.toString());

        FileUtils.writeText(FileUtils.createNewProductInfoFile("123"), new Gson().toJson(ManManBuyModel.instance().getAllProduct()), false);

        browser.loadURL(new YHQLoadProcess(allProduct.get(0)));

//        int size = allProduct.size();
//
//        for (int i = 0; i < size - 1; i++) {
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            browser.loadURL(new YHQLoadProcess(allProduct.get(i)));
//        }
//
//        browser.loadURL(new YHQLoadEndProcess(allProduct.get(size - 1)));

        return process;
    }

}
