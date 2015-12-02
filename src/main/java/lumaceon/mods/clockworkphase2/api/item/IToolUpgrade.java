package lumaceon.mods.clockworkphase2.api.item;

import net.minecraft.item.ItemStack;

public interface IToolUpgrade
{
    /**
     * Called to activate or deactivate this tool upgrade
     * @param item A stack representing the tool upgrade.
     * @param active True to set this to active, false to turn it off.
     */
    public void setActive(ItemStack item, boolean active);

    /**
     * @param item The tool upgrade stack.
     * @return Whether or not this itemstack is active.
     */
    public boolean getActive(ItemStack item);

    public float getQualityMultiplier(ItemStack item);
    public float getSpeedMultiplier(ItemStack item);
}