package lumaceon.mods.clockworkphase2.api.time;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Implemented on tile entity classes which should provide time.
 *
 * (Based off of the RF API - Credits to King Lemming)
 */
public interface ITimeProvider extends ITimeConnection
{
    /**
     * Called to extract time from this tile entity.
     *
     * @param maxExtract The amount of time trying to be extracted.
     * @param simulate If true, a simulation will occur, but no time will actually be removed.
     * @return The amount of time that was removed successfully (or would have been removed if simulated).
     */
    public long extractTime(long maxExtract, boolean simulate);

    /**
     * Returns the maximum amount of time that can be stored.
     */
    public long getMaxCapacity();

    /**
     * Returns the amount of time that's stored.
     */
    public long getTimeStored();
}
