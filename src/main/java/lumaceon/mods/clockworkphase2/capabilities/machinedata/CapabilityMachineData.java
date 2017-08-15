package lumaceon.mods.clockworkphase2.capabilities.machinedata;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityMachineData
{
    @CapabilityInject(IMachineDataHandler.class)
    public static final Capability<IMachineDataHandler> MACHINE_DATA = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IMachineDataHandler.class, new Capability.IStorage<IMachineDataHandler>()
        {
            @Override
            public NBTBase writeNBT(Capability<IMachineDataHandler> capability, IMachineDataHandler instance, EnumFacing side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IMachineDataHandler> capability, IMachineDataHandler instance, EnumFacing side, NBTBase base) {
                instance.deserializeNBT((NBTTagCompound) base);
            }
        }, () -> new MachineDataHandler());
    }
}
