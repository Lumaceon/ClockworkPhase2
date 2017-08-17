package lumaceon.mods.clockworkphase2.capabilities.entitycontainer;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityEntityContainer
{
    @CapabilityInject(IEntityContainer.class)
    public static final Capability<IEntityContainer> ENTITY_CONTAINER = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IEntityContainer.class, new Capability.IStorage<IEntityContainer>()
        {
            @Override
            public NBTBase writeNBT(Capability<IEntityContainer> capability, IEntityContainer instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IEntityContainer> capability, IEntityContainer instance, EnumFacing side, NBTBase base) {
                instance.deserializeNBT((NBTTagCompound) base);
            }
        }, EntityContainer::new);
    }
}
