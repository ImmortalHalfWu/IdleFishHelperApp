package androids.interfaces;

import androids.adbs.IADBProcess;
import androids.beans.UIPostBean;

public interface IFishPostProductTypeChoiceProcess {

    void postProductByFree(IADBProcess adbProcess, String deviceAddress, UIPostBean product);
    void postProductByOrder(IADBProcess adbProcess, String deviceAddress, UIPostBean product);

}
