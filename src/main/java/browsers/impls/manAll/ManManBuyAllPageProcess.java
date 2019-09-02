package browsers.impls.manAll;

import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.queues.NewLoadHtmlRequestQueue;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.teamdev.jxbrowser.chromium.dom.DOMNodeType.ElementNode;

public class ManManBuyAllPageProcess implements NewLoadHtmlRequestQueue.LoadHtmlProcess {

    private Set<ProductInfoBean> productInfoBeanLinkedHashSet;
    private final static String PROCESS_URL = "http://baicai.manmanbuy.com/Default.aspx?PageID=";
    private String mUrl;
    private int page;
    private static int all;

    public ManManBuyAllPageProcess(int page) {
        this.page = page;
        this.mUrl = PROCESS_URL + page;
        productInfoBeanLinkedHashSet = new HashSet<>();
    }

    @Override
    public boolean canProcess(String url) {
        return mUrl.equals(url);
    }

    @Override
    public String getUrl() {
        return mUrl;
    }

    @Override
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {
        BrowserUtils.log(page);

        DOMElement container = domDocument.findElement(By.id("container"));

        List<DOMNode> children = container.getChildren();
        for (DOMNode node :
                children) {

            if (node.getNodeType() == ElementNode) {
//                DOMElement picElement = node.findElement(By.className("pic"));
//                DOMElement imgElement = picElement.findElement(By.tagName("img"));
                String imgSrcUrl = "";
                String yhqUrl;
                String productInfo;
                String priceCurrent = "";
                String priceOld = "";
                String orderNum;

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
                        BrowserUtils.logErroLine();
//                        BrowserUtils.log("优惠卷URL错误, 放弃此商品" + node.getTextContent());
                        BrowserUtils.logErroLine();
                        continue;
                    }


                    // ============================商品名称======================================
                    DOMElement titElement = node.findElement(By.className("tit"));
                    DOMElement titAElement = titElement.findElement(By.tagName("a"));
                    productInfo = BrowserUtils.formatProductName(titAElement.getInnerText());



                    // ============================两种价格======================================

                    DOMElement priceCurrentElement = node.findElement(By.className("price-current"));
                    priceCurrentElement = priceCurrentElement == null ? node.findElement(By.className("price-current-hui")) : priceCurrentElement;
                    priceCurrent = priceCurrentElement.getInnerText().replace("¥", "").replace("券后价", "");   // 劵后价
                    priceOld = node.findElement(By.className("price-old")).getInnerText().replace("¥", "");           // 原价


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
                    productInfoBean.setProductInfo("  [全新包邮]  " + productInfo);
//                    BrowserUtils.log(productInfoBean.getProductInfo());
                    productInfoBeanLinkedHashSet.add(productInfoBean);


                } catch (Exception e) {
                    BrowserUtils.logErroLine();
                    BrowserUtils.log("查找商品数据异常, 放弃此商品 ： " + node.getTextContent());
                    e.printStackTrace();
                    BrowserUtils.logErroLine();
                }

            }
        }
        BrowserUtils.log(page + "over" + ++all);
        ManManBuyAllModel.instance().addManManBuyProducts(productInfoBeanLinkedHashSet);

        return true;
    }
}
