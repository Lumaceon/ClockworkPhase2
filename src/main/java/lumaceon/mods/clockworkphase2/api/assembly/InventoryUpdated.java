package lumaceon.mods.clockworkphase2.api.assembly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentBase;

public class InventoryUpdated implements IInventory
{
    private ItemStack[] inventory;
    private Container eventHandler;
    private int stackLimit;

    public InventoryUpdated(Container eventHandler, int size, int stackLimit) {
        this.inventory = new ItemStack[size];
        this.eventHandler = eventHandler;
        this.stackLimit = stackLimit;
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return p_70301_1_ >= this.getSizeInventory() ? null : this.inventory[p_70301_1_];
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_)
    {
        if (this.inventory[p_70298_1_] != null)
        {
            ItemStack itemstack;

            if (this.inventory[p_70298_1_].stackSize <= p_70298_2_)
            {
                itemstack = this.inventory[p_70298_1_];
                this.inventory[p_70298_1_] = null;
                this.eventHandler.onCraftMatrixChanged(this);
                return itemstack;
            }
            else
            {
                itemstack = this.inventory[p_70298_1_].splitStack(p_70298_2_);

                if (this.inventory[p_70298_1_].stackSize == 0)
                    this.inventory[p_70298_1_] = null;

                this.eventHandler.onCraftMatrixChanged(this);
                return itemstack;
            }
        }
        else
            return null;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack item = inventory[index];
        inventory[index] = null;
        this.eventHandler.onCraftMatrixChanged(this);
        return item;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
        this.inventory[p_70299_1_] = p_70299_2_;
        this.eventHandler.onCraftMatrixChanged(this);
    }

    @Override
    public int getInventoryStackLimit() {
        return stackLimit;
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack is) {
        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for(int n = 0; n < inventory.length; n++)
            inventory[n] = null;
        this.eventHandler.onCraftMatrixChanged(this);
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
    public TextComponentBase getDisplayName() {
        return null;
    }
}
