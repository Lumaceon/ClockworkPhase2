package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkNetworkMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TileClockworkFurnace extends TileClockworkNetworkMachine
{
    /** The number of ticks that the current item has been cooking for */
    public int furnaceCookTime;

    public TileClockworkFurnace() {
        inventory = new ItemStack[2];
    }

    @Override
    public boolean canWork()
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

    @Override
    public void work()
    {
        if(this.canWork())
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
    public ClockworkNetworkContainer getGui() {
        return ClockworkPhase2.proxy.getClockworkNetworkGui(this, 0);
    }

    @Override
    public void setState(int state) {}
    @Override
    public void setStateAndUpdate(int state) {}
}
