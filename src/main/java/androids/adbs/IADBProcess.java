package androids.adbs;

public interface IADBProcess {

    boolean adbStartIdleFishMainActivity(String deviceAddr);

    boolean adbIdleFishIsResume(String deviceAddr);

    boolean adbIdleFishIsInstance(String deviceAddr);

    boolean adbIdleFishUNInstance(String deviceAddr);

    boolean adbIdleFishInstance(String deviceAddr, String apkPath);

    boolean adbGetAndroidUIXML(String deviceAddr, String phonePath, String savePath) ;

    boolean adbPushFile(String deviceAddr, String fromPath, String toPath) ;

    boolean adbScanFile(String deviceAddr, String fileName);

    boolean adbInputTap(String deviceAddr, int x, int y) ;

    boolean adbConnectDevice(String deviceAddr) ;

    boolean adbFindDevice(String deviceAddr) ;

    String adbFindAllDevice() ;

    boolean adbInputText(String deviceAddr, String text) ;

    boolean adbSwipe(String deviceAddr, int startX, int startY, int endX, int endY, int time) ;

    boolean adbDeleteFile(String deviceAddr, String filePath) ;

    boolean adbSendBackKeyEvent(String deviceAddr);

    String findTopActivity(String deviceAddr);

    boolean adbChangeKeyBoard(String deviceAddr);

    boolean runInCmd(String cmd, String resultIsSuc) ;

    String runInCmd(String cmd);

}