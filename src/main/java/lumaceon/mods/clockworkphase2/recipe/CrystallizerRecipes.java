package lumaceon.mods.clockworkphase2.recipe;

import lumaceon.mods.clockworkphase2.util.LogHelper;
import lumaceon.mods.clockworkphase2.util.OreDictHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CrystallizerRecipes
{
    public static final CrystallizerRecipes instance = new CrystallizerRecipes();

    private ArrayList<CrystallizerRecipe> recipes = new ArrayList<>(10);

    public CrystallizerRecipe getRecipe(NonNullList<ItemStack> craftingInventory, FluidStack fluidInventory)
    {
        for(CrystallizerRecipe recipe : recipes)
        {
            if(recipe.matchesRecipe(craftingInventory, fluidInventory))
                return recipe;
        }
        return null;
    }

    public List<CrystallizerRecipe> getRecipes() {
        return recipes;
    }

    /**
     * Input items can stack large stack sizes, but you should never make multiple stacks of a single item. For example:
     * a single stack of redstone with a stacksize of 3 is fine, but two stacks of a single redstone is not.
     */
    public void addCrystallizerRecipe(NonNullList<ItemStack> inputItems, @Nullable FluidStack fluid, ItemStack output)
    {
        //Skip if the input items is null (not an empty array), or the output is null.
        if(inputItems == null || output.isEmpty())
        {
            if(!output.isEmpty())
            {
                LogHelper.info("Skipping crystallizer recipe with null inputItems array (recipes require an empty array): null -> " + output.getDisplayName());
            }
            else if(inputItems != null && inputItems.size() > 0)
            {
                String out = "Skipped crystallizer recipe with no output: ";
                for(ItemStack input : inputItems)
                {
                    if(!input.isEmpty())
                    {
                        out = out + input.getDisplayName() + " ";
                    }
                }
                out = out + "-> null";
                LogHelper.info(out);
            }
            return;
        }

        //Skip if the number of input items is greater than 6 (there's only 6 input slots in a crystallizer).
        if(inputItems.size() > 6)
        {
            String out = "Skipped crystallizer recipe with too many inputs (maximum is 6 item types and a fluid): ";
            for(ItemStack input : inputItems)
            {
                if(!input.isEmpty())
                {
                    out = out + input.getDisplayName() + " ";
                }
            }
            out = out + "-> " + output.getDisplayName();
            LogHelper.info(out);
            return;
        }

        //If the inputs match a recipe that already exists, we should still skip this.
        boolean dupe = getRecipe(inputItems, fluid) != null;
        if(dupe)
        {
            String out = "Skipped duplicate crystallizer recipe: ";
            for(ItemStack input : inputItems)
            {
                out = input.getDisplayName() + " ";
            }
            out = out + "-> " + output.getDisplayName();
            LogHelper.info(out);
            return;
        }

        //Actually create and register the recipe.
        CrystallizerRecipe recipe = new CrystallizerRecipe(inputItems, fluid, output);
        recipes.add(recipe);
    }

    public static class CrystallizerRecipe
    {
        public NonNullList<ItemStack> inputItems;
        public FluidStack fluidInventory;
        private ItemStack output;

        public CrystallizerRecipe(NonNullList<ItemStack> inputItems, FluidStack fluidInventory, ItemStack output)
        {
            this.inputItems = inputItems;
            this.fluidInventory = fluidInventory;
            this.output = output;
        }

        public boolean matchesRecipe(NonNullList<ItemStack> craftingInventory, FluidStack fluidInventory)
        {
            for(ItemStack is : inputItems)
            {
                int targetNumber  = is.getCount();
                int currentNumber = 0;
                for(ItemStack item : craftingInventory)
                {
                    if(!item.isEmpty() && OreDictHelper.itemsMatch(is, item))
                    {
                        currentNumber += item.getCount();
                        if(currentNumber >= targetNumber)
                            break;
                    }
                }

                if(targetNumber > currentNumber)
                {
                    return false;
                }
            }

            if(fluidInventory == null && this.fluidInventory != null)
                return false; //The recipe calls for a fluid, but none are given.

            if(this.fluidInventory != null && (!fluidInventory.isFluidEqual(this.fluidInventory) || fluidInventory.amount < this.fluidInventory.amount))
                return false; //The recipe calls for a fluid, there is one, but it's the wrong fluid or isn't enough.

            return true;
        }

        /**
         * Usually called after matchesRecipe is confirmed to be true, but not always.
         */
        public ItemStack getOutput(NonNullList<ItemStack> craftingInventory, FluidStack fluidInventory) {
            return output;
        }

        public ItemStack getDefaultOutput() {
            return output;
        }
    }
}

