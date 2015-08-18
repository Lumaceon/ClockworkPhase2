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
     * @param from The direction from which the time is being extracted.
     * @param time The kind of time to be extracted.
     * @param maxExtract The amount of time trying to be extracted.
     * @param simulate If true, a simulation will occur, but no time will actually be removed.
     * @return The amount of time that was removed successfully (or would have been removed if simulated).
     */
    public long extractEnergy(ForgeDirection from, Time time, long maxExtract, boolean simulate);

    /**
     * Returns the maximum amount of time that can be stored.
     */
    public long getMaxTotalTimeStorage(ForgeDirection from);

    /**
     * Returns the amount of the specified type of time that's stored.
     */
    public long getTimeStored(ForgeDirection from, Time time);

    /**
     * Returns the maximum amount of time of the specified type that can still accumulate into this tile entity.
     * This is translated to the magnitude internally, so if there are 2 seconds of empty space, this should return
     * 1 * (TimeConverter.SECOND) when called with a time of magnitude 2 passed in for time.
     */
    public long getEmptySpace(ForgeDirection from, Time time);
}
