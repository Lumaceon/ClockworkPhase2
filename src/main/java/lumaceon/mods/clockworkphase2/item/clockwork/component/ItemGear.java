package lumaceon.mods.clockworkphase2.item.clockwork.component;

import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemGear extends ItemClockworkPhase implements IClockwork
{
    public int quality, speed, harvestLevel;

    public ItemGear(String unlocalizedName, int quality, int speed, int harvestLevel)
    {
        super(64, 100, unlocalizedName);
        this.quality = quality;
        this.speed = speed;
        this.harvestLevel = harvestLevel;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        InformationDisplay.addClockworkComponentInformation(stack, tooltip);
    }

    @Override
    public int getQuality(ItemStack is) {
        return quality;
    }

    @Override
    public int getSpeed(ItemStack is) {
        return speed;
    }

    @Override
    public int getTier(ItemStack is) {
        return harvestLevel;
    }
}
