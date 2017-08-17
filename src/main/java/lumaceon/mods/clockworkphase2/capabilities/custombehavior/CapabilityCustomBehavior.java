package lumaceon.mods.clockworkphase2.capabilities.custombehavior;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

/**
 * Internal capability for entities to override their behavior. This mostly exists as a sort of 'container' for other
 * capabilities the entity may have (frequently stored in/as ItemStacks).
 */
public class CapabilityCustomBehavior
{
    @CapabilityInject(ICustomBehavior.class)
    public static final Capability<ICustomBehavior> CUSTOM_BEHAVIOR = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(ICustomBehavior.class, new Capability.IStorage<ICustomBehavior>()
        {
            @Override
            public NBTBase writeNBT(Capability<ICustomBehavior> capability, ICustomBehavior instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<ICustomBehavior> capability, ICustomBehavior instance, EnumFacing side, NBTBase base) {
                instance.deserializeNBT((NBTTagCompound) base);
            }
        }, CustomBehaviorHandler::new);
    }

    public static class CustomBehaviorGenericProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        CustomBehaviorHandler customBehavior;

        public CustomBehaviorGenericProvider() {
            customBehavior = new CustomBehaviorHandler();
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return CUSTOM_BEHAVIOR != null && CUSTOM_BEHAVIOR == capability;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            if(hasCapability(capability, facing))
                return CUSTOM_BEHAVIOR.cast(customBehavior);
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return customBehavior.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            customBehavior.deserializeNBT(nbt);
        }
    }
}
