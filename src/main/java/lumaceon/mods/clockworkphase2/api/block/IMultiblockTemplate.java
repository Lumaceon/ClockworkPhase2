package lumaceon.mods.clockworkphase2.api.block;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public interface IMultiblockTemplate
{
    /**
     * @return The number of blocks in this multiblock minus one.
     */
    public int getMaxIndex();

    /**
     * Used first to get the offset to put down the construction block, and then to set the new block at the end, once
     * all the construction blocks are placed.
     * @param index The index of this template, ranging between 0 and the return value of getMaxIndex.
     * @return The data for the block
     */
    public BlockData getBlockForIndex(int index);

    /**
     * Note, the x,y,z coordinates are local to the multiblock assembler. So 0,1,0 is one block above it. Metadata can
     * range from 0 to 15 (both inclusive) and should be the metadata representing your final block.
     */
    public class BlockData
    {
        public int meta;
        public Block block;

        protected BlockPos pos;

        public BlockData(int x, int y, int z, int meta, Block block)
        {
            this.pos = new BlockPos(x, y, z);
            this.meta = meta;
            this.block = block;
        }

        public Block getBlock() {
            return block;
        }

        public BlockPos getPosition() {
            return pos;
        }
    }
}
