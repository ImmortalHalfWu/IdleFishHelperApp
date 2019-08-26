package androids.interfaces;

import androids.adbs.ADBProcess;

public interface IFishHomeActivityProcess extends IFishProcess {

    IFishHomeMyPageProcess toMyPage(ADBProcess adbProcess, String deviceAddress);
    IFishPostProductTypeChoiceProcess toPostProductActivity(ADBProcess adbProcess, String deviceAddress);

}