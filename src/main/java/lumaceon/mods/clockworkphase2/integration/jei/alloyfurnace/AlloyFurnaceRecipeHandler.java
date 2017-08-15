package lumaceon.mods.clockworkphase2.integration.jei.alloyfurnace;

import lumaceon.mods.clockworkphase2.recipe.AlloyRecipes;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class AlloyFurnaceRecipeHandler implements IRecipeHandler<AlloyRecipes.AlloyRecipe>
{
    @Override
    public Class<AlloyRecipes.AlloyRecipe> getRecipeClass() {
        return AlloyRecipes.AlloyRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid(AlloyRecipes.AlloyRecipe recipe) {
        return AlloyFurnaceRecipeCategory.UID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(AlloyRecipes.AlloyRecipe recipe)
    {
        return new AlloyFurnaceRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(AlloyRecipes.AlloyRecipe recipe) {
        return true;
    }
}
