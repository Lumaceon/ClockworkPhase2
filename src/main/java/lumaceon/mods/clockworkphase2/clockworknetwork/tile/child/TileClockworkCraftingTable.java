package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkNetworkMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.util.EnumFacing;

public class TileClockworkCraftingTable extends TileClockworkNetworkMachine
{
    public ItemStack resultItem = null;

    public TileClockworkCraftingTable() {
        inventory = new ItemStack[9];
    }

    @Override
    public boolean canWork() {
        return false;
    }

    @Override
    public void work() {}

    @Override
    public ClockworkNetworkContainer getGui() {
        return ClockworkPhase2.proxy.getClockworkNetworkGui(this, 6);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[] { 9 };
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, EnumFacing side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, EnumFacing side) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length + 1;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot == 9 ? resultItem : inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int numberToRemove)
    {
        ItemStack itemstack;
        if(slotIndex == 9)
        {
            if(resultItem != null)
            {
                if(resultItem.stackSize <= numberToRemove)
                {
                    itemstack = resultItem;
                    resultItem = null;

                    for(int n = 0; n < 9; n++)
                        if(inventory[n] != null)
                        {
                            inventory[n].stackSize -= 1;
                            if(inventory[n].stackSize <= 0)
                                setInventorySlotContents(n, null);
                        }
                    setInventorySlotContents(9, CraftingManager.getInstance().findMatchingRecipe(new InventoryCraftingSub(inventory, 3, 3), worldObj));
                    return itemstack;
                }
                else
                    return null;
            }
        }
        else if(this.inventory[slotIndex] != null)
        {
            if(this.inventory[slotIndex].stackSize <= numberToRemove)
            {
                itemstack = this.inventory[slotIndex];
                this.inventory[slotIndex] = null;
                setInventorySlotContents(9, CraftingManager.getInstance().findMatchingRecipe(new InventoryCraftingSub(inventory, 3, 3), worldObj));
                return itemstack;
            }
            else
            {
                itemstack = this.inventory[slotIndex].splitStack(numberToRemove);

                if(this.inventory[slotIndex].stackSize == 0)
                    this.inventory[slotIndex] = null;

                setInventorySlotContents(9, CraftingManager.getInstance().findMatchingRecipe(new InventoryCraftingSub(inventory, 3, 3), worldObj));
                return itemstack;
            }
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack item) {
        if(slotIndex == 9)
            resultItem = item;
        else
        {
            this.inventory[slotIndex] = item;

            if(item != null && item.stackSize > this.getInventoryStackLimit())
                item.stackSize = this.getInventoryStackLimit();
            setInventorySlotContents(9, CraftingManager.getInstance().findMatchingRecipe(new InventoryCraftingSub(inventory, 3, 3), worldObj));
        }
        this.markDirty();
    }

    public static class InventoryCraftingSub extends InventoryCrafting
    {
        private ItemStack[] stackList;
        private int inventoryWidth;

        public InventoryCraftingSub(ItemStack[] inventory, int p_i1807_2_, int p_i1807_3_) {
            super(null, p_i1807_2_, p_i1807_3_);
            this.stackList = inventory;
            int k = p_i1807_2_ * p_i1807_3_;
            this.inventoryWidth = p_i1807_2_;
        }

        @Override
        public ItemStack decrStackSize(int slotIndex, int numberToRemove)
        {
            if(this.stackList[slotIndex] != null)
            {
                ItemStack itemstack;

                if(this.stackList[slotIndex].stackSize <= numberToRemove)
                {
                    itemstack = this.stackList[slotIndex];
                    this.stackList[slotIndex] = null;
                    return itemstack;
                }
                else
                {
                    itemstack = this.stackList[slotIndex].splitStack(numberToRemove);

                    if(this.stackList[slotIndex].stackSize == 0)
                        this.stackList[slotIndex] = null;

                    return itemstack;
                }
            }
            else
            {
                return null;
            }
        }

        /**
         * Returns the number of slots in the inventory.
         */
        public int getSizeInventory() {
            return this.stackList.length;
        }

        /**
         * Returns the stack in slot i
         */
        public ItemStack getStackInSlot(int p_70301_1_) {
            return p_70301_1_ >= this.getSizeInventory() ? null : this.stackList[p_70301_1_];
        }

        /**
         * Returns the itemstack in the slot specified (Top left is 0, 0). Args: row, column
         */
        public ItemStack getStackInRowAndColumn(int p_70463_1_, int p_70463_2_)
        {
            if (p_70463_1_ >= 0 && p_70463_1_ < this.inventoryWidth)
            {
                int k = p_70463_1_ + p_70463_2_ * this.inventoryWidth;
                return this.getStackInSlot(k);
            }
            else
                return null;
        }

        /**
         * Returns the name of the inventory
         */
        public String getInventoryName() {
            return "";
        }

        /**
         * Returns if the inventory is named
         */
        public boolean hasCustomInventoryName() {
            return false;
        }

        /**
         * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
         * like when you close a workbench GUI.
         */
        public ItemStack getStackInSlotOnClosing(int p_70304_1_)
        {
            if(this.stackList[p_70304_1_] != null)
            {
                ItemStack itemstack = this.stackList[p_70304_1_];
                this.stackList[p_70304_1_] = null;
                return itemstack;
            }
            else
                return null;
        }

        /**
         * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
         */
        public void setInventorySlotContents(int slotIndex, ItemStack newItem) {
            this.stackList[slotIndex] = newItem;
        }

        /**
         * Returns the maximum stack size for a inventory slot.
         */
        public int getInventoryStackLimit() {
            return 64;
        }

        /**
         * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
         * hasn't changed and skip it.
         */
        public void markDirty() {}

        /**
         * Do not make give this method the name canInteractWith because it clashes with Container
         */
        public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
            return true;
        }

        public void openInventory() {}

        public void closeInventory() {}

        /**
         * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
         */
        public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
            return true;
        }
    }
}
