package lumaceon.mods.clockworkphase2.api.item.clockwork;

import net.minecraft.item.ItemStack;

public interface IClockworkComponent
{
    public int getQuality(ItemStack is);
    public int getSpeed(ItemStack is);

    /**
     * Default clockwork tools will take on the greatest harvest level found in their components.
     *
     * -1 - Hand.
     * 0 - Wood.
     * 1 - Stone.
     * 2 - Iron (and most metals).
     * 3 - Diamond.
     * 4 - Modded materials (Alumite and similar materials from tinker's construct).
     * 5 - Modded materials (Manyullyn from tinker's construct).
     * 6+ - Modded materials.
     * @return This component's harvest level.
     */
    public int getHarvestLevel(ItemStack is);
}
