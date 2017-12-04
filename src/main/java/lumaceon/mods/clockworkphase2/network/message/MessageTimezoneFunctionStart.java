package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageTimezoneFunctionStart implements IMessage
{
    public BlockPos pos;
    public int dimension;
    public String typeID;

    public MessageTimezoneFunctionStart() {}

    public MessageTimezoneFunctionStart(BlockPos pos, int dimension, String typeID)
    {
        this.pos = pos;
        this.dimension = dimension;
        this.typeID = typeID;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(dimension);
        ByteBufUtils.writeUTF8String(buf, typeID);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        this.dimension = buf.readInt();
        typeID = ByteBufUtils.readUTF8String(buf);
    }
}
