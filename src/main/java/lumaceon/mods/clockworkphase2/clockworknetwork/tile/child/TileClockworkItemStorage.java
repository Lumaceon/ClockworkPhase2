package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkTile;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.TileClockworkNetworkMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

public class TileClockworkItemStorage extends TileClockworkNetworkMachine
{
    public int xSlots, ySlots;
    protected final int maxSlots = 108; //Used to set a hard cap for the maximum number of slots.

    public TileClockworkItemStorage() {
        xSlots = 1;
        ySlots = 1;
        inventory = new ItemStack[xSlots * ySlots];
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setInteger("x_slots", xSlots);
        nbt.setInteger("y_slots", ySlots);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("x_slots") && nbt.hasKey("y_slots"))
        {
            xSlots = nbt.getInteger("x_slots");
            ySlots = nbt.getInteger("y_slots");
            inventory = new ItemStack[xSlots * ySlots];
        }
        super.readFromNBT(nbt);
    }

    @Override
    public ClockworkNetworkContainer getGui() {
        return ClockworkPhase2.proxy.getClockworkNetworkItemStorage(this, xSlots, ySlots);
    }

    @Override
    public boolean canExportToTargetInventory() {
        return false;
    }

    @Override
    public IClockworkNetworkTile getTargetInventory() { return null; }

    @Override
    public void setTargetInventory(IClockworkNetworkTile targetInventory) {} //NOOP

    @Override
    public boolean canWork() { return false; }

    @Override
    public void work() {} //NOOP

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        int[] slots = new int[xSlots * ySlots];
        for(int n = 0; n < slots.length; n++)
            slots[n] = n;
        return slots;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return true;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return true;
    }

    public boolean canResizeInventory(int xNum, int yNum)
    {
        if(xNum <= 0 || yNum <= 0 || xNum > 18 || yNum > 10)
            return false;
        if(xNum * yNum < xSlots * ySlots)
        {
            int numberOfNotNullStacks = 0;
            for(ItemStack item : this.inventory)
                if(item != null)
                    ++numberOfNotNullStacks;
            if(numberOfNotNullStacks > xNum * yNum)
                return false;
        }
        return xNum * yNum <= maxSlots;
    }

    public void resizeInventory(int xNum, int yNum)
    {
        if(xNum <= 0 || yNum <= 0 || xNum > 18 || yNum > 10)
            return;
        if(xNum * yNum < xSlots * ySlots) //Shrink the inventory; we need to make sure there's room.
        {
            int numberOfNotNullStacks = 0;
            for(ItemStack item : this.inventory)
                if(item != null)
                    ++numberOfNotNullStacks;
            if(numberOfNotNullStacks > xNum * yNum)
                return;

            //New inventory can hold 'em all; proceed.
            ItemStack[] newInventory = new ItemStack[xNum * yNum];
            int newInvyIndex = 0;
            for(ItemStack item : inventory)
                if(item != null)
                {
                    newInventory[newInvyIndex] = item;
                    ++newInvyIndex;
                }
            this.inventory = newInventory;
            this.xSlots = xNum;
            this.ySlots = yNum;
            markDirty();
            worldObj.markBlockForUpdate(getPos());
        }
        else //Move stack references to a new (larger) array and replace this.inventory with the new array.
        {
            ItemStack[] newInventory = new ItemStack[xNum * yNum];
            for(int n = 0; n < inventory.length; n++)
            {
                ItemStack item = inventory[n];
                if(item != null)
                    newInventory[n] = item;
            }
            this.inventory = newInventory;
            this.xSlots = xNum;
            this.ySlots = yNum;
            markDirty();
            worldObj.markBlockForUpdate(getPos());
        }
    }
}
