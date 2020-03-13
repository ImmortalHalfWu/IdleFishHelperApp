package browsers.impls.man.manHot;

import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.impls.man.ManManBuyBaseProcess;
import browsers.impls.man.ManManBuyCallBack;
import browsers.impls.yhqAndPic.TaoBaoTmallYHQImagManager;
import browsers.impls.yhqAndPic.TaoBaoTmallYHQImagProcess;
import browsers.interfaces.BrowsersInterface;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.ArrayList;
import java.util.List;


public class ManManBuyHotStartPageProcess extends ManManBuyBaseProcess implements TaoBaoTmallYHQImagProcess.TaoBaoTmallYHQImagListener {

    private final static String FILE_SAVE_PATH = FileUtils.createJsonFile("HOT");

    public ManManBuyHotStartPageProcess(ManManBuyCallBack callBack) {
        super(callBack);
    }

    @Override
    public String getUrl() {
        return "http://baicai.manmanbuy.com/hot.aspx";
    }

    @Override
    public boolean canProcess(String url) {
        return url.equals("http://baicai.manmanbuy.com/hot.aspx");
    }

    @Override
    protected boolean process(ManManBuyCallBack callBack, FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {

        List<DOMElement> good_page1 = domDocument.findElements(By.className("good page1"));
        List<ProductInfoBean> productInfoBeans = new ArrayList<>(good_page1.size());

        for (DOMElement node :
                good_page1) {
            ProductInfoBean manManBuyProductInfo = BrowserUtils.findManManBuyProductInfo(node);
            if (manManBuyProductInfo != null) {
                productInfoBeans.add(manManBuyProductInfo);
            }
        }

        BrowserUtils.sortProductForPriceAndOrderNum(productInfoBeans);

        FileUtils.writeText(FILE_SAVE_PATH, new Gson().toJson(productInfoBeans), false);

        BrowserUtils.log("ManManBuy热卖商品抓取完成：" + productInfoBeans.size());
        BrowserUtils.log("开始抓取优惠券数据：");

        TaoBaoTmallYHQImagManager.getInstance().startFindYHQAndPIC(productInfoBeans, "HOT", this);

        return false;
    }

    @Override
    public void loadOver(List<ProductInfoBean> allProduct, Object tag) {
        BrowserUtils.log(tag + "ManManBuy热卖商品优惠券数据抓取完成：" + allProduct.size());
        FileUtils.writeText(FILE_SAVE_PATH, new Gson().toJson(allProduct), false);
        callBack.dataSuc(allProduct, FILE_SAVE_PATH);
    }
}
