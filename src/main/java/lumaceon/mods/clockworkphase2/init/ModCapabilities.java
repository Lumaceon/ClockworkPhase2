package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.capabilities.CapabilityActivatable;
import lumaceon.mods.clockworkphase2.capabilities.CapabilityTimeStorage;
import lumaceon.mods.clockworkphase2.capabilities.CapabilityTimezone;

public class ModCapabilities
{
    public static void init()
    {
        CapabilityActivatable.register();
        CapabilityTimeStorage.register();
        CapabilityTimezone.register();
    }
}
