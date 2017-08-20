package lumaceon.mods.clockworkphase2.capabilities.coordinate;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

/**
 * Internal capability for entities to override their behavior. This mostly exists as a sort of 'container' for other
 * capabilities the entity may have (frequently stored in/as ItemStacks).
 */
public class CapabilityCoordinate
{
    @CapabilityInject(ICoordinateHandler.class)
    public static final Capability<ICoordinateHandler> COORDINATE = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(ICoordinateHandler.class, new Capability.IStorage<ICoordinateHandler>()
        {
            @Override
            public NBTBase writeNBT(Capability<ICoordinateHandler> capability, ICoordinateHandler instance, EnumFacing side)
            {
                NBTTagCompound nbt = new NBTTagCompound();
                BlockPos pos = instance.getCoordinate();
                nbt.setInteger("x", pos.getX());
                nbt.setInteger("y", pos.getY());
                nbt.setInteger("z", pos.getZ());
                EnumFacing facing = instance.getSide();
                int facingInteger = facing == null ? -1 : facing.getIndex();
                nbt.setInteger("side", facingInteger);
                return nbt;
            }

            @Override
            public void readNBT(Capability<ICoordinateHandler> capability, ICoordinateHandler instance, EnumFacing side, NBTBase base)
            {
                NBTTagCompound nbt = (NBTTagCompound) base;

                BlockPos pos;
                if(nbt.hasKey("x") && nbt.hasKey("y") && nbt.hasKey("z"))
                {
                    pos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
                }
                else
                {
                    pos = new BlockPos(0, 0, 0);
                }
                instance.setCoordinate(pos);

                int facingInteger = nbt.getInteger("side");
                if(facingInteger == -1)
                {
                    instance.setSide(null);
                }
                else
                {
                    instance.setSide(EnumFacing.getFront(facingInteger));
                }
            }
        }, CoordinateHandler::new);
    }
}
