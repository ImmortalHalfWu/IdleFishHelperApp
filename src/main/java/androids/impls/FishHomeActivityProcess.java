package androids.impls;

import Utils.FileUtils;
import Utils.XMLUtil;
import androids.AndroidUtils;
import androids.adbs.ADBProcess;
import androids.interfaces.IFishHomeActivityProcess;
import androids.interfaces.IFishHomeMyPageProcess;
import androids.interfaces.IFishPostProductTypeChoiceProcess;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class FishHomeActivityProcess extends FishBaseProcess implements IFishHomeActivityProcess {

    private final static String POINT_NAME_MY = "我的，";
    private final static String POINT_NAME_NOT_UPLOAD = "暂不升级";
    private final static String[] HOME_ACTIVITY_UI_INDEX = {POINT_NAME_MY, POINT_NAME_NOT_UPLOAD};
    private Point postClickPoint;

    private IFishHomeMyPageProcess iFishHomeMyPageProcess;
    private IFishPostProductTypeChoiceProcess iFishPostProductActivityProcess;

    public FishHomeActivityProcess(ADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath) {

        uiPoint = new LinkedHashMap<>();
        this.uiXmlSaveDirPath = uiXmlSaveDirPath;
        String uixmlFileName = AndroidUtils.getUIXMLFileName(this, deviceAddress);

        adbProcess.adbGetAndroidUIXML(deviceAddress, uixmlFileName, uiXmlSaveDirPath + uixmlFileName);

        String xmlString = FileUtils.readFile(uiXmlSaveDirPath + uixmlFileName);

        try {

            Element rootElement = XMLUtil.findRootElement(xmlString);
            Element elementPostClick = XMLUtil.findElementByNodeKeyValue(rootElement, "resource-id", "com.taobao.idlefish:id/post_click");
            postClickPoint = XMLUtil.getElementBoundsCenter(elementPostClick);

            XMLUtil.findAllElementByAttrTextStartWith(rootElement, uiPoint, Arrays.asList(HOME_ACTIVITY_UI_INDEX));

            Point point = uiPoint.get(POINT_NAME_NOT_UPLOAD);
            if (point != null) {
                adbProcess.adbInputTap(deviceAddress, point.x, point.y);
            }

            Thread.sleep(1000);

        } catch (DocumentException | InterruptedException e) {
            e.printStackTrace();
        }


    }


    @Override
    public IFishHomeMyPageProcess toMyPage(ADBProcess adbProcess, String deviceAddress) {
        Point point = uiPoint.get(POINT_NAME_MY);
        adbProcess.adbInputTap(deviceAddress, point.x, point.y);
        return iFishHomeMyPageProcess == null ? iFishHomeMyPageProcess = new FishHomeMyPageProcess(adbProcess, deviceAddress, uiXmlSaveDirPath) : iFishHomeMyPageProcess;
    }

    @Override
    public IFishPostProductTypeChoiceProcess toPostProductActivity(ADBProcess adbProcess, String deviceAddress) {
        adbProcess.adbInputTap(deviceAddress, postClickPoint.x, postClickPoint.y);
        adbProcess.adbInputTap(deviceAddress, postClickPoint.x, postClickPoint.y);
        return iFishPostProductActivityProcess = iFishPostProductActivityProcess == null ? new FishPostProductTypeChoiceProcess(adbProcess, deviceAddress, uiXmlSaveDirPath) : iFishPostProductActivityProcess;
    }
}
