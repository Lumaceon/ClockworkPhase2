package lumaceon.mods.clockworkphase2.api.assembly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryAssemblyComponents implements IInventory
{
    private ItemStack[] inventory;
    private IAssemblyContainer eventHandler;
    private int stackLimit;

    public InventoryAssemblyComponents(IAssemblyContainer eventHandler, int size, int stackLimit)
    {
        this.inventory = new ItemStack[size];
        this.eventHandler = eventHandler;
        this.stackLimit = stackLimit;
    }

    @Override
    public int getSizeInventory()
    {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        return index >= this.getSizeInventory() ? null : this.inventory[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int amountToRemove)
    {
        if(this.inventory[index] != null)
        {
            ItemStack itemstack;

            if(this.inventory[index].stackSize <= amountToRemove)
            {
                itemstack = this.inventory[index];
                this.inventory[index] = null;
                this.eventHandler.onComponentChanged();
                return itemstack;
            }
            else
            {
                itemstack = this.inventory[index].splitStack(amountToRemove);

                if(this.inventory[index].stackSize == 0)
                {
                    this.inventory[index] = null;
                }

                this.eventHandler.onComponentChanged();
                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if(this.inventory[slot] != null)
        {
            ItemStack itemstack = this.inventory[slot];
            this.inventory[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item)
    {
        this.inventory[slot] = item;
        this.eventHandler.onComponentChanged();
    }

    /**
     * Used to set items without updating the container.
     * This should be used to set the inventory when loading during initial inventory setup.
     * @param slot Target slot.
     * @param item New item to set.
     */
    public void setInventorySlotContentsRemotely(int slot, ItemStack item)
    {
        this.inventory[slot] = item;
    }

    @Override
    public String getInventoryName() { return null; }

    @Override
    public boolean hasCustomInventoryName() { return false; }

    @Override
    public int getInventoryStackLimit() { return this.stackLimit; }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) { return true; }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) { return false; }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    @Override
    public void markDirty() {}
}
