package lumaceon.mods.clockworkphase2.block.temporal;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.lib.GUIs;
import lumaceon.mods.clockworkphase2.tile.temporal.TileTimezoneModulator;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockTimezoneModulator extends BlockClockworkPhase implements ITileEntityProvider
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockTimezoneModulator(Material blockMaterial, String name) {
        super(blockMaterial, name);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            if(!world.isRemote)
                player.openGui(ClockworkPhase2.instance, GUIs.TIMEZONE_MODULATOR_CONSTRUCTION.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        if(worldIn.getBlockState(pos.down()).getBlock() == ModBlocks.timezoneModulatorBottom)
        {
            worldIn.setBlockToAir(pos.down());
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.timezoneModulator;
    }

    @Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        return EnumPushReaction.BLOCK;
    }

    private static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(1.0/16.0, 0.0D, 6.0/16.0, 1.0D - 1.0/16.0, 1.0D, 1.0D);
    private static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(1.0/16.0, 0.0D, 0.0D, 1.0D - 1.0/16.0, 1.0D, 1.0D - 6.0/16.0);
    private static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(6.0/16.0, 0.0D, 1.0/16.0, 1.0D, 1.0D, 1.0D - 1.0/16.0);
    private static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.0D, 0.0D, 1.0/16.0, 1.0D - 6.0/16.0, 1.0D, 1.0D - 1.0/16.0);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        if(facing.equals(EnumFacing.NORTH))
            return AABB_NORTH;
        else if(facing.equals(EnumFacing.SOUTH))
            return AABB_SOUTH;
        else if(facing.equals(EnumFacing.WEST))
            return AABB_WEST;
        else if(facing.equals(EnumFacing.EAST))
            return AABB_EAST;

        return FULL_BLOCK_AABB;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getIndex();
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if(!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileTimezoneModulator();
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
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    public static class BlockTimezoneModulatorBottom extends BlockClockworkPhase
    {
        public BlockTimezoneModulatorBottom(Material blockMaterial, String name) {
            super(blockMaterial, name);
            this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        }

        @Override
        public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
        {
            if(!player.isSneaking())
            {
                if(!world.isRemote)
                    player.openGui(ClockworkPhase2.instance, GUIs.TIMEZONE_MODULATOR_CONSTRUCTION.ordinal(), world, pos.getX(), pos.getY() + 1, pos.getZ());
                return true;
            }
            return false;
        }

        @Override
        public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
        {
            super.breakBlock(worldIn, pos, state);
            if(worldIn.getBlockState(pos.up()).getBlock() == ModBlocks.timezoneModulator)
            {
                worldIn.setBlockToAir(pos.up());
            }
        }

        @Override
        public Item getItemDropped(IBlockState state, Random rand, int fortune) {
            return ModItems.timezoneModulator;
        }

        @Override
        public EnumPushReaction getMobilityFlag(IBlockState state) {
            return EnumPushReaction.BLOCK;
        }

        @Override
        public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
        {
            EnumFacing facing = state.getValue(FACING);
            if(facing.equals(EnumFacing.NORTH))
                return AABB_NORTH;
            else if(facing.equals(EnumFacing.SOUTH))
                return AABB_SOUTH;
            else if(facing.equals(EnumFacing.WEST))
                return AABB_WEST;
            else if(facing.equals(EnumFacing.EAST))
                return AABB_EAST;

            return FULL_BLOCK_AABB;
        }

        @Override
        public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
            this.setDefaultFacing(worldIn, pos, state);
        }

        @Override
        public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
            return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
        }

        @Override
        public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
        {
            super.onBlockPlacedBy(world, pos, state, placer, stack);
            world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
        }

        /**
         * Convert the given metadata into a BlockState for this Block
         */
        @SuppressWarnings("deprecation")
        @Override
        public IBlockState getStateFromMeta(int meta)
        {
            EnumFacing enumfacing = EnumFacing.getFront(meta);

            if (enumfacing.getAxis() == EnumFacing.Axis.Y)
            {
                enumfacing = EnumFacing.NORTH;
            }

            return this.getDefaultState().withProperty(FACING, enumfacing);
        }

        /**
         * Convert the BlockState into the correct metadata value
         */
        @Override
        public int getMetaFromState(IBlockState state)
        {
            return state.getValue(FACING).getIndex();
        }

        @SuppressWarnings("deprecation")
        @Override
        public IBlockState withRotation(IBlockState state, Rotation rot) {
            return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
        }

        /**
         * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
         * blockstate.
         */
        @Override
        public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
        {
            return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
        }

        @Override
        protected BlockStateContainer createBlockState()
        {
            return new BlockStateContainer(this, new IProperty[] { FACING });
        }

        private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
        {
            if(!worldIn.isRemote)
            {
                IBlockState iblockstate = worldIn.getBlockState(pos.north());
                IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
                IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
                IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
                EnumFacing enumfacing = state.getValue(FACING);

                if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
                {
                    enumfacing = EnumFacing.SOUTH;
                }
                else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
                {
                    enumfacing = EnumFacing.NORTH;
                }
                else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
                {
                    enumfacing = EnumFacing.EAST;
                }
                else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
                {
                    enumfacing = EnumFacing.WEST;
                }

                worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
            }
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
        @SideOnly(Side.CLIENT)
        public BlockRenderLayer getBlockLayer() {
            return BlockRenderLayer.CUTOUT;
        }
    }
}
