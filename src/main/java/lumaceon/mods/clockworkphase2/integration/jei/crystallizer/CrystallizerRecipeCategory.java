package lumaceon.mods.clockworkphase2.integration.jei.crystallizer;

import lumaceon.mods.clockworkphase2.lib.Reference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CrystallizerRecipeCategory implements IRecipeCategory
{
    public static final String UID = Reference.MOD_ID + ":crystallizer";
    private IDrawable background;
    private IDrawable tankLines;

    public CrystallizerRecipeCategory(IGuiHelper guiHelper)
    {
        ResourceLocation rl = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockwork_crystallizer_jei.png");
        background = guiHelper.createDrawable(rl, 0, 0, 122, 66);
        tankLines = guiHelper.createDrawable(rl, 0, 66, 16, 50);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Clockwork Crystallizer";
    }

    @Override
    public String getModName() {
        return Reference.MOD_NAME;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void drawExtras(Minecraft minecraft)
    {
        GlStateManager.disableBlend();
        tankLines.draw(minecraft, 98, 12);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        boolean isFluidAnInput = !ingredients.getInputs(FluidStack.class).isEmpty();

        if(isFluidAnInput)
        {
            recipeLayout.getFluidStacks().init(0, true, 98, 8, 16, 50, 10000, true, null);
            recipeLayout.getFluidStacks().set(0, ingredients.getInputs(FluidStack.class).get(0));
        }

        List<List<ItemStack>> inputStackList = ingredients.getInputs(ItemStack.class);

        int index = 0;
        for(List<ItemStack> input : inputStackList)
        {
            int row = index / 2;
            int column = index % 2;
            recipeLayout.getItemStacks().init(index, true, 6 + column * 18, 6 + row * 18);
            recipeLayout.getItemStacks().set(index, input);
            ++index;
        }

        recipeLayout.getItemStacks().init(6, false, 72, 24);
        recipeLayout.getItemStacks().set(6, ingredients.getOutputs(ItemStack.class).get(0));

    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return new ArrayList<>();
    }
}
