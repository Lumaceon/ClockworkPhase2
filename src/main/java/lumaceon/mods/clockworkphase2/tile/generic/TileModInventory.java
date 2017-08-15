package lumaceon.mods.clockworkphase2.tile.generic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public abstract class TileModInventory extends TileMod implements IInventory
{
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

    IItemHandler inventory;
    int stackLimit;

    public TileModInventory(int size, int stackLimit)
    {
        inventory = new ItemStackHandlerTileEntity(size);
        ((ItemStackHandlerTileEntity) inventory).setTileEntity(this);
        this.stackLimit = stackLimit;
    }

    private IItemHandler getItemHandler() {
        return inventory;
    }

    @Override
    public int getSizeInventory() {
        IItemHandler itemHandler = getItemHandler();
        return itemHandler == null ? 0 : itemHandler.getSlots();
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        IItemHandler itemHandler = getItemHandler();
        return itemHandler == null ? null : itemHandler.getStackInSlot(index);
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        IItemHandler itemHandler = getItemHandler();
        return itemHandler == null ? null : itemHandler.extractItem(index, count, false);
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        IItemHandler itemHandler = getItemHandler();
        return itemHandler == null ? null : itemHandler.extractItem(index, 64, false);
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        IItemHandler itemHandler = getItemHandler();
        if(itemHandler != null)
        {
            itemHandler.extractItem(index, 64, false);
            itemHandler.insertItem(index, stack, false);
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return stackLimit;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
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
        IItemHandler itemHandler = getItemHandler();
        if(itemHandler != null)
            for (int i = 0; i < itemHandler.getSlots(); i++)
                itemHandler.extractItem(i, 64, false);
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

    protected void onInventoryContentsChanged() {
        markDirty();
    }

    //TODO - Technically, the inventory will fail to save, because we have to save the inventory capability ourselves.
    //....but I don't use this generic class.

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing) {
        return capability != null && capability == ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
    {
        if(hasCapability(capability, facing))
            return ITEM_HANDLER_CAPABILITY.cast(inventory);
        return super.getCapability(capability, facing);
    }

    public static class ItemStackHandlerTileEntity extends ItemStackHandler
    {
        protected TileModInventory tileEntity;

        public ItemStackHandlerTileEntity(int size) {
            super(size);
        }

        public void setTileEntity(TileModInventory tile) {
            this.tileEntity = tile;
        }

        @Override
        protected void onContentsChanged(int slot) {
            tileEntity.onInventoryContentsChanged();
        }
    }
}

