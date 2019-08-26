package androids;

import androids.adbs.ADBProcess;
import androids.interfaces.IAndroidDeviceProcess;
import androids.beans.UIPostBean;
import com.sun.istack.internal.Nullable;

import java.util.*;

public class AndroidManager {

    private static AndroidManager androidUIProcess;
    public static AndroidManager init() {
        synchronized (AndroidManager.class) {
            if (androidUIProcess == null) {
                synchronized (AndroidManager.class) {
                    androidUIProcess = new AndroidManager();
                }
            }
        }
        return androidUIProcess;
    }

    private final Map<String, IAndroidDeviceProcess> deviceProcessMap = new HashMap<>();
    private ADBProcess adbProcess = ADBProcess.getInstance();

    private AndroidManager() {
        AndroidUtils.log("AndroidManager初始化....");
        refreshConnect();
        AndroidUtils.log("AndroidManager完成....");
    }

    /**
     * 连接并查看当前可用的设备
     */
    public void refreshConnect() {

        AndroidUtils.log("刷新设备连接状态....");


        List<String> deviceAddreByAdbResult = AndroidUtils.getDeviceAddreByAdbResult(adbProcess);
        deviceAddreByAdbResult.addAll(Arrays.asList(AndroidUtils.deviceAddress));

        for (String deviceAddre :
                deviceAddreByAdbResult) {

            DeviceProcess deviceProcess = new DeviceProcess(deviceAddre, adbProcess);

            if (deviceProcess.isConnect()) {
                deviceProcessMap.put(deviceAddre, new DeviceProcess(deviceAddre, adbProcess));
            }

        }

        AndroidUtils.log("连接中的设备:" + deviceProcessMap);
    }

    public @Nullable List<String> findPostedProduct(String deviceAddress) {
        AndroidUtils.log("查找指定设备发布过的商品：" + deviceAddress);
        IAndroidDeviceProcess deviceByAddress = getDeviceByAddress(deviceAddress);
        List<String> objects = deviceByAddress == null ? new ArrayList<>(0) : deviceByAddress.findPostedProduct();
        AndroidUtils.log("查找指定设备发布过的商品结果：" + objects);
        return objects;
    }

    public List<String> findOrderSucProduct(String deviceAddress) {
        AndroidUtils.log("查找指定设备交易成功的商品：" + deviceAddress);
        IAndroidDeviceProcess deviceByAddress = getDeviceByAddress(deviceAddress);
        List<String> objects = deviceByAddress == null ? new ArrayList<>(0) : deviceByAddress.findOrderSucProduct();
        AndroidUtils.log("查找指定设备交易成功的商品结果：" + objects);
        return objects;
    }

    public void postProduct(String deviceAddress, UIPostBean uiPostBean) {
        AndroidUtils.log("发送商品：" + uiPostBean + "  到：" + deviceAddress);
        IAndroidDeviceProcess deviceByAddress = getDeviceByAddress(deviceAddress);
        if (deviceByAddress != null) {
            deviceByAddress.postProduct(uiPostBean);
            AndroidUtils.log("发送商品完成");
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            AndroidUtils.log("发送商品失败，未查询到指定设备：" + deviceAddress);
        }
    }

    public void deleteProduct(String deviceAddress, List<String> productNames) {
        AndroidUtils.log("删除" + deviceAddress + "商品：" + productNames);
        IAndroidDeviceProcess deviceByAddress = getDeviceByAddress(deviceAddress);
        if (deviceByAddress != null) {
            deviceByAddress.deleteProduct(productNames);
            AndroidUtils.log("删除商品完成");
        } else {
            AndroidUtils.log("删除商品失败，未查询到指定设备：" + deviceAddress);
        }
    }

    public List<IAndroidDeviceProcess> getAllDevices() {
        return new ArrayList<>(deviceProcessMap.values());
    }

    private @Nullable IAndroidDeviceProcess getDeviceByAddress(String deviceAddress) {
        if (deviceProcessMap.containsKey(deviceAddress)) {
            return deviceProcessMap.get(deviceAddress);
        }
        return null;
    }



}
