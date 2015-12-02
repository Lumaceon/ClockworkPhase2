package lumaceon.mods.clockworkphase2.tile.clockwork;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.components.ItemMainspring;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhaseInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileClockworkFurnace extends TileClockwork implements ISidedInventory
{
    protected ItemStack[] inventory = new ItemStack[2]; //0 - Input, 1 - Output
    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime;

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        NBTTagList nbtList = new NBTTagList();
        for(int index = 0; index < inventory.length; index++)
        {
            if(inventory[index] != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
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

        NBTTagList tagList = nbt.getTagList(TileClockworkPhaseInventory.INVENTORY_TAG, 10);
        inventory = new ItemStack[getSizeInventory()];
        for(int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("slot_index");
            if(slotIndex >= 0 && slotIndex < inventory.length)
                inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
        }
    }

    @Override
    public void updateEntity()
    {
        boolean furnaceOn = isBurning();
        boolean isChanged = false;

        if(!this.worldObj.isRemote)
        {
            if(furnaceOn && this.inventory[0] != null)
            {
                currentTension -= ClockworkHelper.getTensionCostFromStats((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50), quality, speed) / 50;
                currentTension = Math.max(currentTension, 0);
                if(this.isBurning() && this.canSmelt())
                {
                    this.furnaceCookTime += (int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50);
                    if(this.furnaceCookTime >= 10000)
                    {
                        this.furnaceCookTime -= 10000;
                        this.smeltItem();
                        isChanged = true;
                    }
                }
                else
                    this.furnaceCookTime = 0;
            }

            if(furnaceOn != isBurning())
                isChanged = true;
        }

        if(isChanged)
            this.markDirty();
    }

    public boolean isBurning() {
        return this.currentTension > 0 && speed > 0 && quality > 0;
    }

    public boolean canSmelt()
    {
        if(this.inventory[0] == null)
            return false;
        else
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);
            if(itemstack == null) return false;
            if(this.inventory[1] == null) return true;
            if(!this.inventory[1].isItemEqual(itemstack)) return false;
            int result = inventory[1].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= this.inventory[1].getMaxStackSize();
        }
    }

    public void smeltItem()
    {
        if(this.canSmelt())
        {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(this.inventory[0]);

            if(this.inventory[1] == null)
                this.inventory[1] = itemstack.copy();
            else if(this.inventory[1].getItem() == itemstack.getItem())
                this.inventory[1].stackSize += itemstack.stackSize;

            --this.inventory[0].stackSize;

            if(this.inventory[0].stackSize <= 0)
                this.inventory[0] = null;
        }
    }

    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int p_145953_1_) {
        return this.furnaceCookTime * p_145953_1_ / 10000;
    }

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

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[] { 0, 1 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack inputStack, int side) {
        return
           inputStack != null
        && FurnaceRecipes.smelting().getSmeltingResult(inputStack) != null
        && (inventory[0] == null || inventory[0].stackSize < inventory[0].getMaxStackSize())
        && slotID == 0;
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack outputStack, int side) {
        return slotID == 1;
    }

    @Override
    public int wind(int tension) {
        int amountToAdd = Math.min(this.maxTension - this.currentTension, tension);
        currentTension += amountToAdd;
        return amountToAdd;
    }

    public void setTileDataFromItemStack(ItemStack item)
    {
        this.itemBlock = item.copy();
        ItemStack[] items = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);
        if(items[0] != null && items[0].getItem() instanceof ItemMainspring)
            this.maxTension = NBTHelper.INT.get(items[0], NBTTags.MAX_TENSION);
        if(items[1] != null && items[1].getItem() instanceof IClockwork)
        {
            this.quality = ((IClockwork) items[1].getItem()).getQuality(items[1]);
            this.speed = ((IClockwork) items[1].getItem()).getSpeed(items[1]);
        }
    }
}
