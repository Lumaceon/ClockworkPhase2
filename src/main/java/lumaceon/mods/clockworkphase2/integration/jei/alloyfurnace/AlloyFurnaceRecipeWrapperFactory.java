package lumaceon.mods.clockworkphase2.integration.jei.alloyfurnace;

import lumaceon.mods.clockworkphase2.recipe.AlloyRecipes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class AlloyFurnaceRecipeWrapperFactory implements IRecipeWrapperFactory<AlloyRecipes.AlloyRecipe>
{
    @Override
    public IRecipeWrapper getRecipeWrapper(AlloyRecipes.AlloyRecipe recipe) {
        return new AlloyFurnaceRecipeWrapper(recipe);
    }
}
