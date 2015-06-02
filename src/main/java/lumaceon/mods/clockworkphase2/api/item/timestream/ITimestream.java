package lumaceon.mods.clockworkphase2.api.item.timestream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * Abstract interface which all other timestreams extend.
 */
public abstract interface ITimestream
{
    /**
     * Magnitude means different things depending on the timestream. For the most part it represents either speed or
     * limitations of the timestream's effect. For example, a cannibalized bio-growth timestream will limit the number
     * of tree growths it can consume over a given period of time to it's magnitude.
     * @return The magnitude of this timestream.
     */
    public int getMagnitude(ItemStack item);
    public void setMagnitude(ItemStack item, int magnitude);

    //Color methods used for various different rendering.
    public int getColorRed(ItemStack item);
    public int getColorGreen(ItemStack item);
    public int getColorBlue(ItemStack item);

    /**
     * Called in InformationDisplay to add information about this timestream when the Timestream Details key is held.
     * This will (probably) never be used unless an appropriate InformationDisplay method is called during
     * addInformation, such as addTimestreamInformation.
     */
    public void addTimestreamInformation(ItemStack item, EntityPlayer player, List list);
}