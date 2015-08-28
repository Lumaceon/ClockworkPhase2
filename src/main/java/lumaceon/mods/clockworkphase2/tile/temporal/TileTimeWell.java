package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.time.ITimeProvider;
import lumaceon.mods.clockworkphase2.api.time.ITimeReceiver;
import lumaceon.mods.clockworkphase2.api.time.TimeStorage;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.tile.generic.TileTemporal;
import net.minecraftforge.common.util.ForgeDirection;

public class TileTimeWell extends TileTemporal implements ITimeReceiver, ITimeProvider
{
    public TileTimeWell() {
        timeStorage = new TimeStorage(TimeConverter.HOUR * 2);
    }

    @Override
    public long extractTime(long maxExtract, boolean simulate) {
        return timeStorage.extractTime(maxExtract, simulate);
    }

    @Override
    public long receiveTime(long maxReceive, boolean simulate) {
        return timeStorage.receiveTime(maxReceive, simulate);
    }

    @Override
    public long getMaxCapacity() {
        return timeStorage.getMaxCapacity();
    }

    @Override
    public long getTimeStored() {
        return timeStorage.getTimeStored();
    }

    @Override
    public boolean canConnectFrom(ForgeDirection from) {
        return true;
    }

    @Override
    public void setState(int state) {}

    @Override
    public void setStateAndUpdate(int state) {}
}
