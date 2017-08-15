package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageMachineModeActivate implements IMessage
{
    public BlockPos pos;
    public int dimension;

    public MessageMachineModeActivate() {}

    public MessageMachineModeActivate(BlockPos pos, int dimension)
    {
        this.pos = pos;
        this.dimension = dimension;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(dimension);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        dimension = buf.readInt();
    }
}
