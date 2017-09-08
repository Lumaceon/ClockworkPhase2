package lumaceon.mods.clockworkphase2.inventory;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblableButtons;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * The client handles item changes a little differently. Items are completely recreated when a sync occurs. This means
 * that the main item will never be seen as equal, even if the item is the exact same on the server. EVERY time the
 * craft matrix is changed, it would think the main item has changed.
 *
 * Since the client is updated from the server, we only care about the difference in slots, not the actual item. Even
 * if the item is different, in some cases, the slots can remain the same, as the server will tell us the items are
 * different.
 *
 * We also use a very simple inventory here that just contains the items the server claims is there. Our item handler
 * isn't reliable on the client, as nothing really updates it.
 */
public class ContainerAssemblyTableClient extends ContainerAssemblyTable
{
    public InventorySimple inventory;

    public ContainerAssemblyTableClient(InventoryPlayer ip, World world) {
        super(ip, world);
    }

    @Override
    public void onCraftMatrixChanged(IInventory p_75130_1_)
    {
        ItemStack item = mainInventory.getStackInSlot(0);

        //On the client, we care about 3 conditions: new item is added, old item is taken out, and a DIFFERENT TYPE of item was added.
        //This differs from the server, where it's considered a change if the new item is the same type, but a different itemstack instance.
        //As long as the item type is the same, the slots will be the same, so the client doesn't need to update slots.
        boolean mainItemChanged = item.isEmpty() != previousMainStack.isEmpty() || (!item.isEmpty() && !item.getItem().equals(previousMainStack.getItem()));

        //Handle changes to the main item.
        if(mainItemChanged)
        {
            //Remove the component inventory if the change is removal of the main item.
            if((item.isEmpty() || !(item.getItem() instanceof IAssemblable)) && inventory != null)
            {
                cleanContainerAndGUI();
            }

            //Set up the new component inventory if the main item has changed from a separate main item.
            if(!item.isEmpty() && inventory != null && item.getItem() instanceof IAssemblable)
            {
                cleanContainerAndGUI();

                setupNewContainerAndGUI(item);
            }

            //Set up a component inventory if the main item has been added from the default screen.
            if(!item.isEmpty() && inventory == null && item.getItem() instanceof IAssemblable)
            {
                setupNewContainerAndGUI(item);
            }
        }

        previousMainStack = item;
    }

    @Override
    protected void setupNewContainerAndGUI(ItemStack item)
    {
        IAssemblable constructGUI = (IAssemblable) item.getItem();
        Slot[] slots = constructGUI.getContainerSlots(null);
        inventory = new InventorySimple(slots.length, 1);
        slots = constructGUI.getContainerSlots(inventory);
        for(Slot slot : slots)
        {
            this.addSlotToContainer(slot);
        }

        if(item.getItem() instanceof IAssemblableButtons && buttonList != null && guiLeft != -1 && guiTop != -1)
        {
            IAssemblableButtons buttonGUI = (IAssemblableButtons) item.getItem();

            buttonGUI.initButtons(buttonList, this, guiLeft, guiTop);
        }
    }

    @Override
    protected void cleanContainerAndGUI()
    {
        inventory = null;
        while (this.inventorySlots.size() > 37)
        {
            this.inventorySlots.remove(this.inventorySlots.size() - 1); //Remove the slot from container.
            this.inventoryItemStacks.remove(this.inventoryItemStacks.size() - 1); //Remove the itemstack from container.
        }

        if(buttonList != null)
        {
            buttonList.clear();
        }
    }
}
