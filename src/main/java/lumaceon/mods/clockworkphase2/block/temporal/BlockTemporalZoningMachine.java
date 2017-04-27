package lumaceon.mods.clockworkphase2.block.temporal;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.tile.TileTemporalZoningMachine;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockTemporalZoningMachine extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockTemporalZoningMachine(Material blockMaterial, String name) {
        super(blockMaterial, name);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileTemporalZoningMachine();
    }
}
