package lumaceon.mods.clockworkphase2.block.temporal;

import lumaceon.mods.clockworkphase2.api.timezone.TimezoneHandler;
import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTemporalZoningMachine;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTemporalZoningMachine extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockTemporalZoningMachine(Material blockMaterial, String name) {
        super(blockMaterial, name);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        TimezoneHandler.INTERNAL.timezoneCleanup();
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileTemporalZoningMachine();
    }

    /*@Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        ClockworkPhase2.proxy.spawnParticle(1, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
    }*/

    @SuppressWarnings("deprecation")
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
