package lumaceon.mods.clockworkphase2.api.block;

import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface ITrowelBlock
{
    /**
     * Called when this block is right-clicked with a trowel in hand.
     * @param trowelStack The itemstack representing the trowel.
     * @param world The world in which this right-click occurred.
     * @param pos The position of the block in the world.
     * @param trowelLevel The harvesting level of the trowel. 0=Wood 1=Stone 2=Iron 3=Diamond
     */
    public void onTrowelRightClick(ItemStack trowelStack, World world, BlockPos pos, int trowelLevel);
}
