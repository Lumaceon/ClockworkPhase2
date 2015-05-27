package lumaceon.mods.clockworkphase2.api.crafting;

import java.util.ArrayList;

public class TimestreamCraftingRegistry
{
    public static ArrayList<ITimestreamCraftingRecipe> TIMESTREAM_RECIPES = new ArrayList<ITimestreamCraftingRecipe>();

    public static void registerTimestreamRecipe(ITimestreamCraftingRecipe recipe)
    {
        TIMESTREAM_RECIPES.add(recipe);
    }
}
