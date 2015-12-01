package lumaceon.mods.clockworkphase2.api.block.clockwork;

public interface IClockworkTile
{
    public int getMaxCapacity();
    public int getEnergyStored();

    /**
     * All clockwork tiles can be wound up, regardless of whether they are a destination or not.
     * @param tension Tension to wind.
     * @return The tension that was wound.
     */
    public int wind(int tension);
}
