package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageCelestialCompassItemGet implements IMessage
{
    public ItemStack is;
    public BlockPos pos;
    public byte index;

    public MessageCelestialCompassItemGet() {}

    public MessageCelestialCompassItemGet(ItemStack is, BlockPos pos, byte index) {
        this.is = is;
        this.pos = pos;
        this.index = index;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, is);
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeByte(index);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        is = ByteBufUtils.readItemStack(buf);
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        index = buf.readByte();
    }
}
