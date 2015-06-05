package lumaceon.mods.clockworkphase2.api.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ITimeSand
{
    public long getTimeSand(ItemStack item);

    /**
     * Returns only this item's max time sand, does not take into account internal temporal cores.
     * @param item Item to check.
     * @return The max time sand for this item.
     */
    public long getMaxTimeSand(ItemStack item);
    public void setTimeSand(ItemStack item, EntityPlayer player, long timeSand);
    public void setTimeSand(ItemStack item, long timeSand); //Overloaded version for when there is no player involved.
    /**
     * Called to add time sand to the given itemstack.
     * @param item The itemstack to add timesand to.
     * @param player The player using the itemstack if any. Check that this is not null before using for safety.
     * @param amount The amount of time sand to add.
     * @return The amount of time sand that was successfully added.
     */
    public long addTimeSand(ItemStack item, EntityPlayer player, long amount);
    public long addTimeSand(ItemStack item, long amount); //Overloaded version for when there is no player involved.

    /**
     * Called to remove time sand from the given itemstack.
     * @param item The itemstack to consume time sand from.
     * @param player The player using the itemstack if any. Check that this is not null before using for safety.
     * @param amount The amount of time sand to consume.
     * @return The amount of time sand that was successfully consumed.
     */
    public long consumeTimeSand(ItemStack item, EntityPlayer player, long amount);
    public long consumeTimeSand(ItemStack item, long amount); //Overloaded version for when there is no player involved.
}
