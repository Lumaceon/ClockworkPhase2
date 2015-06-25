package lumaceon.mods.clockworkphase2.api.crafting.timestream;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface ITimestreamCraftingRecipe
{
    public String getUnlocalizedName();

    /**
     * Called each tick to check for the correct conditions. Ideally a timer should increment if the conditions are
     * valid.
     * @return True if progress is made, false if not.
     */
    public boolean updateRecipe(World world, int x, int y, int z);

    /**
     * Called every tick before updateRecipe to create the getCraftingResult item if this returns true.
     * @return True if the resulting ItemStack should be created this tick, false if this recipe has more to do.
     */
    public boolean finalize(World world, int x, int y, int z);

    /**
     * This should always return either a new ItemStack or a copy of one.
     * @return The result of this recipe.
     */
    public ItemStack getCraftingResult(World world, int x, int y, int z);

    public ResourceLocation getBackground();

    public ResourceLocation getIcon();
}
