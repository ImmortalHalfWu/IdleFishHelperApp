package browsers.impls;

import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.FinishLoadProcessInteface;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.*;

import java.util.ArrayList;
import java.util.List;

public class MyBrowserLoadListener implements LoadListener {

    private List<FinishLoadProcessInteface> processInterfaces;
    private Browser browser;
    private List<ProductInfoBean> productInfoBeans;

    public MyBrowserLoadListener(Browser browser) {
        this.browser = browser;
        this.productInfoBeans = new ArrayList<>();
        this.processInterfaces = new ArrayList<>(5);
        processInterfaces.add(new YHQFinishLoadProcess());
        processInterfaces.add(new ProductPicFinishLoadProcess());
        processInterfaces.add(new ManManBuyFinishLoadProcess());
        processInterfaces.add(new LoginFinishLoadProcess());
        processInterfaces.add(new AiTaoBaoFinishLoadProcess());
    }


    @Override
    public void onStartLoadingFrame(StartLoadingEvent startLoadingEvent) {

    }

    @Override
    public void onProvisionalLoadingFrame(ProvisionalLoadingEvent provisionalLoadingEvent) {

    }

    @Override
    public void onFinishLoadingFrame(FinishLoadingEvent finishLoadingEvent) {
        String validatedURL = finishLoadingEvent.getValidatedURL();
        BrowserUtils.log( "页面加载完成" + validatedURL);
        DOMDocument document = browser.getDocument();

        for (FinishLoadProcessInteface process :
                processInterfaces) {
            if (process.canProcess(finishLoadingEvent, validatedURL, document, browsersInterface) &&
                process.process(productInfoBeans, finishLoadingEvent, validatedURL, document, browsersInterface)) {
                BrowserUtils.log(process.getClass().getSimpleName() + "页面处理完成");
                return;
            }
        }

//        BrowserUtils.logErroLine();
//        BrowserUtils.log( "未找到页面处理器" + validatedURL);
//        BrowserUtils.logErroLine();

    }

    @Override
    public void onFailLoadingFrame(FailLoadingEvent failLoadingEvent) {

    }

    @Override
    public void onDocumentLoadedInFrame(FrameLoadEvent frameLoadEvent) {

    }

    @Override
    public void onDocumentLoadedInMainFrame(LoadEvent loadEvent) {

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
