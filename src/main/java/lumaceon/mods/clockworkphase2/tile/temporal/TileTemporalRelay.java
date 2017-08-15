package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.temporal.timezone.*;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.List;

public class TileTemporalRelay extends TileMod implements ITemporalRelay, ITickable
{
    protected int tickCount = 0;
    protected List<ITimezone> cachedTimezones = new ArrayList<>(0);
    protected TimezoneInternalStorage internal = new TimezoneInternalStorage(this);

    @Override
    public List<ITimezone> getTimezones()
    {
        ArrayList<ITimezone> ret = new ArrayList<>();
        for(ITimezone tz : cachedTimezones)
        {
            //Although we only query for new timezones every few seconds, we check the range every tick.
            if(tz.isInRange(pos.getX(), pos.getY(), pos.getZ(), getWorld()))
                ret.add(tz);
        }
        ret.add(internal);

        return ret;
    }

    @Override
    public void update()
    {
        if(tickCount % 50 == 0) //About 2.5 seconds
        {
            cachedTimezones.clear();
            List<ITimezone> newTimezones = TimezoneHandler.getTimezonesFromWorldPosition(pos.getX(), pos.getY(), pos.getZ(), getWorld());
            for(ITimezone tz : newTimezones)
            {
                cachedTimezones.add(tz);
            }
        }
        ++tickCount;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        if(internal != null)
        {
            nbt.setTag("timezone_tag", internal.serializeNBT());
        }

        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if(nbt.hasKey("timezone_tag"))
        {
            internal.deserializeNBT((NBTTagCompound) nbt.getTag("timezone_tag"));
        }
    }
}
