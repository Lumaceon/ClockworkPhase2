package lumaceon.mods.clockworkphase2.api.assembly;

import lumaceon.mods.clockworkphase2.inventory.ContainerAssemblyTableClient;
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
    protected ItemStack previousMainStack = ItemStack.EMPTY;
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
    public void detectAndSendChanges()
    {
        for(int i = 0; i < this.inventorySlots.size(); ++i)
        {
            ItemStack itemstack = this.inventorySlots.get(i).getStack();
            ItemStack itemstack1 = this.inventoryItemStacks.get(i);

            if(!ItemStack.areItemStacksEqual(itemstack1, itemstack))
            {
                itemstack1 = itemstack.isEmpty() ? ItemStack.EMPTY : itemstack.copy();
                this.inventoryItemStacks.set(i, itemstack1);

                for (int j = 0; j < this.listeners.size(); ++j)
                {
                    this.listeners.get(j).sendSlotContents(this, i, itemstack1);
                }
            }
        }
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
            if((item.isEmpty() || !(item.getItem() instanceof IAssemblable)) && componentInventory != null)
            {
                cleanContainerAndGUI();
            }

            //Set up the new component inventory if the main item has changed from a separate main item.
            if(!item.isEmpty() && componentInventory != null && item.getItem() instanceof IAssemblable)
            {
                cleanContainerAndGUI();

                setupNewContainerAndGUI(item);
            }

            //Set up a component inventory if the main item has been added from the default screen.
            if(!item.isEmpty() && componentInventory == null && item.getItem() instanceof IAssemblable)
            {
                setupNewContainerAndGUI(item);
            }
        }

        previousMainStack = item;
        detectAndSendChanges();
    }

    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);

        if(!this.world.isRemote)
        {
            ItemStack itemstack = this.mainInventory.getStackInSlot(0);
            if(!itemstack.isEmpty())
                p_75134_1_.dropItem(itemstack, false);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return mainInventory.isUsableByPlayer(p_75145_1_);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        List<Slot> slots = this.inventorySlots;
        Slot sourceSlot = slots.get(index);
        ItemStack inputStack = sourceSlot.getStack();

        if(inputStack.isEmpty())
            return ItemStack.EMPTY;

        ItemStack copy = inputStack.copy();
        boolean tranferFromPlayer = sourceSlot.inventory == player.inventory;

        if(!tranferFromPlayer) //Item is in our container, try placing in player's inventory.
        {
            if(!this.mergeItemStack(inputStack, 0, 36, true))
                return ItemStack.EMPTY;
        }
        else
        {
            if(inputStack.getItem() instanceof IAssemblable)
            {
                if(!this.mergeItemStack(inputStack, 36, 37, false))
                    if(!this.mergeItemStack(inputStack, 37, this.inventorySlots.size(), false))
                        return ItemStack.EMPTY;
            }
            else if(!this.mergeItemStack(inputStack, 37, this.inventorySlots.size(), false))
                return ItemStack.EMPTY;
        }

        if(inputStack.isEmpty())
        {
            sourceSlot.putStack(ItemStack.EMPTY);
        }
        else
        {
            sourceSlot.onSlotChanged();
        }

        if(copy.getCount() == inputStack.getCount())
            return ItemStack.EMPTY;
        sourceSlot.onTake(player, copy);
        detectAndSendChanges();
        return inputStack;
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
                        ItemStack replacementForOldStack = stack.copy();
                        replacementForOldStack.setCount(0);
                        for(Slot tempSlot : this.inventorySlots)
                        {
                            ItemStack is = tempSlot.getStack();
                            if(!is.isEmpty() && is == stack) //The exact same stack...
                            {
                                tempSlot.putStack(replacementForOldStack); //...so we set it to a shrunk copy of itself.
                                stack = replacementForOldStack;
                                break;
                            }
                        }

                        stack.setCount(0);
                        itemstack.setCount(j);
                        slot.onSlotChanged();
                        flag = true;
                    }
                    else if(itemstack.getCount() < stack.getMaxStackSize() && itemstack.getCount() < slot.getSlotStackLimit())
                    {
                        ItemStack replacementForOldStack = stack.copy();
                        replacementForOldStack.shrink(stack.getMaxStackSize() - itemstack.getCount());
                        for(Slot tempSlot : this.inventorySlots)
                        {
                            ItemStack is = tempSlot.getStack();
                            if(!is.isEmpty() && is == stack) //The exact same stack...
                            {
                                tempSlot.putStack(replacementForOldStack); //...so we set it to a shrunk copy of itself.
                                stack = replacementForOldStack;
                                break;
                            }
                        }

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

                    //Calling shrink on the old stack often sets it's stack size to 0.
                    //If the old stack is at count <= 0 when transferStackInSlot sets it null, the item stack handler won't call onContentsChanged.
                    //So instead we loop through all available slots looking for this, and change it that way.
                    ItemStack replacementForOldStack = stack.copy();
                    replacementForOldStack.shrink(newStack.getCount());
                    for(Slot tempSlot : this.inventorySlots)
                    {
                        ItemStack is = tempSlot.getStack();
                        if(!is.isEmpty() && is == stack) //The exact same stack...
                        {
                            tempSlot.putStack(replacementForOldStack); //...so we set it to a shrunk copy of itself.
                            stack = replacementForOldStack;
                            break;
                        }
                    }

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

    protected void setupNewContainerAndGUI(ItemStack item)
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

    protected void cleanContainerAndGUI()
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
