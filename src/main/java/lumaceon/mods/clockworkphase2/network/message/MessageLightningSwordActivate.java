package lumaceon.mods.clockworkphase2.network.message;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;

public class MessageLightningSwordActivate implements IMessage
{
    public int charge;
    public Vec3 pos;
    public Vec3 look;

    public MessageLightningSwordActivate() {}

    public MessageLightningSwordActivate(EntityPlayer player, int charge) {
        this.charge = charge;
        this.pos = player.getPosition(0);
        this.look = player.getLook(0);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(charge);
        buf.writeDouble(pos.xCoord);
        buf.writeDouble(pos.yCoord);
        buf.writeDouble(pos.zCoord);
        buf.writeDouble(look.xCoord);
        buf.writeDouble(look.yCoord);
        buf.writeDouble(look.zCoord);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        charge = buf.readInt();
        pos = Vec3.createVectorHelper(buf.readDouble(), buf.readDouble(), buf.readDouble());
        look = Vec3.createVectorHelper(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }
}
