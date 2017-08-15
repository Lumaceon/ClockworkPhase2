package lumaceon.mods.clockworkphase2.integration.jei.armillary;

import lumaceon.mods.clockworkphase2.recipe.ArmillaryRecipes;
import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class ArmillaryRecipeHandler implements IRecipeHandler<ArmillaryRecipes.ArmillaryRecipe>
{
    @Override
    public Class<ArmillaryRecipes.ArmillaryRecipe> getRecipeClass() {
        return ArmillaryRecipes.ArmillaryRecipe.class;
    }

    @Override
    public String getRecipeCategoryUid(ArmillaryRecipes.ArmillaryRecipe recipe) {
        return ArmillaryRecipeCategory.UID;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(ArmillaryRecipes.ArmillaryRecipe recipe) {
        return new ArmillaryRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(ArmillaryRecipes.ArmillaryRecipe recipe) {
        return true;
    }
}
