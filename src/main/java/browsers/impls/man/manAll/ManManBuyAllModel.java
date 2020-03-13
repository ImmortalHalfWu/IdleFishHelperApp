package browsers.impls.man.manAll;

import browsers.beans.ProductInfoBean;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ManManBuyAllModel {

    private int maxProductCount;
    private static ManManBuyAllModel manManBuyModel;

    public static ManManBuyAllModel instance() {
        if (manManBuyModel == null) {
            synchronized (ManManBuyAllModel.class) {
                if (manManBuyModel == null) {
                    manManBuyModel = new ManManBuyAllModel();
                }
            }
        }
        return manManBuyModel;
    }

    private final List<ProductInfoBean> productInfoBeans;


    private ManManBuyAllModel() {
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

    public @Nullable ProductInfoBean get(int positon) {
        if (positon < productInfoBeans.size()) {
            return productInfoBeans.get(positon);
        }
        return null;
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
