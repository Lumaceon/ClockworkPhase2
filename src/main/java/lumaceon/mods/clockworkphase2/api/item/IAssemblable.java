package lumaceon.mods.clockworkphase2.api.item;

import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyComponents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public interface IAssemblable
{
    /**
     * Used to create and initialize the component inventory.
     * @param container The container for the assembly item and it's components.
     * @return The inventory to be used for components.
     */
    public InventoryAssemblyComponents createComponentInventory(IAssemblyContainer container);

    /**
     * The individual slots to be added alongside the inventory.
     *
     * Conventional indices for clockwork:
     * 0 - Mainspring.
     * 1 - Clockwork.
     */
    public Slot[] getContainerSlots(IInventory inventory);

    /**
     * Called when one (or more) of the itemstacks in the component inventory are changed. This should
     * be used to change the main item.
     * @param container The open container.
     */
    public void onComponentChange(IAssemblyContainer container);

    /**
     * Used to save the component inventory on component update and after the initial setup.
     * @param container The container for the assembly item and it's components.
     */
    public void saveComponentInventory(IAssemblyContainer container);

    /**
     * Called during container setup to initialize buttons.
     * DO NOT import client-side classes here, use a proxy instead.
     * @param buttonList A list of buttons to be added to.
     * @param container The container being set up.
     * @param guiLeft The left coordinate of the gui.
     * @param guiTop The top coordinate of the gui.
     */
    public void initButtons(List buttonList, IAssemblyContainer container, int guiLeft, int guiTop);

    /**
     * Method for handling button clicks, called only on client-side.
     * Be careful, if using the button-list, not to import any minecraft client classes.
     * @param buttonID The ID for the button that was activated.
     * @param buttonList A list of all the buttons currently available in the container.
     */
    public void onButtonActivated(int buttonID, List buttonList);

    public ResourceLocation getBackgroundTexture(IAssemblyContainer container);
}