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
    private ItemStack previousMainStack = ItemStack.EMPTY;
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
        return mainInventory.isUsableByPlayer(p_75145_1_);
    }

    //TODO Ignores max stack size of slots.
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        Slot slot = this.inventorySlots.get(index);
        if(slot == null || !slot.getHasStack())
            return ItemStack.EMPTY;

        ItemStack originalItem = slot.getStack();
        ItemStack copyItem = originalItem.copy();

        if(index >= 36) //Item is in our container, try placing in player's inventory.
        {
            if(!this.mergeItemStack(originalItem, 0, 36, true))
                return ItemStack.EMPTY;
        }
        else
        {
            if(originalItem.getItem() instanceof IAssemblable)
            {
                if(!this.mergeItemStack(originalItem, 36, 37, false))
                    if(!this.mergeItemStack(originalItem, 37, this.inventorySlots.size(), false))
                        return ItemStack.EMPTY;
            }
            else if(!this.mergeItemStack(originalItem, 37, this.inventorySlots.size(), false))
                return ItemStack.EMPTY;
        }

        if(copyItem.getCount() == 0)
            slot.putStack(ItemStack.EMPTY);
        else
            slot.onSlotChanged();

        if(originalItem.getCount() == 0)
            slot.putStack(ItemStack.EMPTY);
        else
            slot.onSlotChanged();

        if(copyItem.getCount() == originalItem.getCount())
            return ItemStack.EMPTY;
        slot.onTake(player, copyItem);
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
            while(stack.getCount() > 0 && (!reverseDirection && i < endIndex || reverseDirection && i >= startIndex))
            {
                Slot slot = this.inventorySlots.get(i);
                ItemStack itemstack = slot.getStack();

                if(itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack))
                {
                    int j = itemstack.getCount() + stack.getCount();
                    if(j <= stack.getMaxStackSize() && j <= slot.getSlotStackLimit())
                    {
                        stack = ItemStack.EMPTY;
                        itemstack.setCount(j);
                        slot.onSlotChanged();
                        flag = true;
                    }
                    else if(itemstack.getCount() < stack.getMaxStackSize() && itemstack.getCount() < slot.getSlotStackLimit())
                    {
                        stack.shrink(stack.getMaxStackSize() - itemstack.getCount());
                        itemstack.setCount(stack.getMaxStackSize());
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

        if(stack.getCount() > 0)
        {
            if(reverseDirection)
                i = endIndex - 1;
            else
                i = startIndex;

            while(!reverseDirection && i < endIndex || reverseDirection && i >= startIndex)
            {
                Slot slot1 = this.inventorySlots.get(i);
                ItemStack itemstack1 = slot1.getStack();

                if(itemstack1.isEmpty() && slot1.isItemValid(stack)) // Forge: Make sure to respect isItemValid in the slot.
                {
                    ItemStack newStack = stack.copy();
                    newStack.setCount(Math.min(stack.getCount(), slot1.getSlotStackLimit()));
                    stack.shrink(newStack.getCount());
                    slot1.putStack(newStack);
                    slot1.onSlotChanged();
                    flag = true;
                    if(stack.getCount() <= 0)
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
        componentInventory = new InventoryAssemblyTableComponents(this, 1, item);
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
