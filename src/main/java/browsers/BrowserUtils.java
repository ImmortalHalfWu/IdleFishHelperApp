package browsers;

import Utils.MLog;
import browsers.beans.ProductInfoBean;

import java.util.Comparator;
import java.util.List;

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

    public static void log(Object info) {
        MLog.logi(info.toString());
    }
}
