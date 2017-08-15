package lumaceon.mods.clockworkphase2.integration.jei.crystallizer;

import lumaceon.mods.clockworkphase2.recipe.CrystallizerRecipes;
import lumaceon.mods.clockworkphase2.util.LogHelper;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CrystallizerRecipeHandler implements IRecipeHandler<CrystallizerRecipes.CrystallizerRecipe>
{
    @Override
    public Class<CrystallizerRecipes.CrystallizerRecipe> getRecipeClass() {
        return CrystallizerRecipes.CrystallizerRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid(CrystallizerRecipes.CrystallizerRecipe recipe) {
        return CrystallizerRecipeCategory.UID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(CrystallizerRecipes.CrystallizerRecipe recipe) {
        return new CrystallizerRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(CrystallizerRecipes.CrystallizerRecipe recipe)
    {
        if(recipe.getDefaultOutput() == null)
        {
            LogHelper.info("JEI skipping crystallizer recipe without default output.");
            return false;
        }

        return true;
    }
}
