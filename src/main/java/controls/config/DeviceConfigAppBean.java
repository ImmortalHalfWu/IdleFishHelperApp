package controls.config;

import java.util.Objects;

public class DeviceConfigAppBean {

    private final String appName;
    private final String appPackageName;
    private final String appMainActivityPath;

    private int refreshLoadDataTime;
    private int refreshProductTime;

    private DeviceConfigAppProductType productType;
    private DeviceConfigAppProductInfoBean productInfoBean;

    public DeviceConfigAppBean(String appName, String appPackageName, String appMainActivityPath) {
        this.appName = appName;
        this.appPackageName = appPackageName;
        this.appMainActivityPath = appMainActivityPath;
    }

    public void setProductType(DeviceConfigAppProductType productType) {
        this.productType = productType;
    }

    public void setProductInfoBean(DeviceConfigAppProductInfoBean productInfoBean) {
        this.productInfoBean = productInfoBean;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public String getAppMainActivityPath() {
        return appMainActivityPath;
    }

    public DeviceConfigAppProductType getProductType() {
        return productType;
    }

    public DeviceConfigAppProductInfoBean getProductInfoBean() {
        return productInfoBean;
    }

    public int getRefreshLoadDataTime() {
        return refreshLoadDataTime;
    }

    public void setRefreshLoadDataTime(int refreshLoadTime) {
        this.refreshLoadDataTime = refreshLoadTime;
    }

    public int getRefreshProductTime() {
        return refreshProductTime;
    }

    public void setRefreshProductTime(int refreshProductTime) {
        this.refreshProductTime = refreshProductTime;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DeviceConfigAppBean)) return false;
        DeviceConfigAppBean that = (DeviceConfigAppBean) o;
        return Objects.equals(appName, that.appName) &&
                Objects.equals(appPackageName, that.appPackageName) &&
                Objects.equals(appMainActivityPath, that.appMainActivityPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appName, appPackageName, appMainActivityPath);
    }

}