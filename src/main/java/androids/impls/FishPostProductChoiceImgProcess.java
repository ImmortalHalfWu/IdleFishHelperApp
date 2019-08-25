package androids.impls;

import Utils.FileUtils;
import Utils.XMLUtil;
import androids.AndroidUtils;
import androids.adbs.IADBProcess;
import androids.interfaces.IFishPostProductActivityProcess;
import androids.beans.UIPostBean;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FishPostProductChoiceImgProcess extends FishBaseProcess implements IFishPostProductActivityProcess {

    private final static String[] choiceProImagUITextIndex = {
            "图片未选中",
    };


    private String uiXmlSaveDirPath;
    private ArrayList<Point> points = new ArrayList<>();

    public FishPostProductChoiceImgProcess(IADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath) {

        this.uiXmlSaveDirPath = uiXmlSaveDirPath;
        String uixmlFileName = AndroidUtils.getUIXMLFileName(this, deviceAddress);

        adbProcess.adbGetAndroidUIXML(deviceAddress, uixmlFileName, uiXmlSaveDirPath + uixmlFileName);
        String xmlString = FileUtils.readFile(uiXmlSaveDirPath + uixmlFileName);

        try {
            Element rootElement = XMLUtil.findRootElement(xmlString);

            if (xmlString.contains("你有一个草稿待发布")) {
                Element elementNotUserOld = XMLUtil.findElementByNodeKeyValue(rootElement, "resource-id", "com.taobao.idlefish:id/left_btn");
                if (elementNotUserOld != null) {
                    Point elementBoundsCenter = XMLUtil.getElementBoundsCenter(elementNotUserOld);
                    adbProcess.adbInputTap(deviceAddress, elementBoundsCenter.x, elementBoundsCenter.y);

                    uixmlFileName = AndroidUtils.getUIXMLFileName(this, deviceAddress);

                    adbProcess.adbGetAndroidUIXML(deviceAddress, uixmlFileName, uiXmlSaveDirPath + uixmlFileName);
                    xmlString = FileUtils.readFile(uiXmlSaveDirPath + uixmlFileName);
                    rootElement = XMLUtil.findRootElement(xmlString);
                }
            }
            XMLUtil.findElementPointByStartWith(rootElement, Arrays.asList(choiceProImagUITextIndex), points);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void postProduct(IADBProcess adbProcess, String deviceAddress, UIPostBean product) {

        int imagLengs = product.getImageList().size();
        int pointLengs = points.size();


        for (int i = pointLengs - Math.min(pointLengs, imagLengs); i < pointLengs; i++) {
            adbProcess.adbInputTap(deviceAddress, points.get(i).x, points.get(i).y);
        }


        try {
            String uixmlFileName = AndroidUtils.getUIXMLFileName(this, deviceAddress);
            adbProcess.adbGetAndroidUIXML(deviceAddress, uixmlFileName, uiXmlSaveDirPath + uixmlFileName);
            String xmlString = FileUtils.readFile(uiXmlSaveDirPath + uixmlFileName);
            Element elementByNodeKeyValue = XMLUtil.findElementByNodeKeyValue(XMLUtil.findRootElement(xmlString), "resource-id", "com.taobao.idlefish:id/confirm");
            Point confirmPoint = XMLUtil.getElementBoundsCenter(elementByNodeKeyValue);
            if (confirmPoint.x != confirmPoint.y) {
                adbProcess.adbInputTap(deviceAddress, confirmPoint.x, confirmPoint.y);
            }

            new FishPostProductAddTagProcess(adbProcess, deviceAddress, uiXmlSaveDirPath).postProduct(adbProcess, deviceAddress, product);


        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
