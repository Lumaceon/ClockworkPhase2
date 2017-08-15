package lumaceon.mods.clockworkphase2.integration.jei.crystallizer;

import lumaceon.mods.clockworkphase2.recipe.CrystallizerRecipes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class CrystallizerRecipeWrapperFactory implements IRecipeWrapperFactory<CrystallizerRecipes.CrystallizerRecipe>
{
    @Override
    public IRecipeWrapper getRecipeWrapper(CrystallizerRecipes.CrystallizerRecipe recipe) {
        return new CrystallizerRecipeWrapper(recipe);
    }
}
