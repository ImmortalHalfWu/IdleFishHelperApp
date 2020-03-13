package controls.config;

public class DeviceConfigAppProductType {

    public final static DeviceConfigAppProductType TYPE_SELECTED = new DeviceConfigAppProductType(0);
    public final static DeviceConfigAppProductType TYPE_JIU = new DeviceConfigAppProductType(1);

    private final int type;

    public DeviceConfigAppProductType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        if (this.equals(TYPE_SELECTED)) {

            return "Select";
        }
        return "JiuJiu";
    }
}
