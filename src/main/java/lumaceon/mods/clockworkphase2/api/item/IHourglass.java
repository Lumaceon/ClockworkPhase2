package lumaceon.mods.clockworkphase2.api.item;

import net.minecraft.entity.player.EntityPlayer;
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

    /**
     * Unlike standard item updates, this only runs if it's the first hourglass to be found in the player's inventory.
     * This ensures that only one hourglass can be ticked per tick per player (outside of shenanigans).
     *
     * Note, this won't be called unless the player has an xp level high enough.
     *
     * @param stack The hourglass stack.
     * @param player The player holding the hourglass.
     * @return True if something happened, false if the hourglass did nothing. False may tick other hourglasses.
     */
    public boolean generateTime(ItemStack stack, EntityPlayer player);
}
