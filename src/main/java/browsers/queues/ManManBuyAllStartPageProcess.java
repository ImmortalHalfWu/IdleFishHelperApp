package browsers.queues;

import browsers.BrowserUtils;
import browsers.interfaces.BrowsersInterface;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

public class ManManBuyAllStartPageProcess implements NewLoadHtmlRequestQueue.LoadHtmlProcess {

    @Override
    public String getUrl() {
        return "http://baicai.manmanbuy.com/";
    }

    @Override
    public boolean canProcess(String url) {
        return url.equals("http://baicai.manmanbuy.com/");
    }

    @Override
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {

        DOMElement ctl00_contentPlaceHolder1_divpage = domDocument.findElement(By.id("ctl00_ContentPlaceHolder1_divpage"));
        String innerText = ctl00_contentPlaceHolder1_divpage.getInnerText();
        String pageString = innerText.split("/")[1].split("页")[0];
        int maxPageCount = Integer.parseInt(pageString);
        BrowserUtils.log(maxPageCount);

        ManManBuyModel.instance().setMaxProductCount(maxPageCount);

        for (int i = 1; i < maxPageCount; i++) {
            browser.loadURL(new ManManBuyAllPageProcess(i));
        }

        browser.loadURL(new ManManBuyAllEndPageProcess(maxPageCount));
        return true;

    }
}
