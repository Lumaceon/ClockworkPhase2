package lumaceon.mods.clockworkphase2.capabilities.stasis;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import java.util.concurrent.Callable;

public class CapabilityStasis
{
    @CapabilityInject(IStasis.class)
    public static final Capability<IStasis> STASIS_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IStasis.class, new Capability.IStorage<IStasis>()
        {
            @Override
            public NBTBase writeNBT(Capability<IStasis> capability, IStasis instance, EnumFacing side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IStasis> capability, IStasis instance, EnumFacing side, NBTBase base)
            {
                instance.deserializeNBT((NBTTagCompound) base);
            }
        }, StasisHandler::new);
    }

    public static class StasisGenericProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        StasisHandler implementation;

        public StasisGenericProvider() {
            implementation = new StasisHandler();
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return STASIS_CAPABILITY != null && STASIS_CAPABILITY == capability;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            if(hasCapability(capability, facing))
                return STASIS_CAPABILITY.cast(implementation);
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return implementation.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            implementation.deserializeNBT(nbt);
        }
    }
}
