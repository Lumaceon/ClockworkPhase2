package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageItemStorageResize implements IMessage
{
    public int xSlots, ySlots;
    public BlockPos pos;

    public MessageItemStorageResize() {}

    public MessageItemStorageResize(int xSlots, int ySlots, BlockPos pos) {
        this.xSlots = xSlots;
        this.ySlots = ySlots;
        this.pos = pos;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(xSlots);
        buf.writeInt(ySlots);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        this.xSlots = buf.readInt();
        this.ySlots = buf.readInt();
    }
}
