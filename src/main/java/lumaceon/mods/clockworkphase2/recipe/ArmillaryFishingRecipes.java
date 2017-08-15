package lumaceon.mods.clockworkphase2.recipe;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ArmillaryFishingRecipes
{
    public static final ArmillaryFishingRecipes INSTANCE = new ArmillaryFishingRecipes();

    public ArrayList<ArmillaryFishingRecipe> recipes = new ArrayList<>();

    public void addRecipe(ArmillaryFishingRecipe recipe)
    {
        if(recipes.contains(recipe))
            return;

        recipes.add(recipe);
    }

    /**
     * Be sure to copy the item stack. The itemstack returned is the actual stack in the recipe.
     */
    public ItemStack getResultForFishing(World world, BlockPos pos)
    {
        return recipes.get(ClockworkPhase2.random.nextInt(recipes.size())).getOutput();
    }

    public static class ArmillaryFishingRecipe
    {
        ItemStack output;

        public ArmillaryFishingRecipe(ItemStack output) {
            this.output = output;
        }

        public ItemStack getOutput() {
            return output;
        }
    }
}
