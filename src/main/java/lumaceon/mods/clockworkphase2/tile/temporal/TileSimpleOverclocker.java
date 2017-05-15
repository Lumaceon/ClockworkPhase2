package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.temporal.Echo;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITimezoneFunction;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITimezoneRelay;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.Timezone;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileSimpleOverclocker extends TileMod implements ITimezoneRelay
{
    private Timezone timezone;

    public TileSimpleOverclocker() {
        this.timezone = new TimezoneSimpleOverclocker(this);
    }

    @Override
    public ITimezone getTimezone() {
        return timezone;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        if(timezone != null)
        {
            nbt.setTag("timezone_tag", timezone.serializeNBT());
        }

        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if(nbt.hasKey("timezone_tag"))
        {
            timezone.deserializeNBT((NBTTagCompound) nbt.getTag("timezone_tag"));
        }
    }

    /**
     * Overridden to simplify some irrelevant methods, disallow insertion, and automatically kill the tile when this
     * timezone runs out of time.
     */
    private static class TimezoneSimpleOverclocker extends Timezone
    {
        public TimezoneSimpleOverclocker(TileEntity te) {
            super(te);
            setMaxCapacity(ConfigValues.SIMPLE_OVERCLOCKER_TIME);
            timeStorage.insertTime(ConfigValues.SIMPLE_OVERCLOCKER_TIME);
        }

        @Override
        public boolean isInRange(double x, double y, double z, World world) {
            return true;
        }

        @Override
        public ITimezoneFunction[] getTimezoneFunctions() {
            return new ITimezoneFunction[0];
        }

        @Override
        public int getEchoCountForType(Echo echoType) {
            return 0;
        }

        @Override
        public int insertEchoes(Echo echoType, int numberToInsert) {
            return 0;
        }

        @Override
        public int extractEchoes(Echo echoType, int numberToExtract) {
            return 0;
        }

        @Override
        public long insertTime(long ticksToInsert) {
            return 0;
        }

        @Override
        public long extractTime(long ticksToExtract)
        {
            long time = super.extractTime(ticksToExtract);

            if(getTimeInTicks() <= TimeConverter.SECOND)
            {
                this.te.getWorld().setBlockToAir(getPosition());
            }

            return time;
        }
    }
}
