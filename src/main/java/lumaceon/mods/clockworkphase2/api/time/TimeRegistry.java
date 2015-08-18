package lumaceon.mods.clockworkphase2.api.time;

import java.util.HashMap;
import java.util.Map;

public class TimeRegistry
{
    public static Map<String, Time> times = new HashMap<String, Time>();

    public static void registerTime(Time time) {
        if(time != null && time.getName() != null)
            times.put(time.getName(), time);
    }

    public static Time getTime(String name) {
        return times.get(name);
    }
}
