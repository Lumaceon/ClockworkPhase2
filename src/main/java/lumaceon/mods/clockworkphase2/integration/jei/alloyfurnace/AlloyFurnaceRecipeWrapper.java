package lumaceon.mods.clockworkphase2.integration.jei.alloyfurnace;

import lumaceon.mods.clockworkphase2.recipe.AlloyRecipes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AlloyFurnaceRecipeWrapper implements IRecipeWrapper
{
    private List<ItemStack> inputStacks;
    private ItemStack output;

    public AlloyFurnaceRecipeWrapper(AlloyRecipes.AlloyRecipe recipe)
    {
        inputStacks = new ArrayList<>();
        inputStacks.add(recipe.first.item);
        inputStacks.add(recipe.second.item);
        output = recipe.output;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        if(inputStacks != null && !inputStacks.isEmpty())
        {
            ingredients.setInputs(ItemStack.class, inputStacks);
        }

        ingredients.setOutput(ItemStack.class, output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }

    @Nullable
    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return null;
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }
}
