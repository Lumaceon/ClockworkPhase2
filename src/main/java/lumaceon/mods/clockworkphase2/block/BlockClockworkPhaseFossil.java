package lumaceon.mods.clockworkphase2.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockClockworkPhaseFossil extends BlockClockworkPhase
{
    public Item itemDropped;

    public BlockClockworkPhaseFossil(Material blockMaterial, int harvestLevel, String unlocalizedName, Item itemDropped)
    {
        super(blockMaterial, unlocalizedName);
        this.setHarvestLevel("trowel", harvestLevel);
        this.setHardness(3F);
        this.itemDropped = itemDropped;
    }

    @Override
    public Item getItemDropped(int meta, Random random, int fortuneLevel) {
        return itemDropped;
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }

    @Override
    public int quantityDroppedWithBonus(int fortuneLevel, Random random)
    {
        if(fortuneLevel > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, random, fortuneLevel))
        {
            int j = random.nextInt(fortuneLevel + 2) - 1;
            if(j < 0)
                j = 0;
            return this.quantityDropped(random) * (j + 1);
        }
        else
            return this.quantityDropped(random);
    }
}
