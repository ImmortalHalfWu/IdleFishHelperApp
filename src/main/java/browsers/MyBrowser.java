package browsers;

import browsers.impls.MyBrowserLoadListener;
import browsers.interfaces.MBrowserLoadListener;
import com.teamdev.jxbrowser.chromium.*;

public class MyBrowser {

    private static MyBrowser myBrowser;

    public static MyBrowser instance() {
        if (myBrowser == null) {
            synchronized (MyBrowser.class) {
                if (myBrowser == null) {
                    myBrowser = new MyBrowser();
                }
            }
        }
        return myBrowser;
    }

    private MyBrowser() {
        BrowserUtils.logLine();
        BrowserUtils.log("MyBrowser 初始化.......");
        browser = new Browser();
        myBrowserLoadListener = new MyBrowserLoadListener(browser);
        browser.setPopupHandler(popupParams -> {
            browser.loadURL(popupParams.getURL());
            return null;
        });
        configBrowserView();
        BrowserUtils.log("MyBrowser 初始化完成");
        BrowserUtils.logLine();
    }

    private Browser browser;
    private MyBrowserLoadListener myBrowserLoadListener;

    public Browser getBrowser() {
        return browser;
    }

    public MyBrowser registerBrowserLoadListener(MBrowserLoadListener browserLoadListener) {
        if (myBrowserLoadListener != null) {
            myBrowserLoadListener.registerBrowserLoadListener(browserLoadListener);
        }
        return this;
    }

    private void configBrowserView() {

        // 全网过滤
//        browser.getContext().getNetworkService().setNetworkDelegate(new DefaultNetworkDelegate(){
//            @Override
//            public void onBeforeURLRequest(BeforeURLRequestParams params) {
//                super.onBeforeURLRequest(params);
//                if (params.getURL().equals("http://baicai.manmanbuy.com/")) {
////                    log("Before：" + params.getURL());
//                }
//            }
//
//            @Override
//            public void onDataReceived(DataReceivedParams params) {
//                super.onDataReceived(params);
//                if (params.getURL().startsWith("http://baicai.manmanbuy.com/") && params.getMimeType().equals("text/html")) {
////                    log(request.get(params.getURL()));
//                }
//            }
//
//            @Override
//            public void onResponseStarted(ResponseStartedParams params) {
//                super.onResponseStarted(params);
//            }
//
//            @Override
//            public void onCompleted(RequestCompletedParams params) {
//                super.onCompleted(params);
////                log(params);
//            }
//
//            @Override
//            public void onDestroyed(RequestParams params) {
//                super.onDestroyed(params);
//            }
//        });
        // 网页加载进度
        browser.addLoadListener(myBrowserLoadListener);
    }

}
