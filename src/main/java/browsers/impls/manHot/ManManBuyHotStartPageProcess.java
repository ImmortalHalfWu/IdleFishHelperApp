package browsers.impls.manHot;

import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.impls.manAll.ManManBuyAllModel;
import browsers.impls.yhqAndPic.TaoBaoTmallYHQImagManager;
import browsers.impls.yhqAndPic.TaoBaoTmallYHQImagProcess;
import browsers.interfaces.BrowsersInterface;
import browsers.queues.NewLoadHtmlRequestQueue;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.ArrayList;
import java.util.List;


public class ManManBuyHotStartPageProcess implements NewLoadHtmlRequestQueue.LoadHtmlProcess  {
    @Override
    public String getUrl() {
        return "http://baicai.manmanbuy.com/hot.aspx";
    }

    @Override
    public boolean canProcess(String url) {
        return url.equals("http://baicai.manmanbuy.com/hot.aspx");
    }

    @Override
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {

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

        FileUtils.writeText(FileUtils.createNewProductInfoFile("Hot"), new Gson().toJson(productInfoBeans), false);

        BrowserUtils.log("ManManBuy热卖商品抓取完成：" + productInfoBeans.size());
        BrowserUtils.log("开始抓取优惠券数据：");

        TaoBaoTmallYHQImagManager.getInstance().startFindYHQAndPIC(productInfoBeans, "HOT", new TaoBaoTmallYHQImagProcess.TaoBaoTmallYHQImagListener() {
            @Override
            public void loadOver(List<ProductInfoBean> allProduct, Object tag) {
                BrowserUtils.log(tag + "ManManBuy热卖商品优惠券数据抓取完成：" + allProduct.size());
                FileUtils.writeText(FileUtils.createNewProductInfoFile("Hot"), new Gson().toJson(allProduct), false);
            }
        });

        return false;
    }
}
