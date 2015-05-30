package lumaceon.mods.clockworkphase2.api.item.temporal;

import net.minecraft.item.ItemStack;

/**
 * ITemporalable items may not always be considered temporal. These usually require some upgrade to occur before they
 * can be considered temporal.
 */
public interface ITemporalable extends ITemporal
{
    public boolean isTemporal(ItemStack item);
    public void setTemporal(ItemStack item, boolean isTemporal);
}
