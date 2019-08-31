package browsers.queues;

import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.List;

public class YHQLoadProcess implements NewLoadHtmlRequestQueue.LoadHtmlProcess {

    private volatile static int loadIndex = 0;
    private volatile static int overLoadIndex = 0;
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
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {
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
                    loadIndex++;
                    BrowserUtils.log("有效优惠券信息：" + name + "_" + endTime);
                } else {
                    BrowserUtils.log(++overLoadIndex + "优惠券今天过期:" + productInfoBean.getProductInfo() + endTime);
                    ManManBuyModel.instance().removeProduct(productInfoBean);
                    productInfoBean = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            BrowserUtils.log(++overLoadIndex + "未查询到优惠券信息" + productInfoBean.getProductInfo());
            try {
                if (domDocument.getDocumentElement().getTextContent().contains("滑动一下马上回来")) {
                    BrowserUtils.log("被检测到刷数据， 暂停1分钟, 队列等待：" + NewLoadHtmlRequestQueue.instance().size());
                    Thread.sleep(30000);
                    overLoadIndex--;
                    BrowserUtils.log("1分钟完成，继续");
                } else {
                    ManManBuyModel.instance().removeProduct(productInfoBean);
                    productInfoBean = null;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        loadNext(browser);

        return true;
    }

    private void loadNext(BrowsersInterface browser) {
        if (loadIndex >=  ManManBuyModel.instance().getProductCurrentCount()) {
            BrowserUtils.log("优惠券已请求完成，有效：" + loadIndex);
            if (productInfoBean != null) {
                browser.loadURL(new ProductPicLoadEndProcess(productInfoBean));
            } else {
                FileUtils.writeText(FileUtils.createNewProductInfoFile("123"), new Gson().toJson(ManManBuyModel.instance().getAllProduct()), false);
            }
            return;
        }

        if (productInfoBean != null) {
            browser.loadURL(new ProductPicLoadProcess(productInfoBean));
        }

        List<ProductInfoBean> allProduct = ManManBuyModel.instance().getAllProduct();


        if (loadIndex < allProduct.size()) {
            ProductInfoBean productInfoBean = ManManBuyModel.instance().getAllProduct().get(loadIndex);

            try {
                Thread.sleep(loadIndex < 11 ? 1500 : 1000 - loadIndex);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            browser.loadURL(new YHQLoadProcess(productInfoBean));

        } else {
            ProductInfoBean productInfoBean = ManManBuyModel.instance().getAllProduct().get(loadIndex);
//            browser.loadURL(new ProductPicLoadEndProcess(productInfoBean));

            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            browser.loadURL(new YHQLoadEndProcess(productInfoBean));

        }
    }

    @Override
    public String toString() {
        return "YHQLoadProcess{" +
                "url='" + url + '\'' +
                ", productInfoBean=" + productInfoBean +
                '}';
    }
}
