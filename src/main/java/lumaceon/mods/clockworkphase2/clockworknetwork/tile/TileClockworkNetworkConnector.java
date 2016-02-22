package lumaceon.mods.clockworkphase2.clockworknetwork.tile;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetwork;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkTile;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;

public class TileClockworkNetworkConnector extends TileClockworkPhase implements IClockworkNetworkTile
{
    protected long uniqueID = -1;

    public ClockworkNetwork clockworkNetwork;

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        if(uniqueID != -1)
            nbt.setLong("uniqueID_CP", uniqueID);
    }

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        if(nbt.hasKey("uniqueID_CP"))
            uniqueID = nbt.getLong("uniqueID_CP");
    }

    @Override
    public void onLoad() {
        if(uniqueID == -1)
            uniqueID = System.currentTimeMillis();
    }

    @Override
    public ClockworkNetwork getClockworkNetwork() {
        return this.clockworkNetwork;
    }

    @Override
    public void setClockworkNetwork(ClockworkNetwork clockworkNetwork) {
        this.clockworkNetwork = clockworkNetwork;
    }

    @Override
    public BlockPos getPosition() {
        return getPos();
    }

    @Override
    public long getUniqueID() {
        return uniqueID;
    }

    @Override
    public void setUniqueID(long uniqueID) {
        this.uniqueID = uniqueID;
        markDirty();
    }
}
