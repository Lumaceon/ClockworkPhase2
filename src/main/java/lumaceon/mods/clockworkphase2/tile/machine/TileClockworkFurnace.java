package lumaceon.mods.clockworkphase2.tile.machine;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ITickable;

import java.util.ArrayList;

public class TileClockworkFurnace extends TileClockworkMachine implements ITickable
{
    public TileClockworkFurnace()
    {
        super(2, 64, 50, 10000);
        this.slots = new Slot[] { new Slot(this, 0, 45, 34), new SlotNever(this, 1, 115, 34) };
        EXPORT_SLOTS = new int[] { 1 };
    }

    @Override
    public boolean canWork()
    {
        if(this.inventory.get(0).isEmpty())
            return false;

        ItemStack itemstack;
        itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inventory.get(0));

        if(itemstack.isEmpty())
            return false;

        itemstack = itemstack.copy();
        return this.exportItem(itemstack, EXPORT_SLOTS, true).isEmpty();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void completeAction()
    {
        ItemStack itemstack;
        itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inventory.get(0)).copy();

        this.inventory.get(0).shrink(1);
        if(this.inventory.get(0).getCount() <= 0)
            this.inventory.set(0, ItemStack.EMPTY);

        ArrayList<ItemStack> items = new ArrayList<>(1);
        items.add(itemstack);
        outputItems(items);
    }

    @Override
    public int temporalActions(int maxNumberOfActions)
    {
        ItemStack smelt = inventory.get(0);
        if(smelt.isEmpty() || smelt.getCount() <= 0)
            return 0;

        ItemStack result;
        int numberOfItemsToProcess = 1;
        result = FurnaceRecipes.instance().getSmeltingResult(smelt);

        if(result.isEmpty())
            return 0;

        int actualMaxActions;
        actualMaxActions = Math.min(maxNumberOfActions, smelt.getCount());

        if(actualMaxActions <= 0)
            return 0;

        //Some recipes may output greater-than-one stack size, or to an Item with a lower max stack size.
        //"True stack size" represents the total number of the resulting item.
        int trueStackSize = result.getCount() * actualMaxActions;
        int tempTrueSize = trueStackSize;
        ArrayList<ItemStack> outputStacks = new ArrayList<>();
        while(tempTrueSize > 0)
        {
            int stackSize = Math.min(result.getMaxStackSize(), tempTrueSize);
            ItemStack tempItem = result.copy();
            tempItem.setCount(stackSize);
            outputStacks.add(tempItem);
            tempTrueSize -= stackSize;
        }

        outputStacks = outputItems(outputStacks); //Do the actual output.

        int remainingStack = 0;
        for(ItemStack s : outputStacks)
            if(!s.isEmpty())
                remainingStack += s.getCount();

        int completeActions = (int) Math.ceil((double) (trueStackSize - remainingStack) / (double) result.getCount());

        smelt.shrink(completeActions * numberOfItemsToProcess);
        if(smelt.getCount() <= 0) { smelt = ItemStack.EMPTY; }
        setInventorySlotContents(0, smelt);

        return completeActions;
    }
}
