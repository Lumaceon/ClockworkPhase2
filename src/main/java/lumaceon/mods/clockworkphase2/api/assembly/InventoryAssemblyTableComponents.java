package lumaceon.mods.clockworkphase2.api.assembly;

import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class InventoryAssemblyTableComponents implements IInventory
{
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

    private IItemHandler itemHandler;
    private ContainerAssemblyTable eventHandler;
    private int stackLimit;

    public InventoryAssemblyTableComponents(ContainerAssemblyTable eventHandler, int stackLimit, ItemStack construct)
    {
        if(construct.isEmpty())
        {
            itemHandler = new ItemStackHandler(0);
        }
        else
        {
            this.itemHandler = construct.getCapability(ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
            if(itemHandler == null)
            {
                itemHandler = new ItemStackHandler(0);
                System.err.println("[" + Reference.MOD_NAME + "] Attach an IItemHandler capability to your item.");
            }
        }

        this.eventHandler = eventHandler;
        this.stackLimit = stackLimit;
    }

    @Override
    public int getSizeInventory() {
        return itemHandler == null ? 0 : itemHandler.getSlots();
    }

    @Override
    public boolean isEmpty()
    {
        if(itemHandler == null)
            return true;

        if(itemHandler.getSlots() > 0)
            return false;

        return true;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return itemHandler == null ? ItemStack.EMPTY : itemHandler.getStackInSlot(index);
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count) {
        return itemHandler == null ? ItemStack.EMPTY : itemHandler.extractItem(index, count, false);
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return itemHandler == null ? ItemStack.EMPTY : itemHandler.extractItem(index, 64, false);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
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
    public void markDirty() {

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
    public void clear()
    {
        if(itemHandler != null)
            for(int i = 0; i < itemHandler.getSlots(); i++)
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
}
