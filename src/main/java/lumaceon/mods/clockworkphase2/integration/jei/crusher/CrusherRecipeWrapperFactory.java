package lumaceon.mods.clockworkphase2.integration.jei.crusher;

import lumaceon.mods.clockworkphase2.recipe.CrusherRecipes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class CrusherRecipeWrapperFactory implements IRecipeWrapperFactory<CrusherRecipes.CrusherRecipe>
{
    @Override
    public IRecipeWrapper getRecipeWrapper(CrusherRecipes.CrusherRecipe recipe) {
        return new CrusherRecipeWrapper(recipe);
    }
}
