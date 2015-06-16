package lumaceon.mods.clockworkphase2.api.steammachine;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Tile Entities implementing this interface are considered capable of providing steam to connected pipes.
 */
public interface ISteamProvider
{
    /**
     * Drains steam from this tile.
     * @param drainingFrom The direction to drain from.
     * @param amountToDrain The amount of steam to drain.
     * @return The amount of steam that was drained.
     */
    public int drain(ForgeDirection drainingFrom, int amountToDrain);

    public int getSteamAvailable(ForgeDirection drainingFrom);

    public boolean canDrainFrom(ForgeDirection direction);
}
