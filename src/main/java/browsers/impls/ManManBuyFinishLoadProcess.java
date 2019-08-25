package browsers.impls;

import Utils.FileUtils;
import Utils.MLog;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.FinishLoadProcessInteface;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import static com.teamdev.jxbrowser.chromium.dom.DOMNodeType.ElementNode;

public class ManManBuyFinishLoadProcess implements FinishLoadProcessInteface {

    private final static String PROCESS_URL_START = "http://baicai.manmanbuy.com/";
    private final static String PROCESS_URL = PROCESS_URL_START + "Default.aspx?PageID=";

    private LinkedHashSet<ProductInfoBean> productInfoBeanLinkedHashSet = new LinkedHashSet<>();
    private int manManBuyLoadIndex = 0;

    @Override
    public boolean htmlLoadingOverCanProcess(FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {
        return url.startsWith(PROCESS_URL_START);
    }

    @Override
    public boolean htmlLoadingOverProcess(List<ProductInfoBean> productInfoBeans,
                                          FinishLoadingEvent event,
                                          String url,
                                          DOMDocument domDocument,
                                          BrowsersInterface browser) {

        if (url.equals(PROCESS_URL_START)) {
            loadNextWebPage(browser);
            return true;
        }

        DOMElement container = domDocument.findElement(By.id("container"));

        if (container.getInnerText().contains("没有搜到您要的商品")) {
            productInfoBeans.clear();
            productInfoBeans.addAll(productInfoBeanLinkedHashSet);
            log(productInfoBeans);
            log("over=========================================================");
            log("over=========================================================");
            log("over=========================================================");
            sortProductForPriceAndOrderNum(productInfoBeans);
            log(productInfoBeans);

            FileUtils.fileLinesWrite(FileUtils.FILE_PATH_JSON, new Gson().toJson(productInfoBeans), false);

            browser.loadURL(productInfoBeans.get(0).getYhqUrl());

            return true;
        }

        List<DOMNode> children = container.getChildren();
        for (DOMNode node :
                children) {

            if (node.getNodeType() == ElementNode) {
                DOMElement picElement = node.findElement(By.className("pic"));
                DOMElement imgElement = picElement.findElement(By.tagName("img"));
                String imgSrcUrl = "";
                String yhqUrl = "";
                String productInfo = "";
                String priceCurrent = "";
                String priceOld = "";
                String orderNum = "";

                try {

                    // ============================商品图像======================================
//                        if (imgElement.hasAttribute("src")) {
//                            imgSrcUrl = imgElement.getAttributes().get("src");                           // 商品图像
//                            if (!imgSrcUrl.startsWith("https:")) {
//                                imgSrcUrl = "https:" + imgSrcUrl;
//                            }
//                        }


                    // ============================优惠券链接======================================
                    DOMElement goBuyElement = node.findElement(By.className("gobuy"));
                    DOMElement goBuyAElement = goBuyElement.findElement(By.tagName("a"));
                    if ((yhqUrl = goBuyAElement.getAttributes().get("href")).contains("ProductDetail.aspx")) {
                        throw new IllegalArgumentException("优惠卷URL错误, 放弃此商品" + node.toString());
                    }


                    // ============================商品名称======================================
                    DOMElement titElement = node.findElement(By.className("tit"));
                    DOMElement titAElement = titElement.findElement(By.tagName("a"));
                    productInfo = BrowserUtils.formatProductName(titAElement.getInnerText());



                    // ============================两种价格======================================
                    try {
//
                        DOMElement priceCurrentElement = node.findElement(By.className("price-current"));
                        priceCurrentElement = priceCurrentElement == null ? node.findElement(By.className("price-current-hui")) : priceCurrentElement;
                        priceCurrent = priceCurrentElement.getInnerText().replace("¥", "").replace("券后价", "");   // 劵后价
                        priceOld = node.findElement(By.className("price-old")).getInnerText().replace("¥", "");           // 原价
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                    // ============================销量======================================
                    DOMElement yhqleft2 = node.findElement(By.className("yhqleft2"));
                    if (yhqleft2 == null) {
                        orderNum = "10";
                    } else {
                        orderNum = yhqleft2.getInnerText().replace("月销", "").replace("笔", "");           // 销量
                    }

                    ProductInfoBean productInfoBean = new ProductInfoBean();
                    productInfoBean.setYhqUrl(yhqUrl);
//                        productInfoBean.addImgSrcUrl(imgSrcUrl);
                    productInfoBean.setOrderNum(Integer.parseInt(orderNum));
                    productInfoBean.setPriceCurrent(Double.parseDouble(priceCurrent));
                    productInfoBean.setPriceOld(Double.parseDouble(priceOld));
                    productInfoBean.setProductInfo("【全新包邮】" + productInfo);
                    productInfoBeanLinkedHashSet.add(productInfoBean);
                } catch (Exception e) {
                    e.printStackTrace();
                    log(imgSrcUrl +"\t" + "\t"+ yhqUrl +"\t" + "\t"+ productInfo +"\t" + "\t"+ priceCurrent +"\t" + "\t"+ priceOld +"\t" + "\t"+ orderNum);
                }

            }
        }

        loadNextWebPage(browser);

        return true;
    }

    private void loadNextWebPage(BrowsersInterface browser) {
        browser.loadURL(PROCESS_URL + ++manManBuyLoadIndex);
    }

    private void sortProductForPriceAndOrderNum(List<ProductInfoBean> productInfoBeans) {
        BrowserUtils.sortProductForPriceAndOrderNum(productInfoBeans);
    }


    private static void log(Object info) {
        BrowserUtils.log(info);
    }
}
