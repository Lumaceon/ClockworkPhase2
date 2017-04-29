package lumaceon.mods.clockworkphase2.tile.machine;

import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;

public abstract class TileClockworkMachine extends TileMod implements ISidedInventory
{
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;
    @CapabilityInject(IEnergyStorage.class)
    static Capability<IEnergyStorage> ENERGY_STORAGE_CAPABILITY = null;

    public IEnergyStorage energyStorage = new EnergyStorage(1);

    SidedInvWrapper invUP = new SidedInvWrapper(this, EnumFacing.UP);
    SidedInvWrapper invDOWN = new SidedInvWrapper(this, EnumFacing.DOWN);
    SidedInvWrapper invNORTH = new SidedInvWrapper(this, EnumFacing.NORTH);
    SidedInvWrapper invSOUTH = new SidedInvWrapper(this, EnumFacing.SOUTH);
    SidedInvWrapper invEAST = new SidedInvWrapper(this, EnumFacing.EAST);
    SidedInvWrapper invWEST = new SidedInvWrapper(this, EnumFacing.WEST);

    int[] slotsUP = new int[0];
    int[] slotsDOWN = new int[0];
    int[] slotsNORTH = new int[0];
    int[] slotsSOUTH = new int[0];
    int[] slotsEAST = new int[0];
    int[] slotsWEST = new int[0];

    protected Slot[] slots; //Must remain in the same order as the inventory.
    protected ItemStack[] inventory;
    protected int inventoryStackSizeLimit = 64;

    public TileClockworkMachine()
    {
        slots = new Slot[0];
        inventory = new ItemStack[1];
    }

    public TileClockworkMachine(int inventorySize, int maxStackSize)
    {
        this.inventory = new ItemStack[inventorySize];
        this.inventoryStackSizeLimit = maxStackSize;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return index < inventory.length ? inventory[index] : null;
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack is = getStackInSlot(index);
        if(is != null)
        {
            if(count >= is.stackSize)
            {
                setInventorySlotContents(index, null);
            }
            else
            {
                is = is.splitStack(count);
                if(is.stackSize == 0)
                {
                    setInventorySlotContents(index, null);
                }
            }
            markDirty();
        }
        return is;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack ret = inventory[index];
        inventory[index] = null;
        markDirty();
        return ret;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        inventory[index] = stack;

        if(stack != null && stack.stackSize > this.getInventoryStackLimit())
            stack.stackSize = this.getInventoryStackLimit();
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return inventoryStackSizeLimit;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        //Confirm we have an array of slots and the index is in-bounds.
        if(slots == null || index >= slots.length)
            return false;

        return slots[index].isItemValid(stack);
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {}

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for(int i = 0; i < inventory.length; i++)
            inventory[i] = null;
        markDirty();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        if(side.equals(EnumFacing.UP))
            return slotsUP;
        if(side.equals(EnumFacing.DOWN))
            return slotsDOWN;
        if(side.equals(EnumFacing.NORTH))
            return slotsNORTH;
        if(side.equals(EnumFacing.SOUTH))
            return slotsSOUTH;
        if(side.equals(EnumFacing.EAST))
            return slotsEAST;
        if(side.equals(EnumFacing.WEST))
            return slotsWEST;
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        //Confirm we have an array of slots and the index is in-bounds.
        if(slots == null || index >= slots.length)
            return false;

        //Confirm this index is accessible for the direction.
        boolean foundSlot = false;
        int[] validSlots = getSlotsForFace(direction);
        for(int slot : validSlots)
        {
            if(slot == index)
            {
                foundSlot = true;
                break;
            }
        }

        return foundSlot && slots[index].isItemValid(itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        int[] validSlots = getSlotsForFace(direction);
        for(int slot : validSlots)
            if(slot == index)
                return true;
        return false;
    }

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing) {
        return capability != null && capability == ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
    {
        if(hasCapability(capability, facing))
        {
            if(facing == EnumFacing.UP)
                return ITEM_HANDLER_CAPABILITY.cast(invUP);
            if(facing == EnumFacing.DOWN)
                return ITEM_HANDLER_CAPABILITY.cast(invDOWN);
            if(facing == EnumFacing.NORTH)
                return ITEM_HANDLER_CAPABILITY.cast(invNORTH);
            if(facing == EnumFacing.SOUTH)
                return ITEM_HANDLER_CAPABILITY.cast(invSOUTH);
            if(facing == EnumFacing.EAST)
                return ITEM_HANDLER_CAPABILITY.cast(invEAST);
            if(facing == EnumFacing.WEST)
                return ITEM_HANDLER_CAPABILITY.cast(invWEST);
        }
        return super.getCapability(capability, facing);
    }
}
