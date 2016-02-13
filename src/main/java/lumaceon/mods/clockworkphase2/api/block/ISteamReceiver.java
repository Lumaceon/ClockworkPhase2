package lumaceon.mods.clockworkphase2.api.block;


import net.minecraft.util.EnumFacing;

/**
 * Implementing this interface in your tile entity class will mark it as capable of receiving steam.
 */
public interface ISteamReceiver
{
    /**
     * Called from pipes (and similar blocks) periodically to fill this tile with steam. Many machines may run directly
     * off of steam, in which case your machine can run based on the amount filled here.
     * @param filledFrom The direction from which steam is trying to be inserted.
     * @param amountToFill The amount of steam being added.
     * @return Any overspill (steam that couldn't be added due to capacity and such).
     */
    public int fill(EnumFacing filledFrom, int amountToFill);

    /**
     * Used to determine the maximum amount of steam to fill each fill tick (see getInputDelay).
     * @param filledFrom The incoming direction.
     * @return The amount of steam to try and fill each attempt.
     */
    public int getInputAmount(EnumFacing filledFrom);

    /**
     * Used to determine how frequent each "fill tick" is. 0 would cause each attached pipe to try and fill this on
     * every game tick; 1 would be every other game tick.
     * @param filledFrom The incoming direction.
     * @return The delay between fill attempts.
     */
    public int getInputDelay(EnumFacing filledFrom);

    public boolean canFillFrom(EnumFacing direction);
}