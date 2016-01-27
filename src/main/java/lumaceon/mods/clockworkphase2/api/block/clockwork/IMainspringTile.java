package lumaceon.mods.clockworkphase2.api.block.clockwork;

public interface IMainspringTile extends IClockworkNetworkTile
{
    public int getTension();
    public int getMaxTension();

    /**
     * @return The amount of tension that was removed from this tile.
     */
    public int consumeTension(int tensionToConsume);
}
