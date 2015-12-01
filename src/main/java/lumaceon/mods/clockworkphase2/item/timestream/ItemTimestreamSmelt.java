package lumaceon.mods.clockworkphase2.item.timestream;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemTimestreamSmelt extends ItemClockworkPhase
{
    public ItemTimestreamSmelt(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        //InformationDisplay.addTimestreamInformation(is, player, list, getQualityMultiplier(is), getSpeedMultiplier(is), getMemoryMultiplier(is));
    }
}