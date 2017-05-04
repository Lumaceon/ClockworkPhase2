package lumaceon.mods.clockworkphase2.tile.machine;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ITickable;

public class TileClockworkFurnace extends TileClockworkMachine implements ITickable
{
    private static final int[] EXPORT_SLOTS = new int[] { 1 };

    public TileClockworkFurnace()
    {
        super(2, 64, 50, 10000);
        this.slots = new Slot[] { new Slot(this, 0, 56, 25), new SlotNever(this, 1, 116, 34) };
        energyStorage.setMaxCapacity(1000000);
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
            return this.exportItem(itemstack, EXPORT_SLOTS, true) == null;
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void completeAction()
    {
        ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inventory[0]).copy();

        --this.inventory[0].stackSize;
        if(this.inventory[0].stackSize <= 0)
            this.inventory[0] = null;

        exportItem(itemstack, EXPORT_SLOTS, false);
    }
}
