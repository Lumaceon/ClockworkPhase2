package lumaceon.mods.clockworkphase2.timetravel.third.block;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.timetravel.third.tile.TileGhostlyLantern;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockGhostlyLantern extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockGhostlyLantern(Material blockMaterial, String unlocalizedName) {
        super(blockMaterial, unlocalizedName);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileGhostlyLantern();
    }
}
