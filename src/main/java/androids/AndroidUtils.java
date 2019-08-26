package androids;

import Utils.FileUtils;
import Utils.MLog;
import androids.adbs.ADBProcess;
import java.util.ArrayList;

public class AndroidUtils {

    public final static String[] deviceAddress = {
            "127.0.0.1:62001",
            "127.0.0.1:21503",
            "127.0.0.1:6555",
            "127.0.0.1:53001",
            "127.0.0.1:7555",
    };

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
                AndroidUtils.deviceAddress) {
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


}
