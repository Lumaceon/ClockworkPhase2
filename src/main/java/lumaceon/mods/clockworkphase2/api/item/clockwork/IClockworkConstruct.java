package lumaceon.mods.clockworkphase2.api.item.clockwork;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IClockworkConstruct extends IClockwork
{
    public int getTier(ItemStack item);
    public int getTension(ItemStack item);
    public int getMaxTension(ItemStack item);
    public void setTension(ItemStack item, int tension);

    /**
     * Sets the 'tier' of this construct's tiles. This is context sensitive; in tiles tools this sets the
     * appropriate harvest level, but in machines it's usually ignored or saved and referred to directly.
     * @param tier The tier to set up, which is set by the highest tiered component in the tiles.
     */
    public void setTier(ItemStack item, int tier);

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
    public void addConstructInformation(ItemStack item, EntityPlayer player, List list);
}
