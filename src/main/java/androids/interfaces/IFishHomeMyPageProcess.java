package androids.interfaces;

import androids.adbs.ADBProcess;

public interface IFishHomeMyPageProcess extends IFishProcess {

    String getPostedNum(ADBProcess adbProcess, String deviceAddress);
    IFishMyPostedActivityProcess toMyPostedActivity(ADBProcess adbProcess, String deviceAddress);
    IFishOrdeSucActivityProcess toMyOrderSucActivity(ADBProcess adbProcess, String deviceAddress);

}
