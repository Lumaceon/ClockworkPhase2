package lumaceon.mods.clockworkphase2.integration.jei.alloyfurnace;

import lumaceon.mods.clockworkphase2.lib.Reference;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AlloyFurnaceRecipeCategory implements IRecipeCategory
{
    public static final String UID = Reference.MOD_ID + ":alloyfurnace";
    private IDrawable background;

    public AlloyFurnaceRecipeCategory(IGuiHelper guiHelper)
    {
        ResourceLocation rl = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockwork_alloy_furnace_jei.png");
        background = guiHelper.createDrawable(rl, 0, 0, 101, 66);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Clockwork Alloy Furnace";
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
    {}

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        List<List<ItemStack>> inputStackList = ingredients.getInputs(ItemStack.class);

        recipeLayout.getItemStacks().init(0, true, 6, 6);
        if(inputStackList.size() > 0)
        recipeLayout.getItemStacks().set(0, inputStackList.get(0));

        recipeLayout.getItemStacks().init(1, true, 6, 32);
        if(inputStackList.size() > 1)
            recipeLayout.getItemStacks().set(1, inputStackList.get(1));

        recipeLayout.getItemStacks().init(2, false, 77, 19);
        recipeLayout.getItemStacks().set(2, ingredients.getOutputs(ItemStack.class).get(0));
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return new ArrayList<String>();
    }
}
