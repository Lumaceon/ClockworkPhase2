package lumaceon.mods.clockworkphase2.item.timezonemodulation;

import lumaceon.mods.clockworkphase2.api.time.timezone.ITimezoneModulationItem;
import lumaceon.mods.clockworkphase2.api.time.timezone.TileTimezoneModulator;
import lumaceon.mods.clockworkphase2.api.time.timezone.TimezoneModulation;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.modulations.TimezoneModulationSanctification;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTimezoneModulationSactification extends ItemClockworkPhase implements ITimezoneModulationItem
{
    public ItemTimezoneModulationSactification(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public TimezoneModulation createTimezoneModulation(ItemStack item, World world, TileTimezoneModulator tile) {
        return new TimezoneModulationSanctification(tile);
    }
}
