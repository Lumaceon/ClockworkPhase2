package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageTemporalInfluence implements IMessage
{
    public int newTemporalInfluence;

    public MessageTemporalInfluence() {}

    public MessageTemporalInfluence(int newTemporalInfluence) {
        this.newTemporalInfluence = newTemporalInfluence;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(newTemporalInfluence);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        newTemporalInfluence = buf.readInt();
    }
}
