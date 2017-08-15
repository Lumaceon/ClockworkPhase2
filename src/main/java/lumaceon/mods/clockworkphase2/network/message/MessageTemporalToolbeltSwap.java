package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageTemporalToolbeltSwap implements IMessage
{
    public int toolbeltRowIndex;

    public MessageTemporalToolbeltSwap() {}

    public MessageTemporalToolbeltSwap(int toolbeltRowIndex) {
        this.toolbeltRowIndex = toolbeltRowIndex;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(toolbeltRowIndex);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.toolbeltRowIndex = buf.readInt();
    }
}
