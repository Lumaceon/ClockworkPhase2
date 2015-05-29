package lumaceon.mods.clockworkphase2.item.components.clockwork;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.item.IClockworkComponent;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemMemoryComponent extends ItemClockworkPhase implements IClockworkComponent
{
    public int quality, speed, memory;

    public ItemMemoryComponent(String unlocalizedName, int quality, int speed, int memory)
    {
        super(64, 100, unlocalizedName);
        this.quality = quality;
        this.speed = speed;
        this.memory = memory;
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
    public int getMemory(ItemStack is) {
        return memory;
    }
}