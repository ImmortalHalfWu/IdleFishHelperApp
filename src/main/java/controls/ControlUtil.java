package controls;

import Utils.FileUtils;
import com.google.gson.Gson;
import controls.config.DeviceConfigBean;

public class ControlUtil {

    static DeviceConfigBean findDeviceConfig(String deviceId) {
        try {
            String s = FileUtils.readFile(FileUtils.createDeviceConfigFile(deviceId));
            return new Gson().fromJson(s, DeviceConfigBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
