package lumaceon.mods.clockworkphase2.api.item.temporal;

import net.minecraft.item.ItemStack;

/**
 * Used for tools (extending ItemTool ideally) that may not always be considered temporal.
 */
public interface ITemporalableTool extends ITemporalable
{
    /**
     * Each temporal tool is guaranteed one (and only one) active temporal function. This method allows the tool itself
     * to specify how many passive temporal functions this tool can have.
     * @return The maximum number of passive temporal functions for this tool.
     */
    public int getNumberOfPassiveTemporalFunctions(ItemStack item);

    /**
     * Called usually to upgrade this tool and request additional functions slots be added.
     * @param item The item to set.
     */
    public void setNumberOfPassiveTemporalFunctions(ItemStack item, int numberOfFunctions);
}