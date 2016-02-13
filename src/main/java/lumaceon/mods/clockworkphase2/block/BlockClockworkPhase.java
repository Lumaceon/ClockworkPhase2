package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockClockworkPhase extends Block
{
    public BlockClockworkPhase(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial);
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setHardness(3.0F);
        this.setUnlocalizedName(unlocalizedName);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }
}
