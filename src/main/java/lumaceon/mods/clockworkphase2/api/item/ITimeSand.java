package lumaceon.mods.clockworkphase2.api.item;

import net.minecraft.item.ItemStack;

public interface ITimeSand
{
    public long getTimeSand(ItemStack item);
    public long getMaxTimeSand(ItemStack item);
    public void setTimeSand(ItemStack item, long timeSand);
    /**
     * Called to add time sand to the given itemstack.
     * @param item The itemstack to add timesand to.
     * @param amount The amount of time sand to add.
     * @return Overspill (the amount that couldn't be added for whatever reason).
     */
    public long addTimeSand(ItemStack item, long amount);

    /**
     * Called to remove time sand from the given itemstack.
     * @param item The itemstack to consume time sand from.
     * @param amount The amount of time sand to consume.
     * @return Overspill (the amount that couldn't be removed, usually because the item runs out).
     */
    public long consumeTimeSand(ItemStack item, long amount);
}
