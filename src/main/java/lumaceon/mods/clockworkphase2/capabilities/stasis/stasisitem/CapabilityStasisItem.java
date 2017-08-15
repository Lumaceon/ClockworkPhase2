package lumaceon.mods.clockworkphase2.capabilities.stasis.stasisitem;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityStasisItem
{
    @CapabilityInject(IStasisItemHandler.class)
    public static final Capability<IStasisItemHandler> STASIS_ITEM_CAPABILITY = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IStasisItemHandler.class, new Capability.IStorage<IStasisItemHandler>() {
            @Override
            public NBTBase writeNBT(Capability<IStasisItemHandler> capability, IStasisItemHandler instance, EnumFacing side) {
                return new NBTTagLong(instance.getTimeToOffset());
            }

            @Override
            public void readNBT(Capability<IStasisItemHandler> capability, IStasisItemHandler instance, EnumFacing side, NBTBase base) {
                instance.setTimeToOffset(((NBTTagLong) base).getLong());
            }
        }, StasisItemHandler::new);
    }
}
