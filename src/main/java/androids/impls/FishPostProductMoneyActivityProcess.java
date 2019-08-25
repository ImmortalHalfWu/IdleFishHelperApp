package androids.impls;

import Utils.XMLUtil;
import androids.AndroidUtils;
import androids.adbs.ADBBuilder;
import androids.adbs.IADBProcess;
import androids.interfaces.IFishPostProductActivityProcess;
import androids.beans.UIPostBean;

public class FishPostProductMoneyActivityProcess extends FishBaseProcess implements IFishPostProductActivityProcess {

    /*
            "价格",
            "未选中,不讲价,复选框, 不讲价， 单选按钮",
            "入手价",
            "未选中, 包邮， 单选按钮",
            "确定",
     */

    private final static String POINT_NAME_PRICE = "价格";
    private final static String POINT_NAME_JIANG_JIA = "未选中,不讲价,复选框, 不讲价， 单选按钮";
    private final static String POINT_NAME_OLD_PRICE = "入手价";
    private final static String POINT_NAME_EMS = "未选中, 包邮， 单选按钮";
    private final static String POINT_NAME_SUC = "确定";


    private final static String[] postProductMoneyNumUITextIndex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "小数点",};


    FishPostProductMoneyActivityProcess(IADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath) {
        super(adbProcess, deviceAddress, uiXmlSaveDirPath, AndroidUtils.postProductMoneyUITextIndex);
    }

    @Override
    public void postProduct(IADBProcess adbProcess, String deviceAddress, UIPostBean product) {


        try {
            new ADBBuilder()
                    .addClick(uiPoint.get(POINT_NAME_PRICE), 200, 0)
                    .addClick(XMLUtil.numKeyBroadPoint(product.getPrice(), postProductMoneyNumUITextIndex, uiPoint))
                    .addClick(uiPoint.get(POINT_NAME_JIANG_JIA))
                    .addClick(uiPoint.get(POINT_NAME_OLD_PRICE), 200, 0)
                    .addClick(XMLUtil.numKeyBroadPoint(product.getOldPrice(), postProductMoneyNumUITextIndex, uiPoint))
                    .addClick(uiPoint.get(POINT_NAME_EMS))
                    .addClick(uiPoint.get(POINT_NAME_SUC))
                    .send(deviceAddress, adbProcess);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
