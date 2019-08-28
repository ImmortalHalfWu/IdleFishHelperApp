package browsers.impls;

import browsers.BrowserUtils;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.MBrowserLoadListener;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MyBrowserLoadListener extends LoadAdapter {

    private final List<MBrowserLoadListener> processInterfaces = new ArrayList<>();
    private Browser browser;

    public MyBrowserLoadListener(Browser browser) {
        this.browser = browser;
    }

    @Override
    public void onFinishLoadingFrame(FinishLoadingEvent finishLoadingEvent) {
        String validatedURL = finishLoadingEvent.getValidatedURL();
        BrowserUtils.log( "页面加载完成" + validatedURL);
        DOMDocument document = browser.getDocument();
        BrowserUtils.log( "开始分发页面数据" + validatedURL);
        for (MBrowserLoadListener process :
                processInterfaces) {

            try {

                if (process.onFinishLoadingFrame(finishLoadingEvent, validatedURL, document, browsersInterface)) {
                    return;
                }

            } catch (Exception e) {
                e.printStackTrace();
                BrowserUtils.logErroLine( "分发页面数据异常！！！！！！！" + validatedURL);
            }
        }
//        BrowserUtils.logErroLine();
//        BrowserUtils.log( "未找到页面处理器" + validatedURL);
//        BrowserUtils.logErroLine();

    }

    public void registerBrowserLoadListener(MBrowserLoadListener browserLoadListener) {
        if (browserLoadListener != null && !processInterfaces.contains(browserLoadListener)) {
            processInterfaces.add(browserLoadListener);
        }
    }

    private BrowsersInterface browsersInterface = new BrowsersInterface() {
        @Override
        public void loadURL(String url) {
            if (browser != null) {
                browser.loadURL(url);
            }
        }

        @Override
        public DOMDocument getDOMDocument() {
            return browser.getDocument();
        }

        @Override
        public void executeJavaScriptAndReturnValue(String js) {
            browser.executeJavaScriptAndReturnValue(js);
        }

        @Override
        public void goBack() {
            browser.goBack();
        }
    };

}