package lumaceon.mods.clockworkphase2.api.block;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
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

    /**
     * Similar to getDrops, except that this won't be called by most automatic mining systems by default. This is
     * intended to make it so that miners like the quarry or arcane bore won't be able to collect the items without the
     * mod author adding specific compatibility.
     *
     * If you intend to allow your miner to call this method, please make a special (ideally expensive) upgrade to allow
     * it to call this method. After all, it's very intensive for a machine to be gentle when ripping out the world's
     * resources. From a balancing point, relics are also meant to be impossible to automate until late-to-end-game of
     * all the achievements combined.
     *
     * @param world The world in which this block currently resides.
     * @param pos The position of the block in the world.
     */
    public ItemStack getCleaningResult(World world, BlockPos pos);
}
