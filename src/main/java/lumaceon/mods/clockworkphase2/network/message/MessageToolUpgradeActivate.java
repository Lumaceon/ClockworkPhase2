package lumaceon.mods.clockworkphase2.network.message;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessageToolUpgradeActivate implements IMessage
{
    public int buttonID;

    public MessageToolUpgradeActivate() {}

    public MessageToolUpgradeActivate(int buttonID) {
        this.buttonID = buttonID;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(buttonID);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        buttonID = buf.readInt();
    }
}
