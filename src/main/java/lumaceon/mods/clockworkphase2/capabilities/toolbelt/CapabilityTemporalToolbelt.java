package lumaceon.mods.clockworkphase2.capabilities.toolbelt;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class CapabilityTemporalToolbelt
{
    @CapabilityInject(ITemporalToolbeltHandler.class)
    public static final Capability<ITemporalToolbeltHandler> TEMPORAL_TOOLBELT = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(ITemporalToolbeltHandler.class, new Capability.IStorage<ITemporalToolbeltHandler>()
        {
            @Override
            public NBTBase writeNBT(Capability<ITemporalToolbeltHandler> capability, ITemporalToolbeltHandler instance, EnumFacing side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<ITemporalToolbeltHandler> capability, ITemporalToolbeltHandler instance, EnumFacing side, NBTBase base) {
                instance.deserializeNBT((NBTTagCompound) base);
            }
        }, TemporalToolbeltHandler::new );
    }

    public static class TemporalToolbeltGenericProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        TemporalToolbeltHandler implementation;

        public TemporalToolbeltGenericProvider() {
            implementation = new TemporalToolbeltHandler();
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return TEMPORAL_TOOLBELT != null && TEMPORAL_TOOLBELT == capability;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            if(hasCapability(capability, facing))
                return TEMPORAL_TOOLBELT.cast(implementation);
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
