package browsers.impls.yhqAndPic;

import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.impls.manAll.ManManBuyAllModel;
import browsers.interfaces.BrowsersInterface;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

public class ProductPicLoadEndProcess extends ProductPicLoadProcess {
    public ProductPicLoadEndProcess(ProductInfoBean productInfoBean) {
        super(productInfoBean);
    }

    @Override
    public ProductInfoBean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {
        ProductInfoBean process = super.process(event, resultUrl, domDocument, browser);

        BrowserUtils.log("商品图片全部加载完毕");
        FileUtils.writeText(FileUtils.createNewProductInfoFile("ALL"), new Gson().toJson(ManManBuyAllModel.instance().getAllProduct()), false);

        return process;
    }
}
