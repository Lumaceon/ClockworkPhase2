package lumaceon.mods.clockworkphase2.tile.generic;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.timezone.TimezoneHandler;
import net.minecraft.tileentity.TileEntity;

public abstract class TileTimezoneUsage extends TileClockworkPhase
{
    public ITimezone timezone;
    protected int tz_x, tz_y, tz_z;

    public ITimezone getTimezone()
    {
        if(timezone != null && Math.sqrt(Math.pow(tz_x - xCoord, 2) + Math.pow(tz_z - zCoord, 2)) <= timezone.getRange())
            return timezone;
        TileEntity te = worldObj.getTileEntity(tz_x, tz_y, tz_z);
        if(te != null && te instanceof ITimezone)
        {
            if(Math.sqrt(Math.pow(tz_x - xCoord, 2) + Math.pow(tz_z - zCoord, 2)) > timezone.getRange())
                return null;
            timezone = (ITimezone) te;
            return timezone;
        }

        ITimezone timezone = TimezoneHandler.getTimeZone(xCoord, yCoord, zCoord, worldObj);
        if(timezone != null)
            this.timezone = timezone;
        if(timezone == null || Math.sqrt(Math.pow(tz_x - xCoord, 2) + Math.pow(tz_z - zCoord, 2)) <= timezone.getRange())
            return null;
        return timezone;
    }
}
