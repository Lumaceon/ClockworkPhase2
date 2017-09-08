package lumaceon.mods.clockworkphase2.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class AlloyRecipes
{
    public static final AlloyRecipes instance = new AlloyRecipes();

    private ArrayList<AlloyRecipe> RECIPES = new ArrayList<AlloyRecipe>(10);

    /**
     * @return The alloy this will create, or null if none exist.
     */
    public AlloyRecipe getRecipe(ItemStack firstSlot, ItemStack secondSlot)
    {
        AlloyRecipe theRecipe = null;
        for(AlloyRecipe recipe : RECIPES)
            if(recipe.matchesRecipe(firstSlot, secondSlot))
            {
                theRecipe = recipe;
                break;
            }

        return theRecipe;
    }

    /**
     * Adds an alloy recipe to those available.
     * @param output A desired output for this recipe
     */
    public void addAlloyRecipe(RecipeComponent firstComponent, RecipeComponent secondComponent, ItemStack output)
    {
        if(output == null || output.isEmpty())
            return;
        else if(!firstComponent.metalExists() || !secondComponent.metalExists())
            return;

        AlloyRecipe recipe = new AlloyRecipe(firstComponent, secondComponent, output);
        for(AlloyRecipe r : RECIPES)
            if(r.sameRecipe(recipe))
                return;

        RECIPES.add(recipe);
    }

    public ArrayList<AlloyRecipe> getRecipes()
    {
        return RECIPES;
    }

    public class AlloyRecipe
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
            if(firstSlot.isEmpty() || secondSlot.isEmpty() || first == null || second == null)
                return false;

            if((first.itemMatches(firstSlot) || first.itemMatches(secondSlot)) && (second.itemMatches(firstSlot) || second.itemMatches(secondSlot)))
                return true;
            return false;
        }

        public boolean sameRecipe(AlloyRecipe anotherRecipe)
        {
            if((this.first.isSameComponent(anotherRecipe.first) || this.first.isSameComponent(anotherRecipe.second))
            && (this.second.isSameComponent(anotherRecipe.first) || this.second.isSameComponent(anotherRecipe.second)))
                return true;
            return false;
        }
    }

    public static class RecipeComponent
    {
        public ItemStack item;
        public String oreName;
        public byte ratio = 1;

        public RecipeComponent(ItemStack item, String oreName, byte ratio) {
            this.item = item;
            this.oreName = oreName;
            this.ratio = ratio;
        }

        /**
         * Checks to see if the item passed is this item. Also checks ratio + stack size.
         */
        private boolean itemMatches(ItemStack item)
        {
            if(item.isEmpty() || item.getCount() < ratio)
                return false;

            return this.item != null && OreDictionary.containsMatch(false, OreDictionary.getOres(this.oreName), item);
        }

        public boolean metalExists() {
            return ratio > 0 && ratio <= 64 && oreName != null && item != null;
        }

        public boolean isSameComponent(RecipeComponent component) {
            return component.oreName.equals(this.oreName) && component.ratio == this.ratio;
        }
    }
}
