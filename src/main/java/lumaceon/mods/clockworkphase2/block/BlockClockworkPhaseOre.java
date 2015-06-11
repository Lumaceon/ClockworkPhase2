package lumaceon.mods.clockworkphase2.block;

import net.minecraft.block.material.Material;

public class BlockClockworkPhaseOre extends BlockClockworkPhase
{
    public BlockClockworkPhaseOre(Material blockMaterial, int harvestLevel, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
        this.setHarvestLevel("pickaxe", 1);
    }
}
