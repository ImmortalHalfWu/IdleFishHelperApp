package androids.adbs;

import Utils.FileUtils;
import Utils.MLog;
import com.teamdev.jxbrowser.chromium.internal.FileUtil;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ADBUtils {

    private final static String ADB_PHONE_ROOT_DIR = "/sdcard/";
    private final static String ADB = "adb -s ";
    private final static String ADB_UI_AUTO_MATOR = " shell uiautomator dump " + ADB_PHONE_ROOT_DIR;
    private final static String ADB_PULL_FILE = " pull " + ADB_PHONE_ROOT_DIR;
    private final static String ADB_PUSH_FILE = " push " ;
    private final static String ADB_START_IDLE_FISH_MAIN_ACTIVITY = " shell am start -n com.taobao.idlefish/com.taobao.fleamarket.home.activity.InitActivity";
    private final static String ADB_IDLE_FISH_IS_RUNNING = " shell dumpsys activity activities | grep ResumedActivity";
    private final static String ADB_IDLE_FISH_IS_INSTANCES = " shell pm list packages | grep com.taobao.idlefish";
    private final static String ADB_INPUT_TAP = " shell input tap ";
    private final static String ADB_CONNECT = " connect ";
    private final static String ADB_FIND_DEVICES = " devices ";
    private final static String ADB_INPUT_KEY = " shell input keyevent ";
    private final static String ADB_INPUT_TEXT = " shell input text ";
    private final static String ADB_SWIPE = " shell input swipe ";
    private final static String ADB_DELETE_FILE = " shell rm ";
    private final static String ADB_TOP_ACTIVITY = " shell dumpsys activity top ";
    private final static String ADB_SCAN_FILE = " shell am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file://";

    public synchronized static boolean adbStartIdleFishMainActivity(String deviceAddr) {
        return runInCmd(ADB + deviceAddr + ADB_START_IDLE_FISH_MAIN_ACTIVITY, "Stringing:");
    }

    public synchronized static boolean adbIdleFishIsRunning(String deviceAddr) {
        return runInCmd(ADB + deviceAddr + ADB_IDLE_FISH_IS_RUNNING, "com.taobao.idlefish/com.taobao.fleamarket.home.activity.MainActivity");
    }

    public synchronized static boolean adbIdleFishIsInstance(String deviceAddr) {
        return runInCmd(ADB + deviceAddr + ADB_IDLE_FISH_IS_INSTANCES, "com.taobao.idlefish");
    }

    synchronized static boolean adbGetAndroidUIXML(String deviceAddr, String phoneFileName, String saveFileName) {
        return runInCmd(ADB + deviceAddr + ADB_UI_AUTO_MATOR + phoneFileName, "UI hierchary dumped to: ") &&
                runInCmd(ADB + deviceAddr + ADB_PULL_FILE + phoneFileName + " " + saveFileName, phoneFileName);
    }

    synchronized static boolean adbPushFile(String deviceAddr, String fromPath, String toFileName) {
        return runInCmd(ADB + deviceAddr + ADB_PUSH_FILE + fromPath + " " + ADB_PHONE_ROOT_DIR + toFileName, fromPath);
    }

    synchronized static boolean adbScanFile(String deviceAddr, String fileName) {
//        "adb shell am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file:///sdcard/1231.jpg";
        return runInCmd(ADB + deviceAddr + ADB_SCAN_FILE + "/sdcard/" + fileName, "result=0");
    }

    synchronized static boolean adbInputTap(String deviceAddr, int x, int y) {
        return runInCmd(ADB + deviceAddr + ADB_INPUT_TAP + x + " " + y, "");
    }

    public synchronized static boolean adbConnectDevice(String deviceAddr) {
        runInCmd(ADB + ADB_CONNECT + deviceAddr, "");
        return adbFindDevice(deviceAddr);
    }

    synchronized static boolean adbFindDevice(String deviceAddr) {
        return runInCmd(ADB + ADB_FIND_DEVICES, deviceAddr);
    }

    public synchronized static String adbFindAllDevice() {
        return runInCmd(ADB + ADB_FIND_DEVICES);
    }

    synchronized static boolean adbInputText(String deviceAddr, String text) {
        return runInCmd(ADB + deviceAddr + " shell am broadcast -a ADB_INPUT_B64 --es msg `echo '"+text+"' | base64`", "Broadcast completed");
    }

    synchronized static boolean adbSwipe(String deviceAddr, int startX, int startY, int endX, int endY, int time) {
        return runInCmd(ADB + deviceAddr + ADB_SWIPE + startX + " " + startY + " " + endX + " " + endY + " " + time, "");
    }

    synchronized static String findTopActivity(String deviceAddr) {
        return runInCmd(ADB + deviceAddr + ADB_TOP_ACTIVITY);
    }

    synchronized static boolean adbDeleteFile(String deviceAddr, String filePath) {
        return !runInCmd(ADB + deviceAddr + ADB_DELETE_FILE + filePath, "No Such");
    }

    public synchronized static boolean adbSendBackKeyEvent(String deviceAddr) {
        return adbSendKeyEvent(deviceAddr, "4");
    }

    public synchronized static boolean adbSendKeyEvent(String deviceAddr, String key) {
        return runInCmd(ADB + deviceAddr + ADB_INPUT_KEY + key, "");
    }

    public synchronized static boolean runInCmd(String cmd, String resultIsSuc) {
        return runInCmd(cmd).contains(resultIsSuc);
    }

    synchronized static String runInCmd(String cmd) {

        try {
            MLog.logi("执行ADB：" + cmd);
            Process process = Runtime.getRuntime().exec(cmd);
//            process.waitFor();

            String erroResult = readCMDResult(process.getErrorStream());

            if (!FileUtils.isEmpty(erroResult)) {
                MLog.logi("执行结果：" + erroResult);
                return erroResult;
            }

            String result = readCMDResult(process.getInputStream());
            MLog.logi("执行结果：" + result);
            return result;

        } catch (IOException e) {
            e.printStackTrace();
            MLog.logi("执行结果：" + e.getMessage());
            return e.getMessage();
        }
    }

    private static String readCMDResult(InputStream inputStream) throws IOException {

        if (inputStream == null) {
            return "inputStream == null";
        }

        byte[] readBytes = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();

        BufferedInputStream bis = new BufferedInputStream(inputStream);

        while (bis.read(readBytes) > -1) {
            String s = new String(readBytes, StandardCharsets.UTF_8);
            stringBuilder.append(s, 0, s.indexOf('\u0000') < 0 ? s.length() : s.indexOf('\u0000'));
        }

        bis.close();
        return stringBuilder.toString();

    }

}
