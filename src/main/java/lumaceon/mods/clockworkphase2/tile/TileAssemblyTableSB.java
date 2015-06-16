package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.api.assembly.AssemblySlot;
import lumaceon.mods.clockworkphase2.api.item.IAssemblable;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileAssemblyTableSB extends TileClockworkPhase
{
    private ItemStack workItem;
    public AssemblySlot[] assemblySlots;

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if(workItem != null)
        {
            NBTTagCompound tag = new NBTTagCompound();
            workItem.writeToNBT(tag);
            nbt.setTag("work_item", tag);
        }
        else if(nbt.hasKey("work_item"))
            nbt.removeTag("work_item");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if(nbt.hasKey("work_item"))
        {
            workItem = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbt.getTag("work_item"));
            if(workItem != null && workItem.getItem() instanceof IAssemblable)
                assemblySlots = ((IAssemblable) workItem.getItem()).initializeSlots(workItem);
        }
    }

    @Override
    public void setState(int state) {}
    @Override
    public void setStateAndUpdate(int state) {}

    public ItemStack getWorkItem() {
        return workItem;
    }

    public void setWorkItem(ItemStack item) {
        workItem = item;
        if(workItem != null && workItem.getItem() instanceof IAssemblable)
            assemblySlots = ((IAssemblable) workItem.getItem()).initializeSlots(workItem);
        markDirty();
    }

    public AssemblySlot[] getAssemblySlots()
    {
        return assemblySlots;
    }
}
