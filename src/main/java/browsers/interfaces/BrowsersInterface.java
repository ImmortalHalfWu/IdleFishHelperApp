package browsers.interfaces;


import com.teamdev.jxbrowser.chromium.dom.DOMDocument;

public interface BrowsersInterface {

    void loadURL(String url);
    DOMDocument getDOMDocument();
    void executeJavaScriptAndReturnValue(String js);
    void goBack();

}
