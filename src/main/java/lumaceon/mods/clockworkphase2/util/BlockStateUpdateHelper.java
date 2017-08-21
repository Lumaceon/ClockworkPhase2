package lumaceon.mods.clockworkphase2.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockStateUpdateHelper
{
    @SuppressWarnings("deprecation")
    public static void updateBlockState(World world, BlockPos pos, Block block, int meta)
    {
        world.notifyBlockUpdate(pos, block.getStateFromMeta(meta), block.getActualState(block.getStateFromMeta(meta), world, pos), 3);
        world.setBlockState(pos, block.getActualState(block.getStateFromMeta(meta), world, pos));
        world.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
        world.scheduleBlockUpdate(pos, block, 0, 0);
    }
}
