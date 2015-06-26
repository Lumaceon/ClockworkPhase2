package lumaceon.mods.clockworkphase2.api.item;

import net.minecraft.item.ItemStack;

public interface ITemporalToolModule
{
    /**
     * @param item The IToolTimestream stack.
     * @return Whether or not this itemstack is enabled, allowing the player to disable functions.
     */
    public boolean isEnabled(ItemStack item);

    public float getQualityMultiplier(ItemStack item);
    public float getSpeedMultiplier(ItemStack item);
    public float getMemoryMultiplier(ItemStack item);

    public int getColorRed(ItemStack item);
    public int getColorGreen(ItemStack item);
    public int getColorBlue(ItemStack item);
}