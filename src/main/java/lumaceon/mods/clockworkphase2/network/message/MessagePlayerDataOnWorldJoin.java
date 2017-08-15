package lumaceon.mods.clockworkphase2.network.message;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessagePlayerDataOnWorldJoin implements IMessage
{
    public NonNullList<NonNullList<ItemStack>> temporalToolbeltItems;

    public MessagePlayerDataOnWorldJoin() {}

    public MessagePlayerDataOnWorldJoin(NonNullList<NonNullList<ItemStack>> temporalToolbeltItems)
    {
        this.temporalToolbeltItems = temporalToolbeltItems;
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(temporalToolbeltItems.size());
        for(NonNullList<ItemStack> row : temporalToolbeltItems)
        {
            for(int i = 0; i < 9; i++)
            {
                ByteBufUtils.writeItemStack(buf, row.get(i));
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        byte rows = buf.readByte();
        temporalToolbeltItems = NonNullList.withSize(rows, NonNullList.create());
        for(int rowIndex = 0; rowIndex < rows; rowIndex++)
        {
            temporalToolbeltItems.set(rowIndex, NonNullList.withSize(9, ItemStack.EMPTY));
            for(int i = 0; i < 9; i++)
            {
                temporalToolbeltItems.get(rowIndex).set(i, ByteBufUtils.readItemStack(buf));
            }
        }
    }
}
