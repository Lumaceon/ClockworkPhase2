package lumaceon.mods.clockworkphase2.block;

import net.minecraft.block.material.Material;

public class BlockLightningRod extends BlockClockworkPhase
{
    public BlockLightningRod(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
        this.setLightOpacity(0);
    }

    public boolean isOpaqueCube() {
        return false;
    }
}
