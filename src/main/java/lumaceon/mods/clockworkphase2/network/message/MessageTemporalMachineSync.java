package lumaceon.mods.clockworkphase2.network.message;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import lumaceon.mods.clockworkphase2.api.time.TimeStorage;
import net.minecraft.nbt.NBTTagCompound;

public class MessageTemporalMachineSync implements IMessage
{
    public TimeStorage timeStorage;
    public int x, y, z;
    public NBTTagCompound nbt;

    public MessageTemporalMachineSync() {}

    public MessageTemporalMachineSync(TimeStorage timeStorage, int x, int y, int z)
    {
        this.timeStorage = timeStorage;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        NBTTagCompound nbt = new NBTTagCompound();
        timeStorage.writeToNBT(nbt);
        ByteBufUtils.writeTag(buf, nbt);
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        nbt = ByteBufUtils.readTag(buf);
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
    }
}
