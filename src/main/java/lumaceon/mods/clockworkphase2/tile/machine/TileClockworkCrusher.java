package lumaceon.mods.clockworkphase2.tile.machine;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import lumaceon.mods.clockworkphase2.recipe.CrusherRecipes;
import lumaceon.mods.clockworkphase2.util.LogHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class TileClockworkCrusher extends TileClockworkMachine
{
    public TileClockworkCrusher() {
        super(2, 64, 50, 20000);
        this.slots = new Slot[] { new Slot(this, 0, 44, 18), new SlotNever(this, 1, 115, 34) };
        EXPORT_SLOTS = new int[] { 1 };
    }

    @Override
    public boolean canWork()
    {
        if(this.inventory.get(0).isEmpty())
            return false;

        ItemStack itemstack;
        itemstack = CrusherRecipes.instance.getCrusherResult(this.inventory.get(0));

        if(itemstack.isEmpty())
            return false;

        itemstack = itemstack.copy();
        return this.exportItem(itemstack, EXPORT_SLOTS, true).isEmpty();
    }

    @Override
    public void completeAction()
    {
        ItemStack itemstack;
        itemstack = CrusherRecipes.instance.getCrusherResult(this.inventory.get(0)).copy();

        this.inventory.get(0).shrink(1);
        if(this.inventory.get(0).getCount() <= 0)
            this.inventory.set(0, ItemStack.EMPTY);

        ArrayList<ItemStack> items = new ArrayList<>(1);
        items.add(itemstack);
        outputItems(items, this.inventory.get(1));
    }

    @Override
    public int temporalActions(int maxNumberOfActions)
    {
        ItemStack input = inventory.get(0);
        if(input.isEmpty() || input.getCount() <= 0)
            return 0;

        ItemStack result;
        int numberOfItemsToProcess = 1; //Can be used for recipes that consume more than one item. Ignored for now.
        result = CrusherRecipes.instance.getCrusherResult(input);

        if(result.isEmpty())
            return 0;

        int actualMaxActions;
        actualMaxActions = Math.min(maxNumberOfActions, input.getCount());

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

        ItemStack outputSlot = getStackInSlot(1);
        outputStacks = outputItems(outputStacks, outputSlot); //Do the actual output.

        int remainingStack = 0;
        for(ItemStack s : outputStacks)
            if(!s.isEmpty())
                remainingStack += s.getCount();

        int completeActions = (int) Math.ceil((double) (trueStackSize - remainingStack) / (double) result.getCount());

        input.shrink(completeActions * numberOfItemsToProcess);
        if(input.getCount() <= 0) { input = ItemStack.EMPTY; }
        setInventorySlotContents(0, input);

        return completeActions;
    }
}
