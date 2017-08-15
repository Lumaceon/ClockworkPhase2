package lumaceon.mods.clockworkphase2.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class ArmillaryRecipes
{
    public static final ArmillaryRecipes instance = new ArmillaryRecipes();

    public ArrayList<ArmillaryRecipe> recipes = new ArrayList<>();

    public void addRecipe(ItemStack input, ItemStack output, NonNullList<ItemStack> additionalChanceItems)
    {
        for(ArmillaryRecipe r : recipes)
            if(input.equals(r.input))
                return;

        ArmillaryRecipe recipe = new ArmillaryRecipe(input, output, additionalChanceItems);
        recipes.add(recipe);
    }

    public ArmillaryRecipe getRecipe(ItemStack input)
    {
        for(ArmillaryRecipe r : recipes)
            if(OreDictionary.itemMatches(r.input, input, false))
                return r;
        return null;
    }

    public ArrayList<ArmillaryRecipe> getRecipes() {
        return recipes;
    }

    public static class ArmillaryRecipe
    {
        public ItemStack input;
        public ItemStack output;
        public NonNullList<ItemStack> additionalChanceInputs;

        public ArmillaryRecipe(ItemStack input, ItemStack output, NonNullList<ItemStack> additionalChanceItems) {
            this.input = input;
            this.output = output;
            this.additionalChanceInputs = additionalChanceItems;
        }

        /**
         * Used to find out how many of the given item a recipe can accept. Note: this ignores the main input.
         * @param input The type of item.
         * @return The number of the type of item this recipe can accept, including 0 if it isn't valid.
         */
        public int getMaxNumberOfItem(ItemStack input)
        {
            if(input.isEmpty())
                return 0;

            int ret = 0;

            for(ItemStack i : additionalChanceInputs)
            {
                if(!i.isEmpty() && OreDictionary.itemMatches(i, input, false))
                {
                    ++ret;
                }
            }

            return ret;
        }
    }
}
