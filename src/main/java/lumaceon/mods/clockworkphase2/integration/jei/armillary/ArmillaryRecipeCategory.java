package lumaceon.mods.clockworkphase2.integration.jei.armillary;

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

public class ArmillaryRecipeCategory implements IRecipeCategory
{
    public static final String UID = Reference.MOD_ID + ":armillary";
    private IDrawable background;

    public ArmillaryRecipeCategory(IGuiHelper guiHelper)
    {
        ResourceLocation rl = new ResourceLocation(Reference.MOD_ID, "textures/gui/clockwork_armillary_JEI.png");
        background = guiHelper.createDrawable(rl, 0, 0, 101, 70);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Armillary Restoration";
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

        int index = 0;
        for(int i = 0; i < inputStackList.size(); i++)
        {
            recipeLayout.getItemStacks().init(i, true, 6 + i*18, 6);
            recipeLayout.getItemStacks().set(i, inputStackList.get(i));
            index = i;
        }

        recipeLayout.getItemStacks().init(index+1, false, 77, 22);
        recipeLayout.getItemStacks().set(index+1, ingredients.getOutputs(ItemStack.class).get(0));
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return new ArrayList<String>();
    }
}
