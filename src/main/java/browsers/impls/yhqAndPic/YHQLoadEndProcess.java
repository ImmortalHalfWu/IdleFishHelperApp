package browsers.impls.yhqAndPic;

import browsers.beans.ProductInfoBean;
import browsers.exceptions.ManLoadHtmlException;
import browsers.interfaces.BrowsersInterface;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

public class YHQLoadEndProcess extends YHQLoadProcess {

    public YHQLoadEndProcess(ProductInfoBean productInfoBeans) {
        super(productInfoBeans);
    }

    @Override
    public ProductInfoBean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) throws ManLoadHtmlException {
        ProductInfoBean process = super.process(event, resultUrl, domDocument, browser);

//        List<ProductInfoBean> allProduct = ManManBuyAllModel.init().getAllProduct();
////        int size = allProduct.size();
//
//        BrowserUtils.log("优惠券查询完毕：");
//        FileUtils.writeText(FileUtils.createNewProductInfoFile("ALL"), new Gson().toJson(allProduct), false);

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
