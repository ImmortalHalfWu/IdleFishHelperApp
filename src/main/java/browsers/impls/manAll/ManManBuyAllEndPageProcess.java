package browsers.impls.manAll;

import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.impls.yhqAndPic.TaoBaoTmallYHQImagManager;
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

        int maxProductCount = ManManBuyAllModel.instance().getMaxProductCount();
        int productCurrentCount = ManManBuyAllModel.instance().getProductCurrentCount();
        BrowserUtils.log("慢慢买主页爬取完，目前数量：" + productCurrentCount + "——总量：" + maxProductCount);

//        if (maxProductCount != productCurrentCount) {11:36:54.440
//            return process;
//        }

        if (productCurrentCount <= 0) {
            return process;
        }

        List<ProductInfoBean> allProduct = ManManBuyAllModel.instance().getAllProduct();

        BrowserUtils.sortProductForPriceAndOrderNum(allProduct);

        if (productCurrentCount > 300) {
            List<ProductInfoBean> productInfoBeans = allProduct.subList(300, productCurrentCount);
            ManManBuyAllModel.instance().removeAllProduct(productInfoBeans);
        }
        BrowserUtils.log(allProduct.toString());

        FileUtils.writeText(FileUtils.createNewProductInfoFile("123"), new Gson().toJson(ManManBuyAllModel.instance().getAllProduct()), false);

        TaoBaoTmallYHQImagManager.getInstance()
                .startFindYHQAndPIC(
                        ManManBuyAllModel.instance().getAllProduct(),
                        "ALLProduct",
                        (allProduct1, tag) -> {
                            FileUtils.writeText(FileUtils.createNewProductInfoFile("123"), new Gson().toJson(allProduct1), false);
                            BrowserUtils.log(tag + ":" + allProduct1.size());
                            BrowserUtils.log(allProduct1);
                        });


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
