package lumaceon.mods.clockworkphase2.api.block.clockwork;

import net.minecraftforge.common.util.ForgeDirection;

public interface IClockworkDestination extends IClockworkTile
{
    public boolean canReceiveFrom(ForgeDirection direction);

    /**
     * Called to transfer clockwork energy into this destination.
     *
     * @param maxReception The amount of energy this destination is being exposed to.
     * @return The amount of energy taken by this destination.
     */
    public int receiveClockworkEnergy(ForgeDirection from, int maxReception);
}
