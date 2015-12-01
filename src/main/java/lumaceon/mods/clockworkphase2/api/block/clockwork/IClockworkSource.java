package lumaceon.mods.clockworkphase2.api.block.clockwork;

import net.minecraftforge.common.util.ForgeDirection;

public interface IClockworkSource extends IClockworkTile
{
    public boolean canDistributeTo(ForgeDirection direction);

    /**
     * Called to transfer clockwork energy from this source outward.
     *
     * @param maxExtraction The amount of energy trying to be extracted.
     * @return The amount of energy taken from this source.
     */
    public int distributeClockworkEnergy(ForgeDirection direction, int maxExtraction);
}
