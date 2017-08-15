package lumaceon.mods.clockworkphase2.integration.jei.crusher;

import lumaceon.mods.clockworkphase2.recipe.CrusherRecipes;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CrusherRecipeHandler implements IRecipeHandler<CrusherRecipes.CrusherRecipe>
{
    @Override
    public Class<CrusherRecipes.CrusherRecipe> getRecipeClass() {
        return CrusherRecipes.CrusherRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid(CrusherRecipes.CrusherRecipe recipe) {
        return CrusherRecipeCategory.UID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(CrusherRecipes.CrusherRecipe recipe)
    {
        return new CrusherRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(CrusherRecipes.CrusherRecipe recipe) {
        return true;
    }
}
