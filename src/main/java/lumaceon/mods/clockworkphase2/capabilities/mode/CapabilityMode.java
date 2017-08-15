package lumaceon.mods.clockworkphase2.capabilities.mode;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityMode
{
    @CapabilityInject(IModeHandler.class)
    public static final Capability<IModeHandler> TEMPORAL_TOOLBELT = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IModeHandler.class, new Capability.IStorage<IModeHandler>()
        {
            @Override
            public NBTBase writeNBT(Capability<IModeHandler> capability, IModeHandler instance, EnumFacing side) {
                return new NBTTagInt(instance.getMode());
            }

            @Override
            public void readNBT(Capability<IModeHandler> capability, IModeHandler instance, EnumFacing side, NBTBase base) {
                instance.setMode(((NBTTagInt)base).getInt());
            }
        },
        () -> new ModeHandler(0, 1)
        );
    }
}
