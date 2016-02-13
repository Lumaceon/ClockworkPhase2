package lumaceon.mods.clockworkphase2.api.assembly;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

/**
 * To be implemented on item which can be placed into the tiles assembly table's main assembly slot. These methods
 * control various aspects of the sub-GUI
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
     * Used to create and initialize the component inventory. You should NEVER call setInventorySlotContents
     * inside this method. Use setInventorySlotContentsRemotely instead.
     *
     * @param container The container for the assembly item and it's components.
     * @return The inventory to be used for components.
     */
    public InventoryAssemblyTableComponents getGUIInventory(ContainerAssemblyTable container);

    /**
     * The individual slots to be added alongside the inventory.
     *
     * Conventional index list for ITEMs:
     * 0 - Mainspring,
     * 1 - Clockwork.
     * 2+ - Custom stuff.
     *
     * Clockwork tile entities tend to use index 0 for either the mainspring or the tiles only (depending on the
     * tile). After 0 it's usually just custom stuff, though the tiles are theoretically capable of having both a
     * tiles and a mainspring.
     */
    public Slot[] getContainerSlots(IInventory inventory);

    /**
     * Used to save the component inventory on component update and after the initial setup.
     * @param container The container for the assembly item and it's components.
     */
    public void saveComponentInventory(ContainerAssemblyTable container);

    /**
     * Called when the inventory changes. Here is where you would update your item based on the new inventory contents.
     */
    public void onInventoryChange(ContainerAssemblyTable container);
}
