package androids.impls;

import Utils.FileUtils;
import Utils.XMLUtil;
import androids.AndroidUtils;
import androids.adbs.ADBBuilder;
import androids.adbs.IADBProcess;
import androids.interfaces.IFishPostProductChoiceTagProcess;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FishPostProductChoiceTagProcess extends FishBaseProcess implements IFishPostProductChoiceTagProcess {

    private String uiXmlSaveDirPath;
    private Point edittextBoundsCenter;

    public FishPostProductChoiceTagProcess(IADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath) {

        this.uiXmlSaveDirPath = uiXmlSaveDirPath;

        String uixmlFileName = AndroidUtils.getUIXMLFileName(this, deviceAddress);
        adbProcess.adbGetAndroidUIXML(deviceAddress, uixmlFileName, uiXmlSaveDirPath + uixmlFileName);
        String xmlString = FileUtils.readFile(uiXmlSaveDirPath + uixmlFileName);

        try {
            Element rootElement = XMLUtil.findRootElement(xmlString);
            Element editElement = XMLUtil.findElementByNodeKeyValue(rootElement, "class", "android.widget.EditText");

            edittextBoundsCenter = XMLUtil.getElementBoundsCenter(editElement);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //         adbProcess.adbInputTap(deviceAddress, point.x, point.y);
    }

    @Override
    public void choiceTag(IADBProcess adbProcess, String deviceAddress, String tagName) {

        if (edittextBoundsCenter == null || FileUtils.isEmpty(tagName)) {
            return;
        }

        try {
            new ADBBuilder()
                    .addClick(edittextBoundsCenter)
                    .addText(tagName)
                    .send(deviceAddress, adbProcess);


            String uixmlFileName = AndroidUtils.getUIXMLFileName(this, deviceAddress);
            adbProcess.adbGetAndroidUIXML(deviceAddress, uixmlFileName, uiXmlSaveDirPath + uixmlFileName);
            String xmlString = FileUtils.readFile(uiXmlSaveDirPath + uixmlFileName);
            Element rootElement = XMLUtil.findRootElement(xmlString);

            List<Element> elementByStartWith = XMLUtil.findElementByStartWith(rootElement, Collections.singletonList(tagName), new ArrayList<>());

            if (elementByStartWith.size() > 1) {
                Element element = elementByStartWith.get(1);
                Point elementBoundsCenter = XMLUtil.getElementBoundsCenter(element);
                adbProcess.adbInputTap(deviceAddress, elementBoundsCenter.x, elementBoundsCenter.y);
            } else {
                adbProcess.adbSendBackKeyEvent(deviceAddress);
                adbProcess.adbSendBackKeyEvent(deviceAddress);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
