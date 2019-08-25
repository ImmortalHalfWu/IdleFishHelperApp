package androids.impls;

import androids.adbs.ADBProcess;
import androids.interfaces.IFishOrdeSucActivityProcess;

import java.util.List;

public class FishOrdeSucActivityProcess extends FishBaseProcess implements IFishOrdeSucActivityProcess {
    @Override
    public List<String> getAllProductName(ADBProcess adbProcess, String deviceAddress) {
        return null;
    }
}
