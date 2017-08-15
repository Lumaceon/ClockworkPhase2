package lumaceon.mods.clockworkphase2.block.multiblocktemplate;

import lumaceon.mods.clockworkphase2.api.block.IMultiblockTemplate;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;

public class MultiblockTemplateArmillaryRing implements IMultiblockTemplate
{
    public static MultiblockTemplateArmillaryRing INSTANCE = new MultiblockTemplateArmillaryRing();
    public BlockData[] ARMILLARY_RING;

    public void init()
    {
        Block armillaryRing = ModBlocks.armillaryRing;
        Block topFrame = ModBlocks.armillaryRingFrame;
        Block bottomFrame = ModBlocks.armillaryRingFrameBottom;
        Block bottomFrameCorner = ModBlocks.armillaryRingFrameBottomCorner;

        ARMILLARY_RING = new BlockData[]
                {
                        //Center bottom block
                        new BlockData(0, -6, 0, EnumFacing.DOWN.ordinal(), bottomFrame),

                        //Bottom blocks moving outward.
                        new BlockData(-1, -6, 0, EnumFacing.WEST.ordinal(), bottomFrame),
                        new BlockData(0, -6, -1, EnumFacing.NORTH.ordinal(), bottomFrame),
                        new BlockData(1, -6, 0, EnumFacing.EAST.ordinal(), bottomFrame),
                        new BlockData(0, -6, 1, EnumFacing.SOUTH.ordinal(), bottomFrame),

                        new BlockData(-2, -6, 0, EnumFacing.WEST.ordinal(), bottomFrame),
                        new BlockData(0, -6, -2, EnumFacing.NORTH.ordinal(), bottomFrame),
                        new BlockData(2, -6, 0, EnumFacing.EAST.ordinal(), bottomFrame),
                        new BlockData(0, -6, 2, EnumFacing.SOUTH.ordinal(), bottomFrame),

                        //Corner steps moving both out and up.
                        new BlockData(-3, -6, 0, EnumFacing.WEST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -6, -3, EnumFacing.NORTH.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(3, -6, 0, EnumFacing.EAST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -6, 3, EnumFacing.SOUTH.getHorizontalIndex(), bottomFrameCorner),

                        new BlockData(-3, -5, 0, EnumFacing.WEST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -5, -3, EnumFacing.NORTH.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(3, -5, 0, EnumFacing.EAST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -5, 3, EnumFacing.SOUTH.getHorizontalIndex(), bottomFrameCorner),

                        new BlockData(-4, -5, 0, EnumFacing.WEST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -5, -4, EnumFacing.NORTH.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(4, -5, 0, EnumFacing.EAST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -5, 4, EnumFacing.SOUTH.getHorizontalIndex(), bottomFrameCorner),

                        new BlockData(-4, -4, 0, EnumFacing.WEST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -4, -4, EnumFacing.NORTH.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(4, -4, 0, EnumFacing.EAST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -4, 4, EnumFacing.SOUTH.getHorizontalIndex(), bottomFrameCorner),

                        new BlockData(-5, -4, 0, EnumFacing.WEST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -4, -5, EnumFacing.NORTH.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(5, -4, 0, EnumFacing.EAST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -4, 5, EnumFacing.SOUTH.getHorizontalIndex(), bottomFrameCorner),

                        new BlockData(-5, -3, 0, EnumFacing.WEST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -3, -5, EnumFacing.NORTH.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(5, -3, 0, EnumFacing.EAST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -3, 5, EnumFacing.SOUTH.getHorizontalIndex(), bottomFrameCorner),

                        new BlockData(-6, -3, 0, EnumFacing.WEST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -3, -6, EnumFacing.NORTH.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(6, -3, 0, EnumFacing.EAST.getHorizontalIndex(), bottomFrameCorner),
                        new BlockData(0, -3, 6, EnumFacing.SOUTH.getHorizontalIndex(), bottomFrameCorner),

                        //Top of the bottom.
                        new BlockData(-6, -2, 0, EnumFacing.UP.ordinal(), bottomFrame),
                        new BlockData(0, -2, -6, EnumFacing.UP.ordinal(), bottomFrame),
                        new BlockData(6, -2, 0, EnumFacing.UP.ordinal(), bottomFrame),
                        new BlockData(0, -2, 6, EnumFacing.UP.ordinal(), bottomFrame),

                        new BlockData(-6, -1, 0, EnumFacing.UP.ordinal(), bottomFrame),
                        new BlockData(0, -1, -6, EnumFacing.UP.ordinal(), bottomFrame),
                        new BlockData(6, -1, 0, EnumFacing.UP.ordinal(), bottomFrame),
                        new BlockData(0, -1, 6, EnumFacing.UP.ordinal(), bottomFrame),

                        //Main North-West ring which moves towards 0,0,6
                        new BlockData(0, 0, -6, EnumFacing.WEST.ordinal(), topFrame),
                        new BlockData(-1, 0, -6, EnumFacing.WEST.ordinal(), topFrame),
                        new BlockData(-2, 0, -6, EnumFacing.WEST.ordinal(), topFrame),
                        new BlockData(-3, 0, -6, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(-3, 0, -5, EnumFacing.WEST.ordinal(), topFrame),
                        new BlockData(-4, 0, -5, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(-4, 0, -4, EnumFacing.WEST.ordinal(), topFrame),
                        new BlockData(-5, 0, -4, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(-5, 0, -3, EnumFacing.WEST.ordinal(), topFrame),
                        new BlockData(-6, 0, -3, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(-6, 0, -2, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(-6, 0, -1, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(-6, 0, 0, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(-6, 0, 1, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(-6, 0, 2, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(-6, 0, 3, EnumFacing.EAST.ordinal(), topFrame),
                        new BlockData(-5, 0, 3, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(-5, 0, 4, EnumFacing.EAST.ordinal(), topFrame),
                        new BlockData(-4, 0, 4, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(-4, 0, 5, EnumFacing.EAST.ordinal(), topFrame),
                        new BlockData(-3, 0, 5, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(-3, 0, 6, EnumFacing.EAST.ordinal(), topFrame),
                        new BlockData(-2, 0, 6, EnumFacing.EAST.ordinal(), topFrame),
                        new BlockData(-1, 0, 6, EnumFacing.EAST.ordinal(), topFrame),
                        new BlockData(0, 0, 6, EnumFacing.DOWN.ordinal(), topFrame), //Special, in that this is where the metadata leads. Use to find main ring tile.

                        //Main South-East ring, which moves towards 0,0,6 but in a backward fashion.
                        new BlockData(1, 0, 6, EnumFacing.WEST.ordinal(), topFrame),
                        new BlockData(2, 0, 6, EnumFacing.WEST.ordinal(), topFrame),
                        new BlockData(3, 0, 6, EnumFacing.WEST.ordinal(), topFrame),
                        new BlockData(3, 0, 5, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(4, 0, 5, EnumFacing.WEST.ordinal(), topFrame),
                        new BlockData(4, 0, 4, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(5, 0, 4, EnumFacing.WEST.ordinal(), topFrame),
                        new BlockData(5, 0, 3, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(6, 0, 3, EnumFacing.WEST.ordinal(), topFrame),
                        new BlockData(6, 0, 2, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(6, 0, 1, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(6, 0, 0, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(6, 0, -1, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(6, 0, -2, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(6, 0, -3, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(5, 0, -3, EnumFacing.EAST.ordinal(), topFrame),
                        new BlockData(5, 0, -4, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(4, 0, -4, EnumFacing.EAST.ordinal(), topFrame),
                        new BlockData(4, 0, -5, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(3, 0, -5, EnumFacing.EAST.ordinal(), topFrame),
                        new BlockData(3, 0, -6, EnumFacing.SOUTH.ordinal(), topFrame),
                        new BlockData(2, 0, -6, EnumFacing.EAST.ordinal(), topFrame),
                        new BlockData(1, 0, -6, EnumFacing.EAST.ordinal(), topFrame),

                        //Technically the main ring doesn't have to be last in our list, but it's always placed last.
                        new BlockData(0, 0, 0, 0, armillaryRing)
                };
    }

    @Override
    public int getMaxIndex() {
        return 93;
    }

    @Override
    public BlockData getBlockForIndex(int index) {
        return ARMILLARY_RING[index];
    }
}
