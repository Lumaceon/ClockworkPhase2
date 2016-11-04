package lumaceon.mods.clockworkphase2.api.item;

import lumaceon.mods.clockworkphase2.api.EnumExpTier;

public interface ITimeCompressor
{
    /**
     * @return The required tier of hourglass to use this compressor.
     */
    public EnumExpTier getTier();

    /**
     * @return The amount of ticks of time energy to compress per game tick.
     */
    public int getCompressionRate();

    /**
     * @return The number of ticks this compressor will last.
     */
    public int getTotalTicks();
}
