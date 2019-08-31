package browsers.interfaces;


import browsers.queues.NewLoadHtmlRequestQueue;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;

public interface BrowsersInterface {

    void loadURL(NewLoadHtmlRequestQueue.LoadHtmlProcess url);
    DOMDocument getDOMDocument();
    void executeJavaScriptAndReturnValue(String js);
    void goBack();

}
