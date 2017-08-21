package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.temporal.timezone.*;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import lumaceon.mods.clockworkphase2.util.BlockStateUpdateHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

import java.util.ArrayList;
import java.util.List;

public class TileTemporalRelay extends TileMod implements ITemporalRelay, ITickable
{
    public int tickCount = 0;
    public List<ITimezone> cachedTimezones = new ArrayList<>(0);
    public TimezoneInternalStorage internal = new TimezoneInternalStorage(this);

    protected boolean previousUpdateHadTimezones = false;

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
            previousUpdateHadTimezones = !cachedTimezones.isEmpty();

            cachedTimezones.clear();
            List<ITimezone> newTimezones = TimezoneHandler.getTimezonesFromWorldPosition(pos.getX(), pos.getY(), pos.getZ(), getWorld());
            for(ITimezone tz : newTimezones)
            {
                cachedTimezones.add(tz);
            }

            if(previousUpdateHadTimezones != cachedTimezones.isEmpty()) //Has changed.
            {
                BlockStateUpdateHelper.updateBlockState(world, pos, getBlockType(), getBlockMetadata());
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
