package lumaceon.mods.clockworkphase2.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageTimestreamRecipe implements IMessage
{
    public int x, y, z, index;

    public MessageTimestreamRecipe() {}

    public MessageTimestreamRecipe(int x, int y, int z, int index)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.index = index;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(index);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        index = buf.readInt();
    }
}
