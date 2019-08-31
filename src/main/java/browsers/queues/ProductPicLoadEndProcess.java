package browsers.queues;

import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

public class ProductPicLoadEndProcess extends ProductPicLoadProcess {
    public ProductPicLoadEndProcess(ProductInfoBean productInfoBean) {
        super(productInfoBean);
    }

    @Override
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {
        boolean process = super.process(event, resultUrl, domDocument, browser);

        BrowserUtils.log("商品图片全部加载完毕");
        FileUtils.writeText(FileUtils.createNewProductInfoFile("123"), new Gson().toJson(ManManBuyModel.instance().getAllProduct()), false);

        return process;
    }
}
