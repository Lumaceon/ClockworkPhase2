package lumaceon.mods.clockworkphase2.api.temporal.timezone;

import lumaceon.mods.clockworkphase2.api.temporal.Echo;
import lumaceon.mods.clockworkphase2.api.temporal.EchoRegistry;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.capabilities.TimeStorage;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class representing a timezone, which is an area of space-time manipulated by a timezone provider. Functionally,
 * time is stored within an ITimezoneProvider and effects are determined by the timezone modulators.
 */
public class Timezone implements ITimezone
{
    public static final int MAX_ECHO_CAPACITY = 2000000000; //Two billion, which is close to a signed integer's maximum.

    @CapabilityInject(ITimezoneFunction.class)
    public static final Capability<ITimezoneFunction> TIMEZONE_FUNCTION = null;

    protected HashMap<String, Integer> echoes = new HashMap<String, Integer>();
    protected TileEntity te;

    protected TimeStorage timeStorage = new TimeStorage(TimeConverter.DAY);
    int radius = 16;

    public Timezone()
    {

    }

    public Timezone(TileEntity te)
    {
        this.te = te;
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
    public int getEchoCountForType(Echo echoType)
    {
        Integer i = echoes.get(echoType.getRegistryName());
        if(i == null)
            return 0;
        return i;
    }

    @Override
    public int insertEchoes(Echo echoType, int numberToInsert)
    {
        int count = getEchoCountForType(echoType);
        int echoesAccepted = Math.min(MAX_ECHO_CAPACITY - count, numberToInsert);
        count += echoesAccepted;
        echoes.put(echoType.getRegistryName(), count);

        return echoesAccepted;
    }

    @Override
    public int extractEchoes(Echo echoType, int numberToExtract)
    {
        int count = getEchoCountForType(echoType);
        int echoesRemoved = Math.min(count, numberToExtract);
        count -= echoesRemoved;
        echoes.put(echoType.getRegistryName(), count);

        return echoesRemoved;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setLong("time_stored", timeStorage.getTimeInTicks());
        nbt.setLong("time_capacity", timeStorage.getMaxCapacity());

        NBTTagList list = new NBTTagList();
        NBTTagCompound tag;
        for(Map.Entry<String, Integer> entry : echoes.entrySet())
        {
            tag = new NBTTagCompound();
            tag.setString("registry_name", entry.getKey());
            tag.setInteger("count", entry.getValue());
            list.appendTag(tag);
        }
        nbt.setTag("echoes", list);

        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        timeStorage.setMaxCapacity(nbt.getLong("time_capacity"));
        timeStorage.setTimeStored(nbt.getLong("time_stored"));

        NBTTagList list = nbt.getTagList("echoes", Constants.NBT.TAG_COMPOUND);
        for(int i = 0; i < list.tagCount(); i++)
        {
            NBTTagCompound tag = (NBTTagCompound) list.get(i);
            String registryName = tag.getString("registry_name");
            Echo e = EchoRegistry.getEchoFromRegistryName(registryName);

            if(e != null)
                echoes.put(registryName, tag.getInteger("count"));
        }
    }

    @Override
    public long insertTime(long ticksToInsert) {
        markDirty();
        return timeStorage.insertTime(ticksToInsert);
    }

    @Override
    public long extractTime(long ticksToExtract) {
        markDirty();
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
        markDirty();
        return timeStorage.setMaxCapacity(maxCapacity);
    }

    /**
     * Call whenever internal values are changed to make sure they're saved properly.
     */
    public void markDirty()
    {
        te.markDirty();
    }
}
