package androids.interfaces;

import androids.beans.UIPostBean;

import java.util.List;

public interface IAndroidDeviceProcess {

    List<String> findPostedProduct();
    List<String> findOrderSucProduct();
    boolean isConnect();
    String getDeviceAddress();
    void postProduct(UIPostBean uiPostBean);
    void startFishApp();
    void installFishApp();
    void deleteFishApp();

}
