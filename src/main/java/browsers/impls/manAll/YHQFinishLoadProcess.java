package browsers.impls.manAll;

import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.FinishLoadProcessInterface;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class YHQFinishLoadProcess implements FinishLoadProcessInterface {

    private final static String PROCESS_URL = "https://uland.taobao.com/coupon/";
    private int yhqIndex = 0;
    private int maxYHQCount = 200;
    private LinkedHashSet<ProductInfoBean> yhqTimeRemoveList = new LinkedHashSet<>();

    @Override
    public boolean canProcess(FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {
        return url.startsWith(PROCESS_URL);
    }

    @Override
    public boolean process(String productInfoSavePath, List<ProductInfoBean> productInfoBeans, FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {

        BrowserUtils.log("查找第" + yhqIndex + "个商品优惠券信息");
        ProductInfoBean productInfoBean = productInfoBeans.get(yhqIndex);

        if (domDocument.findElement(By.className("coupons-data")) != null) {
            DOMElement element1 = domDocument.findElement(By.className("coupons-data"));
            String endTime = element1.getInnerText().split("-")[1];
            long innerText = BrowserUtils.timeToStamp(endTime);              // 优惠券到期时间

            // TODO: 2019-08-17 这部分的超时过滤太夸张
            if (innerText > BrowserUtils.timeTomorrowBegin()) {
                DOMElement element = domDocument.findElement(By.className("item-wrap"));
                DOMElement a = element.findElement(By.tagName("a"));                                    // 淘宝链接
                String href = a.getAttribute("href");
                String name = a.findElement(By.className("title")).getInnerText().replace("\n", "");
                name = BrowserUtils.formatProductName(name);

                productInfoBean.setYhqEndTime(innerText);
                productInfoBean.setBuyUrl(href);
                productInfoBean.setTags(BrowserUtils.findFengCi(name));
                productInfoBean.setProductName(name);

                BrowserUtils.log("有效优惠券信息：" + name + "_" + endTime);
            } else {
                BrowserUtils.log("优惠券超时:" + productInfoBean.getProductInfo() + endTime);
                yhqTimeRemoveList.add(productInfoBean);
                ++maxYHQCount;
            }

        } else {
            BrowserUtils.log("未查询到优惠券信息" + productInfoBean.getProductInfo());
            yhqTimeRemoveList.add(productInfoBean);
            ++maxYHQCount;
        }

        if (++yhqIndex < maxYHQCount && yhqIndex < productInfoBeans.size()) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            browser.loadURL(productInfoBeans.get(yhqIndex).getYhqUrl());

        } else {

            BrowserUtils.logLine();
            BrowserUtils.log("全部优惠券信息查询完成");
            BrowserUtils.log("异常优惠券" + yhqTimeRemoveList.size() + "条");
            BrowserUtils.logLine();

            List<ProductInfoBean> productInfoBeansTemp = new ArrayList<>(productInfoBeans);
            productInfoBeansTemp.removeAll(yhqTimeRemoveList);
            productInfoBeans.clear();
            productInfoBeans.addAll(productInfoBeansTemp.subList(0, productInfoBeansTemp.size() < 200 ? productInfoBeansTemp.size() : 200));

            FileUtils.fileLinesWrite(productInfoSavePath, new Gson().toJson(productInfoBeans), false);

            browser.loadURL(productInfoBeans.get(0).getBuyUrl());
        }

        return true;
    }

    @Override
    public boolean productIsLoadComplete() {
        return false;
    }

}
