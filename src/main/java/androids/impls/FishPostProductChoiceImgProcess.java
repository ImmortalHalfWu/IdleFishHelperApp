package androids.impls;

import Utils.FileUtils;
import Utils.XMLUtil;
import androids.AndroidUtils;
import androids.adbs.IADBProcess;
import androids.interfaces.IFishPostProductActivityProcess;
import androids.beans.UIPostBean;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FishPostProductChoiceImgProcess extends FishBaseProcess implements IFishPostProductActivityProcess {


    private String uiXmlSaveDirPath;
    private ArrayList<Point> points;

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

            Element canScrollableElement = XMLUtil.findElementByNodeKeyValue(rootElement, "scrollable", "true");
            List<Element> elements = canScrollableElement.elements();
            points = new ArrayList<>(elements.size());

            for (Element element :
                    elements) {
                Attribute aClass = element.attribute("class");
                if (aClass != null && aClass.getValue().equals("android.widget.ImageView")) {
                    continue;
                }

                /*
                    左上角         右下             宽       高
                    [815,1823][1080,2088]        265      265     中心  946 1954    x加四分之一 948 + 66 1014    y加八分之一1956 + 33  =
                    984  1934   1039 1989 2017     55      55
                 */


                Point elementLeftTopPoint = XMLUtil.getElementLeftTopPoint(element);
                Point elementRightBottomPoint = XMLUtil.getElementRightBottomPoint(element);

                int left = elementLeftTopPoint.x;
                int top = elementLeftTopPoint.y;

                int right = elementRightBottomPoint.x;
                int bottom = elementRightBottomPoint.y;

                int wid = right - left;
                int hei = bottom -top;
                int centerX = left + wid / 2;
                int centerY = top + hei / 2;

                points.add(new Point(centerX + wid / 4, centerY + hei / 8));

            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void postProduct(IADBProcess adbProcess, String deviceAddress, UIPostBean product) {

        int imagLengs = product.getImageList().size();
        imagLengs = imagLengs > 10 ? 10 : imagLengs;
        int pointLengs = points.size();


        for (int i = pointLengs - Math.min(pointLengs, imagLengs); i < pointLengs; i++) {
            adbProcess.adbInputTap(deviceAddress, points.get(i).x, points.get(i).y);
        }


        try {
            String uixmlFileName = AndroidUtils.getUIXMLFileName(this, deviceAddress);
            adbProcess.adbGetAndroidUIXML(deviceAddress, uixmlFileName, uiXmlSaveDirPath + uixmlFileName);
            String xmlString = FileUtils.readFile(uiXmlSaveDirPath + uixmlFileName);
            Element elementByNodeKeyValue = XMLUtil.findElementByNodeStartWithKeyValue(XMLUtil.findRootElement(xmlString), "text", "完成");

            if (elementByNodeKeyValue != null) {
                Point elementLeftTopPoint = XMLUtil.getElementLeftTopPoint(elementByNodeKeyValue);
                Point elementRightBottomPoint = XMLUtil.getElementRightBottomPoint(elementByNodeKeyValue);

                int left = elementLeftTopPoint.x;
                int top = elementLeftTopPoint.y;

                int right = elementRightBottomPoint.x;
                int bottom = elementRightBottomPoint.y;
                Point confirmPoint = new Point(left + (right - left) / 2, bottom + (bottom - top));
                adbProcess.adbInputTap(deviceAddress, confirmPoint.x, confirmPoint.y);
                new FishPostProductAddTagProcess(adbProcess, deviceAddress, uiXmlSaveDirPath).postProduct(adbProcess, deviceAddress, product);
            }

//adb shell input tap 947 1895
//755     1874

                /*
                <node index="27" text="1" resource-id="" class="android.view.View" package="com.taobao.idlefish" content-desc="" checkable="false" checked="false" clickable="false" enabled="true" focusable="true" focused="false" scrollable="false" long-clickable="false"
                password="false" selected="false" bounds="[815,1823][1080,1968]">
                 bounds="[815,1823][1080,1968]"/>
                  bounds="[984,1832][1068,1916]"/>

                    左上角         右下             宽       高
                    [815,1823][1080,2088]        265      265     中心  946 1954    再加四分之一 948 + 66 1014    1956 + 66 =
                    984  1934   1039 1989 2017     55      55



                  右下    1039 2017
                  左上    984  1934


                  宽     高
                  266    265    中心  133 133



                   华为   上2031    下2231   左1240  右1040
                   咸鱼             下1299   左1140

                 */
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }
}
