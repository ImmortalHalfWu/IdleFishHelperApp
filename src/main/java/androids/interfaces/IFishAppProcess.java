package androids.interfaces;

import androids.adbs.ADBProcess;

public interface IFishAppProcess extends IFishProcess {

    IFishHomeActivityProcess toMainActivity(ADBProcess adbProcess, String deviceAddress);
    IFishAppProcess startApp(ADBProcess adbProcess, String deviceAddress);
    boolean isShowing(ADBProcess adbProcess, String deviceAddress);

}
