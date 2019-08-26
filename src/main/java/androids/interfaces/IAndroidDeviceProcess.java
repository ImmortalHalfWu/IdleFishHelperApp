package androids.interfaces;

import androids.beans.UIPostBean;

import java.util.List;

public interface IAndroidDeviceProcess {

    List<String> findPostedProduct();
    List<String> findOrderSucProduct();
    boolean isConnect();
    void refreshConnect();
    String getDeviceAddress();
    void postProduct(UIPostBean uiPostBean);
    void deleteProduct(List<String> productName);
    void startFishApp();
    void installFishApp();
    void deleteFishApp();

}
