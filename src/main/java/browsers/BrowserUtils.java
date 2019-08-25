package browsers;

import Utils.MLog;
import browsers.beans.ProductInfoBean;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BrowserUtils {

    private static String[] nameFilter = new String[]{"第2件10元", "买", "卖", "赠", "旗舰", "半价", "件", "双", "送", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    public static String formatProductName(String productNameT) {
        if (productNameT.contains("】")) {
            // 买卖   赠   旗舰  半价  件   送   0-9
            String substring;
            substring = productNameT.substring(productNameT.contains("【") ? productNameT.indexOf("【") : 0, productNameT.indexOf("】"));

            for (String filter :
                    nameFilter) {
                if (substring.contains(filter)) {
                    productNameT = productNameT.replace(substring, "").replace(filter, "");
                    break;
                }
            }
            productNameT = productNameT.replace("【", "").replace("】", "");
        }

        else if (productNameT.contains(" ")) {

            String[] s = productNameT.split(" ");
            StringBuilder productNameBuilder = new StringBuilder();
            for (int i = 1; i < s.length; i++) {
                productNameBuilder.append(s[i]);
            }
            productNameT = productNameBuilder.toString().replace(" ", "").trim();    // 商品名称
        }

        return productNameT;
    }


    public static void sortProductForPriceAndOrderNum(List<ProductInfoBean> productInfoBeans) {

        productInfoBeans.sort((o1, o2) -> {

            // 1,  销量分数，取大
            double orderGradeA = o1.getOrderNum() * 1f / o2.getOrderNum(), orderGradeB = o2.getOrderNum() * 1f / o1.getOrderNum();
            orderGradeA = orderGradeA > 1 ? 1 : orderGradeA;
            orderGradeB = orderGradeB > 1 ? 1 : orderGradeB;

            // 2, 原价分数，取大
            double oldPriceGradeA, oldPriceGradeB;
            double priceSum = o1.getPriceOld() + o2.getPriceOld();
            priceSum /= 100;
            oldPriceGradeA = o1.getPriceOld() / priceSum / 100 * 2;
            oldPriceGradeB = o2.getPriceOld() / priceSum / 100 * 2;

            // 3, 优惠分数，取大
            double yhqGradeA = (100 - o1.getPriceCurrent() / (o1.getPriceOld() / 100)) / 100 * 3;
            double yhqGradeB = (100 - o2.getPriceCurrent() / (o2.getPriceOld() / 100)) / 100 * 3;

            // 4, 现价分数，取小
            double priceCurrentSum = (o1.getPriceCurrent() + o2.getPriceCurrent()) / 100;
            double priceCurrentGradeA = (100 - o1.getPriceCurrent() / priceCurrentSum) / 100 * 3;
            double priceCurrentGradeB = (100 - o2.getPriceCurrent() / priceCurrentSum) / 100 * 3;

            return Double.compare(
                    orderGradeA + oldPriceGradeA + yhqGradeA + priceCurrentGradeA,
                    orderGradeB + oldPriceGradeB + yhqGradeB + priceCurrentGradeB
            ) * -1;
        });

    }


    /* //日期转换为时间戳 */
    public static long timeToStamp(String timers) {
        Date d = new Date();
        long timeTemp;
        try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy.MM.dd");
            d = sf.parse(timers);// 日期转换为时间戳
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timeTemp = d.getTime();
        return timeTemp;
    }

    /**
     * @return 当天零点时间戳
     */
    public static long getTodayZeroTime() {
        long nowTime =System.currentTimeMillis();
        return nowTime - (nowTime + TimeZone.getDefault().getRawOffset())% (1000*3600*24);
    }

    /**
     * @param title 指定字符串，获取名词，动词，形容词
     * @return 名词，动词，形容词
     */
    public static List<String> findFengCi(String title) {
        List<Term> terms = ToAnalysis.parse(title).getTerms();
        HashSet<String> resultList = new HashSet<>(terms.size());
        for(Term term : terms) {
            String natureStr = term.getNatureStr();
            if (natureStr.startsWith("n") || natureStr.startsWith("a") || natureStr.startsWith("b")) {
                resultList.add(term.getName());
            }
        }
        return new ArrayList<>(resultList);
    }

    /**
     * @return 获得天猫淘宝的商品图片
     */
    public static List<String> findTmailTaoBaoProductImg(List<DOMElement> imgs) {
        List<String> productImgs = new ArrayList<>(imgs.size());
        int size = imgs.size();
        int start = size <= 5 ? 0 : size / 3;
        size = start + 10 >= size ? size : start + 10;
        for (int i = start; i < size; i++) {

            DOMElement domElement = imgs.get(i);
            String src = "";
            if (domElement.hasAttribute("data-ks-lazyload")) {
                src = domElement.getAttribute("data-ks-lazyload");
            } else if (domElement.hasAttribute("src")) {
                src = domElement.getAttribute("src");
            }
            src = src.startsWith("http") ? src : "http:" + src;
            productImgs.add(src);
        }
        return productImgs;
    }

    /**
     * @return 明天00：00
     */
    public static long timeTomorrowBegin() {
        return getTodayZeroTime() + 60 * 60 * 24 * 1000;
    }

    public static void log(Object info) {
        MLog.logi(info.toString());
    }

    public static void logLine() {
        MLog.logi("================================================================================");
    }

    public static void logErroLine() {
        MLog.logi("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
    }
}
