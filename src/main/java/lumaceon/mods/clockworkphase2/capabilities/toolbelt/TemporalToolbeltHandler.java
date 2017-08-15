package lumaceon.mods.clockworkphase2.capabilities.toolbelt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

public class TemporalToolbeltHandler implements ITemporalToolbeltHandler
{
    protected int rowCount;
    protected NonNullList<NonNullList<ItemStack>> rows;

    public TemporalToolbeltHandler()
    {
        rowCount = 0;
        rows = NonNullList.withSize(5, NonNullList.create());
        for(int i = 0; i < 5; i++)
        {
            rows.set(i, NonNullList.withSize(9, ItemStack.EMPTY));
        }
    }

    public TemporalToolbeltHandler(int rowCount)
    {
        this();
        this.rowCount = rowCount;
    }

    @Override
    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public void setRow(int index, NonNullList<ItemStack> row)
    {
        if(rows.size() > index)
        {
            rows.set(index, row);
        }
    }

    @Override
    public NonNullList<ItemStack> getRow(int index)
    {
        if(rows.size() > index)
        {
            return rows.get(index);
        }
        return NonNullList.withSize(9, ItemStack.EMPTY);
    }

    @Override
    public NonNullList<NonNullList<ItemStack>> getAllRows()
    {
        NonNullList<NonNullList<ItemStack>> ret = NonNullList.withSize(rowCount, NonNullList.create());
        for(int i = 0; i < rowCount; i++)
        {
            ret.set(i, rows.get(i));
        }
        return ret;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();

        nbt.setInteger("row_count", rowCount);

        if(rows != null)
        {
            NBTTagList rowList = new NBTTagList();
            for(NonNullList<ItemStack> row : rows)
            {
                NBTTagCompound rowTag = new NBTTagCompound();
                NBTTagList nbtList = new NBTTagList();
                for(int index = 0; index < row.size(); index++)
                {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setByte("slot_index", (byte)index);
                    row.get(index).writeToNBT(tag);
                    nbtList.appendTag(tag);
                }
                rowTag.setTag("inventory", nbtList);
                rowList.appendTag(rowTag);
            }
            nbt.setTag("row_list", rowList);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("row_count"))
        {
            setRowCount(nbt.getInteger("row_count"));
        }

        if(nbt.hasKey("row_list"))
        {
            NBTTagList rowList = nbt.getTagList("row_list", Constants.NBT.TAG_COMPOUND);
            for(int n = 0; n < rowList.tagCount(); n++)
            {
                NBTTagCompound rowTag = rowList.getCompoundTagAt(n);
                NBTTagList nbtList = rowTag.getTagList("inventory", Constants.NBT.TAG_COMPOUND);
                for(int i = 0; i < nbtList.tagCount(); i++)
                {
                    NBTTagCompound tagCompound = nbtList.getCompoundTagAt(i);
                    byte slotIndex = tagCompound.getByte("slot_index");
                    NonNullList<ItemStack> l = rows.get(n);
                    l.set(slotIndex, new ItemStack(tagCompound));
                    rows.set(n, l);
                }
            }
        }
    }
}
