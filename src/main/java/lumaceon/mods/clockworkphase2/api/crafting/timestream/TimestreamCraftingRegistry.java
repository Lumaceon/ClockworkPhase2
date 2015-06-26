package lumaceon.mods.clockworkphase2.api.crafting.timestream;

import java.util.ArrayList;

public class TimestreamCraftingRegistry
{
    public static ArrayList<ITimestreamCraftingRecipe> TIMESTREAM_RECIPES = new ArrayList<ITimestreamCraftingRecipe>();

    public static void registerTimestreamRecipe(ITimestreamCraftingRecipe recipe) {
        TIMESTREAM_RECIPES.add(recipe);
    }

    public static ITimestreamCraftingRecipe getRecipe(String unlocalizedName)
    {
        for(ITimestreamCraftingRecipe recipe : TIMESTREAM_RECIPES)
        {
            if(recipe.getUnlocalizedName().equals(unlocalizedName))
                return recipe;
        }
        return null;
    }

    public static void sortRecipesByTimeRequirement()
    {
        ArrayList<ITimestreamCraftingRecipe> sortedList = new ArrayList<ITimestreamCraftingRecipe>(TIMESTREAM_RECIPES.size());
        ITimestreamCraftingRecipe leastCostly = null;
        for(int n = 0; n < sortedList.size(); n++)
        {
            for(ITimestreamCraftingRecipe recipe : TIMESTREAM_RECIPES)
            {
                if(leastCostly == null)
                    leastCostly = recipe;
                else if(leastCostly.getTimeSandRequirement() > recipe.getTimeSandRequirement())
                    leastCostly = recipe;
            }
            sortedList.add(leastCostly);
            TIMESTREAM_RECIPES.remove(leastCostly);
        }
    }
}
