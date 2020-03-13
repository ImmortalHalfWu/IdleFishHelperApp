package browsers.impls.yhqAndPic;

import Utils.ThreadPoolManager;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import browsers.exceptions.ManLoadHtmlException;
import browsers.interfaces.BrowsersInterface;
import browsers.interfaces.ManManBuyLoadHtmlProcess;
import browsers.queues.NewLoadHtmlRequestQueue;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.events.FinishLoadingEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class TaoBaoTmallYHQImagProcess implements NewLoadHtmlRequestQueue.LoadHtmlProcess, Runnable {

    private static AtomicBoolean isShield = new AtomicBoolean(false);

    private final static int TYPE_YHQ = 1;
    private final static int TYPE_PIC = 2;


    private ProductInfoBean productInfoBean;
    private ManManBuyLoadHtmlProcess manManBuyLoadHtmlProcess;

    private List<ProductInfoBean> allProducts;
    private List<ProductInfoBean> removeProductInfo;

    private TaoBaoTmallYHQImagListener listener;

    private String url;
    private int currentType;
    private boolean isEnd;

    private Object tag;

    private TaoBaoTmallYHQImagProcess(
            ProductInfoBean productInfoBean,
            List<ProductInfoBean> allProducts,
            List<ProductInfoBean> removeProductInfo,
            TaoBaoTmallYHQImagListener listener,
            boolean isEnd,
            Object tag) {
        this.productInfoBean = productInfoBean;
        this.manManBuyLoadHtmlProcess = new YHQLoadProcess(productInfoBean);
        this.allProducts = allProducts;
        this.removeProductInfo = removeProductInfo;
        this.listener = listener;
        this.isEnd = isEnd;
        this.tag = tag;
        config(new YHQLoadProcess(productInfoBean), TYPE_YHQ);
    }

    static void startFindYHQAndPIC(List<ProductInfoBean> productInfoBeans, Object tag, TaoBaoTmallYHQImagListener listener) {

        if (listener == null) {
            return;
        }

        List<ProductInfoBean> mList = new ArrayList<>(productInfoBeans);
        List<ProductInfoBean> removeProductInfo = Collections.synchronizedList(new ArrayList<>());

        int size = mList.size();

        for (int i = 0; i < size - 1; i++) {
            ProductInfoBean productInfoBean = mList.get(i);
            ThreadPoolManager.init().postDelayForS(new TaoBaoTmallYHQImagProcess(productInfoBean, mList, removeProductInfo, listener, false, tag), i * 2f);
        }

        ProductInfoBean productInfoBean = mList.get(size - 1);
        ThreadPoolManager.init().postDelayForS(new TaoBaoTmallYHQImagProcess(productInfoBean, mList, removeProductInfo, listener, true, tag), (size - 1) * 2f);

    }

    @Override
    public String getUrl() {
        if (currentType == TYPE_YHQ && isShield.get()) {
            BrowserUtils.log("被屏蔽，延时ing");
            ThreadPoolManager.init().postDelayForS(this, 60);
            return "";
        }
        return url;
    }

    @Override
    public boolean canProcess(String url) {
        return manManBuyLoadHtmlProcess.canProcess(url);
    }

    @Override
    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {

//        if (currentType == TYPE_YHQ && isShield.get()) {
//            BrowserUtils.log("被屏蔽，延时ing");
//            ThreadPoolManager.init().postDelay(this, 50);
//            return true;
//        }

        ProductInfoBean process = null;
        try {
            process = manManBuyLoadHtmlProcess.process(event, resultUrl, domDocument, browser);
        } catch (ManLoadHtmlException e) {
//            e.printStackTrace();
            if (e.getCode() == ManLoadHtmlException.CODE_SHIELD) {
                if (!isShield.get()) {
                    isShield.set(true);
                    BrowserUtils.log("被屏蔽，开启延时");
                    ThreadPoolManager.init().postDelayForS(new Runnable() {
                        @Override
                        public void run() {
                            BrowserUtils.log("被屏蔽，延时结束");
                            isShield.set(false);
                        }
                    }, 59);
                }
                ThreadPoolManager.init().postDelayForS(this, 60);
                return true;
            }
        }

        if (process == null) {
            BrowserUtils.log(productInfoBean.getYhqUrl());
            BrowserUtils.log("优惠券及图像处理异常：" + (currentType == TYPE_YHQ ? "优惠券" : "图像"));
            removeProductInfo.add(productInfoBean);
        }
        if (process != null && currentType == TYPE_YHQ) {
            config(new ProductPicLoadProcess(process), TYPE_PIC);
            browser.loadURL(this);
        }
        if (isEnd) {
            allProducts.removeAll(removeProductInfo);
            BrowserUtils.log("优惠券及图像处理结束：" + "有效：" + allProducts.size());
            BrowserUtils.log("优惠券及图像处理结束：" + "无效：" + removeProductInfo.size());
            removeProductInfo.clear();
            if (listener != null) {
                listener.loadOver(allProducts, tag);
            }
        }
        return true;
    }

    private void config(ManManBuyLoadHtmlProcess loadHtmlProcess, int type) {
        manManBuyLoadHtmlProcess = loadHtmlProcess;
        currentType = type;
        url = manManBuyLoadHtmlProcess.getUrl();
    }

    @Override
    public void run() {
        NewLoadHtmlRequestQueue.instance().put(this);
    }


    public interface TaoBaoTmallYHQImagListener {
        void loadOver(List<ProductInfoBean> allProduct, Object tag);
    }

}
