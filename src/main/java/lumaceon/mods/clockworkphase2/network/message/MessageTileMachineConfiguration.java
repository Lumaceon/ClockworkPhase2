package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageTileMachineConfiguration implements IMessage
{
    public BlockPos tilePosition;
    public int dimension;
    public int slotID;
    public EnumFacing direction;
    public boolean activate;

    public MessageTileMachineConfiguration() {}

    public MessageTileMachineConfiguration(BlockPos pos, int dimensionID, int slotID, EnumFacing direction, boolean activate) {
        this.tilePosition = pos;
        this.dimension = dimensionID;
        this.slotID = slotID;
        this.direction = direction;
        this.activate = activate;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(tilePosition.getX());
        buf.writeInt(tilePosition.getY());
        buf.writeInt(tilePosition.getZ());
        buf.writeInt(dimension);
        buf.writeInt(slotID);
        buf.writeByte(direction.ordinal());
        buf.writeBoolean(activate);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        tilePosition = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        dimension = buf.readInt();
        slotID = buf.readInt();
        direction = EnumFacing.getFront(buf.readByte());
        activate = buf.readBoolean();
    }
}
