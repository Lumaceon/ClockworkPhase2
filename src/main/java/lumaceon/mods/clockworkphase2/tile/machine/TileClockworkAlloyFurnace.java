package lumaceon.mods.clockworkphase2.tile.machine;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import lumaceon.mods.clockworkphase2.recipe.AlloyRecipes;
import lumaceon.mods.clockworkphase2.util.OreDictHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public class TileClockworkAlloyFurnace extends TileClockworkMachine
{
    public TileClockworkAlloyFurnace() {
        super(3, 64, 50, 20000);
        this.slots = new Slot[] { new Slot(this, 0, 44, 21), new Slot(this, 1, 44, 47), new SlotNever(this, 2, 115, 34) };
        EXPORT_SLOTS = new int[] { 2 };
    }

    @Override
    public boolean canWork()
    {
        if(this.inventory.get(0).isEmpty() || this.inventory.get(1).isEmpty())
            return false;

        ItemStack itemstack;
        AlloyRecipes.AlloyRecipe recipe = AlloyRecipes.instance.getRecipe(this.inventory.get(0), this.inventory.get(1));

        if(recipe == null)
            return false;

        itemstack = recipe.output;

        if(itemstack.isEmpty())
            return false;

        itemstack = itemstack.copy();
        return this.exportItem(itemstack, EXPORT_SLOTS, true).isEmpty();
    }

    @Override
    public void completeAction()
    {
        ItemStack itemstack;
        AlloyRecipes.AlloyRecipe recipe = AlloyRecipes.instance.getRecipe(this.inventory.get(0), this.inventory.get(1));
        if(recipe == null)
            return;

        //Recipes will always retain the same order but this may not be the way the player places the items.
        //If the second inventory slot matches the first item in the recipe, we'll consider it "flipped."
        boolean isRecipeFlipped = false;
        if(OreDictHelper.itemsMatch(recipe.first.item, inventory.get(1)))
            isRecipeFlipped = true;

        //Stack cost is respective of the INVENTORY slot. So first item, here, is what to remove from index 0.
        int stackCostOfFirstItem = isRecipeFlipped ? recipe.second.ratio : recipe.first.ratio;
        int stackCostOfSecondItem = isRecipeFlipped ? recipe.first.ratio : recipe.second.ratio;

        itemstack = recipe.output.copy();

        //Decrease stack size based on cost for the recipe.
        this.inventory.get(0).shrink(stackCostOfFirstItem);
        this.inventory.get(1).shrink(stackCostOfSecondItem);
        if(this.inventory.get(0).getCount() <= 0)
            this.inventory.set(0, ItemStack.EMPTY);
        if(this.inventory.get(1).getCount() <= 0)
            this.inventory.set(1, ItemStack.EMPTY);

        ArrayList<ItemStack> items = new ArrayList<>(1);
        items.add(itemstack);
        outputItems(items);
    }

    @Override
    public int temporalActions(int maxNumberOfActions)
    {
        ItemStack firstSmelt = inventory.get(0);
        ItemStack secondSmelt = inventory.get(1);
        if(firstSmelt.isEmpty() || secondSmelt.isEmpty())
            return 0;

        ItemStack result;
        AlloyRecipes.AlloyRecipe recipe =  AlloyRecipes.instance.getRecipe(firstSmelt, secondSmelt);

        if(recipe == null)
            return 0;

        //Recipes will always retain the same order but this may not be the way the player places the items.
        //If the second inventory slot matches the first item in the recipe, we'll consider it "flipped."
        boolean isRecipeFlipped = false;
        if(OreDictHelper.itemsMatch(recipe.first.item, inventory.get(1)))
            isRecipeFlipped = true;

        //Stack cost is respective of the INVENTORY slot. So first item, here, is what to remove from index 0.
        int stackCostOfFirstItem = isRecipeFlipped ? recipe.second.ratio : recipe.first.ratio;
        int stackCostOfSecondItem = isRecipeFlipped ? recipe.first.ratio : recipe.second.ratio;


        //To get the true max actions, divide the each stack size by the cost, and floor it so it doesn't round up.
        int actualMaxActions;
        actualMaxActions = Math.min(Math.min(maxNumberOfActions, (int) Math.floor((float) firstSmelt.getCount() / (float) stackCostOfFirstItem)), (int) Math.floor((float) secondSmelt.getCount() / (float) stackCostOfSecondItem));

        if(actualMaxActions <= 0)
            return 0;

        result = recipe.output;

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
            remainingStack += s.getCount();

        int completeActions = (int) Math.ceil((double) (trueStackSize - remainingStack) / (double) result.getCount());

        firstSmelt.shrink(completeActions * stackCostOfFirstItem);
        secondSmelt.shrink(completeActions * stackCostOfSecondItem);
        if(firstSmelt.getCount() <= 0) { firstSmelt = null; }
        if(secondSmelt.getCount() <= 0) { secondSmelt = null; }
        setInventorySlotContents(0, firstSmelt);
        setInventorySlotContents(1, secondSmelt);

        return completeActions;
    }
}
