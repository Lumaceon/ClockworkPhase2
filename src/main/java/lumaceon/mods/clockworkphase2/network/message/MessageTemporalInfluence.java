package lumaceon.mods.clockworkphase2.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageTemporalInfluence implements IMessage
{
    public long newTemporalInfluence;

    public MessageTemporalInfluence() {}

    public MessageTemporalInfluence(long newTemporalInfluence) {
        this.newTemporalInfluence = newTemporalInfluence;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(newTemporalInfluence);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        newTemporalInfluence = buf.readLong();
    }
}
