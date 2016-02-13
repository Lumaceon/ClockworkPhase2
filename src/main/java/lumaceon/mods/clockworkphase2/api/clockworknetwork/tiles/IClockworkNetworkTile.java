package lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetwork;
import net.minecraft.util.BlockPos;

/**
 * Tiles implementing this are marked as being part of a tiles network.
 */
public interface IClockworkNetworkTile
{
    public ClockworkNetwork getClockworkNetwork();
    public void setClockworkNetwork(ClockworkNetwork clockworkNetwork);
    public BlockPos getPosition();
}
