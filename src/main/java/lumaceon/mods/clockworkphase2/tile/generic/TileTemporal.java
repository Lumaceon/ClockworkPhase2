package lumaceon.mods.clockworkphase2.tile.generic;

import lumaceon.mods.clockworkphase2.api.time.timezone.ITimezoneProvider;
import lumaceon.mods.clockworkphase2.api.time.TimeStorage;
import lumaceon.mods.clockworkphase2.api.time.timezone.TimezoneHandler;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public abstract class TileTemporal extends TileClockworkPhase
{
    private ITimezoneProvider timezone;
    public TimeStorage timeStorage;
    protected BlockPos timezonePosition;

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

    public ITimezoneProvider getTimezoneProvider()
    {
        BlockPos tilePosition = this.getPos();
        int xCoordinate = tilePosition.getX();
        int yCoordinate = tilePosition.getY();
        int zCoordinate = tilePosition.getZ();
        if(timezonePosition != null)
        {
            int tz_x = timezonePosition.getX();
            int tz_z = timezonePosition.getZ();

            //Check to see if the timezone is stored in the parameter and is in range. If so, we can stop here.
            if(timezone != null && Math.sqrt(Math.pow(tz_x - xCoordinate, 2) + Math.pow(tz_z - zCoordinate, 2)) <= timezone.getRange())
                return timezone;

            //Timezone parameter was either null or out of range, try and find it via stored coordinates.
            if(timezonePosition != null)
            {
                TileEntity te = worldObj.getTileEntity(timezonePosition);
                if(te != null && te instanceof ITimezoneProvider)
                {
                    if(Math.sqrt(Math.pow(tz_x - xCoordinate, 2) + Math.pow(tz_z - zCoordinate, 2)) > timezone.getRange())
                        return null;
                    timezone = (ITimezoneProvider) te;
                    return timezone;
                }
            }
        }

        //None of the above parameters were helpful; try to find one in the area.
        ITimezoneProvider timezoneProvider = TimezoneHandler.getTimeZone(xCoordinate, yCoordinate, zCoordinate, worldObj);
        if(timezoneProvider != null)
        {
            this.timezone = timezoneProvider;
            this.timezonePosition = new BlockPos(timezoneProvider.getX(), timezoneProvider.getY(), timezoneProvider.getZ());
        }
        if(timezoneProvider == null)
            return null;
        return timezoneProvider;
    }

    public void updateClientTime(NBTTagCompound nbt) {
        if(timeStorage != null)
            timeStorage.readFromNBT(nbt);
    }
}
