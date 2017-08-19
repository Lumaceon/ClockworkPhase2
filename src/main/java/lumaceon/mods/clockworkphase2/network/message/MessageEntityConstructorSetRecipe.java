package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageEntityConstructorSetRecipe implements IMessage
{
    public BlockPos pos;
    public int dimension;
    public String recipeID;

    public MessageEntityConstructorSetRecipe() {}

    public MessageEntityConstructorSetRecipe(BlockPos pos, int dimension, String recipeID)
    {
        this.pos = pos;
        this.dimension = dimension;
        this.recipeID = recipeID;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(dimension);
        ByteBufUtils.writeUTF8String(buf, recipeID);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        dimension = buf.readInt();
        recipeID = ByteBufUtils.readUTF8String(buf);
    }
}
