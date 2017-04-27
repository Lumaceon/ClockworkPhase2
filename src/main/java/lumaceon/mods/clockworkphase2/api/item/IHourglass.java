package lumaceon.mods.clockworkphase2.api.item;

import net.minecraft.item.ItemStack;

/**
 * Hourglasses are used as somewhat like time batteries. By implementing this, several other items will search and find
 * your item, and assume it has the ITimeStorage capability attached. Expect the TimeStorage to be drained by other
 * sources fairly frequently.
 */
public interface IHourglass
{
    /**
     * @return Whether or not this hourglass is currently active (if inactive, it will usually be ignored).
     */
    public boolean isActive(ItemStack stack);
}
