package androids.impls;

import Utils.XMLUtil;
import androids.adbs.ADBProcess;
import androids.adbs.IADBProcess;
import androids.interfaces.IFishHomeMyPageProcess;
import androids.interfaces.IFishMyPostedActivityProcess;
import androids.interfaces.IFishOrdeSucActivityProcess;
import org.dom4j.Element;

import java.awt.*;

public class FishHomeMyPageProcess extends FishBaseProcess  implements IFishHomeMyPageProcess {


    private final static String POINT_NAME_MY_POSTED = "我发布的 ";
    private final static String POINT_NAME_MY_OEDER_SUC = "我卖出的 ";


    private final static String[] homePageMyUITextIndex = {
            POINT_NAME_MY_POSTED,
            POINT_NAME_MY_OEDER_SUC,
            "参与的免费送",
            "闲鱼，",
            "鱼塘，",
            "消息，",
            "发布",
    };

    private IFishMyPostedActivityProcess iFishMyPostedActivityProcess;
    private IFishOrdeSucActivityProcess iFishOrdeSucActivityProcess;

    public FishHomeMyPageProcess(IADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath) {
        adbProcess.adbSwipe(deviceAddress, 300, 300, 300, 800, 300);
        adbProcess.adbSwipe(deviceAddress, 300, 300, 300, 800, 300);
        adbProcess.adbSwipe(deviceAddress, 300, 300, 300, 800, 300);

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        init(adbProcess, deviceAddress, uiXmlSaveDirPath, homePageMyUITextIndex);
    }


    @Override
    public String getPostedNum(ADBProcess adbProcess, String deviceAddress) {

        Element text = XMLUtil.findElementByNodeStartWithKeyValue(rootElement, "text", POINT_NAME_MY_POSTED);
        if (text == null || text.attribute("text") == null) {
            return "0";
        }

        return text.attribute("text").getValue().replace(POINT_NAME_MY_POSTED, "");
    }

    @Override
    public IFishMyPostedActivityProcess toMyPostedActivity(ADBProcess adbProcess, String deviceAddress) {

        Point point = uiPoint.get(POINT_NAME_MY_POSTED);
        if (point == null) {
            return null;
        }

        adbProcess.adbInputTap(deviceAddress, point.x, point.y);

        return iFishMyPostedActivityProcess == null ? iFishMyPostedActivityProcess = new FishMyPostedActivityProcess(adbProcess, deviceAddress, uiXmlSaveDirPath) : iFishMyPostedActivityProcess;
    }

    @Override
    public IFishOrdeSucActivityProcess toMyOrderSucActivity(ADBProcess adbProcess, String deviceAddress) {

        Point point = uiPoint.get(POINT_NAME_MY_OEDER_SUC);
        if (point == null) {
            return null;
        }

        adbProcess.adbInputTap(deviceAddress, point.x, point.y);

        return iFishOrdeSucActivityProcess == null ? iFishOrdeSucActivityProcess = new FishOrdeSucActivityProcess() : iFishOrdeSucActivityProcess;
    }
}
