package lumaceon.mods.clockworkphase2.api.crafting.timestream;

import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class TimestreamCraftingRecipe implements ITimestreamCraftingRecipe
{
    protected String unlocalizedName;
    protected long timeSandRequirement;
    protected ResourceLocation icon;
    protected ResourceLocation background;
    protected ItemStack result;

    /**
     * Creates a new timestream recipe. This new recipe should then be registered with TimestreamCraftingRegistry.
     * Up to 8 objects can be passed in after centerItem. If they are instances of the Item, Block or ItemStack classes
     * they will be required for this recipe to be valid.
     *
     * @param unlocalizedName The name of this recipe, which must be unique.
     * @param timeSandRequirement The amount of time sand required in a nearby timezone, which should not be consumed.
     */
    public TimestreamCraftingRecipe(String unlocalizedName, long timeSandRequirement, ResourceLocation icon, ResourceLocation background, ItemStack result)
    {
        this.unlocalizedName = unlocalizedName;
        this.timeSandRequirement = timeSandRequirement;
        this.icon = icon;
        this.background = background;
        this.result = result;
    }

    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @Override
    public long getTimeSandRequirement() {
        return timeSandRequirement;
    }

    @Override
    public boolean updateRecipe(World world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean finalize(World world, int x, int y, int z) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(World world, int x, int y, int z) {
        return result;
    }

    @Override
    public ResourceLocation getBackground() {
        return background;
    }

    @Override
    public ResourceLocation getIcon() {
        return icon;
    }
}
