package androids.impls;

import Utils.MLog;
import Utils.XMLUtil;
import androids.adbs.IADBProcess;
import androids.interfaces.IFishPostProductTypeChoiceProcess;
import androids.beans.UIPostBean;
import org.dom4j.Element;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FishPostProductTypeChoiceProcess extends FishBaseProcess implements IFishPostProductTypeChoiceProcess {

    private final static String POINT_NAME_POST_ORDER = "发布闲置";

    private final static String[] postProductTypeChoiceUITextIndex = {
            POINT_NAME_POST_ORDER,
    };

    private Point freePoint;

    public FishPostProductTypeChoiceProcess(IADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath) {
        super(adbProcess, deviceAddress, uiXmlSaveDirPath, postProductTypeChoiceUITextIndex);
        Element elementByNodeKeyValue = XMLUtil.findElementByNodeKeyValue(rootElement, "resource-id", "com.taobao.idlefish:id/top_entry_first");
        freePoint = XMLUtil.getElementBoundsCenter(elementByNodeKeyValue);
    }

    @Override
    public void postProductByFree(IADBProcess adbProcess, String deviceAddress, UIPostBean product) {

    }

    @Override
    public void postProductByOrder(IADBProcess adbProcess, String deviceAddress, UIPostBean product) {

        Point point = uiPoint.get(POINT_NAME_POST_ORDER);
        if (point == null) {
            return;
        }

        java.util.List<File> imageList = product.getImageList();
        List<File> erroImages = new ArrayList<>(imageList.size());
        for (File imagePath :
                imageList) {

            if (!imagePath.exists()) {
                MLog.logi("发布商品图片上传失败：" + imagePath + "不存在");
                erroImages.add(imagePath);
                continue;
            }
            if (!imagePath.isFile()) {
                erroImages.add(imagePath);
                MLog.logi("发布商品图片上传失败：" + imagePath + "不是文件");
                continue;
            }
            if (!adbProcess.adbPushFile(deviceAddress, imagePath.getAbsolutePath(), imagePath.getName())) {
                erroImages.add(imagePath);
                MLog.logi("发布商品图片上传失败：" + deviceAddress + "__" + imagePath);
                continue;
            }
            adbProcess.adbScanFile(deviceAddress, imagePath.getName());
        }

        imageList.removeAll(erroImages);

        adbProcess.adbInputTap(deviceAddress, point.x, point.y);
        new FishPostProductChoiceImgProcess(
                adbProcess,
                deviceAddress,
                uiXmlSaveDirPath
        ).postProduct(adbProcess, deviceAddress, product);
    }
}
