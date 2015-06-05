package lumaceon.mods.clockworkphase2.api.crafting.timestream;

import java.util.HashMap;

public class TimestreamCraftingRegistry
{
    public static HashMap<String, ITimestreamCraftingRecipe> TIMESTREAM_RECIPES = new HashMap<String, ITimestreamCraftingRecipe>();

    public static void registerTimestreamRecipe(ITimestreamCraftingRecipe recipe) {
        TIMESTREAM_RECIPES.put(recipe.getUnlocalizedName(), recipe);
    }
}
