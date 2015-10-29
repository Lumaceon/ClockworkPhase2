package lumaceon.mods.clockworkphase2.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockClockworkPhaseFossil extends BlockClockworkPhase
{
    public BlockClockworkPhaseFossil(Material blockMaterial, int harvestLevel, String unlocalizedName)
    {
        super(blockMaterial, unlocalizedName);
        this.setHarvestLevel("trowel", harvestLevel);
        this.setHardness(3F);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortuneLevel) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }
}
