package lumaceon.mods.clockworkphase2.api.assembly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ContainerAssemblyTable extends Container
{
    public InventoryUpdated mainInventory = new InventoryUpdated(this, 1, 1);
    public InventoryAssemblyTableComponents componentInventory;
    public World world;
    public EntityPlayer player;
    private ItemStack previousMainStack;
    public List buttonList = null;
    public int guiLeft = -1;
    public int guiTop = -1;

    public ContainerAssemblyTable(InventoryPlayer ip, World world)
    {
        this.world = world;
        this.player = ip.player;

        for(int y = 0; y < 3; y++)
            for(int x = 0; x < 9; x++)
                this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, 70 + x * 18, 147 + y * 18));

        for(int x = 0; x < 9; x++)
            this.addSlotToContainer(new Slot(ip, x, 70 + x * 18 , 205));

        this.addSlotToContainer(new SlotAssemblable(mainInventory, 0, 142, 68));
    }

    @Override
    public void onCraftMatrixChanged(IInventory p_75130_1_)
    {
        ItemStack item = mainInventory.getStackInSlot(0);
        boolean mainItemChanged = item != previousMainStack;

        //Handle changes to the main item.
        if(mainItemChanged)
        {
            //Remove the component inventory if the change is removal of the main item.
            if((item == null || !(item.getItem() instanceof IAssemblable)) && componentInventory != null)
            {
                cleanContainerAndGUI();
            }

            //Set up the new component inventory if the main item has changed from a separate main item.
            if(item != null && componentInventory != null && item.getItem() instanceof IAssemblable)
            {
                cleanContainerAndGUI();

                setupNewContainerAndGUI(item);
            }

            //Set up a component inventory if the main item has been added from the default screen.
            if(item != null && componentInventory == null && item.getItem() instanceof IAssemblable)
            {
                setupNewContainerAndGUI(item);
            }
        }

        previousMainStack = item;
    }

    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);

        if(!this.world.isRemote)
        {
            ItemStack itemstack = this.mainInventory.getStackInSlot(0);
            if(itemstack != null)
                p_75134_1_.dropItem(itemstack, false);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return mainInventory.isUseableByPlayer(p_75145_1_);
    }

    //TODO Ignores max stack size of slots.
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        Slot slot = this.inventorySlots.get(index);
        if(slot == null || !slot.getHasStack())
            return null;

        ItemStack originalItem = slot.getStack();
        ItemStack copyItem = originalItem.copy();

        if(index >= 36) //Item is in our container, try placing in player's inventory.
        {
            if(!this.mergeItemStack(originalItem, 0, 36, true))
                return null;
        }
        else
        {
            if(originalItem.getItem() instanceof IAssemblable)
            {
                if(!this.mergeItemStack(originalItem, 36, 37, false))
                    if(!this.mergeItemStack(originalItem, 37, this.inventorySlots.size(), false))
                        return null;
            }
            else if(!this.mergeItemStack(originalItem, 37, this.inventorySlots.size(), false))
                return null;
        }

        if(copyItem.stackSize == 0)
            slot.putStack(null);
        else
            slot.onSlotChanged();

        if(originalItem.stackSize == 0)
            slot.putStack(null);
        else
            slot.onSlotChanged();

        if(copyItem.stackSize == originalItem.stackSize)
            return null;
        slot.onPickupFromSlot(player, copyItem);
        return originalItem;
    }

    @Override
    protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection)
    {
        boolean flag = false;
        int i = startIndex;

        if(reverseDirection)
            i = endIndex - 1;

        if(stack.isStackable())
        {
            while(stack.stackSize > 0 && (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex))
            {
                Slot slot = this.inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();

                if(itemstack != null && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack))
                {
                    int j = itemstack.stackSize + stack.stackSize;
                    if(j <= stack.getMaxStackSize() && j <= slot.getSlotStackLimit())
                    {
                        stack.stackSize = 0;
                        itemstack.stackSize = j;
                        slot.onSlotChanged();
                        flag = true;
                    }
                    else if(itemstack.stackSize < stack.getMaxStackSize() && itemstack.stackSize < slot.getSlotStackLimit())
                    {
                        stack.stackSize -= stack.getMaxStackSize() - itemstack.stackSize;
                        itemstack.stackSize = stack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag = true;
                    }
                }

                if(reverseDirection)
                    --i;
                else
                    ++i;
            }
        }

        if(stack.stackSize > 0)
        {
            if(reverseDirection)
                i = endIndex - 1;
            else
                i = startIndex;

            while(!reverseDirection && i < endIndex || reverseDirection && i >= startIndex)
            {
                Slot slot1 = this.inventorySlots.get(i);
                ItemStack itemstack1 = slot1.getStack();

                if(itemstack1 == null && slot1.isItemValid(stack)) // Forge: Make sure to respect isItemValid in the slot.
                {
                    ItemStack newStack = stack.copy();
                    newStack.stackSize = Math.min(stack.stackSize, slot1.getSlotStackLimit());
                    stack.stackSize -= newStack.stackSize;
                    slot1.putStack(newStack);
                    slot1.onSlotChanged();
                    flag = true;
                    if(stack.stackSize <= 0)
                        break;
                }

                if(reverseDirection)
                    --i;
                else
                    ++i;
            }
        }

        return flag;
    }

    public void onCraftMatrixComponentChanged()
    {
        ItemStack item = mainInventory.getStackInSlot(0);
        if(item != null && item.getItem() instanceof IAssemblable)
        {
            ((IAssemblable) item.getItem()).onInventoryChange(this);
            if(!world.isRemote)
                ((IAssemblable) item.getItem()).saveComponentInventory(this);
        }
    }

    public void onGUIResize()
    {
        ItemStack item = mainInventory.getStackInSlot(0);
        if(item != null && item.getItem() instanceof IAssemblableButtons && buttonList != null && guiLeft != -1 && guiTop != -1)
        {
            ((IAssemblableButtons) item.getItem()).initButtons(buttonList, this, guiLeft, guiTop);
        }
    }

    private void setupNewContainerAndGUI(ItemStack item)
    {
        IAssemblable constructGUI = (IAssemblable) item.getItem();
        componentInventory = constructGUI.getGUIInventory(this);
        constructGUI.saveComponentInventory(this);
        Slot[] slots = constructGUI.getContainerSlots(componentInventory);
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

    private void cleanContainerAndGUI()
    {
        componentInventory = null;
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
