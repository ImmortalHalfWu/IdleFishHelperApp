package androids.adbs;


public class ADBProcess implements IADBProcess {

    private static ADBProcess instance;

    public static ADBProcess getInstance() {
        if (instance == null) {
            synchronized (ADBProcess.class) {
                if (instance == null) {
                    instance = new ADBProcess();
                }
            }
        }
        return instance;
    }

    @Override
    public boolean adbStartIdleFishMainActivity(String deviceAddr) {
        return ADBUtils.adbStartIdleFishMainActivity(deviceAddr);
    }

    @Override
    public boolean adbIdleFishIsResume(String deviceAddr) {
        return ADBUtils.adbIdleFishIsResume(deviceAddr);
    }

    @Override
    public boolean adbIdleFishIsInstance(String deviceAddr) {
        return ADBUtils.adbIdleFishIsInstance(deviceAddr);
    }

    @Override
    public boolean adbKeyBoardIsInstance(String deviceAddr) {
        return ADBUtils.adbKeyBoardIsInstance(deviceAddr);
    }

    @Override
    public boolean adbIdleFishUNInstance(String deviceAddr) {
        return ADBUtils.adbIdleFishUNInstance(deviceAddr);
    }

    @Override
    public boolean adbIdleFishInstance(String deviceAddr, String apkPath) {
        return ADBUtils.adbIdleFishInstance(deviceAddr, apkPath);
    }

    @Override
    public boolean adbKeyBoardInstance(String deviceAddr, String apkPath) {
        return ADBUtils.adbKeyBoardInstance(deviceAddr, apkPath);
    }

    @Override
    public boolean adbGetAndroidUIXML(String deviceAddr, String phoneFileName, String savePath) {
        return ADBUtils.adbGetAndroidUIXML(deviceAddr, phoneFileName, savePath);
    }

    @Override
    public boolean adbPushFile(String deviceAddr, String fromPath, String toFileName) {
        return ADBUtils.adbPushFile(deviceAddr, fromPath, toFileName);
    }

    @Override
    public boolean adbScanFile(String deviceAddr, String fileName) {
        return ADBUtils.adbScanFile(deviceAddr, fileName);
    }

    @Override
    public boolean adbInputTap(String deviceAddr, int x, int y) {
        return ADBUtils.adbInputTap(deviceAddr, x, y);
    }

    @Override
    public boolean adbConnectDevice(String deviceAddr) {
        return ADBUtils.adbConnectDevice(deviceAddr);
    }

    @Override
    public boolean adbFindDevice(String deviceAddr) {
        return ADBUtils.adbFindDevice(deviceAddr);
    }

    @Override
    public String adbFindAllDevice() {
        return ADBUtils.adbFindAllDevice();
    }

    @Override
    public boolean adbInputText(String deviceAddr, String text) {
        return ADBUtils.adbInputText(deviceAddr, text);
    }

    @Override
    public boolean adbSwipe(String deviceAddr, int startX, int startY, int endX, int endY, int time) {
        return ADBUtils.adbSwipe(deviceAddr, startX, startY, endX, endY, time);
    }

    @Override
    public boolean adbDeleteFile(String deviceAddr, String filePath) {
        return ADBUtils.adbDeleteFile(deviceAddr, filePath);
    }

    @Override
    public boolean adbSendBackKeyEvent(String deviceAddr) {
        return ADBUtils.adbSendBackKeyEvent(deviceAddr);
    }

    @Override
    public String findTopActivity(String deviceAddr) {
        return ADBUtils.findTopActivity(deviceAddr);
    }

    @Override
    public boolean adbChangeKeyBoard(String deviceAddr) {
        return ADBUtils.adbChangeKeyBoard(deviceAddr);
    }

    @Override
    public boolean runInCmd(String cmd, String resultIsSuc) {
        return ADBUtils.runInCmd(cmd, resultIsSuc);
    }

    @Override
    public String runInCmd(String cmd) {
        return ADBUtils.runInCmd(cmd);
    }

}
