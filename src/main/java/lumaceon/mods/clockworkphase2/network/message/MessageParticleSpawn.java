package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageParticleSpawn implements IMessage
{
    public int particleIndex;
    public double x, y, z;

    public MessageParticleSpawn() {}

    public MessageParticleSpawn(int particleIndex, double x, double y, double z) {
        this.particleIndex = particleIndex;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(particleIndex);
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.particleIndex = buf.readInt();
        this.x = buf.readDouble();
        this.y = buf.readDouble();
        this.z = buf.readDouble();
    }
}
