package androids;

import Utils.FileUtils;
import Utils.MLog;
import androids.adbs.ADBProcess;
import java.util.ArrayList;

public class AndroidUtils {

    public final static String[] postProductUITextIndex = {
            "关闭",
            "发布",
            "标题 品类品牌型号都是买家喜欢搜索的, 输入框",
            "描述宝贝的转手原因、入手渠道和使用感受, 输入框",
            "复选框未选中, 全新宝贝",
            "价格",
            "分类",
            "邮寄，",
            "鱼塘：",
            "确认发布",
    };


    public final static String[] postProductMoneyUITextIndex = {
            "价格",
            "未选中,不讲价,复选框, 不讲价， 单选按钮",
            "入手价",
            "未选中, 包邮， 单选按钮",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
            "小数点",
            "0",
            "确定",
    };



    public final static String[] VM_ADDRESS = {
            "127.0.0.1:62001",      // 夜神
            "127.0.0.1:21503",      // 逍遥
            "127.0.0.1:6555 ",      // 天天
            "127.0.0.1:53001",      // 海马
            "127.0.0.1:7555",      //  网易UU
    };

    static java.util.List<String> getDeviceAddreByAdbResult(ADBProcess adbProcess) {
        ArrayList<String> arrayList = new ArrayList<>();
        String s = adbProcess.adbFindAllDevice();
        s = s.replace("List of devices attached", "").replace("device", "");
        String[] split = s.split("\n");
        if (split.length < 2) {
            return arrayList;
        }

        for (int i = 0; i < split.length; i++) {
            if (!FileUtils.isEmpty(split[i])) {
                arrayList.add(split[i].replace("\t", ""));
            }
        }
        return arrayList;
    }

    static java.util.List<String> connectDeviceByAddress(java.util.List<String> deviceAddr, ADBProcess adbProcess) {
        for (String address :
                deviceAddr) {
            adbProcess.adbConnectDevice(address);
        }

        return getDeviceAddreByAdbResult(adbProcess);
    }

    public static String getUIXMLFileName(Object o, String tag) {
        return tag + o.getClass().getSimpleName() + ".xml";
    }

    public static void log(Object info) {
        MLog.logi(info.toString());
    }


    public static void installFishApp(String deviceAddress) {
        if (!isInstallFishApp(deviceAddress)) {
            String path = deviceAddress.getClass().getResource("idleFish.apk").getPath();
            if (!FileUtils.isEmpty(path)) {
                ADBProcess.getInstance().adbIdleFishInstance(deviceAddress, path);
            }
        }

    }

    public static void installADBKeyBoardApp(String deviceAddress) {

        if (!ADBProcess.getInstance().adbKeyBoardIsInstance(deviceAddress)) {
            String path = deviceAddress.getClass().getResource("ADBKeyboard.apk").getPath();
            if (!FileUtils.isEmpty(path)) {
                ADBProcess.getInstance().adbKeyBoardInstance(deviceAddress, path);
            }
        }

        if (ADBProcess.getInstance().adbKeyBoardIsInstance(deviceAddress)) {
            ADBProcess.getInstance().adbChangeKeyBoard(deviceAddress);
        }

    }

    public static boolean isInstallFishApp(String deviceAddress) {
        return ADBProcess.getInstance().adbIdleFishIsInstance(deviceAddress);
    }


    public static void deleteFishApp(String deviceAddress) {
        if (isInstallFishApp(deviceAddress)) {
            ADBProcess.getInstance().adbIdleFishUNInstance(deviceAddress);
        }
    }

}
