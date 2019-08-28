package browsers.impls.manAll;

import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.FinishLoadProcessInterface;
import browsers.interfaces.MBrowserLoadListener;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ManManBuyAllProductBaseFinishLoadProcess implements MBrowserLoadListener {

    private static ManManBuyAllProductBaseFinishLoadProcess instance;

    private List<FinishLoadProcessInterface> processInterfaces;
    private String deviceId;
    private String newProductJsonSavePath;
    private List<ProductInfoBean> productInfoBeans;
    private List<ManManBuyAllProductCallBack> callBacks;

    public static ManManBuyAllProductBaseFinishLoadProcess getInstance(String deviceId) {
        if (instance == null) {
            synchronized (ManManBuyAllProductBaseFinishLoadProcess.class) {
                if (instance == null) {
                    instance = new ManManBuyAllProductBaseFinishLoadProcess(deviceId);
                }
            }
        }
        return instance;
    }

    private ManManBuyAllProductBaseFinishLoadProcess(String deviceId) {
        this.deviceId = deviceId;

        callBacks = new ArrayList<>();
        newProductJsonSavePath = FileUtils.createNewProductInfoFile(deviceId);

        this.productInfoBeans = new CopyOnWriteArrayList<>();
        this.processInterfaces = new ArrayList<>(5);
        processInterfaces.add(new YHQFinishLoadProcess());
        processInterfaces.add(new ProductPicFinishLoadProcess());
        processInterfaces.add(new ManManBuyFinishLoadProcess());
        processInterfaces.add(new LoginFinishLoadProcess());
        processInterfaces.add(new AiTaoBaoFinishLoadProcess());
    }

    @Override
    public boolean onFinishLoadingFrame(FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browser) {


            for (FinishLoadProcessInterface finishLoadProcessInterface : processInterfaces) {
                try {

                    if (finishLoadProcessInterface.canProcess(event, url, domDocument, browser) &&
                            finishLoadProcessInterface.process(newProductJsonSavePath, productInfoBeans, event, url, domDocument, browser)) {

                        if (finishLoadProcessInterface.productIsLoadComplete() && productInfoBeans.size() > 0) {
                            BrowserUtils.logLine();
                            BrowserUtils.log("慢慢买的数据抓取完成，设备：" + deviceId);
                            BrowserUtils.log("慢慢买的数据抓取完成，开始分发数据：" + deviceId);
                            BrowserUtils.logLine();
                        }

                        try {
                            for (ManManBuyAllProductCallBack callBack :
                                    callBacks) {
                                callBack.newProductCallBack(productInfoBeans);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            BrowserUtils.logErroLine("慢慢买的数据抓取完成，分发失败" + productInfoBeans);
                        }

                        return true;

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    BrowserUtils.logErroLine("ManManBuyAllProductBaseFinishLoadProcess 分发处理页面数据异常崩溃" + url);
                }
            }

        return false;
    }

    public ManManBuyAllProductBaseFinishLoadProcess regiterNewProductCallBack(ManManBuyAllProductCallBack callBack) {
        if (callBack != null) {
            callBacks.add(callBack);
        }
        return this;
    }

    interface ManManBuyAllProductCallBack {
        void newProductCallBack(List<ProductInfoBean> productInfoBeans);
    }

}