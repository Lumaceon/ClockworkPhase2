package lumaceon.mods.clockworkphase2.api.item;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Used to mark items which go in the center slot of the timezone controller. This item is responsible for determining
 * what actually occurs during and after this crafting, as well as the work of the crafting.
 */
public interface ITimeframeKeyItem
{
    /**
     * @return The number of ticks for this crafting to complete; this should remain consistent.
     */
    public int getTickLength();

    /**
     * Called every tick this item is part of a timezone phase crafting process.
     * @param world The world in which this crafting is occurring.
     * @param tilePosition The position in world of this Timezone Controller tile.
     * @param craftingItems An array of items involved in the crafting. Index 8 is the center item; 0-7 are the other 8
     *                      which circle around it, starting with the top (index 0) and continuing clockwise up to 7.
     * @param tickNumber The number of previous 'successful' ticks in the crafting that have occurred, starting at 0.
     *                   A 'successful' tick is considered one where onCraftTick is called and returns true. In this
     *                   way, a tick may or may not repeat, depending on how you implement this method. The final tick
     *                   will be equal to the value returned in getTickLength.
     * @return True to count this tick as successful and progress tickNumber. False to stall and repeat this tickNumber.
     */
    public boolean onCraftTick(World world, TileEntity tile, BlockPos tilePosition, ItemStack[] craftingItems, int tickNumber);
}