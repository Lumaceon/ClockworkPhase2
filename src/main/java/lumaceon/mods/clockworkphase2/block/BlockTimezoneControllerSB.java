package lumaceon.mods.clockworkphase2.block;

import net.minecraft.block.material.Material;

public class BlockTimezoneControllerSB extends BlockClockworkPhase
{
    public BlockTimezoneControllerSB(Material blockMaterial, String unlocalizedName)
    {
        super(blockMaterial, unlocalizedName);
        this.setCreativeTab(null);
        this.setBlockUnbreakable();
        this.setResistance(1000000F);
        this.setLightLevel(1.0F);
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }
}