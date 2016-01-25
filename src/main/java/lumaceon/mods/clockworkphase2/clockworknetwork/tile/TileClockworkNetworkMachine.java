package lumaceon.mods.clockworkphase2.clockworknetwork.tile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.block.clockwork.IClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetwork;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhaseInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TileClockworkNetworkMachine extends TileClockworkPhase implements IClockworkNetworkMachine, ISidedInventory
{
    protected ItemStack[] inventory; //Typically, the first number(s) is input and the last number(s) is output.

    protected ClockworkNetwork clockworkNetwork;
    public ItemStack itemBlock;
    public int quality; //Tension efficiency; higher = less tension used per operation.
    public int speed; //Work speed; higher = faster machines.

    protected int workProgress = 0;

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        NBTTagCompound tag;
        if(itemBlock != null)
        {
            tag = new NBTTagCompound();
            itemBlock.writeToNBT(tag);
            nbt.setTag("itemBlock", tag);
        }

        nbt.setInteger(NBTTags.QUALITY, quality);
        nbt.setInteger(NBTTags.SPEED, speed);

        NBTTagList nbtList = new NBTTagList();
        for(int index = 0; index < inventory.length; index++)
        {
            if(inventory[index] != null)
            {
                tag = new NBTTagCompound();
                tag.setByte("slot_index", (byte)index);
                inventory[index].writeToNBT(tag);
                nbtList.appendTag(tag);
            }
        }
        nbt.setTag(TileClockworkPhaseInventory.INVENTORY_TAG, nbtList);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        NBTTagCompound tagCompound;
        tagCompound = nbt.getCompoundTag("itemBlock");
        itemBlock = ItemStack.loadItemStackFromNBT(tagCompound);

        quality = nbt.getInteger(NBTTags.QUALITY);
        speed = nbt.getInteger(NBTTags.SPEED);

        NBTTagList tagList = nbt.getTagList(TileClockworkPhaseInventory.INVENTORY_TAG, 10);
        inventory = new ItemStack[getSizeInventory()];
        for(int i = 0; i < tagList.tagCount(); ++i)
        {
            tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("slot_index");
            if(slotIndex >= 0 && slotIndex < inventory.length)
                inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
        }
    }

    @Override
    public void updateEntity()
    {
        int tensionCost = getTensionCostPerTick();
        boolean working = isWorking(tensionCost);
        boolean isChanged = false;

        if(!this.worldObj.isRemote)
        {
            if(working)
            {
                if(this.canWork())
                {
                    if(getClockworkNetwork().consumeTension(tensionCost) >= tensionCost)
                    {
                        this.workProgress += (int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50);
                        if(this.workProgress >= 10000)
                        {
                            this.workProgress -= 10000;
                            this.work();
                            isChanged = true;
                        }
                    }
                }
                else
                    this.workProgress = 0;
            }

            if(working != isWorking(tensionCost))
                isChanged = true;
        }

        if(isChanged)
            this.markDirty();
    }

    public int getTensionCostPerTick() {
        return ClockworkHelper.getTensionCostFromStats((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50), quality, speed) / 50;
    }

    protected int getBaseProgressSpeed() {
        return 10000;
    }

    /**
     * @param tensionCost The tension it would cost to work this tick.
     * @return Whether or not the internal machine's stats allow for this to work this tick.
     */
    public boolean isWorking(int tensionCost) {
        return clockworkNetwork != null && clockworkNetwork.getCurrentTension() > tensionCost && speed > 0 && quality > 0;
    }

    /**
     * Whether or not this inventory can process something this tick.
     */
    public abstract boolean canWork();

    /**
     * Handle whatever processing this machine does (like smelting an ingot out of an ore).
     * This is called once every time the workProgress value passes getBaseProgressSpeed().
     */
    public abstract void work();

    @SideOnly(Side.CLIENT)
    public int getProgressScaled(int maxValue) {
        return this.workProgress * maxValue / getBaseProgressSpeed();
    }

    @Override
    public ClockworkNetwork getClockworkNetwork() {
        return clockworkNetwork;
    }

    public void setClockworkNetwork(ClockworkNetwork cn) {
        clockworkNetwork = cn;
    }

    @Override
    public abstract ClockworkNetworkContainer getGui();

    // ********* SIDED INVENTORY IMPLEMENTATION START ********* \\

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amountToDecrease)
    {
        ItemStack is = getStackInSlot(slot);
        if(is != null)
        {
            if(amountToDecrease >= is.stackSize)
                setInventorySlotContents(slot, null);
            else
            {
                is = is.splitStack(amountToDecrease);
                if(is.stackSize == 0)
                    setInventorySlotContents(slot, null);
            }
        }
        return is;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if(inventory[slot] != null)
        {
            ItemStack itemStack = inventory[slot];
            inventory[slot] = null;
            return itemStack;
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item)
    {
        inventory[slot] = item;

        if(item != null && item.stackSize > this.getInventoryStackLimit())
            item.stackSize = this.getInventoryStackLimit();
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getDistance((double) xCoord, (double) yCoord, (double) zCoord) <= 8;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return true;
    }

    @Override
    public void openInventory() {}
    @Override
    public void closeInventory() {}
    @Override
    public String getInventoryName() { return null; }
    @Override
    public boolean hasCustomInventoryName() { return false; }

    // ********* SIDED INVENTORY IMPLEMENTATION END ********* \\


    public void setTileDataFromItemStack(ItemStack item)
    {
        this.itemBlock = item.copy();
        ItemStack[] items = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);
        if(items == null)
            return;
        if(items[0] != null && items[0].getItem() instanceof IClockwork)
        {
            this.quality = ((IClockwork) items[0].getItem()).getQuality(items[0]);
            this.speed = ((IClockwork) items[0].getItem()).getSpeed(items[0]);
        }
    }

    @Override
    public void setState(int state) {}
    @Override
    public void setStateAndUpdate(int state) {}
}
