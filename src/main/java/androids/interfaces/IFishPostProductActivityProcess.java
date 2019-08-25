package androids.interfaces;

import androids.adbs.IADBProcess;
import androids.beans.UIPostBean;

public interface IFishPostProductActivityProcess extends IFishProcess {

    void postProduct(IADBProcess adbProcess, String deviceAddress, UIPostBean product);

}
