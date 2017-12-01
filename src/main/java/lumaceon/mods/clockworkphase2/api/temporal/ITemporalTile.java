package lumaceon.mods.clockworkphase2.api.temporal;

/**
 * To be implemented on tile entities that have a temporal mode. Used by CP2 packets to handle server/client syncs.
 */
public interface ITemporalTile extends ITimeSink
{
    public boolean hasReceivedTemporalUpgrade();
    public boolean isInTemporalMode();
    public void toggleTemporalMode();
}
