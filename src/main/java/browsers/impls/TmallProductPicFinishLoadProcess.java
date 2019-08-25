package browsers.impls;

import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.ProductPicProcessInterface;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.List;

public class TmallProductPicFinishLoadProcess implements ProductPicProcessInterface {
    @Override
    public boolean htmlLoadingOverCanProcess(FinishLoadingEvent event, String validatedURL, DOMDocument domDocument, BrowsersInterface browser) {
        return  (validatedURL.startsWith("https://detail.tmall.com") || validatedURL.startsWith("https://detail.yao.95095.com"));
    }

    @Override
    public boolean process(ProductInfoBean productInfoBean, FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {
        // content ke-post  详情图
        // J_AttrUL  产品参数，用于标签
        DOMElement documentElement = domDocument.getDocumentElement();

        // 详情图
        DOMElement element = documentElement.findElement(By.className("content ke-post"));
        List<DOMElement> imgs = element.findElements(By.tagName("img"));
        productInfoBean.setImgSrcUrls(BrowserUtils.findTmailTaoBaoProductImg(imgs));
        BrowserUtils.log("天猫查找图片完成 ： " + productInfoBean.getImgSrcUrls());
        // 产品标签
//            DOMElement j_attrUL = documentElement.findElement(By.id("J_AttrUL"));
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
        return domDocument.findElement(By.className("content ke-post")).getInnerText().contains("描述加载");
    }
}
