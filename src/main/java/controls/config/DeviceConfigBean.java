package controls.config;

public class DeviceConfigBean {

    private final String deviceId;
    private DeviceConfigAppBean deviceAppBean;
    private String saveFileName;

    DeviceConfigBean(String deviceId) {
        this.deviceId = deviceId;
    }

    public DeviceConfigAppBean getDeviceAppBean() {
        return deviceAppBean;
    }

    void setDeviceAppBean(DeviceConfigAppBean deviceAppBean) {
        this.deviceAppBean = deviceAppBean;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getSaveFileName() {
        return saveFileName;
    }

    public void setSaveFileName(String saveFileName) {
        this.saveFileName = saveFileName;
    }
}
