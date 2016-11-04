package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageAchievementScore implements IMessage
{
    public int newAchievementScore;

    public MessageAchievementScore() {}

    public MessageAchievementScore(int newAchievementScore) {
        this.newAchievementScore = newAchievementScore;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(newAchievementScore);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        newAchievementScore = buf.readInt();
    }
}
