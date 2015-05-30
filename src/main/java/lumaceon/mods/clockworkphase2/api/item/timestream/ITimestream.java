package lumaceon.mods.clockworkphase2.api.item.timestream;

import net.minecraft.item.ItemStack;

/**
 * Abstract interface which all other timestreams extend.
 */
public abstract interface ITimestream
{
    //Color methods used for various different rendering.
    public int getColorRed(ItemStack item);
    public int getColorGreen(ItemStack item);
    public int getColorBlue(ItemStack item);
}