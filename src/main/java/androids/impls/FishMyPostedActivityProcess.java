package androids.impls;

import Utils.FileUtils;
import Utils.MLog;
import Utils.XMLUtil;
import androids.AndroidUtils;
import androids.adbs.ADBProcess;
import androids.adbs.IADBProcess;
import androids.interfaces.IFishMyPostedActivityProcess;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.awt.*;
import java.util.*;
import java.util.List;


public class FishMyPostedActivityProcess extends FishBaseProcess implements IFishMyPostedActivityProcess {

    public final static String[] productListFilterUITextIndex = {
            "标题栏",
            "返回",
            "宝贝",
            "曝光",
            "划重点：",
            "曝光力",
            "发布",
            "我的问答",
            "¥",
            "留言",
            "浏览",
            "刚刚擦亮",
            "已擦亮",
            "+",
            "箭头",
    };

    private int lastDatasSize = -1;
    private Map<String, PostedDataBean> datas;
    private int downSwipeCount = 0;

    public FishMyPostedActivityProcess(IADBProcess adbProcess, String deviceAddress, String uiXmlSaveDirPath) {
        datas = new LinkedHashMap<>();
        this.uiXmlSaveDirPath = uiXmlSaveDirPath;
        findAllDatas(adbProcess, deviceAddress, null);

        do {
            adbProcess.adbSwipe(deviceAddress, 100, 300, 110, 800, 100);
        } while (--downSwipeCount > 0);
    }


    private void findAllDatas(IADBProcess adbProcess, String deviceAddress, FindAllProductCallBack callBack) {

        String uixmlFileName = AndroidUtils.getUIXMLFileName(this, deviceAddress);

        adbProcess.adbGetAndroidUIXML(deviceAddress, uixmlFileName, uiXmlSaveDirPath + uixmlFileName);

        String xmlString = FileUtils.readFile(uiXmlSaveDirPath + uixmlFileName);

        try {

            Element rootElement = XMLUtil.findRootElement(xmlString);
            List<Element> allNode = XMLUtil.findAllElementByTagName(rootElement, "node", new ArrayList<>());
            List<Element> elements = XMLUtil.removeElementByAttrTextWithNull(allNode, Arrays.asList(productListFilterUITextIndex));
            List<String> textByElements = XMLUtil.findTextByElements(elements);

            try {
                Integer.parseInt(textByElements.get(0));
                textByElements.remove(0);
                elements.remove(0);
            } catch (Exception ignored) { }

            if (elements .size() == 0 || textByElements.size() == 0) {
                return;
            }

            int size = textByElements.size();
            String name = null;
            PostedDataBean postedDataBean = null;
            for (int i = 0; i < size; i++) {

                String elementText = textByElements.get(i);

                if (elementText.equals("擦亮")) {
                    if (postedDataBean != null) {
                        postedDataBean.setRefreshPoint(XMLUtil.getElementBoundsCenter(elements.get(i)));
                    }
                } else if (elementText.equals("编辑")) {

                } else if (elementText.equals("降价")) {

                } else if (elementText.equals("刚刚擦亮")) {

                } else if (elementText.equals("更多")) {
                    if (postedDataBean != null) {
                        postedDataBean.setMorePoint(XMLUtil.getElementBoundsCenter(elements.get(i)));
                        if (callBack != null && callBack.callBack(postedDataBean)) {
                            return;
                        }
                    }
                } else if (elementText.startsWith("一口价")) {

                } else {
                    try {
                        Double.valueOf(elementText);
                        // 价格
                    } catch (Exception ignored) {
                        // 名称
                        postedDataBean = new PostedDataBean(null, null);
                        name = elementText;
                        postedDataBean.setName(elementText);
                        datas.put(name, postedDataBean);
                    }
                }
            }

            downSwipeCount++;
            adbProcess.adbSwipe(deviceAddress, 100, 800, 110, 300, 100);
            Thread.sleep(150);

            if (postedDataBean != null && (name != null && postedDataBean.getMorePoint() == null || postedDataBean.getRefreshPoint() == null)) {
                datas.remove(name);
            }


            MLog.logi(textByElements.toString());
            MLog.logi(datas.toString());


            if (lastDatasSize > 0 && lastDatasSize == datas.size()) {
                return;
            }
            lastDatasSize = datas.size();
            findAllDatas(adbProcess, deviceAddress, callBack);

        } catch (DocumentException | InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Override
    public List<String> getAllProductName(ADBProcess adbProcess, String deviceAddress) {
        return new ArrayList<>(datas.keySet());
    }

    @Override
    public void deleteProductsByName(ADBProcess adbProcess, String deviceAddress, List<String> proNames) {
        findAllDatas(adbProcess, deviceAddress, new FindAllProductCallBack() {
            FishCheckDeletePosedProcess fishCheckDeletePosedProcess;
            @Override
            public boolean callBack(PostedDataBean postedDataBean) {
                String name = postedDataBean.getName();
                if (!FileUtils.isEmpty(name)) {

                    for (String proname : proNames) {
                        if (proname.startsWith(name.replace("...", ""))) {
                            adbProcess.adbInputTap(deviceAddress, postedDataBean.morePoint.x, postedDataBean.morePoint.y);

                            fishCheckDeletePosedProcess =
                                    fishCheckDeletePosedProcess == null ?
                                            fishCheckDeletePosedProcess = new FishCheckDeletePosedProcess(adbProcess, deviceAddress, uiXmlSaveDirPath) :
                                            fishCheckDeletePosedProcess;

                            fishCheckDeletePosedProcess.delete(adbProcess, deviceAddress);

                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }


    @Override
    public void refreshAllProduct(ADBProcess adbProcess, String deviceAddress) {
        findAllDatas(adbProcess, deviceAddress, new FindAllProductCallBack() {
            @Override
            public boolean callBack(PostedDataBean postedDataBean) {
                Point refreshPoint = postedDataBean.getRefreshPoint();
                if (refreshPoint != null) {
                    adbProcess.adbInputTap(deviceAddress, refreshPoint.x, refreshPoint.y);
                }
                return false;
            }
        });
    }

    private interface FindAllProductCallBack {
        boolean callBack(PostedDataBean postedDataBean);
    }

    private final static class PostedDataBean {

        private String name;
        private Point refreshPoint;
        private Point morePoint;

        public PostedDataBean(Point refreshPoint, Point morePoint) {
            this.refreshPoint = refreshPoint;
            this.morePoint = morePoint;
        }

        public void setRefreshPoint(Point refreshPoint) {
            this.refreshPoint = refreshPoint;
        }

        public void setMorePoint(Point morePoint) {
            this.morePoint = morePoint;
        }

        public Point getRefreshPoint() {
            return refreshPoint;
        }

        public Point getMorePoint() {
            return morePoint;
        }

        @Override
        public String toString() {
            return "PostedDataBean{" +
                    "refreshPoint=" + refreshPoint +
                    ", morePoint=" + morePoint +
                    '}';
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
