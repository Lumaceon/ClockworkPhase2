package lumaceon.mods.clockworkphase2.capabilities;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.timezone.Timezone;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityTimezone
{
    @CapabilityInject(ITimezone.class)
    public static final Capability<ITimezone> TIMEZONE_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(ITimezone.class, new Capability.IStorage<ITimezone>()
        {
            @Override
            public NBTBase writeNBT(Capability<ITimezone> capability, ITimezone instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<ITimezone> capability, ITimezone instance, EnumFacing side, NBTBase base) {
                instance.deserializeNBT((NBTTagCompound) base);
            }
        }, () -> new Timezone());
    }
}
