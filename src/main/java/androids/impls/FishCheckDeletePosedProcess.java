package androids.impls;

import Utils.FileUtils;
import Utils.XMLUtil;
import androids.AndroidUtils;
import androids.adbs.IADBProcess;
import androids.impls.FishBaseProcess;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.awt.*;

public class FishCheckDeletePosedProcess extends FishBaseProcess {

    private final static String POINT_NAME_DELETE = "删除";

    private final static String[] checkDeletePosedUITextIndex = {
            POINT_NAME_DELETE,
    };


    FishCheckDeletePosedProcess(IADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath) {
        super(adbProcess, deviceAddress, uiXmlSaveDirPath, checkDeletePosedUITextIndex);
    }

    public void delete(IADBProcess adbProcess, String deviceAddress) {
        Point point = uiPoint.get(POINT_NAME_DELETE);
        if (point != null) {
            adbProcess.adbInputTap(deviceAddress, point.x, point.y);


            String uixmlFileName = AndroidUtils.getUIXMLFileName(this, deviceAddress);
            adbProcess.adbGetAndroidUIXML(deviceAddress, uixmlFileName, uiXmlSaveDirPath + uixmlFileName);
            String xmlString = FileUtils.readFile(uiXmlSaveDirPath + uixmlFileName);

            try {
                Element rootElement = XMLUtil.findRootElement(xmlString);
                Element elementByNodeKeyValue = XMLUtil.findElementByNodeKeyValue(rootElement, "content-desc", "确认");
                Point elementBoundsCenter = XMLUtil.getElementBoundsCenter(elementByNodeKeyValue);
                adbProcess.adbInputTap(deviceAddress, elementBoundsCenter.x, elementBoundsCenter.y);
            } catch (DocumentException e) {
                e.printStackTrace();
            }


        }
    }

}
