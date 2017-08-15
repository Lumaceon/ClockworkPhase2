package lumaceon.mods.clockworkphase2.integration.jei.crystallizer;

import lumaceon.mods.clockworkphase2.recipe.CrystallizerRecipes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nullable;
import java.util.List;

public class CrystallizerRecipeWrapper implements IRecipeWrapper
{
    private List<ItemStack> inputStacks;
    private FluidStack fluidInput;
    private ItemStack output;

    public CrystallizerRecipeWrapper(CrystallizerRecipes.CrystallizerRecipe recipe) {
        inputStacks = recipe.inputItems;
        fluidInput = recipe.fluidInventory;
        output = recipe.getDefaultOutput();
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        if(inputStacks != null && !inputStacks.isEmpty())
        {
            ingredients.setInputs(ItemStack.class, inputStacks);
        }

        if(fluidInput != null)
        {
            ingredients.setInput(FluidStack.class, fluidInput);
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
