package lumaceon.mods.clockworkphase2.capabilities;

import lumaceon.mods.clockworkphase2.api.capabilities.ITimeStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import java.util.concurrent.Callable;

public class CapabilityTimeStorage
{
    @CapabilityInject(ITimeStorage.class)
    public static final Capability<ITimeStorage> TIME_STORAGE_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(ITimeStorage.class, new Capability.IStorage<ITimeStorage>()
        {
            @Override
            public NBTBase writeNBT(Capability<ITimeStorage> capability, ITimeStorage instance, EnumFacing side)
            {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setLong("ticks_stored", instance.getTimeInTicks());
                nbt.setLong("max_capacity", instance.getMaxCapacity());
                return nbt;
            }

            @Override
            public void readNBT(Capability<ITimeStorage> capability, ITimeStorage instance, EnumFacing side, NBTBase base)
            {
                instance.setMaxCapacity(((NBTTagCompound) base).getLong("max_capacity"));
                instance.insertTime(((NBTTagCompound) base).getLong("ticks_stored"));
            }
        }, new Callable<ITimeStorage>()
        {
            @Override
            public TimeStorage call() throws Exception {
                return new TimeStorage();
            }
        });
    }
}
