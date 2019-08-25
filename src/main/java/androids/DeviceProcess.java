package androids;

import androids.adbs.ADBUtils;
import androids.interfaces.IAndroidDeviceProcess;
import androids.beans.UIPostBean;

import java.util.List;

public class DeviceProcess implements IAndroidDeviceProcess {

    private final String deviceAddress;
    private volatile boolean isConnect;

    public DeviceProcess(String deviceAddress) {
        this.deviceAddress = deviceAddress;
        isConnect = ADBUtils.adbConnectDevice(deviceAddress);
    }

    @Override
    public List<String> findPostedProduct() {
        return null;
    }

    @Override
    public List<String> findOrderSucProduct() {
        return null;
    }

    @Override
    public boolean isConnect() {
        return isConnect;
    }

    @Override
    public String getDeviceAddress() {
        return deviceAddress;
    }

    @Override
    public void postProduct(UIPostBean uiPostBean) {

    }

    @Override
    public void startFishApp() {

    }

    @Override
    public void installFishApp() {

    }

    @Override
    public void deleteFishApp() {

    }


    private void toHomeMyPage() {
        ADBUtils.adbStartIdleFishMainActivity(deviceAddress);
    }

    private void toMyPostedPage() {
        ADBUtils.adbStartIdleFishMainActivity(deviceAddress);
    }

    private void toPostPage() {
        ADBUtils.adbStartIdleFishMainActivity(deviceAddress);
    }


}
