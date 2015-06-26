package lumaceon.mods.clockworkphase2.item.timestream;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.item.timestream.ITimestream;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemTimestreamRelocation extends ItemClockworkPhase implements ITimestream
{
    public ItemTimestreamRelocation(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        //InformationDisplay.addTimestreamInformation(is, player, list, getQualityMultiplier(is), getSpeedMultiplier(is), getMemoryMultiplier(is));
    }
}