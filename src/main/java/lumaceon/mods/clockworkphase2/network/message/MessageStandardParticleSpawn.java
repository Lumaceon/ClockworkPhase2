package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageStandardParticleSpawn implements IMessage
{
    public double x, y, z;
    public int ID, particleNumber;

    public MessageStandardParticleSpawn() {}

    public MessageStandardParticleSpawn(double x, double y, double z, int ID, int particleNumber)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.ID = ID;
        this.particleNumber = particleNumber;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeInt(ID);
        buf.writeInt(particleNumber);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        x = buf.readDouble();
        y = buf.readDouble();
        z = buf.readDouble();
        ID = buf.readInt();
        particleNumber = buf.readInt();
    }
}
