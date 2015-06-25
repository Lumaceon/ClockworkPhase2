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
}
