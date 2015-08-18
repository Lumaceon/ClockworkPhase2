package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.time.ITimeConnection;
import lumaceon.mods.clockworkphase2.api.time.ITimeReceiver;
import lumaceon.mods.clockworkphase2.api.time.Time;
import lumaceon.mods.clockworkphase2.api.time.TimeStorage;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileTemporalConduit extends TileClockworkPhase implements ITimeReceiver
{
    private TimeStorage timeStorage = new TimeStorage(TimeConverter.SECOND);
    public boolean[] connections = new boolean[6];

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        timeStorage.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        timeStorage.readFromNBT(nbt);
    }

    @Override
    public void updateEntity()
    {
        /**if(worldObj.getWorldTime() % 40 == 0)
            for(int n = 0; n < 6; n++)
                Logger.info(connections[n]);*/
    }

    @Override
    public long receiveTime(ForgeDirection from, Time time, long maxReceive, boolean simulate) {
        return timeStorage.receiveTime(time, maxReceive, simulate);
    }

    @Override
    public long getMaxTotalTimeStorage(ForgeDirection from) {
        return timeStorage.getMaxTotalTimeStorage();
    }

    @Override
    public long getTimeStored(ForgeDirection from, Time time) {
        return timeStorage.getTimeStored(time);
    }

    @Override
    public long getEmptySpace(ForgeDirection from, Time time) {
        return timeStorage.getEmptySpace(time);
    }

    @Override
    public boolean canConnectFrom(ForgeDirection from) {
        return true;
    }

    @Override
    public void setState(int state) {}

    @Override
    public void setStateAndUpdate(int state) {}

    public void updateConnections(int x, int y, int z)
    {
        TileEntity te = worldObj.getTileEntity(x, y + 1, z);
        if(te != null && te instanceof ITimeConnection && ((ITimeConnection) te).canConnectFrom(ForgeDirection.UP))
            connections[ForgeDirection.UP.ordinal()] = true;
        else
            connections[ForgeDirection.UP.ordinal()] = false;

        te = worldObj.getTileEntity(x, y - 1, z);
        if(te != null && te instanceof ITimeConnection && ((ITimeConnection) te).canConnectFrom(ForgeDirection.DOWN))
            connections[ForgeDirection.DOWN.ordinal()] = true;
        else
            connections[ForgeDirection.DOWN.ordinal()] = false;

        te = worldObj.getTileEntity(x, y, z - 1);
        if(te != null && te instanceof ITimeConnection && ((ITimeConnection) te).canConnectFrom(ForgeDirection.NORTH))
            connections[ForgeDirection.NORTH.ordinal()] = true;
        else
            connections[ForgeDirection.NORTH.ordinal()] = false;

        te = worldObj.getTileEntity(x, y, z + 1);
        if(te != null && te instanceof ITimeConnection && ((ITimeConnection) te).canConnectFrom(ForgeDirection.SOUTH))
            connections[ForgeDirection.SOUTH.ordinal()] = true;
        else
            connections[ForgeDirection.SOUTH.ordinal()] = false;

        te = worldObj.getTileEntity(x - 1, y, z);
        if(te != null && te instanceof ITimeConnection && ((ITimeConnection) te).canConnectFrom(ForgeDirection.WEST))
            connections[ForgeDirection.WEST.ordinal()] = true;
        else
            connections[ForgeDirection.WEST.ordinal()] = false;

        te = worldObj.getTileEntity(x + 1, y, z);
        if(te != null && te instanceof ITimeConnection && ((ITimeConnection) te).canConnectFrom(ForgeDirection.EAST))
            connections[ForgeDirection.EAST.ordinal()] = true;
        else
            connections[ForgeDirection.EAST.ordinal()] = false;
    }
}
