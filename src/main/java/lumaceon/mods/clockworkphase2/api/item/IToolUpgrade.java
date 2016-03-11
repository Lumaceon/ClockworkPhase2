package lumaceon.mods.clockworkphase2.api.item;

import net.minecraft.item.ItemStack;

/**
 * Tool upgrades to be applied to a temporal excavator.
 */
public interface IToolUpgrade
{
    /**
     * Called to activate or deactivate this tool upgrade
     * @param upgradeStack A stack representing the tool upgrade.
     * @param toolStack A stack representing the tool this is upgrade is in.
     * @param active True to set this to active, false to turn it off.
     */
    public void setActive(ItemStack upgradeStack, ItemStack toolStack, boolean active);

    /**
     * @param upgradeStack The tool upgrade stack.
     * @param toolStack The tool this upgrade is in.
     * @return Whether or not this itemstack is active.
     */
    public boolean getActive(ItemStack upgradeStack, ItemStack toolStack);
}