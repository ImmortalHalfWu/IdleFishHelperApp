package Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

    private static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("YYYY/MM/dd HH:mm:ss.SSS");
    public static String getTimeFormat() {
        return TIME_FORMAT.format(new Date());
    }

}
