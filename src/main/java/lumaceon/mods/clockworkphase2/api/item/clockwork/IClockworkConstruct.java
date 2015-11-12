package lumaceon.mods.clockworkphase2.api.item.clockwork;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IClockworkConstruct
{
    public int getTension(ItemStack item);
    public int getMaxTension(ItemStack item);
    public int getQuality(ItemStack item);
    public int getSpeed(ItemStack item);
    public void setTension(ItemStack item, int tension);
    public void setHarvestLevels(ItemStack item, int harvestLevel);

    /**
     * Used by a winding box and similar contraptions to add tension to this item.
     * @param tension Amount of tension to add.
     * @return Overspill (the amount of tension that wasn't added).
     */
    public int addTension(ItemStack item, int tension);

    /**
     * Called to consume tension from this item.
     * @param tension Amount of tension to consume.
     * @return Overspill (the amount of tension that couldn't be removed, most often due to the lack of tension).
     */
    public int consumeTension(ItemStack item, int tension);

    /**
     * Used by InformationDisplay to display context sensitive information when the control key is held.
     * @param item The itemstack to add information for.
     * @param list The list of informative lines.
     */
    public void addClockworkInformation(ItemStack item, EntityPlayer player, List list);
}
