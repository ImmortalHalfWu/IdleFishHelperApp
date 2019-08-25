package androids.impls;

import Utils.FileUtils;
import Utils.MLog;
import Utils.XMLUtil;
import androids.AndroidUtils;
import androids.adbs.IADBProcess;
import androids.interfaces.IFishProcess;
import org.dom4j.Element;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class FishBaseProcess implements IFishProcess {

    Map<String, Point> uiPoint;
    Element rootElement;
    String uiXmlSaveDirPath;

    FishBaseProcess() { }

    FishBaseProcess(IADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath, String[] uiTextIndex) {
        init(adbProcess, deviceAddress, uiXmlSaveDirPath, uiTextIndex);
    }

    protected void init(IADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath, String[] uiTextIndex) {

        this.uiXmlSaveDirPath = uiXmlSaveDirPath;
        String uixmlFileName = AndroidUtils.getUIXMLFileName(this, deviceAddress);
        adbProcess.adbGetAndroidUIXML(deviceAddress, uixmlFileName, uiXmlSaveDirPath + uixmlFileName);
        String xmlString = FileUtils.readFile(uiXmlSaveDirPath + uixmlFileName);

        try {
            uiPoint = new LinkedHashMap<>();
            XMLUtil.findAllElementByAttrTextStartWith(
                    rootElement = XMLUtil.findRootElement(xmlString),
                    uiPoint,
                    Arrays.asList(uiTextIndex)
            );

            MLog.logi(uiPoint.toString());

        } catch (Exception e) {
            e.printStackTrace();
            MLog.logi(e.getMessage());
        }

    }

}
