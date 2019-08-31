package browsers.queues;

import browsers.beans.ProductInfoBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ManManBuyModel {

    private int maxProductCount;
    private static ManManBuyModel manManBuyModel;

    public static ManManBuyModel instance() {
        if (manManBuyModel == null) {
            synchronized (ManManBuyModel.class) {
                if (manManBuyModel == null) {
                    manManBuyModel = new ManManBuyModel();
                }
            }
        }
        return manManBuyModel;
    }

    private final List<ProductInfoBean> productInfoBeans;


    private ManManBuyModel() {
        this.productInfoBeans = Collections.synchronizedList(new ArrayList<>());
    }


    public void addManManBuyProducts(Collection<ProductInfoBean> c) {
        productInfoBeans.addAll(c);
    }
    public void addManManBuyProduct(ProductInfoBean c) {
        productInfoBeans.add(c);
    }

    public List<ProductInfoBean> getAllProduct() {
        return productInfoBeans;
    }

    public void removeProduct(ProductInfoBean productInfoBean) {
        if (productInfoBean != null && productInfoBeans != null) {
            productInfoBeans.remove(productInfoBean);
        }
    }
    public void removeAllProduct(List<ProductInfoBean> productInfoBean) {
        if (productInfoBean != null && productInfoBean.size() > 0) {
            productInfoBeans.removeAll(productInfoBean);
        }
    }

    public int getProductCurrentCount() {
        return productInfoBeans.size();
    }

    public int getMaxProductCount() {
        return maxProductCount;
    }

    public void setMaxProductCount(int maxProductCount) {
        this.maxProductCount = maxProductCount;
    }
}
