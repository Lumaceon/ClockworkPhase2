package lumaceon.mods.clockworkphase2.block.temporal.armillary;

import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.tile.temporal.TileArmillaryRing;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockArmillaryRingFrame extends BlockClockworkPhase
{
    public static final PropertyEnum<TextureOrientation> TEXTURE_ORIENTATION = PropertyEnum.create("texture_orientation", TextureOrientation.class);

    public BlockArmillaryRingFrame(Material blockMaterial, String name) {
        super(blockMaterial, name);
        this.setLightLevel(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(BlockDirectional.FACING, EnumFacing.NORTH).withProperty(TEXTURE_ORIENTATION, TextureOrientation.STRAIGHT_NORTHSOUTH));
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        BlockPos originalPos = pos;
        IBlockState originalState = state;

        if(checkAndBreakRing(worldIn, pos, state))
            return;

        pos = pos.offset(state.getValue(BlockDirectional.FACING));
        state = worldIn.getBlockState(pos);

        int iterations = 0;
        while(iterations < 30)
        {
            if(state == null || !state.getBlock().equals(ModBlocks.armillaryRingFrame))
            {
                return;
            }

            pos = pos.offset(state.getValue(BlockDirectional.FACING));
            state = worldIn.getBlockState(pos);

            if(checkAndBreakRing(worldIn, pos, state))
                break;

            ++iterations;
        }

        super.breakBlock(worldIn, originalPos, originalState);
    }

    private boolean checkAndBreakRing(World world, BlockPos pos, IBlockState state)
    {
        if(state != null && state.getBlock().equals(this) && state.getValue(BlockDirectional.FACING).equals(EnumFacing.DOWN))
        {
            BlockPos mainRing = pos.north(6);
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
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        if(placer.rotationPitch < -65)
            world.setBlockState(pos, state.withProperty(BlockDirectional.FACING, EnumFacing.UP), 2);
        else
            world.setBlockState(pos, state.withProperty(BlockDirectional.FACING, placer.getHorizontalFacing()), 2);
    }

    @Override
    public IBlockState getActualState (IBlockState state, IBlockAccess world, BlockPos position)
    {
        EnumFacing direction = null;
        IBlockState north = world.getBlockState(position.north());
        IBlockState south = world.getBlockState(position.south());
        IBlockState east = world.getBlockState(position.east());
        IBlockState west = world.getBlockState(position.west());

        boolean isFrameNorth = north != null && north.getBlock().equals(this);
        boolean isFrameSouth = south != null && south.getBlock().equals(this);
        boolean isFrameEast = east != null && east.getBlock().equals(this);
        boolean isFrameWest = west != null && west.getBlock().equals(this);

        if(isFrameNorth && isFrameSouth)
        {
            return state.withProperty(TEXTURE_ORIENTATION, TextureOrientation.STRAIGHT_NORTHSOUTH);
        }
        else if(isFrameEast && isFrameWest)
        {
            return state.withProperty(TEXTURE_ORIENTATION, TextureOrientation.STRAIGHT_EASTWEST);
        }
        else if(isFrameNorth && isFrameEast)
        {
            return state.withProperty(TEXTURE_ORIENTATION, TextureOrientation.CORNER_NORTHEAST);
        }
        else if(isFrameEast && isFrameSouth)
        {
            return state.withProperty(TEXTURE_ORIENTATION, TextureOrientation.CORNER_EASTSOUTH);
        }
        else if(isFrameSouth && isFrameWest)
        {
            return state.withProperty(TEXTURE_ORIENTATION, TextureOrientation.CORNER_SOUTHWEST);
        }
        else if(isFrameWest && isFrameNorth)
        {
            return state.withProperty(TEXTURE_ORIENTATION, TextureOrientation.CORNER_WESTNORTH);
        }

        return state.withProperty(TEXTURE_ORIENTATION, TextureOrientation.STRAIGHT_NORTHSOUTH);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        return this.getDefaultState().withProperty(BlockDirectional.FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(BlockDirectional.FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { BlockDirectional.FACING, TEXTURE_ORIENTATION });
    }

    public static enum TextureOrientation implements IStringSerializable
    {
        STRAIGHT_NORTHSOUTH("straight_ns"), STRAIGHT_EASTWEST("straight_ew"),
        //Corners represent which parts of the texture are connected to another.
        CORNER_NORTHEAST("corner_ne"), CORNER_EASTSOUTH("corner_es"), CORNER_SOUTHWEST("corner_sw"), CORNER_WESTNORTH("corner_wn");

        private final String name;

        private TextureOrientation(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }
}
