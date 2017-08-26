package lumaceon.mods.clockworkphase2.block.temporal.armillary;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.tile.temporal.TileArmillaryRing;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings("ALL")
public class BlockArmillaryRing extends BlockClockworkPhase implements ITileEntityProvider
{
    public BlockArmillaryRing(Material blockMaterial, String name) {
        super(blockMaterial, name);
        this.setCreativeTab(null);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        if(te != null && te instanceof TileArmillaryRing && !((TileArmillaryRing) te).isBeingDestroyed)
            TileArmillaryRing.destroyMultiblock((TileArmillaryRing) te, worldIn, pos);
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileArmillaryRing();
    }
}
