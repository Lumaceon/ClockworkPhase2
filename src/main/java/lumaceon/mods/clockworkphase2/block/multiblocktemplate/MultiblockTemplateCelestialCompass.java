package lumaceon.mods.clockworkphase2.block.multiblocktemplate;

import lumaceon.mods.clockworkphase2.api.block.IMultiblockTemplate;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;

public class MultiblockTemplateCelestialCompass implements IMultiblockTemplate
{
    public static MultiblockTemplateCelestialCompass INSTANCE = new MultiblockTemplateCelestialCompass();
    public Block subBlock;
    public BlockData[] CELESTIAL_COMPASS;

    public void init()
    {
        subBlock = ModBlocks.celestialCompassSB.getBlock();

        CELESTIAL_COMPASS = new BlockData[]{
                new BlockData(5, 0, 2, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(5, 0, 1, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(5, 0, 0, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(5, 0, -1, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(5, 0, -2, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(4, 0, 3, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(4, 0, 2, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(4, 0, 1, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(4, 0, 0, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(4, 0, -1, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(4, 0, -2, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(4, 0, -3, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(3, 0, 4, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(3, 0, 3, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(3, 0, 2, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(3, 0, 1, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(3, 0, 0, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(3, 0, -1, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(3, 0, -2, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(3, 0, -3, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(3, 0, -4, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(2, 0, 5, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(2, 0, 4, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(2, 0, 3, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(2, 0, 2, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(2, 0, 1, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(2, 0, 0, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(2, 0, -1, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(2, 0, -2, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(2, 0, -3, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(2, 0, -4, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(2, 0, -5, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(1, 0, 5, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(1, 0, 4, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(1, 0, 3, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(1, 0, 2, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(1, 0, 1, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(1, 0, 0, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(1, 0, -1, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(1, 0, -2, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(1, 0, -3, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(1, 0, -4, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(1, 0, -5, EnumFacing.WEST.ordinal(), subBlock),
                new BlockData(0, 0, 5, EnumFacing.NORTH.ordinal(), subBlock),
                new BlockData(0, 0, 4, EnumFacing.NORTH.ordinal(), subBlock),
                new BlockData(0, 0, 3, EnumFacing.NORTH.ordinal(), subBlock),
                new BlockData(0, 0, 2, EnumFacing.NORTH.ordinal(), subBlock),
                new BlockData(0, 0, 1, EnumFacing.NORTH.ordinal(), subBlock),
                new BlockData(0, 0, 0, EnumFacing.EAST.ordinal(), ModBlocks.celestialCompass.getBlock()),
                new BlockData(0, 0, -1, EnumFacing.SOUTH.ordinal(), subBlock),
                new BlockData(0, 0, -2, EnumFacing.SOUTH.ordinal(), subBlock),
                new BlockData(0, 0, -3, EnumFacing.SOUTH.ordinal(), subBlock),
                new BlockData(0, 0, -4, EnumFacing.SOUTH.ordinal(), subBlock),
                new BlockData(0, 0, -5, EnumFacing.SOUTH.ordinal(), subBlock),
                new BlockData(-1, 0, 5, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-1, 0, 4, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-1, 0, 3, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-1, 0, 2, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-1, 0, 1, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-1, 0, 0, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-1, 0, -1, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-1, 0, -2, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-1, 0, -3, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-1, 0, -4, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-1, 0, -5, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-2, 0, 5, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-2, 0, 4, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-2, 0, 3, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-2, 0, 2, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-2, 0, 1, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-2, 0, 0, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-2, 0, -1, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-2, 0, -2, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-2, 0, -3, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-2, 0, -4, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-2, 0, -5, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-3, 0, 4, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-3, 0, 3, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-3, 0, 2, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-3, 0, 1, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-3, 0, 0, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-3, 0, -1, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-3, 0, -2, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-3, 0, -3, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-3, 0, -4, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-4, 0, 3, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-4, 0, 2, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-4, 0, 1, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-4, 0, 0, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-4, 0, -1, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-4, 0, -2, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-4, 0, -3, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-5, 0, 2, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-5, 0, 1, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-5, 0, 0, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-5, 0, -1, EnumFacing.EAST.ordinal(), subBlock),
                new BlockData(-5, 0, -2, EnumFacing.EAST.ordinal(), subBlock)
        };
    }

    @Override
    public int getMaxIndex() {
        return 96;
    }

    @Override
    public BlockData getBlockForIndex(int index) {
        return CELESTIAL_COMPASS[index];
    }
}
