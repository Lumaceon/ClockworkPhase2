package lumaceon.mods.clockworkphase2.inventory;

import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyTableComponents;
import lumaceon.mods.clockworkphase2.api.item.IFishingRelic;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotFishingRelic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;

public class ContainerTemporalFishingRod extends Container
{
    public InventoryAssemblyTableComponents internalObjectInventory;

    public ContainerTemporalFishingRod(EntityPlayer player, ItemStack fishingRodStack)
    {
        IItemHandler itemHandler = fishingRodStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        if(fishingRodStack.isEmpty() || itemHandler == null)
        {
            return;
        }

        int playerInventoryX = 8;
        int playerInventoryY = 51;

        InventoryPlayer ip = player.inventory;

        //Player Inventory
        for(int x = 0; x < 9; x++)
            if(ip.getStackInSlot(x) != fishingRodStack)
                this.addSlotToContainer(new Slot(ip, x, playerInventoryX + x * 18 , playerInventoryY+58));

        for(int x = 0; x < 9; x++)
            for(int y = 0; y < 3; y++)
                if(ip.getStackInSlot(9 + y * 9 + x) != fishingRodStack)
                    this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, playerInventoryX + x * 18, playerInventoryY + y * 18));

        internalObjectInventory = new InventoryAssemblyTableComponents(this, 64, fishingRodStack);

        this.addSlotToContainer(new SlotFishingRelic(internalObjectInventory, 0, 80, 20));
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
            if(!this.mergeItemStack(inputStack, 0, this.inventorySlots.size() - 1, false))
                return ItemStack.EMPTY;
        }
        else
        {
            if(inputStack.getItem() instanceof IFishingRelic)
            {
                if(!this.mergeItemStack(inputStack, this.inventorySlots.size() - 1, this.inventorySlots.size(), false))
                    return ItemStack.EMPTY;
            }
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
                        stack.setCount(0);
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

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
}
