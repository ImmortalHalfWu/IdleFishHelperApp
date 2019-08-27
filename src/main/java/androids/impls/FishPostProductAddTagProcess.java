package androids.impls;

import Utils.XMLUtil;
import androids.adbs.IADBProcess;
import androids.interfaces.IFishPostProductActivityProcess;
import androids.beans.UIPostBean;
import org.dom4j.Element;

import java.awt.*;
import java.util.List;

public class FishPostProductAddTagProcess extends FishBaseProcess implements IFishPostProductActivityProcess {

    private String uiXmlSaveDirPath;
    private final static String POINT_NAME_EDIT = "添加说明标签可被更多人看见";
    private final static String POINT_NAME_NEXT= "下一步";

    private final static String[] choiceTagUITextIndex = {
            POINT_NAME_EDIT,
    };


    public FishPostProductAddTagProcess(IADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath) {
        super(adbProcess, deviceAddress, uiXmlSaveDirPath, choiceTagUITextIndex);
        this.uiXmlSaveDirPath = uiXmlSaveDirPath;

        Element nextElement = XMLUtil.findElementByNodeKeyValue(rootElement, "index", "8");
        if (nextElement != null) {
            Point elementBoundsCenter = XMLUtil.getElementBoundsCenter(nextElement);
            Point elementRightBottomPoint = XMLUtil.getElementRightBottomPoint(nextElement);

            uiPoint.put(POINT_NAME_NEXT, new Point(elementBoundsCenter.x, elementRightBottomPoint.y));
        }

    }

    @Override
    public void postProduct(IADBProcess adbProcess, String deviceAddress, UIPostBean product) {

        Point point = uiPoint.get(POINT_NAME_EDIT);
        if (point == null) {
            return;
        }

        FishPostProductChoiceTagProcess choiceTagProcess = null;
        List<String> imgTag = product.getImgTag();

        for (String tag :
                imgTag) {
            adbProcess.adbInputTap(deviceAddress, point.x, point.y);
//            choiceTagProcess = choiceTagProcess == null ? new FishPostProductChoiceTagProcess(adbProcess, deviceAddress, uiXmlSaveDirPath) : choiceTagProcess;
            if (choiceTagProcess == null) {
                choiceTagProcess = new FishPostProductChoiceTagProcess(adbProcess, deviceAddress, uiXmlSaveDirPath);
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            choiceTagProcess.choiceTag(adbProcess, deviceAddress, tag);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        point = uiPoint.get(POINT_NAME_NEXT);
        adbProcess.adbInputTap(deviceAddress, point.x, point.y);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new FishPostProductActivityProcess(adbProcess, deviceAddress, uiXmlSaveDirPath)
                .postProduct(adbProcess, deviceAddress, product);
    }
}
