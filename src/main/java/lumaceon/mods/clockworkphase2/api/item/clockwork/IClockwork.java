package lumaceon.mods.clockworkphase2.api.item.clockwork;

import net.minecraft.item.ItemStack;

public interface IClockwork
{
    public int getQuality(ItemStack item);
    public int getSpeed(ItemStack item);
}
