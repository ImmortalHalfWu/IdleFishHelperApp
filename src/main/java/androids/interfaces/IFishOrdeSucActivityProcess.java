package androids.interfaces;

import androids.adbs.ADBProcess;

import java.util.List;

public interface IFishOrdeSucActivityProcess extends IFishProcess {

    List<String> getAllProductName(ADBProcess adbProcess, String deviceAddress);

}
