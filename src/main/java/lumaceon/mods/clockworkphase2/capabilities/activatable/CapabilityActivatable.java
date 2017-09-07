package lumaceon.mods.clockworkphase2.capabilities.activatable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityActivatable
{
    @CapabilityInject(IActivatableHandler.class)
    public static final Capability<IActivatableHandler> ACTIVATABLE_HANDLER_CAPABILITY = null;

    public static final EnumFacing DEFAULT_FACING = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IActivatableHandler.class, new Capability.IStorage<IActivatableHandler>()
        {
            @Override
            public NBTBase writeNBT(Capability<IActivatableHandler> capability, IActivatableHandler instance, EnumFacing side)
            {
                NBTTagCompound nbt = new NBTTagCompound();
                nbt.setBoolean("is_active", instance.getActive());
                return nbt;
            }

            @Override
            public void readNBT(Capability<IActivatableHandler> capability, IActivatableHandler instance, EnumFacing side, NBTBase base) {
                instance.setActive(((NBTTagCompound) base).getBoolean("is_active"));
            }
        }, ActivatableHandler::new);
    }
}
