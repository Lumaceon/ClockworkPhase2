package lumaceon.mods.clockworkphase2.api.assembly;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

/**
 * To be implemented on items which can be placed into the assembly table's main assembly slot. These methods control
 * various aspects of the sub-GUI that is shown.
 */
public interface IAssemblable
{
    /**
     * Determines what background to draw.
     * @param container The container for the assembly item and it's components.
     * @return The GUI to show.
     */
    public ResourceLocation getGUIBackground(ContainerAssemblyTable container);

    /**
     * The individual slots to be added alongside the inventory.
     *
     * Conventional index list for items clockwork construct items:
     * 0 - Mainspring,
     * 1 - Clockwork.
     * 2+ - Other stuff.
     */
    public Slot[] getContainerSlots(IInventory inventory);
}
