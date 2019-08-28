package browsers.interfaces;

import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

public interface MBrowserLoadListener {

    boolean onFinishLoadingFrame(FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser);

}
