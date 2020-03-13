package androids;

import Utils.FileUtils;
import androids.adbs.ADBProcess;
import androids.impls.FishAppProcess;
import androids.interfaces.IAndroidDeviceProcess;
import androids.beans.UIPostBean;
import androids.interfaces.IFishAppProcess;
import com.sun.tools.corba.se.idl.constExpr.And;

import java.util.ArrayList;
import java.util.List;

class DeviceProcess implements IAndroidDeviceProcess {

    private final String deviceAddress;
    private volatile boolean isConnect;
    private IFishAppProcess mFishAppProcess;
    private final ADBProcess mADBProcess;

    DeviceProcess(String deviceAddress, ADBProcess mADBProcess) {
        this.deviceAddress = deviceAddress;
        this.mADBProcess = mADBProcess;
        refreshConnect();
    }

    @Override
    public List<String> findPostedProduct() {
        if (!isConnect || !isInstallFishApp()) {
            return new ArrayList<>(0);
        }
        List<String> allProductName = mFishAppProcess.toMainActivity(mADBProcess, deviceAddress)
                .toMyPage(mADBProcess, deviceAddress)
                .toMyPostedActivity(mADBProcess, deviceAddress)
                .getAllProductName(mADBProcess, deviceAddress);
        return allProductName == null ? new ArrayList<>(0) : allProductName;
    }

    @Override
    public List<String> findOrderSucProduct() {
        if (!isConnect || !isInstallFishApp()) {
            return new ArrayList<>(0);
        }
        List<String> allProductName = mFishAppProcess.toMainActivity(mADBProcess, deviceAddress)
                .toMyPage(mADBProcess, deviceAddress)
                .toMyOrderSucActivity(mADBProcess, deviceAddress)
                .getAllProductName(mADBProcess, deviceAddress);
        return allProductName == null ? new ArrayList<>(0) : allProductName;
    }

    @Override
    public boolean isConnect() {
        return isConnect;
    }

    @Override
    public void refreshConnect() {
        isConnect = mADBProcess.adbFindDevice(deviceAddress) || mADBProcess.adbConnectDevice(deviceAddress);
        AndroidUtils.log(deviceAddress + "连接" + isConnect);
        if (isConnect) {

            if (mFishAppProcess != null) {
                return;
            }

            mFishAppProcess = new FishAppProcess();

            installFishApp();
            AndroidUtils.installADBKeyBoardApp(deviceAddress);
        }
    }

    @Override
    public String getDeviceAddress() {
        return deviceAddress;
    }

    @Override
    public void postProduct(UIPostBean uiPostBean) {

        if (mFishAppProcess == null || !isInstallFishApp()) {
            return;
        }

        mFishAppProcess.toMainActivity(mADBProcess, deviceAddress)
                .toPostProductActivity(mADBProcess, deviceAddress)
                .postProductByOrder(mADBProcess, deviceAddress, uiPostBean);

    }

    @Override
    public void deleteProduct(List<String> productName) {

        if (mFishAppProcess == null || !isInstallFishApp()) {
            return;
        }
        mFishAppProcess.toMainActivity(mADBProcess, deviceAddress)
                .toMyPage(mADBProcess, deviceAddress)
                .toMyPostedActivity(mADBProcess, deviceAddress)
                .deleteProductsByName(mADBProcess, deviceAddress, productName);
    }

    @Override
    public void startFishApp() {
        if (mFishAppProcess == null) {
            return;
        }
        if (isInstallFishApp()) {
            mADBProcess.adbStartIdleFishMainActivity(deviceAddress);
        }
    }

    @Override
    public void installFishApp() {
        if (!isConnect) {
            return;
        }
        // 2019-08-25 确认闲鱼安装
        AndroidUtils.installFishApp(deviceAddress);
    }

    @Override
    public boolean isInstallFishApp() {
        return isConnect && AndroidUtils.isInstallFishApp(deviceAddress);
    }

    @Override
    public void deleteFishApp() {
        AndroidUtils.deleteFishApp(deviceAddress);
    }

    @Override
    public String toString() {
        return "DeviceProcess{" +
                "deviceAddress='" + deviceAddress + '\'' +
                ", isConnect=" + isConnect +
                '}';
    }
}
