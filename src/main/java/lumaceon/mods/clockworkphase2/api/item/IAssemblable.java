package lumaceon.mods.clockworkphase2.api.item;

import lumaceon.mods.clockworkphase2.api.assembly.AssemblySlot;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyComponents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public interface IAssemblable
{
    /**
     * The individual slots to be added alongside the inventory.
     *
     * Conventional indices for clockwork:
     * 0 - Mainspring.
     * 1 - Clockwork.
     */
    public AssemblySlot[] initializeSlots(ItemStack workItem);

    /**
     * Called when one of the stacks in the component inventory is changed. This should be used to setup the main item.
     */
    public void onComponentChange(ItemStack workItem, AssemblySlot[] slots);

    /**
     * Used to save the component inventory on component update and after the initial setup.
     */
    public void saveComponentInventory(ItemStack workItem, AssemblySlot[] slots);

    /**
     * Called during container setup to initialize buttons.
     * DO NOT import client-side classes here, use a proxy instead.
     * @param buttonList A list of buttons to be added to.
     * @param container The container being set up.
     * @param guiLeft The left coordinate of the gui.
     * @param guiTop The top coordinate of the gui.

    public void initButtons(List buttonList, IAssemblyContainer container, int guiLeft, int guiTop);

    /**
     * Method for handling button clicks, called only on client-side.
     * Be careful, if using the button-list, not to import any minecraft client classes.
     * @param buttonID The ID for the button that was activated.
     * @param buttonList A list of all the buttons currently available in the container.

    public void onButtonActivated(int buttonID, List buttonList);

    public ResourceLocation getBackgroundTexture(IAssemblyContainer container);*/
}