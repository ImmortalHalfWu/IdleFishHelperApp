package androids.interfaces;

import androids.adbs.ADBProcess;

import java.util.List;

public interface IFishMyPostedActivityProcess extends IFishProcess {

    List<String> getAllProductName(ADBProcess adbProcess, String deviceAddress);
    void deleteProductsByName(ADBProcess adbProcess, String deviceAddress, List<String> proName);
    void refreshAllProduct(ADBProcess adbProcess, String deviceAddress);

}
