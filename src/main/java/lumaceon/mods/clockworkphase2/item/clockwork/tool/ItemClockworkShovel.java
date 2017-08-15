package lumaceon.mods.clockworkphase2.item.clockwork.tool;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

import java.util.Set;

public class ItemClockworkShovel extends ItemClockworkTool
{
    private static final Set field_150916_c = Sets.newHashSet(new Block[]{Blocks.GRASS, Blocks.DIRT, Blocks.SAND, Blocks.GRAVEL, Blocks.SNOW_LAYER, Blocks.SNOW, Blocks.CLAY, Blocks.FARMLAND, Blocks.SOUL_SAND, Blocks.MYCELIUM});

    public ItemClockworkShovel(ToolMaterial material, String unlocalizedName) {
        super(1.0F, material, field_150916_c, unlocalizedName);
    }

    @Override
    public boolean canHarvestBlock(IBlockState state) {
        return state.getBlock() == Blocks.SNOW_LAYER || state.getBlock() == Blocks.SNOW;
    }

    @Override
    public String getHarvestType() {
        return "shovel";
    }

    @Override
    public Material[] getEffectiveMaterials() {
        return new Material[] { Material.GROUND, Material.GRASS, Material.SAND, Material.SNOW };
    }
}
