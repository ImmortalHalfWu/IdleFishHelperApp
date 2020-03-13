package browsers.impls.man;

import browsers.beans.ProductInfoBean;

import java.util.List;

public interface ManManBuyCallBack {

    void dataSuc(List<ProductInfoBean> productInfoBeans, String saveFilePath);

}
