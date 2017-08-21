package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.capabilities.activatable.CapabilityActivatable;
import lumaceon.mods.clockworkphase2.capabilities.coordinate.CapabilityCoordinate;
import lumaceon.mods.clockworkphase2.capabilities.custombehavior.CapabilityCustomBehavior;
import lumaceon.mods.clockworkphase2.capabilities.entitycontainer.CapabilityEntityContainer;
import lumaceon.mods.clockworkphase2.capabilities.machinedata.CapabilityMachineData;
import lumaceon.mods.clockworkphase2.capabilities.mode.CapabilityMode;
import lumaceon.mods.clockworkphase2.capabilities.stasis.CapabilityStasis;
import lumaceon.mods.clockworkphase2.capabilities.stasis.stasisitem.CapabilityStasisItem;
import lumaceon.mods.clockworkphase2.capabilities.timestorage.CapabilityTimeStorage;
import lumaceon.mods.clockworkphase2.capabilities.CapabilityTimezone;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.CapabilityTemporalToolbelt;

public class ModCapabilities
{
    public static void init()
    {
        CapabilityActivatable.register();
        CapabilityTimeStorage.register();
        CapabilityMachineData.register();
        CapabilityTimezone.register();
        CapabilityTemporalToolbelt.register();
        CapabilityMode.register();
        CapabilityStasis.register();
        CapabilityStasisItem.register();
        CapabilityEntityContainer.register();
        CapabilityCustomBehavior.register();
        CapabilityCoordinate.register();
    }
}
