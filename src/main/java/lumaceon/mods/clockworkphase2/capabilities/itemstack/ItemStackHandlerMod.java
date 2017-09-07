package lumaceon.mods.clockworkphase2.capabilities.itemstack;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class ItemStackHandlerMod extends ItemStackHandler
{
    public ItemStackHandlerMod() {
        super();
    }

    public ItemStackHandlerMod(int size) {
        super(size);
    }

    public ItemStackHandlerMod(NonNullList<ItemStack> stacks) {
        super(stacks);
    }

    @Override
    public boolean equals(@Nullable final Object obj)
    {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        final ItemStackHandler that = (ItemStackHandler) obj;

        if(this.getSlots() != that.getSlots())
            return false;

        ItemStack thisItem;
        ItemStack thatItem;
        for(int i = 0; i < this.stacks.size(); i++)
        {
            thisItem = stacks.get(i);
            thatItem = that.getStackInSlot(i);

            if(!ItemStack.areItemStacksEqual(thisItem, thatItem)) return false;
        }

        return true;
    }
}
