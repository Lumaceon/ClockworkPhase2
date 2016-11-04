package lumaceon.mods.clockworkphase2.api.item;

/**
 * Used to mark items which are considered 'artifacts'. Typically these are acquired by excavating relic blocks, but
 * they may also be acquired in other ways, such as chests or slaying mobs. Functionally, by implementing this interface
 * on an item, the timezone controller will accept the item into it's 8 outer circles, as long as the center contains a
 * valid ITimeframeKeyItem (see getTimeframeKeyItem method).
 */
public interface IArtifactItem
{
    /**
     * @return The timeframe key item which this corresponds to.
     */
    public ITimeframeKeyItem getTimeframeKeyItem();
}
