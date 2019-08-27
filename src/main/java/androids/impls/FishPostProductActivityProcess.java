package androids.impls;

import Utils.FileUtils;
import androids.AndroidUtils;
import androids.adbs.ADBBuilder;
import androids.adbs.IADBProcess;
import androids.interfaces.IFishPostProductActivityProcess;
import androids.beans.UIPostBean;

public class FishPostProductActivityProcess extends FishBaseProcess implements IFishPostProductActivityProcess {

    /*
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
     */

    private final static String POINT_NAME_CLOSE = "关闭";
    private final static String POINT_NAME_TITLE = "发布";
    private final static String POINT_NAME_TITLE_INUT = "标题 品类品牌型号都是买家喜欢搜索的, 输入框";
    private final static String POINT_NAME_PRODUCT_INFO = "描述宝贝的转手原因、入手渠道和使用感受, 输入框";
    private final static String POINT_NAME_NEW = "复选框未选中, 全新宝贝";
    private final static String POINT_NAME_PRICE = "价格";
    private final static String POINT_NAME_TYPE = "分类";
    private final static String POINT_NAME_EMS = "邮寄，";
    private final static String POINT_NAME_YUTANG = "鱼塘：";
    private final static String POINT_NAME_SEND = "确认发布";


    public FishPostProductActivityProcess(IADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath) {
        super(adbProcess, deviceAddress, uiXmlSaveDirPath, AndroidUtils.postProductUITextIndex);
    }

    @Override
    public void postProduct(IADBProcess adbProcess, String deviceAddress, UIPostBean product) {
        try {
            new ADBBuilder()
                    .addClick(uiPoint.get(POINT_NAME_TITLE_INUT))
                    .addText(product.getTitle())
                    .addClick(uiPoint.get(POINT_NAME_PRODUCT_INFO))
                    .addText(product.getInfo())
                    .addClick(uiPoint.get(POINT_NAME_NEW))
                    .addClick(uiPoint.get(POINT_NAME_EMS))
                    .addClick(uiPoint.get(POINT_NAME_PRICE))
                    .addCallBack(() -> new FishPostProductMoneyActivityProcess(adbProcess, deviceAddress, FileUtils.DIR_PATH_XML).postProduct(adbProcess, deviceAddress, product))
                    .addClick(uiPoint.get(POINT_NAME_SEND))
                    .addCallBack(() -> {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    })
                    .addBackClick()
                    .addBackClick()
                    .addCallBack(() -> {
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    })
                    .send(deviceAddress, adbProcess);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}