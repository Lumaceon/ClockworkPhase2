package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageClockworkControllerSetup implements IMessage
{
    public BlockPos controllerPos;
    public NBTTagCompound settings;

    public MessageClockworkControllerSetup() {}

    public MessageClockworkControllerSetup(BlockPos controllerPos, NBTTagCompound nbt) {
        this.controllerPos = controllerPos;
        this.settings = nbt;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(controllerPos.getX());
        buf.writeInt(controllerPos.getY());
        buf.writeInt(controllerPos.getZ());
        ByteBufUtils.writeTag(buf, settings);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        controllerPos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        settings = ByteBufUtils.readTag(buf);
    }
}
