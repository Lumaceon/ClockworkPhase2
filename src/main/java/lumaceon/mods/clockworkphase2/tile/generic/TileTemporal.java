package lumaceon.mods.clockworkphase2.tile.generic;

import lumaceon.mods.clockworkphase2.api.block.ITimezoneProvider;
import lumaceon.mods.clockworkphase2.api.time.TimeStorage;
import lumaceon.mods.clockworkphase2.api.time.TimezoneHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class TileTemporal extends TileClockworkPhase
{
    private ITimezoneProvider timezone;
    public TimeStorage timeStorage;
    protected int tz_x, tz_y, tz_z;

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if(timeStorage != null)
            timeStorage.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if(timeStorage != null)
            timeStorage.readFromNBT(nbt);
    }

    public ITimezoneProvider getTimezone()
    {
        if(timezone != null && Math.sqrt(Math.pow(tz_x - xCoord, 2) + Math.pow(tz_z - zCoord, 2)) <= timezone.getRange())
            return timezone;
        TileEntity te = worldObj.getTileEntity(tz_x, tz_y, tz_z);
        if(te != null && te instanceof ITimezoneProvider)
        {
            if(Math.sqrt(Math.pow(tz_x - xCoord, 2) + Math.pow(tz_z - zCoord, 2)) > timezone.getRange())
                return null;
            timezone = (ITimezoneProvider) te;
            return timezone;
        }

        ITimezoneProvider timezone = TimezoneHandler.getTimeZone(xCoord, yCoord, zCoord, worldObj);
        if(timezone != null)
            this.timezone = timezone;
        if(timezone == null || Math.sqrt(Math.pow(tz_x - xCoord, 2) + Math.pow(tz_z - zCoord, 2)) <= timezone.getRange())
            return null;
        return timezone;
    }

    public void updateClientTime(NBTTagCompound nbt) {
        if(timeStorage != null)
            timeStorage.readFromNBT(nbt);
    }
}
