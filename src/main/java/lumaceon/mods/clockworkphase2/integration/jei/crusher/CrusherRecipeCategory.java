package lumaceon.mods.clockworkphase2.integration.jei.crusher;

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

public class CrusherRecipeCategory implements IRecipeCategory
{
    public static final String UID = Reference.MOD_ID + ":crusher";
    private IDrawable background;

    public CrusherRecipeCategory(IGuiHelper guiHelper)
    {
        ResourceLocation rl = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockwork_crusher_jei.png");
        background = guiHelper.createDrawable(rl, 0, 0, 101, 70);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Clockwork Crusher";
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
        recipeLayout.getItemStacks().set(0, inputStackList.get(0));

        recipeLayout.getItemStacks().init(1, false, 77, 22);
        recipeLayout.getItemStacks().set(1, ingredients.getOutputs(ItemStack.class).get(0));
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return new ArrayList<String>();
    }
}
