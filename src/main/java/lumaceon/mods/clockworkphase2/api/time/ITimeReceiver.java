package lumaceon.mods.clockworkphase2.api.time;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Implemented on tile entity classes which should receive time.
 *
 * (Based off of the RF API - Credits to King Lemming)
 */
public interface ITimeReceiver extends ITimeConnection
{
    /**
     * Called to add time to this tile entity.
     *
     * @param from The direction from which the time is being received.
     * @param time The kind of time this storage is receiving.
     * @param maxReceive The amount of time trying to be added to storage.
     * @param simulate If true, a simulation will occur, but no time will actually be added.
     * @return The amount of time that was added successfully (or would have been added if simulated).
     */
    public long receiveTime(ForgeDirection from, Time time, long maxReceive, boolean simulate);

    /**
     * Returns the maximum amount of time that can be stored.
     */
    public long getMaxTotalTimeStorage(ForgeDirection from);

    /**
     * Returns the amount of the specified type of time that's stored.
     */
    public long getTimeStored(ForgeDirection from, Time time);

    /**
     * Returns the maximum amount of time of the specified type that can still be received into this tile entity.
     * This is translated to the magnitude internally, so if there are 2 seconds of empty space, this should return
     * 1 * (TimeConverter.SECOND) when called with a time of magnitude 2 passed in for time.
     */
    public long getEmptySpace(ForgeDirection from, Time time);
}
