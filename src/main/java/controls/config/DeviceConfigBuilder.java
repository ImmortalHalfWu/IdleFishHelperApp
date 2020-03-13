package controls.config;

public class DeviceConfigBuilder {




    private DeviceConfigAppBean deviceConfigAppBean = new DeviceConfigAppBean(
            "IdleFish",
            "com.taobao.idlefish",
            "com.taobao.idlefish/com.taobao.fleamarket.home.activity.InitActivity"
    );

    private DeviceConfigAppProductType deviceConfigAppProductType = DeviceConfigAppProductType.TYPE_JIU;

    private DeviceConfigAppProductInfoBean deviceConfigAppProductInfoBean = new DeviceConfigAppProductInfoBean();


    public static DeviceConfigBuilder newInstance() {
        return new DeviceConfigBuilder();
    }

    private DeviceConfigBuilder() {
    }

    public DeviceConfigBuilder setIdleFishApp() {
        deviceConfigAppBean = new DeviceConfigAppBean(
                "IdleFish",
                "com.taobao.idlefish",
                "com.taobao.idlefish/com.taobao.fleamarket.home.activity.InitActivity"
        );
        return this;
    }


    public DeviceConfigBuilder setFreeProductType() {
        deviceConfigAppProductType = DeviceConfigAppProductType.TYPE_JIU;
        return this;
    }

    public DeviceConfigBuilder setSelectProductType() {
        deviceConfigAppProductType = DeviceConfigAppProductType.TYPE_SELECTED;
        return this;
    }

    public DeviceConfigBuilder setIdleFishRefreshLoadDataTime(int time) {
        deviceConfigAppBean.setRefreshLoadDataTime(time < 0 ? 0 : time > 24 ? 24 : time);
        return this;
    }
    public DeviceConfigBuilder setIdleFishRefreshProductTime(int time) {
        deviceConfigAppBean.setRefreshProductTime(time < 0 ? 0 : time > 24 ? 24 : time);
        return this;
    }

    public DeviceConfigBean build(String deviceId) {

        deviceConfigAppBean.setProductType(deviceConfigAppProductType);
        deviceConfigAppBean.setProductInfoBean(deviceConfigAppProductInfoBean);

        DeviceConfigBean deviceConfigBean = new DeviceConfigBean(deviceId);
        deviceConfigBean.setSaveFileName(deviceId);
        deviceConfigBean.setDeviceAppBean(deviceConfigAppBean);
        return deviceConfigBean;
    }

}
