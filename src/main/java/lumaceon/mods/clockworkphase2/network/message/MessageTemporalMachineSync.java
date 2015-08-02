package lumaceon.mods.clockworkphase2.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageTemporalMachineSync implements IMessage
{
    public long newTime;
    public int x, y, z;

    public MessageTemporalMachineSync() {}

    public MessageTemporalMachineSync(long newTime, int x, int y, int z)
    {
        this.newTime = newTime;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeLong(this.newTime);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        newTime = buf.readLong();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }
}
