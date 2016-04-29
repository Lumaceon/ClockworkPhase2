package lumaceon.mods.clockworkphase2.modulations;

import lumaceon.mods.clockworkphase2.api.time.timezone.ITimezoneProvider;
import lumaceon.mods.clockworkphase2.api.time.timezone.TileTimezoneModulator;
import lumaceon.mods.clockworkphase2.api.time.timezone.TimezoneModulation;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.lib.Configs;
import net.minecraft.item.ItemStack;

public class TimezoneModulationSanctification extends TimezoneModulation
{
    public TimezoneModulationSanctification(TileTimezoneModulator tile) {
        super(tile);
    }

    public void onUpdate(ItemStack item, ITimezoneProvider tileEntity) {
        if(Configs.TIME.modulationSanctificationPerTick > 0)
            tileEntity.consumeTime(Configs.TIME.modulationSanctificationPerTick);
    }
}
