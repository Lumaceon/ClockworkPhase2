package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.tile.temporal.TileTimeWell;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTimeWell extends BlockClockworkPhaseSided implements ITileEntityProvider
{
    public BlockTimeWell(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTimeWell();
    }
}
