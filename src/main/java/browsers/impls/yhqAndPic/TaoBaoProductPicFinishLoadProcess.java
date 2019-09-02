package browsers.impls.yhqAndPic;

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

        // 详情图
        DOMElement j_divItemDesc = domDocument.findElement(By.id("J_DivItemDesc"));
        List<DOMElement> imgs1 = j_divItemDesc.findElements(By.tagName("img"));

        DOMElement p_Element = j_divItemDesc.findElement(By.tagName("p"));
        List<DOMElement> imgs2 = p_Element.findElements(By.tagName("img"));

        List<String> tmailTaoBaoProductImg = BrowserUtils.findTmailTaoBaoProductImg(imgs1.size() > imgs2.size() ? imgs1 : imgs2);

        productInfoBean.setImgSrcUrls(tmailTaoBaoProductImg);
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
        DOMElement j_divItemDesc = domDocument.findElement(By.id("J_DivItemDesc"));
        DOMElement p_Element = domDocument.findElement(By.id("J_DivItemDesc")).findElement(By.tagName("p"));
        return p_Element.getChildren().size() > 0 || j_divItemDesc.findElements(By.tagName("img")).size() > 0;
    }
}
