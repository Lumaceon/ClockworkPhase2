package lumaceon.mods.clockworkphase2.recipe;

import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class AlloyRecipes
{
    private static ArrayList<AlloyRecipe> RECIPES = new ArrayList<AlloyRecipe>(10);

    /**
     * @return The alloy this will create, or null if none exist.
     */
    public static AlloyRecipe getRecipe(ItemStack firstSlot, ItemStack secondSlot)
    {
        AlloyRecipe theRecipe = null;
        for(AlloyRecipe recipe : RECIPES)
            if(recipe.matchesRecipe(firstSlot, secondSlot))
            {
                theRecipe = recipe;
                break;
            }

        return theRecipe == null ? null : theRecipe;
    }

    /**
     * Adds an alloy recipe to those available.
     * @param output A desired output for this recipe
     */
    public static void addAlloyRecipe(RecipeComponent firstComponent, RecipeComponent secondComponent, ItemStack output)
    {
        if(output == null)
        {
            Logger.info("Tried to add an alloy recipe with null output. This is usually NOT an error.");
            return;
        }
        else if(!firstComponent.metalExists() || !secondComponent.metalExists())
        {
            Logger.info("Tried to add an alloy recipe for \"" + output.getDisplayName() + "\" with an invalid material. This is usually NOT an error.");
            return;
        }

        AlloyRecipe recipe = new AlloyRecipe(firstComponent, secondComponent, output);
        for(AlloyRecipe r : RECIPES)
            if(r.sameRecipe(recipe))
            {
                Logger.info("Tried to add a duplicate \"" + output.getDisplayName() + "\" recipe. This is usually NOT an error.");
                return;
            }

        RECIPES.add(recipe);
    }

    public static class AlloyRecipe
    {
        public RecipeComponent first;
        public RecipeComponent second;
        public ItemStack output;

        public AlloyRecipe(RecipeComponent first, RecipeComponent second, ItemStack output) {
            this.first = first;
            this.second = second;
            this.output = output;
        }

        public boolean matchesRecipe(ItemStack firstSlot, ItemStack secondSlot)
        {
            if(firstSlot == null || secondSlot == null || first == null || second == null)
                return false;

            if((first.itemMatches(firstSlot) || first.itemMatches(secondSlot)) && (second.itemMatches(firstSlot) || second.itemMatches(secondSlot)))
                return true;
            return false;
        }

        public boolean sameRecipe(AlloyRecipe anotherRecipe)
        {
            if(anotherRecipe.output == null)
            {
                Logger.error("WARNING: alloy recipe found with a null output. Someone probably didn't use ItemStack.copy().");
                return false;
            }

            if((this.first.itemMatches(anotherRecipe.first.item) || this.first.itemMatches(anotherRecipe.second.item))
            && (this.second.itemMatches(anotherRecipe.first.item) || this.second.itemMatches(anotherRecipe.second.item)))
                return true;
            return false;
        }
    }

    public static class RecipeComponent
    {
        public ItemStack item;
        public byte ratio = 1;

        public RecipeComponent(ItemStack item, byte ratio) {
            this.item = item;
            this.ratio = ratio;
        }

        /**
         * Checks to see if the item passed is this item. Also checks ratio + stack size.
         */
        public boolean itemMatches(ItemStack item)
        {
            if(item == null || item.stackSize < ratio)
                return false;

            return this.item != null && OreDictionary.itemMatches(this.item, item, false);
        }

        public boolean metalExists() {
            return ratio > 0 && ratio <= 64 && item != null;
        }
    }
}
