package lumaceon.mods.clockworkphase2.api.item.temporal;

import lumaceon.mods.clockworkphase2.api.item.ITimeSand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface ITemporal extends ITimeSand
{
    public void addTemporalInformation(ItemStack item, EntityPlayer player, List list);
}
