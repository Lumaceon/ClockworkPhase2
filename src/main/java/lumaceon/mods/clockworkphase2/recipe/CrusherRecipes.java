package lumaceon.mods.clockworkphase2.recipe;


import lumaceon.mods.clockworkphase2.util.LogHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CrusherRecipes
{
    public static final CrusherRecipes instance = new CrusherRecipes();

    private HashMap<ItemStack, ItemStack> RECIPES = new HashMap<>(10);
    private ArrayList<CrusherRecipe> recipeClasses = new ArrayList<>();

    public ItemStack getCrusherResult(ItemStack input)
    {
        for(Map.Entry<ItemStack, ItemStack> entry : RECIPES.entrySet())
        {
            if(compareItemStacks(input, entry.getKey()))
            {
                return entry.getValue();
            }
        }

        return ItemStack.EMPTY;
    }

    public void addCrusherRecipe(ItemStack input, ItemStack output)
    {
        if(!getCrusherResult(input).isEmpty())
        {
            LogHelper.info("Skipped duplicate crusher recipe: " + input.getDisplayName() + " -> " + output.getDisplayName());
            return;
        }
        RECIPES.put(input, output);
        recipeClasses.add(new CrusherRecipe(input, output));
    }

    public Map<ItemStack, ItemStack> getRecipes() {
        return RECIPES;
    }

    public ArrayList<CrusherRecipe> getRecipeClasses() {
        return recipeClasses;
    }

    /**
     * Compares two itemstacks to ensure that they are the same. This checks both the item and the metadata of the item.
     */
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }

    public static class CrusherRecipe
    {
        public ItemStack input;
        public ItemStack output;

        public CrusherRecipe(ItemStack input, ItemStack output) {
            this.input = input;
            this.output = output;
        }
    }
}
