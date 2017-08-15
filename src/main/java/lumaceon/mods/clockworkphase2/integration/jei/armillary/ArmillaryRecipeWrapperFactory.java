package lumaceon.mods.clockworkphase2.integration.jei.armillary;

import lumaceon.mods.clockworkphase2.recipe.ArmillaryRecipes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;

public class ArmillaryRecipeWrapperFactory implements IRecipeWrapperFactory<ArmillaryRecipes.ArmillaryRecipe>
{
    @Override
    public IRecipeWrapper getRecipeWrapper(ArmillaryRecipes.ArmillaryRecipe recipe) {
        return new ArmillaryRecipeWrapper(recipe);
    }
}
