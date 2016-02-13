package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageTileStateChange implements IMessage
{
    public int x, y, z, state;

    public MessageTileStateChange() {}

    public MessageTileStateChange(int x, int y, int z, int stateOrdinal)
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
        buf.writeInt(state);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        state = buf.readInt();
    }
}
