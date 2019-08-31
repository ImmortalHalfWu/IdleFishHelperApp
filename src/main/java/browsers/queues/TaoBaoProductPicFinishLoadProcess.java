package browsers.queues;

import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.ProductPicProcessInterface;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.List;

public class TaoBaoProductPicFinishLoadProcess implements ProductPicProcessInterface {
    @Override
    public boolean htmlLoadingOverCanProcess(FinishLoadingEvent event, String validatedURL, DOMDocument domDocument, BrowsersInterface browser) {
        return  (validatedURL.startsWith("https://item.taobao.com/item.htm"));
    }

    @Override
    public boolean process(ProductInfoBean productInfoBean, FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {
        // content ke-post  详情图
        // J_AttrUL  产品参数，用于标签
        DOMElement documentElement = domDocument.getDocumentElement();

        // 详情图
        DOMElement element = documentElement.findElement(By.id("J_DivItemDesc")).findElement(By.tagName("p"));
        List<DOMElement> imgs = element.findElements(By.tagName("img"));
        productInfoBean.setImgSrcUrls(BrowserUtils.findTmailTaoBaoProductImg(imgs));
        BrowserUtils.log("淘宝查找图片完成 ： " + productInfoBean.getImgSrcUrls().size());
        // 产品标签
//            DOMElement j_attrUL = documentElement.findElement(By.className("attributes-list"));
//            List<DOMElement> lis = j_attrUL.findElements(By.tagName("li"));
//            for (DOMElement domElement : lis) {
//                if (domElement.hasAttribute("title")) {
//                    String title = domElement.getAttribute("title");
//                    if (title.indexOf("&nbsp;") == title.lastIndexOf("&nbsp;")) {
//                        productInfoBean.addTag(title.replace("&nbsp;", ""));
//                    }
//                }
//            }
        return true;
    }

    @Override
    public boolean canFindPicElement(DOMDocument domDocument) {
        DOMElement contentElement = domDocument.findElement(By.id("J_DivItemDesc")).findElement(By.tagName("p"));
        return contentElement.getChildren().size() == 0;
    }
}
