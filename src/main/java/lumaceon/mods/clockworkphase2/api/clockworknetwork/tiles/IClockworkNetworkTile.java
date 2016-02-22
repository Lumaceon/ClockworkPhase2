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

    /**
     * Typically System.currentTimeMillis() is taken and saved when the tile is initially loaded or constructed.
     * @return A unique ID for this tile which should be set once when the block is first constructed.
     */
    public long getUniqueID();

    /**
     * Called from ClockworkNetwork on the off-chance that the ID isn't unique.
     * @param uniqueID The new uniqueID for this tile.
     */
    public void setUniqueID(long uniqueID);
}
