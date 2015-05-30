package lumaceon.mods.clockworkphase2.api.item.temporal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface ITemporal
{
    public void addTemporalInformation(ItemStack item, EntityPlayer player, List list, boolean flag);
}
