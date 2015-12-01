package lumaceon.mods.clockworkphase2.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ITimeSand
{
    public int getTimeSand(ItemStack item);

    /**
     * Returns only this item's max time sand, does not take into account internal temporal cores.
     * @param item Item to check.
     * @return The max time sand for this item.
     */
    public int getMaxTimeSand(ItemStack item);
    public void setTimeSand(ItemStack item, EntityPlayer player, int timeSand);
    public void setTimeSand(ItemStack item, int timeSand); //Overloaded version for when there is no player involved.
    /**
     * Called to add time sand to the given itemstack.
     * @param item The itemstack to add timesand to.
     * @param player The player using the itemstack if any. Check that this is not null before using for safety.
     * @param amount The amount of time sand to add.
     * @return The amount of time sand that was successfully added.
     */
    public int addTimeSand(ItemStack item, EntityPlayer player, int amount);
    public int addTimeSand(ItemStack item, int amount); //Overloaded version for when there is no player involved.

    /**
     * Called to remove time sand from the given itemstack.
     * @param item The itemstack to consume time sand from.
     * @param player The player using the itemstack if any. Check that this is not null before using for safety.
     * @param amount The amount of time sand to consume.
     * @return The amount of time sand that was successfully consumed.
     */
    public int consumeTimeSand(ItemStack item, EntityPlayer player, int amount);
    public int consumeTimeSand(ItemStack item, int amount); //Overloaded version for when there is no player involved.
}
