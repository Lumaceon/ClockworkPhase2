package lumaceon.mods.clockworkphase2.block.temporal;

import lumaceon.mods.clockworkphase2.api.temporal.ITimeSink;
import lumaceon.mods.clockworkphase2.block.BlockClockworkPhase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockTemporalCompressionConduit extends BlockClockworkPhase
{
    public static PropertyInteger ANIMATION_OFFSET = PropertyInteger.create("anim", 0, 7);
    public static PropertyBool ACTIVE = PropertyBool.create("active");
    public static PropertyBool CORNER = PropertyBool.create("corner");
    public static PropertyBool NORTH_C = PropertyBool.create("north_c");
    public static PropertyBool SOUTH_C = PropertyBool.create("south_c");
    public static PropertyBool WEST_C = PropertyBool.create("west_c");
    public static PropertyBool EAST_C = PropertyBool.create("east_c");
    public static PropertyBool UP_C = PropertyBool.create("up_c");
    public static PropertyBool DOWN_C = PropertyBool.create("down_c");

    EnumFacing conduitOutputDirection;

    public BlockTemporalCompressionConduit(Material blockMaterial, String name, @Nullable EnumFacing conduitOutputDirection) {
        super(blockMaterial, name);
        this.conduitOutputDirection = conduitOutputDirection;
        //noinspection ConstantConditions
        this.setCreativeTab(null);

        this.setDefaultState(this.blockState.getBaseState().withProperty(CORNER, false).withProperty(NORTH_C, false).withProperty(SOUTH_C, false).withProperty(WEST_C, false).withProperty(EAST_C, false).withProperty(UP_C, false).withProperty(DOWN_C, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos position)
    {
        if(conduitOutputDirection == null)
            return state;

        boolean isCornerByMultiConnection = false; //If we're forcing corner state because there's multiple connections.
        boolean north = false;
        boolean south = false;
        boolean west = false;
        boolean east = false;
        boolean up = false;
        boolean down = false;
        IBlockState targetState = world.getBlockState(position.offset(conduitOutputDirection.getOpposite()));
        if(targetState != null && (targetState.getBlock() instanceof ITimeSink || (targetState.getBlock() instanceof BlockTemporalCompressionConduit && ((BlockTemporalCompressionConduit) targetState.getBlock()).getConduitOutputDirection().equals(conduitOutputDirection))))
        {
            //There is a block opposite this block's facing, and it's valid in one way or another.
            //Now check for additional ITimeSink connections that also may require the corner block...
            for(int i = 0; i < 6; i++)
            {
                EnumFacing facing = EnumFacing.getFront(i);
                if(!facing.equals(conduitOutputDirection) && !facing.equals(conduitOutputDirection.getOpposite()))
                {
                    IBlockState s = world.getBlockState(position.offset(facing));
                    if(s != null && (s.getBlock() instanceof ITimeSink || (s.getBlock() instanceof BlockTemporalCompressionConduit && ((BlockTemporalCompressionConduit) s.getBlock()).conduitOutputDirection != null && ((BlockTemporalCompressionConduit) s.getBlock()).conduitOutputDirection.getOpposite().equals(facing))))
                    {
                        isCornerByMultiConnection = true;
                        if(facing.equals(EnumFacing.NORTH))
                            north = true;
                        else if(facing.equals(EnumFacing.SOUTH))
                            south = true;
                        else if(facing.equals(EnumFacing.WEST))
                            west = true;
                        else if(facing.equals(EnumFacing.EAST))
                            east = true;
                        else if(facing.equals(EnumFacing.UP))
                            up = true;
                        else if(facing.equals(EnumFacing.DOWN))
                            down = true;
                    }
                }
            }

            if(conduitOutputDirection.getOpposite().equals(EnumFacing.NORTH))
                north = true;
            else if(conduitOutputDirection.getOpposite().equals(EnumFacing.SOUTH))
                south = true;
            else if(conduitOutputDirection.getOpposite().equals(EnumFacing.WEST))
                west = true;
            else if(conduitOutputDirection.getOpposite().equals(EnumFacing.EAST))
                east = true;
            else if(conduitOutputDirection.getOpposite().equals(EnumFacing.UP))
                up = true;
            else if(conduitOutputDirection.getOpposite().equals(EnumFacing.DOWN))
                down = true;

            if(isCornerByMultiConnection)
                return state.withProperty(CORNER, true).withProperty(NORTH_C, north).withProperty(SOUTH_C, south).withProperty(WEST_C, west).withProperty(EAST_C, east).withProperty(UP_C, up).withProperty(DOWN_C, down);
            else
                return state.withProperty(CORNER, false);
        }

        for(int i = 0; i < 6; i++)
        {
            EnumFacing facing = EnumFacing.getFront(i);
            if(!facing.equals(conduitOutputDirection) && !facing.equals(conduitOutputDirection.getOpposite()))
            {
                IBlockState s = world.getBlockState(position.offset(facing));
                if(s != null && (s.getBlock() instanceof ITimeSink || (s.getBlock() instanceof BlockTemporalCompressionConduit && ((BlockTemporalCompressionConduit) s.getBlock()).conduitOutputDirection != null && ((BlockTemporalCompressionConduit) s.getBlock()).conduitOutputDirection.getOpposite().equals(facing))))
                {
                    if(facing.equals(EnumFacing.NORTH))
                        north = true;
                    else if(facing.equals(EnumFacing.SOUTH))
                        south = true;
                    else if(facing.equals(EnumFacing.WEST))
                        west = true;
                    else if(facing.equals(EnumFacing.EAST))
                        east = true;
                    else if(facing.equals(EnumFacing.UP))
                        up = true;
                    else if(facing.equals(EnumFacing.DOWN))
                        down = true;
                }
            }
        }

        return state.withProperty(CORNER, true).withProperty(NORTH_C, north).withProperty(SOUTH_C, south).withProperty(WEST_C, west).withProperty(EAST_C, east).withProperty(UP_C, up).withProperty(DOWN_C, down);
    }

    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }

    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] { CORNER, NORTH_C, SOUTH_C, WEST_C, EAST_C, UP_C, DOWN_C });
    }

    /**
     * @return The direction this is pointing, or null. Animation should move in the /opposite/ direction.
     */
    public EnumFacing getConduitOutputDirection() {
        return conduitOutputDirection;
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
