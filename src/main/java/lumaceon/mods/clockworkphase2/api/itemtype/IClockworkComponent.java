package lumaceon.mods.clockworkphase2.api.itemtype;

import net.minecraft.item.ItemStack;

public interface IClockworkComponent
{
    public int getGearQuality(ItemStack is);
    public int getGearSpeed(ItemStack is);
    public int getMemoryValue(ItemStack is);
}
