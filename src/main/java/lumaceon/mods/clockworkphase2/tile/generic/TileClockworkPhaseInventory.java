package lumaceon.mods.clockworkphase2.tile.generic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public abstract class TileClockworkPhaseInventory extends TileClockworkPhase implements IInventory
{
    public static final String INVENTORY_TAG = "inventory_items";

    protected ItemStack[] inventory;

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        NBTTagList nbtList = new NBTTagList();
        for (int index = 0; index < inventory.length; index++)
        {
            if (inventory[index] != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("slot_index", (byte)index);
                inventory[index].writeToNBT(tag);
                nbtList.appendTag(tag);
            }
        }
        nbt.setTag(INVENTORY_TAG, nbtList);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        NBTTagList tagList = nbt.getTagList(INVENTORY_TAG, 10);
        inventory = new ItemStack[getSizeInventory()];
        for (int i = 0; i < tagList.tagCount(); ++i)
        {
            NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
            byte slotIndex = tagCompound.getByte("slot_index");
            if (slotIndex >= 0 && slotIndex < inventory.length)
            {
                inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }
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
                if (is.stackSize == 0)
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
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
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
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return true;
    }
}
