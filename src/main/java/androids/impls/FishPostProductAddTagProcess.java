package androids.impls;

import androids.adbs.IADBProcess;
import androids.interfaces.IFishPostProductActivityProcess;
import androids.beans.UIPostBean;

import java.awt.*;
import java.util.List;

public class FishPostProductAddTagProcess extends FishBaseProcess implements IFishPostProductActivityProcess {

    private String uiXmlSaveDirPath;
    private final static String POINT_NAME_EDIT = "标签";
    private final static String POINT_NAME_NEXT= "下一步";

    private final static String[] choiceTagUITextIndex = {
            POINT_NAME_EDIT,
            POINT_NAME_NEXT,
    };


    public FishPostProductAddTagProcess(IADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath) {
        super(adbProcess, deviceAddress, uiXmlSaveDirPath, choiceTagUITextIndex);
        this.uiXmlSaveDirPath = uiXmlSaveDirPath;
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
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new FishPostProductActivityProcess(adbProcess, deviceAddress, uiXmlSaveDirPath)
                .postProduct(adbProcess, deviceAddress, product);
    }
}
