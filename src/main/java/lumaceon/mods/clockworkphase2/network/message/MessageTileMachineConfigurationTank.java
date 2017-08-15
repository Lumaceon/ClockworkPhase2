package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageTileMachineConfigurationTank implements IMessage
{
    public BlockPos tilePosition;
    public int dimension;
    public int tankID;
    public EnumFacing direction;
    public boolean activate;

    public MessageTileMachineConfigurationTank() {}

    public MessageTileMachineConfigurationTank(BlockPos pos, int dimensionID, int tankID, EnumFacing direction, boolean activate) {
        this.tilePosition = pos;
        this.dimension = dimensionID;
        this.tankID = tankID;
        this.direction = direction;
        this.activate = activate;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(tilePosition.getX());
        buf.writeInt(tilePosition.getY());
        buf.writeInt(tilePosition.getZ());
        buf.writeInt(dimension);
        buf.writeInt(tankID);
        buf.writeByte(direction.ordinal());
        buf.writeBoolean(activate);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        tilePosition = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        dimension = buf.readInt();
        tankID = buf.readInt();
        direction = EnumFacing.getFront(buf.readByte());
        activate = buf.readBoolean();
    }
}
