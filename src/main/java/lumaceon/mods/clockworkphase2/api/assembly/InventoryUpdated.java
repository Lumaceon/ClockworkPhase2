package lumaceon.mods.clockworkphase2.api.assembly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentBase;

public class InventoryUpdated implements IInventory
{
    private NonNullList<ItemStack> inventory;
    private Container eventHandler;
    private int stackLimit;

    public InventoryUpdated(Container eventHandler, int size, int stackLimit) {
        this.inventory = NonNullList.withSize(size, ItemStack.EMPTY);
        this.eventHandler = eventHandler;
        this.stackLimit = stackLimit;
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty()
    {
        for(ItemStack item : inventory)
        {
            if(!item.isEmpty())
                return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index >= this.getSizeInventory() ? null : this.inventory.get(index);
    }

    @Override
    public ItemStack decrStackSize(int index, int p_70298_2_)
    {
        ItemStack itemstack;

        if (this.inventory.get(index).getCount() <= p_70298_2_)
        {
            itemstack = this.inventory.get(index);
            this.inventory.set(index, ItemStack.EMPTY);
            this.eventHandler.onCraftMatrixChanged(this);
            return itemstack;
        }
        else
        {
            itemstack = this.inventory.get(index).splitStack(p_70298_2_);

            if (this.inventory.get(index).getCount() == 0)
                this.inventory.set(index, ItemStack.EMPTY);

            this.eventHandler.onCraftMatrixChanged(this);
            return itemstack;
        }
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack item = inventory.get(index);
        inventory.set(index, ItemStack.EMPTY);
        this.eventHandler.onCraftMatrixChanged(this);
        return item;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack item) {
        this.inventory.set(index, item);
        this.eventHandler.onCraftMatrixChanged(this);
    }

    @Override
    public int getInventoryStackLimit() {
        return stackLimit;
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean isUsableByPlayer(EntityPlayer p_70300_1_) {
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
        for(int n = 0; n < inventory.size(); n++)
            inventory.set(n, ItemStack.EMPTY);
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
