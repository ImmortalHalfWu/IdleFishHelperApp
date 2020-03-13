package browsers.impls.man;

import browsers.interfaces.BrowsersInterface;
import browsers.queues.NewLoadHtmlRequestQueue;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

public abstract class ManManBuyBaseProcess  implements NewLoadHtmlRequestQueue.LoadHtmlProcess  {

    protected ManManBuyCallBack callBack;

    public ManManBuyBaseProcess(ManManBuyCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {
        if (callBack != null) {
            return process(callBack, event, resultUrl, domDocument, browser);
        }
        return false;
    }

    protected abstract boolean process(ManManBuyCallBack callBack, FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser);
}
