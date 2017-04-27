package lumaceon.mods.clockworkphase2.api.timezone;

import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.capabilities.TimeStorage;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.ArrayList;

/**
 * A class representing a timezone, which is an area of space-time manipulated by a timezone provider. Functionally,
 * time is stored within an ITimezoneProvider and effects are determined by the timezone modulators.
 */
public class Timezone implements ITimezone
{
    @CapabilityInject(ITimezoneFunction.class)
    public static final Capability<ITimezoneFunction> TIMEZONE_FUNCTION = null;

    World world;
    TileEntity te;

    TimeStorage timeStorage = new TimeStorage(TimeConverter.DAY);
    int radius = 16;

    public Timezone()
    {

    }

    public Timezone(TileEntity te)
    {
        this.te = te;
        this.world = te.getWorld();
    }

    @Override
    public boolean isInRange(double x, double y, double z, World world)
    {
        BlockPos pos = getPosition();
        return y <= pos.getY() && pos.getDistance((int) x, pos.getY(), (int) z) <= radius;
    }

    @Override
    public BlockPos getPosition() {
        return te.getPos();
    }

    @Override
    public TileEntity getTile() {
        return te;
    }

    @Override
    public ITimezoneFunction[] getTimezoneFunctions()
    {
        ArrayList<ITimezoneFunction> functions = new ArrayList<ITimezoneFunction>();
        if(te != null && te instanceof IInventory)
        {
            IInventory inventory = (IInventory) te;
            ItemStack stack;
            for(int i = 0; i < inventory.getSizeInventory(); i++)
            {
                stack = inventory.getStackInSlot(i);
                if(stack != null)
                {
                    ITimezoneFunction cap = stack.getCapability(TIMEZONE_FUNCTION, EnumFacing.DOWN);
                    if(cap != null)
                    {
                        functions.add(cap);
                    }
                }
            }
        }

        if(functions.isEmpty())
            return new ITimezoneFunction[0];

        ITimezoneFunction[] ret = new ITimezoneFunction[functions.size()];
        return functions.toArray(ret);
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("time_stored", timeStorage.getTimeInTicks());
        nbt.setLong("time_capacity", timeStorage.getMaxCapacity());
        return new NBTTagCompound();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        timeStorage.setMaxCapacity(nbt.getLong("time_capacity"));
        timeStorage.insertTime(nbt.getLong("time_stored"));
    }

    @Override
    public long insertTime(long ticksToInsert) {
        return timeStorage.insertTime(ticksToInsert);
    }

    @Override
    public long extractTime(long ticksToExtract) {
        return timeStorage.extractTime(ticksToExtract);
    }

    @Override
    public long getTimeInTicks() {
        return timeStorage.getTimeInTicks();
    }

    @Override
    public long getMaxCapacity() {
        return timeStorage.getMaxCapacity();
    }

    @Override
    public long setMaxCapacity(long maxCapacity) {
        return timeStorage.setMaxCapacity(maxCapacity);
    }
}
