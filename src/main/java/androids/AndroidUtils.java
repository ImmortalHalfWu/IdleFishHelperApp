package androids;

import androids.adbs.ADBUtils;

import java.awt.*;
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


    public final static String[] productSetingUITextIndex = {
            "删除",
    };

    public final static String[] productDeleteDialogUITextIndex = {
            "确定",
    };



    public static java.util.List<String> getDeviceAddreByAdbResult() {
        ArrayList<String> arrayList = new ArrayList<>();
        String s = ADBUtils.adbFindAllDevice();
        s = s.replace("List of devices attached", "");
        String[] split = s.split("");
        if (split.length < 2) {
            return arrayList;
        }

        for (int i = 0; i < split.length; i+=2) {
            arrayList.add(split[i]);
        }
        return arrayList;
    }

    public static java.util.List<String> connectDeviceByAddress(java.util.List<String> deviceAddr) {
        for (String address :
                AndroidUtils.deviceAddress) {
            ADBUtils.adbConnectDevice(address);
        }

        return getDeviceAddreByAdbResult();
    }

    public static String getUIXMLFileName(Object o, String tag) {
        return tag + o.getClass().getSimpleName() + ".xml";
    }

}
