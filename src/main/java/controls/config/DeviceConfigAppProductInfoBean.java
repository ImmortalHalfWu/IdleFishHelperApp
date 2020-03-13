package controls.config;

import Utils.TimeUtil;
import browsers.beans.ProductInfoBean;

import java.util.ArrayList;
import java.util.List;

public class DeviceConfigAppProductInfoBean {

    private long refreshTime;
    private String refreshTimeFormat;
    private List<ProductInfoBean> postedProductInfoBeans;

    DeviceConfigAppProductInfoBean() {
        refreshTime = System.currentTimeMillis();
        refreshTimeFormat = TimeUtil.getTimeFormat();
        postedProductInfoBeans = new ArrayList<>();
    }

    public long getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }

    public String getRefreshTimeFormat() {
        return refreshTimeFormat;
    }

    public void setRefreshTimeFormat(String refreshTimeFormat) {
        this.refreshTimeFormat = refreshTimeFormat;
    }

    public void refeshTime() {
        refreshTime = System.currentTimeMillis();
        refreshTimeFormat = TimeUtil.getTimeFormat();
    }

    public List<ProductInfoBean> getPostedProductInfoBeans() {
        return postedProductInfoBeans;
    }

    public void setPostedProductInfoBeans(List<ProductInfoBean> postedProductInfoBeans) {
        this.postedProductInfoBeans = postedProductInfoBeans;
    }
}
