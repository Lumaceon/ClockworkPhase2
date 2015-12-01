package lumaceon.mods.clockworkphase2.item.components.tool.clockwork;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkComponent;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemGear extends ItemClockworkPhase implements IClockworkComponent
{
    public int quality, speed, memory, harvestLevel;

    public ItemGear(String unlocalizedName, int quality, int speed, int memory, int harvestLevel)
    {
        super(64, 100, unlocalizedName);
        this.quality = quality;
        this.speed = speed;
        this.memory = memory;
        this.harvestLevel = harvestLevel;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addClockworkComponentInformation(is, list);
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
