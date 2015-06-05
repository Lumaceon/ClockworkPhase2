package lumaceon.mods.clockworkphase2.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageTileStateChange implements IMessage
{
    public int x, y, z;
    public byte state;

    public MessageTileStateChange() {}

    public MessageTileStateChange(int x, int y, int z, byte stateOrdinal)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.state = stateOrdinal;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(state);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        state = buf.readByte();
    }
}
