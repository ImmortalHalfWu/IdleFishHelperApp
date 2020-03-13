package browsers.impls.man.manAll;

import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.impls.man.ManManBuyCallBack;
import browsers.impls.yhqAndPic.TaoBaoTmallYHQImagManager;
import browsers.impls.yhqAndPic.TaoBaoTmallYHQImagProcess;
import browsers.interfaces.BrowsersInterface;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.List;

class ManManBuyAllEndPageProcess extends ManManBuyAllPageProcess implements TaoBaoTmallYHQImagProcess.TaoBaoTmallYHQImagListener {

    private final static String FILE_SAVE_PATH = FileUtils.createJsonFile("ALLProduct");

    ManManBuyAllEndPageProcess(int page, ManManBuyCallBack callBack) {
        super(page, callBack);
    }

    @Override
    protected boolean process(ManManBuyCallBack callBack, FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {

        boolean process = super.process(callBack, event, resultUrl, domDocument, browser);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int maxProductCount = ManManBuyAllModel.instance().getMaxProductCount();
        int productCurrentCount = ManManBuyAllModel.instance().getProductCurrentCount();
        BrowserUtils.log("慢慢买主页爬取完，目前数量：" + productCurrentCount + "——总量：" + maxProductCount);


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

        FileUtils.writeText(FILE_SAVE_PATH, new Gson().toJson(ManManBuyAllModel.instance().getAllProduct()), false);

        TaoBaoTmallYHQImagManager.getInstance()
                .startFindYHQAndPIC(
                        ManManBuyAllModel.instance().getAllProduct(),
                        "ALLProduct",
                        this);



        return process;
    }

    @Override
    public void loadOver(List<ProductInfoBean> allProduct, Object tag) {
        FileUtils.writeText(FILE_SAVE_PATH, new Gson().toJson(allProduct), false);
        BrowserUtils.log(tag + ":" + allProduct.size());
        BrowserUtils.log(allProduct);
        if (callBack != null) {
            callBack.dataSuc(allProduct, FILE_SAVE_PATH);
        }
    }

}
