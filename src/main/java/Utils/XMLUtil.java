package Utils;

import androids.adbs.ADBProcess;
import org.dom4j.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class XMLUtil {

    private final static String[] homePageUITextIndex = {
            "图片未选中",
    };

    public static void findStringXml(String xml) {

        try {

            Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML

            Element rootElt = doc.getRootElement(); // 获取根节点
            System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称

            Element elementByNodeKeyValue = findElementByNodeKeyValue(rootElt, "resource-id", "com.taobao.idlefish:id/collect_grid_item_list");

            ArrayList<Point> points = new ArrayList<>();
            findElementPointByStartWith(elementByNodeKeyValue, Arrays.asList(homePageUITextIndex), points);

            for (Point point: points
                 ) {
                ADBProcess.getInstance().adbInputTap("5ENDU19214004179", point.x, point.y);
            }

//            LinkedHashMap<String, Point> stringPointLinkedHashMap = new LinkedHashMap<>();
//            findAllElementByAttrTextStartWith(elementByNodeKeyValue, stringPointLinkedHashMap, Arrays.asList(homePageUITextIndex));
//            List<Element> nodes = findAllElementByTagName(rootElt, "node", new ArrayList<>());

//            findAllElementByTagName(rootElt, "node", new ArrayList<>());




//            removeElementByAttrText(
//                    nodes,
//                    Arrays.asList(homePageUITextIndex));


        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public static Element findRootElement(String xml) throws DocumentException {
        return DocumentHelper.parseText(xml).getRootElement();
    }

    /**
     * 获取文档中的所有指定元素
     * @param rootElement 根元素
     * @param tagName 指定元素名称
     * @param elements 存放集合
     * @return 文档中的所有指定元素
     */
    public static List<Element> findAllElementByTagName(Element rootElement, String tagName, List<Element> elements) {

        List<Element> mElements = rootElement.elements(QName.get(tagName));
        for (Element element :
                mElements) {
            elements.add(element);
            findAllElementByTagName(element, tagName, elements);
        }

        return elements;
    }


    /**
     * 获取文档中的所有指定元素Point
     * @param rootElement 根元素
     * @param filterString text 文本过滤
     * @param resultElements 存放集合
     * @return 文档中的所有指定元素的Point
     */
    public static List<Point> findElementPointByStartWith(Element rootElement, List<String> filterString, List<Point> resultElements) {


        List<Element> elementByStartWith = findElementByStartWith(rootElement, filterString, new ArrayList<>());

        for (Element element :
                elementByStartWith) {
            resultElements.add(getElementBoundsCenter(element));
        }

        return resultElements;
    }

    /**
     * 获取文档中的所有指定元素
     * @param rootElement 根元素
     * @param filterString text 文本过滤
     * @param resultElements 存放集合
     * @return 文档中的所有指定元素的
     */
    public static List<Element> findElementByStartWith(Element rootElement, List<String> filterString, List<Element> resultElements) {

        List<Element> mElements = rootElement.elements(QName.get("node"));
        for (Element element :
                mElements) {

            if (!attrBoundsIsNull(element)) {

                for (String value : filterString) {
                    if (element.attribute("text").getValue().startsWith(value) || element.attribute("content-desc").getValue().startsWith(value)) {

                        resultElements.add(element);

                        break;
                    }
                }

            }

            findElementByStartWith(element , filterString, resultElements);
        }

        return resultElements;
    }

    /**
     * 获取文档中的所有text
     * @param rootElement 根元素
     * @return 文档中的所有指定元素的
     */
    public static List<String> findElementByTextNotNull(Element rootElement, List<String> resultStrings) {

        List<Element> mElements = rootElement.elements(QName.get("node"));
        String string;
        for (Element element :
                mElements) {

            if (!FileUtils.isEmpty(string = element.attribute("text").getValue()) || !FileUtils.isEmpty(string = element.attribute("content-desc").getValue())) {
                resultStrings.add(string);
            }

            findElementByTextNotNull(element , resultStrings);
        }

        return resultStrings;
    }


    /**
     * @param rootElement 根元素
     * @param key 查找第一个，attr中包含指定键值对的元素
     * @param value 键值对
     * @return 查找第一个，attr中包含指定键值对的元素
     */
    public static Element findElementByNodeKeyValue(Element rootElement, String key, String value) {

        List<Element> elements = rootElement.elements();
        for (Element element :
                elements) {
            Attribute attribute = element.attribute(key);
            if (attribute != null && attribute.getValue() != null && attribute.getValue().equals(value)) {
                return element;
            }
            if ((element = findElementByNodeKeyValue(element, key, value)) != null) {
                return element;
            }
        }

        return null;
    }

    /**
     * @param rootElement 根元素
     * @param key 查找第一个，attr中包含指定键值对的元素
     * @param value 键值对
     * @return 查找第一个，attr中包含指定键值对的元素
     */
    public static Element findElementByNodeStartWithKeyValue(Element rootElement, String key, String value) {

        List<Element> elements = rootElement.elements();
        for (Element element :
                elements) {
            Attribute attribute = element.attribute(key);
            if (attribute != null && attribute.getValue() != null && attribute.getValue().startsWith(value)) {
                return element;
            }
            if ((element = findElementByNodeStartWithKeyValue(element, key, value)) != null) {
                return element;
            }
        }

        return null;
    }

    /**
     * @param elements 指定元素集合
     * @return 所有text字段的值
     */
    public static List<String> findTextByElements(List<Element> elements) {

        ArrayList<String> strings = new ArrayList<>(elements.size());
        String string;
        for (Element element: elements
             ) {

            if (!FileUtils.isEmpty(string = element.attribute("text").getValue()) || !FileUtils.isEmpty(string = element.attribute("content-desc").getValue())) {
                strings.add(string);
            }
        }

        return strings;
    }

    /**
     * @param nodes 元素列表
     * @return 打印所有元素的text content-desc bounds的值
     */
    public static Element printElementByNodeText(List<Element> nodes) {
        for (int i = 0; i < nodes.size(); i++) {

            Element element = nodes.get(i);
            if (attrBoundsIsNull(element)) {
                continue;
            }

            Attribute text = element.attribute("text");
            Attribute contentDesc = element.attribute("content-desc");
            String contentText = text != null && text.getValue() != null && text.getValue().length() > 0 ? text.getValue() : "";
            contentText = contentText.length() != 0 ? contentText : contentDesc != null && contentDesc.getValue() != null && contentDesc.getValue().length() > 0 ? contentDesc.getValue() : "";

            if (contentText.length() == 0) {
                continue;
            }
            System.out.println("\"" + contentText + "\"" + ",");

        }
        return nodes.get(0);
    }

    /**
     * @param nodes 指定元素列表
     * @param filterMap 指定文本内容
     * @return 删除元素列表中，content-desc 或 text 包含 指定string的元素 或者 为空
     */
    public static List<Element> removeElementByAttrTextWithNull(List<Element> nodes, List<String> filterMap) {

        List<Element> removeElement = new ArrayList<>();

        String string;
        for (Element element : nodes) {
            if (!FileUtils.isEmpty(string = element.attribute("text").getValue()) || !FileUtils.isEmpty(string = element.attribute("content-desc").getValue())) {

                for (String item : filterMap) {
                    if (string.contains(item)) {
                        removeElement.add(element);
                    }
                }

            } else {
                removeElement.add(element);
            }
        }
        nodes.removeAll(removeElement);
        return nodes;
    }

    /**
     * 获取文档中的所有指定元素
     * @param rootElement 根元素
     * @param elements 存放集合
     * @return 文档中的所有指定元素
     */
    public static Map<String, Point> findAllElementByAttrTextStartWith(Element rootElement, Map<String, Point> elements, List<String> filterMap) {

        List<Element> mElements = rootElement.elements(QName.get("node"));
        for (Element element :
                mElements) {

            if (!attrBoundsIsNull(element)) {

                for (String value : filterMap) {
                    if (element.attribute("text").getValue().startsWith(value) || element.attribute("content-desc").getValue().startsWith(value)) {

                        Point elementBoundsCenter = getElementBoundsCenter(element);
                        elements.put(value, elementBoundsCenter);

                        break;
                    }
                }

            }

            findAllElementByAttrTextStartWith(element , elements, filterMap);
        }

        return elements;
    }


    /**
     * @param nodes 指定元素列表
     * @param filterMap 指定文本内容
     * @return 查找元素列表中，content-desc 或 text 包含 指定string的元素及坐标中心
     */
    public static Map<String, Point> findElementByAttrTextStartWith(List<Element> nodes, List<String> filterMap) {
        List<String> filterStringKey = new ArrayList<>();
        filterStringKey.add("content-desc");
        filterStringKey.add("text");

        Map<String, Point> resultMap = new LinkedHashMap<>(filterMap.size());

        for (Element element : nodes) {

            if (attrBoundsIsNull(element)) {
                continue;
            }

            for (String key : filterStringKey) {
                for (String value :
                        filterMap) {
                    if (element.attribute(key).getValue().startsWith(value)) {

                        Point elementBoundsCenter = getElementBoundsCenter(element);
                        resultMap.put(key, elementBoundsCenter);

                        break;
                    }
                }
            }
        }

        return resultMap;
    }

    /**
     * @param element 指定元素
     * @return bounds属性是否为null
     */
    public static boolean attrBoundsIsNull(Element element) {
        Attribute bounds = element.attribute("bounds");
        return bounds == null ||
                bounds.getValue().length() == 0 ||
                bounds.getValue().equals("[0,0][0,0]");
    }

    /**
     * @param element 指定元素
     * @return 获取指定元素bounds属性记录的中心位置
     */
    public static Point getElementBoundsCenter(Element element) {
        if (attrBoundsIsNull(element)) {
            return new Point(0, 0);
        }
        Attribute bounds = element.attribute("bounds");
        String value = bounds.getValue();
        value = value.replace("][", "@@");
        String[] split = value.split("@@");
        split[0] = split[0].replace("[", "");
        split[1] = split[1].replace("]", "");


        String[] lifeTop = split[0].split(",");
        int left = Integer.parseInt(lifeTop[0]);
        int top = Integer.parseInt(lifeTop[1]);


        String[] rightBottom = split[1].split(",");
        int right = Integer.parseInt(rightBottom[0]);
        int bottom = Integer.parseInt(rightBottom[1]);

        return getCenterPoint(left, top, right, bottom);
    }

    /**
     * @return 根据四边，计算中心点
     */
    public static Point getCenterPoint(int left, int top, int right, int bottom) {
        int x = left + (right - left) / 2;
        int y = top + (bottom - top) / 2;
        return new Point(x, y);
    }


    /**
     * @param price 数字
     * @param numList 数字对应Map的Key
     * @param numPointMap 通过key活的point
     * @return 指定数字对应point组合
     */
    public static Point[] numKeyBroadPoint(String price, String[] numList, Map<String, Point> numPointMap) {
        Point[] pricePoints = new Point[price.length()];
        int length = price.length();
        for (int i = 0; i < length; i++) {
            int c = price.charAt(i);
            if (c > 47 && c < 58) {
                // 数字
                pricePoints[i] = numPointMap.get(numList[c - 48]);
            } else {
                pricePoints[i] = numPointMap.get(numList[numList.length - 1]);
            }
        }
        return pricePoints;
    }


}
