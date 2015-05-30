package lumaceon.mods.clockworkphase2.api.item.clockwork;

import net.minecraft.item.ItemStack;

public interface IMainspring
{
    public int getMaxSize(ItemStack item);

    public int getTension(ItemStack item);
}
