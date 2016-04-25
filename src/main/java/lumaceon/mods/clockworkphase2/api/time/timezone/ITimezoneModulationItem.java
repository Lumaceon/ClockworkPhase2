package lumaceon.mods.clockworkphase2.api.time.timezone;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ITimezoneModulationItem
{
    /**
     * Called each time a stack of this item is inserted into a TileTimezoneModulator. This method is only called when
     * the tile has confirmed that the item can be accepted and is called before the tile adds the stack.
     * @param item The stack being placed into the modulator.
     * @param world The world in which this transfer is occurring.
     * @param tile The modulator tile which is accepting this ITimezoneModulationItem.
     * @return A TimezoneModulation instance which represents the modulation this item provides to a timezone.
     */
    public TimezoneModulation createTimezoneModulation(ItemStack item, World world, TileTimezoneModulator tile);
}
