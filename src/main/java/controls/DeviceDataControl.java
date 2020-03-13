package controls;

import androids.AndroidManager;
import browsers.BrowserManager;
import controls.config.DeviceConfigBean;
import controls.config.DeviceConfigBuilder;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceDataControl {

    private static DeviceDataControl deviceDataControl;

    public static DeviceDataControl init() {
        if (deviceDataControl == null) {
            synchronized (DeviceDataControl.class) {
                if (deviceDataControl == null) {
                    deviceDataControl = new DeviceDataControl();
                }
            }
        }
        return deviceDataControl;
    }

    private Map<String, DeviceConfigBean> deviceConfigBeanMap = new ConcurrentHashMap<>();

    private DeviceDataControl() {
        AndroidManager.init();
        BrowserManager.init();

        List<String> allDevicesAddress = AndroidManager.init().getAllDevicesAddress();

        for (String address :
                allDevicesAddress) {

            DeviceConfigBean deviceConfig = ControlUtil.findDeviceConfig(address);
            if (deviceConfig == null) {
                deviceConfig = DeviceConfigBuilder.newInstance()
                        .setSelectProductType()
                        .setIdleFishApp()
                        .setIdleFishRefreshLoadDataTime(1)
                        .setIdleFishRefreshProductTime(8)
                        .build(address);
            }
            deviceConfigBeanMap.put(address, deviceConfig);

            

        }

    }


}
