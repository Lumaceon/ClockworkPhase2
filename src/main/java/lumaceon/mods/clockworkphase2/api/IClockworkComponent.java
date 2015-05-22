package lumaceon.mods.clockworkphase2.api;

import net.minecraft.item.ItemStack;

public interface IClockworkComponent
{
    public int getQuality(ItemStack is);
    public int getSpeed(ItemStack is);
    public int getMemory(ItemStack is);
}
