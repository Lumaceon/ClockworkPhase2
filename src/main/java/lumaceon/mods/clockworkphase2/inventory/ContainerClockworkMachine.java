package lumaceon.mods.clockworkphase2.inventory;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ContainerClockworkMachine extends Container
{
    protected TileClockworkMachine tile;
    private int playerInventoryX, playerInventoryY;

    private int progressTimer = 0;
    private int energy = 0;
    private int maxEnergy = 0;
    int[] tankValues;

    public ContainerClockworkMachine(TileClockworkMachine tile, int playerInventoryX, int playerInventoryY) {
        this.tile = tile;
        this.playerInventoryX = playerInventoryX;
        this.playerInventoryY = playerInventoryY;
        this.tankValues = new int[this.tile.fluidTanks.length];
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
            if(!this.mergeItemStack(inputStack, 0, 36, false))
                return ItemStack.EMPTY;
        }
        else
        {
            if(!this.mergeItemStack(inputStack, 36, this.inventorySlots.size(), false))
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
    public void addListener(IContainerListener listener)
    {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tile);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        for(int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

            if(this.progressTimer != this.tile.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.tile.getField(0));
            }

            if(this.energy != this.tile.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.tile.getField(1));
            }

            if(this.maxEnergy != this.tile.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.tile.getField(2));
            }

            if(tankValues != null)
            {
                for(int n = 0; n < tile.fluidTanks.length; n++)
                {
                    if(tile.fluidTanks.length > n && tile.fluidTanks[n] != null && tile.fluidTanks[n].getFluidAmount() != tankValues[n])
                    {
                        icontainerlistener.sendWindowProperty(this, 3+n, tile.fluidTanks[n].getFluidAmount());
                    }
                }
            }

        }

        this.progressTimer = this.tile.getField(0);
        this.energy = this.tile.getField(1);
        this.maxEnergy = this.tile.getField(2);
        for(int i = 0 ; i < tile.fluidTanks.length; i++)
        {
            if(tile.fluidTanks[i] != null)
            {
                tankValues[i] = tile.fluidTanks[i].getFluidAmount();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data)
    {
        this.tile.setField(id, data);
    }

    public void initializeSlots(InventoryPlayer ip, boolean hideSlots)
    {
        this.inventorySlots.clear();

        if(hideSlots) //GUI is being configured, hide all the slots off-screen.
        {
            //Player Inventory
            for(int n = 0; n < 36; n++)
                this.addSlotToContainer(new Slot(ip, n, -1000000, -1000000));

            if(tile != null)
            {
                Slot[] slots = tile.slots;
                if(slots != null)
                {
                    for(Slot slot : slots)
                    {
                        Slot newSlot = new SlotNever(slot.inventory, slot.getSlotIndex(), -1000000, -1000000);
                        this.addSlotToContainer(newSlot);
                    }
                }
            }
        }
        else //GUI is in a usable state, display the slots normally.
        {
            //Player Inventory
            for(int x = 0; x < 9; x++)
                this.addSlotToContainer(new Slot(ip, x, playerInventoryX + x * 18 , playerInventoryY+58));

            for(int x = 0; x < 9; x++)
                for(int y = 0; y < 3; y++)
                    this.addSlotToContainer(new Slot(ip, 9 + y * 9 + x, playerInventoryX + x * 18, playerInventoryY + y * 18));

            if(tile != null)
            {
                Slot[] slots = tile.slots;
                if(slots != null)
                    for(Slot slot : slots)
                        this.addSlotToContainer(slot);
            }
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return tile.isUsableByPlayer(playerIn);
    }
}
