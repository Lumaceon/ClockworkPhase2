package lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles;

public interface IMainspringTile extends IClockworkNetworkTile
{
    public int getTension();
    public int getMaxTension();

    /**
     * @return The amount of tension that was removed from this tile.
     */
    public int consumeTension(int tensionToConsume);

    /**
     * @return The amount of tension that was added to this tile.
     */
    public int addTension(int tensionToAdd);
}
