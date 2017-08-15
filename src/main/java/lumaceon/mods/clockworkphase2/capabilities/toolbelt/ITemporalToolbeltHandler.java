package lumaceon.mods.clockworkphase2.capabilities.toolbelt;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;

public interface ITemporalToolbeltHandler
{
    /**
     * @param rowCount The number of rows of hotbars in this toolbelt.
     */
    public void setRowCount(int rowCount);

    /**
     * @return The number of rows of hotbars in this toolbelt.
     */
    public int getRowCount();

    public void setRow(int index, NonNullList<ItemStack> row);

    public NonNullList<ItemStack> getRow(int index);

    public NonNullList<NonNullList<ItemStack>> getAllRows();

    public NBTTagCompound serializeNBT();
    public void deserializeNBT(NBTTagCompound nbt);
}
