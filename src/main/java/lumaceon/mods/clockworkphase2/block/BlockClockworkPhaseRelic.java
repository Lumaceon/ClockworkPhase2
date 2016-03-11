package lumaceon.mods.clockworkphase2.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockClockworkPhaseRelic extends BlockClockworkPhase
{
    public BlockClockworkPhaseRelic(Material blockMaterial, int harvestLevel, String unlocalizedName)
    {
        super(blockMaterial, unlocalizedName);
        this.setHarvestLevel("trowel", harvestLevel);
        this.setHardness(3F);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }
}
