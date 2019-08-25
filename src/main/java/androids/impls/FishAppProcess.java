package androids.impls;

import Utils.FileUtils;
import androids.adbs.ADBProcess;
import androids.interfaces.IFishAppProcess;
import androids.interfaces.IFishHomeActivityProcess;

public class FishAppProcess extends FishBaseProcess implements IFishAppProcess {

    private IFishHomeActivityProcess iFishHomeActivityProcess;

    @Override
    public IFishHomeActivityProcess toMainActivity(ADBProcess adbProcess, String deviceAddress) {
        startApp(adbProcess, deviceAddress);
        return iFishHomeActivityProcess == null ?
                iFishHomeActivityProcess = new FishHomeActivityProcess(adbProcess, deviceAddress, FileUtils.DIR_PATH_XML) :
                iFishHomeActivityProcess;
    }

    @Override
    public IFishAppProcess startApp(ADBProcess adbProcess, String deviceAddress) {
        if (!adbProcess.adbIdleFishIsInstance(deviceAddress)) {
            throw new IllegalArgumentException("咸鱼未安装");
        }
        if (isShowing(adbProcess, deviceAddress)){
            return this;
        }
        adbProcess.adbStartIdleFishMainActivity(deviceAddress);
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this;
    }

    @Override
    public boolean isShowing(ADBProcess adbProcess, String deviceAddress) {
        return adbProcess.adbIdleFishIsRunning(deviceAddress);
    }
}
