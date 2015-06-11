package lumaceon.mods.clockworkphase2.item.construct.tool;

import com.google.common.collect.Sets;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.Set;

public class ItemClockworkShovel extends ItemClockworkTool
{
    private static final Set field_150916_c = Sets.newHashSet(new Block[]{Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium});

    public ItemClockworkShovel(ToolMaterial material, String unlocalizedName) {
        super(1.0F, material, field_150916_c, unlocalizedName);
    }

    @Override
    public boolean func_150897_b(Block p_150897_1_) {
        return p_150897_1_ == Blocks.snow_layer ? true : p_150897_1_ == Blocks.snow;
    }

    @Override
    public void setHarvestLevels(ItemStack item, int harvestLevel) {
        NBTHelper.INT.set(item, NBTTags.HARVEST_LEVEL_SHOVEL, harvestLevel);
    }
}
