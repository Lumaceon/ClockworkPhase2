package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.api.time.Time;
import lumaceon.mods.clockworkphase2.api.time.TimeRegistry;

public class Times
{
    public static Time raw;
    public static Time smelting;
    public static void init()
    {
        raw = new Time("Raw", 1, null);
        smelting = new Time("Smelting", 1, null);

        TimeRegistry.registerTime(raw);
        TimeRegistry.registerTime(smelting);
    }
}
