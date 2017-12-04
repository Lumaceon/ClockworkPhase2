package lumaceon.mods.clockworkphase2.block.temporal;

import lumaceon.mods.clockworkphase2.api.temporal.ITimeSink;
import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalCompressor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockTemporalCompressor extends BlockClockworkPhase implements ITileEntityProvider, ITimeSink
{
    public BlockTemporalCompressor(Material blockMaterial, String name) {
        super(blockMaterial, name);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileTemporalCompressor();
    }
}
