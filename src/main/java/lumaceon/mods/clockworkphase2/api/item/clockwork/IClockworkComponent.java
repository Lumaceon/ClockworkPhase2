package lumaceon.mods.clockworkphase2.api.item.clockwork;

import net.minecraft.item.ItemStack;

public interface IClockworkComponent extends IClockwork
{
    /**
     * Default clockwork tools will take on the greatest 'tier' found in their components as their harvest level.
     * Machines may ignore this depending on the machine in question. There is no standard convention for component tier
     * use in machines, so base your tier off of the (sometimes theoretical) pickaxe mining level.
     * -1 - Hand.
     * 0 - Wood pickaxe.
     * 1 - Stone pickaxe.
     * 2 - Iron pickaxe.
     * 3 - Diamond pickaxe.
     * 4 - Mod tools (Not used in vanilla, a good example is the Alumite pickaxe from Tinker's Construct).
     * 5 - Mod tools (Not used in vanilla, a good example is the Manyullyn pickaxe from Tinker's Construct).
     * @return This component's tier.
     */
    public int getTier(ItemStack is);
}
