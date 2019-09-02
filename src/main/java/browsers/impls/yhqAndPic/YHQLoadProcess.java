package browsers.impls.yhqAndPic;

import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.exceptions.ManLoadHtmlException;
import browsers.impls.manAll.ManManBuyAllModel;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.ManManBuyLoadHtmlProcess;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;


public class YHQLoadProcess implements ManManBuyLoadHtmlProcess {

    private String url;
    private ProductInfoBean productInfoBean;

    public YHQLoadProcess(ProductInfoBean productInfoBeans) {
        this.url = productInfoBeans.getYhqUrl();
        this.productInfoBean = productInfoBeans;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public boolean canProcess(String url) {
        return url.equals(this.url);
    }

    @Override
    public ProductInfoBean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) throws ManLoadHtmlException {
        BrowserUtils.log(resultUrl);

        DOMElement itemRoot;
        if (    ((itemRoot = domDocument.findElement(By.className("item-wrap"))) != null ||
                (itemRoot = domDocument.findElement(By.className("item-info-con"))) != null) &&
                !domDocument.getDocumentElement().getInnerHTML().contains("优惠券已失效") &&
                !domDocument.getDocumentElement().getInnerHTML().contains("宝贝不见了")) {
            try {

                DOMElement element1 = domDocument.findElement(By.className("coupon-date"));
                element1 = element1 == null ? domDocument.findElement(By.className("coupons-data")) : element1;
                String endTime = element1.getInnerText().split("-")[1];
                long innerText = BrowserUtils.timeToStamp(endTime);              // 优惠券到期时间
                
                // TODO: 2019-08-17 这部分的超时过滤太夸张
                if (innerText >= BrowserUtils.timeTomorrowBegin()) {
                    // item-wrap
                    DOMElement a = itemRoot.findElement(By.tagName("a"));
                    String href = a.getAttribute("href");
                    String name = a.findElement(By.className("title")).getTextContent().replace(" ", "").replace("\n", "");
                    name = BrowserUtils.formatProductName(name);

                    productInfoBean.setYhqEndTime(innerText);
                    productInfoBean.setBuyUrl(href);
                    productInfoBean.setTags(BrowserUtils.findFengCi(name));
                    productInfoBean.setProductName(name);
                    BrowserUtils.log("有效优惠券信息：" + name + "_" + endTime);
                } else {
                    BrowserUtils.log("优惠券今天过期:" + productInfoBean.getProductInfo() + endTime);
                    ManManBuyAllModel.instance().removeProduct(productInfoBean);
                    throw ManLoadHtmlException.newInstance(ManLoadHtmlException.CODE_YHQ_OVER_TIME);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw ManLoadHtmlException.newInstance(ManLoadHtmlException.CODE_CRASH);
            }

        } else {
            BrowserUtils.log("未查询到优惠券信息" + productInfoBean.getProductInfo());
            if (domDocument.getDocumentElement().getTextContent().contains("滑动一下马上回来")) {
//                    BrowserUtils.log("被检测到刷数据， 暂停1分钟, 队列等待：" + NewLoadHtmlRequestQueue.instance().size());
//                    Thread.sleep(30000);
//                    BrowserUtils.log("1分钟完成，继续");
                throw ManLoadHtmlException.newInstance(ManLoadHtmlException.CODE_SHIELD);
            } else {
                throw ManLoadHtmlException.newInstance(ManLoadHtmlException.CODE_NOT_FOUND);
            }

        }

//        loadNext(browser);

        return productInfoBean;
    }

//    private void loadNext(BrowsersInterface browser) {
//        if (loadIndex >=  ManManBuyAllModel.instance().getProductCurrentCount()) {
//            BrowserUtils.log("优惠券已请求完成，有效：" + loadIndex);
//            if (productInfoBean != null) {
//                browser.loadURL(new ProductPicLoadEndProcess(productInfoBean));
//            } else {
//                FileUtils.writeText(FileUtils.createNewProductInfoFile("123"), new Gson().toJson(ManManBuyAllModel.instance().getAllProduct()), false);
//            }
//            return;
//        }
//
//        if (productInfoBean != null) {
//            browser.loadURL(new ProductPicLoadProcess(productInfoBean));
//        }
//
//        List<ProductInfoBean> allProduct = ManManBuyAllModel.instance().getAllProduct();
//
//
//        if (loadIndex < allProduct.size()) {
//            ProductInfoBean productInfoBean = ManManBuyAllModel.instance().getAllProduct().get(loadIndex);
//
//            try {
//                Thread.sleep(loadIndex < 11 ? 1500 : 1000 - loadIndex);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            browser.loadURL(new YHQLoadProcess(productInfoBean));
//
//        } else {
//            ProductInfoBean productInfoBean = ManManBuyAllModel.instance().getAllProduct().get(loadIndex);
////            browser.loadURL(new ProductPicLoadEndProcess(productInfoBean));
//
//            try {
//                Thread.sleep(1500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            browser.loadURL(new YHQLoadEndProcess(productInfoBean));
//
//        }
//    }

    @Override
    public String toString() {
        return "YHQLoadProcess{" +
                "url='" + url + '\'' +
                ", productInfoBean=" + productInfoBean +
                '}';
    }
}
