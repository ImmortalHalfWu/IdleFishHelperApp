package browsers;

import browsers.beans.ProductInfoBean;
import browsers.impls.man.ManManBuyCallBack;
import browsers.impls.man.manAll.ManManBuyAllStartPageProcess;
import browsers.impls.man.manHot.ManManBuyHotStartPageProcess;
import browsers.impls.yhqAndPic.AiTaoBaoLoadProcess;
import browsers.impls.yhqAndPic.LoginLoadProcess;
import browsers.queues.MLoadFinishAdapter;
import browsers.queues.NewLoadHtmlRequestQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BrowserManager {


    private static BrowserManager instance;

    public static BrowserManager init() {
        if (instance == null) {
            synchronized (BrowserManager.class) {
                if (instance == null) {
                    instance = new BrowserManager();
                }
            }
        }
        return instance;
    }

    private final CallBackSender manAllCallBackSender = new CallBackSender();
    private final CallBackSender manHotCallBackSender = new CallBackSender();

    private BrowserManager() {
        MLoadFinishAdapter.registerStaticLoadHtmlProcee(AiTaoBaoLoadProcess.getInstance());
        MLoadFinishAdapter.registerStaticLoadHtmlProcee(LoginLoadProcess.getInstance());
    }

    public void findManManBuyAllProductData(ManManBuyCallBack callBack) {
        if (callBack == null) {
            return;
        }
        manAllCallBackSender.addCallBack(callBack);
        if (!manAllCallBackSender.isRunningAndRun()) {
            NewLoadHtmlRequestQueue.instance().put(new ManManBuyAllStartPageProcess(callBack));
        }
    }

    public void findManManBuyHotProductData(ManManBuyCallBack callBack) {
        if (callBack == null) {
            return;
        }
        manHotCallBackSender.addCallBack(callBack);
        if (!manHotCallBackSender.isRunningAndRun()) {
            NewLoadHtmlRequestQueue.instance().put(new ManManBuyHotStartPageProcess(callBack));
        }
    }







    private final static class CallBackSender implements ManManBuyCallBack {

        private final List<ManManBuyCallBack> callBacks;
        private boolean isRunning;

        private CallBackSender() {
            callBacks = Collections.synchronizedList(new ArrayList<>());
        }

        @Override
        public void dataSuc(List<ProductInfoBean> productInfoBeans, String saveFilePath) {
            if (callBacks.size() > 0) {
                for (ManManBuyCallBack calBack : callBacks
                        ) {
                    try {

                        calBack.dataSuc(productInfoBeans, saveFilePath);
                    } catch (Exception e) {
                        BrowserUtils.logErroLine(saveFilePath + "慢慢买数据分发遇到异常 ：" + e.getMessage());
                    }
                }
                callBacks.clear();
            }
            isRunning = false;
        }

        void addCallBack(ManManBuyCallBack callBack) {
            if (callBack != null) {
                callBacks.add(callBack);
            }
        }

        boolean isRunningAndRun() {
            boolean resultRunning = isRunning;

            if (!isRunning) {
                isRunning = true;
            }

            return resultRunning;
        }
    }
}
