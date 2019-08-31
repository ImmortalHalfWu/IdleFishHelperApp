package browsers.queues;

import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.List;

public class YHQLoadEndProcess extends YHQLoadProcess {

    public YHQLoadEndProcess(ProductInfoBean productInfoBeans) {
        super(productInfoBeans);
    }

    @Override
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {
        boolean process = super.process(event, resultUrl, domDocument, browser);

        List<ProductInfoBean> allProduct = ManManBuyModel.instance().getAllProduct();
//        int size = allProduct.size();

        BrowserUtils.log("优惠券查询完毕：");
        FileUtils.writeText(FileUtils.createNewProductInfoFile("123"), new Gson().toJson(allProduct), false);

//        BrowserUtils.log(allProduct.toString());
//
//        for (int i = 0; i < size - 1; i++) {
//            ProductInfoBean productInfoBean = allProduct.get(i);
//            browser.loadURL(new ProductPicLoadProcess(productInfoBean));
//        }
//
//        browser.loadURL(new ProductPicLoadEndProcess(allProduct.get(size - 1)));

        return process;
    }
}
