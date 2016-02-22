package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.TileClockworkNetworkMachine;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumFacing;

public class TileClockworkFurnace extends TileClockworkNetworkMachine
{
    public TileClockworkFurnace() {
        inventory = new ItemStack[1];
    }

    @Override
    public boolean canWork()
    {
        if(this.inventory[0] == null)
            return false;
        else
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inventory[0]);
            if(itemstack == null)
                return false;
            itemstack = itemstack.copy();
            //if(System.nanoTime() % 100 == 0)
            //    System.out.println(this.canExportAll(new ItemStack[]{itemstack}));
            return this.canExportAll(new ItemStack[]{itemstack});
        }
    }

    @Override
    public void work()
    {
        if(this.canWork())
        {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inventory[0]).copy();

            --this.inventory[0].stackSize;
            if(this.inventory[0].stackSize <= 0)
                this.inventory[0] = null;

            exportAll( new ItemStack[] {itemstack} );
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[] {0};
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack inputStack, EnumFacing side) {
        return true;/*
           inputStack != null
        && FurnaceRecipes.instance().getSmeltingResult(inputStack) != null
        && (inventory[0] == null || inventory[0].stackSize < inventory[0].getMaxStackSize())
        && slotID == 0;*/
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack outputStack, EnumFacing side) {
        return false;
    }

    @Override
    public ClockworkNetworkContainer getGui() {
        return ClockworkPhase2.proxy.getClockworkNetworkGui(this, 0);
    }
}
