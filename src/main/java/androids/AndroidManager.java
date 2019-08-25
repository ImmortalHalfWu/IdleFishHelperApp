package androids;

import Utils.MLog;
import androids.interfaces.IAndroidDeviceProcess;
import androids.beans.UIPostBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AndroidManager {

    private static AndroidManager androidUIProcess;
    public static void init() {
        synchronized (AndroidManager.class) {
            if (androidUIProcess == null) {
                synchronized (AndroidManager.class) {
                    androidUIProcess = new AndroidManager();
                }
            }
        }
    }

    private final List<IAndroidDeviceProcess> deviceProcess = new ArrayList<>();

    private AndroidManager() {

        List<String> deviceByAddress = AndroidUtils.connectDeviceByAddress(Arrays.asList(AndroidUtils.deviceAddress));

        for (String deviceAddre :
                deviceByAddress) {
            deviceProcess.add(new DeviceProcess(deviceAddre));
        }
        
        String s = deviceByAddress.toString();
        for (String address :
                AndroidUtils.deviceAddress) {
            if (!s.contains(address)) {
                MLog.logi("未连接上" + address);
            }
        }

    }

    public List<String> findPostedProduct(String deviceAddress) {
        return null;
    }

    public List<String> findOrderSucProduct(String deviceAddress) {
        return null;
    }

    public void postProduct(String deviceAddress, UIPostBean uiPostBean) {

    }

}
