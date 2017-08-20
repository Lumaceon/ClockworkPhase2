package lumaceon.mods.clockworkphase2.block.temporal.armillary;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.tile.temporal.TileArmillaryRing;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

@SuppressWarnings("ALL")
public class BlockArmillaryRingFrameBottomCorner extends BlockClockworkPhase
{
    public static PropertyBool BOTTOM_BLOCK = PropertyBool.create("bottom");

    public BlockArmillaryRingFrameBottomCorner(Material blockMaterial, String name) {
        super(blockMaterial, name);
        this.setLightLevel(1.0F);
        this.setCreativeTab(null);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockFurnace.FACING, EnumFacing.NORTH).withProperty(BOTTOM_BLOCK, false));
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        BlockPos originalPos = pos;
        IBlockState originalState = state;

        int iterations = 0;
        while(iterations < 15)
        {
            if(state == null || !(state.getBlock().equals(ModBlocks.armillaryRingFrameBottom) || state.getBlock().equals(ModBlocks.armillaryRingFrameBottomCorner)))
            {
                return;
            }
            else if(state.getBlock().equals(ModBlocks.armillaryRingFrameBottomCorner))
            {
                if(state.getBlock().getActualState(state, worldIn, pos).getValue(BlockArmillaryRingFrameBottomCorner.BOTTOM_BLOCK))
                {
                    pos = pos.offset(state.getValue(BlockFurnace.FACING).getOpposite());
                    state = worldIn.getBlockState(pos);
                }
                else
                {
                    pos = pos.down();
                    state = worldIn.getBlockState(pos);
                }

            }
            else if(state.getBlock().equals(ModBlocks.armillaryRingFrameBottom))
            {
                pos = pos.offset(state.getValue(BlockDirectional.FACING).getOpposite());
                state = worldIn.getBlockState(pos);
            }

            if(checkAndBreakRing(worldIn, pos, state))
                break;

            ++iterations;
        }

        super.breakBlock(worldIn, originalPos, originalState);
    }

    private boolean checkAndBreakRing(World world, BlockPos pos, IBlockState state)
    {
        if(state != null && state.getBlock().equals(ModBlocks.armillaryRingFrameBottom) && state.getValue(BlockDirectional.FACING).equals(EnumFacing.DOWN))
        {
            BlockPos mainRing = pos.up(6);
            TileEntity te = world.getTileEntity(mainRing);
            if(te != null && te instanceof TileArmillaryRing && !((TileArmillaryRing) te).isBeingDestroyed)
            {
                TileArmillaryRing.destroyMultiblock((TileArmillaryRing) te, world, mainRing);
                return true;
            }
        }
        return false;
    }

    @Override
    public IBlockState getActualState (IBlockState state, IBlockAccess world, BlockPos position)
    {
        EnumFacing direction = EnumFacing.DOWN;
        IBlockState s = world.getBlockState(position.offset(direction));
        if(s != null && s.getBlock() != null && (s.getBlock().equals(ModBlocks.armillaryRingFrame) || s.getBlock().equals(ModBlocks.armillaryRingFrameBottomCorner)))
        {
            direction = EnumFacing.UP;
        }

        if(direction.equals(EnumFacing.UP)) //There's a block below this one, this is the upper block.
            return state.withProperty(BOTTOM_BLOCK, false);
        else
            return state.withProperty(BOTTOM_BLOCK, true);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        world.setBlockState(pos, state.withProperty(BlockFurnace.FACING, placer.getHorizontalFacing()), 2);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
        return this.getDefaultState().withProperty(BlockFurnace.FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockFurnace.FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { BlockFurnace.FACING, BOTTOM_BLOCK });
    }
}
