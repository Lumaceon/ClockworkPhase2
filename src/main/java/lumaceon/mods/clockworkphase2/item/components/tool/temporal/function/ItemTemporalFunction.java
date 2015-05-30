package lumaceon.mods.clockworkphase2.item.components.tool.temporal.function;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalToolFunction;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class ItemTemporalFunction extends ItemClockworkPhase implements ITemporalToolFunction
{
    private boolean active;

    public ItemTemporalFunction(int maxStack, int maxDamage, String unlocalizedName, boolean isActiveFunction) {
        super(maxStack, maxDamage, unlocalizedName);
        this.active = isActiveFunction;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addTemporalFunctionInformation(is, list, getQualityMultiplier(is), getSpeedMultiplier(is), getMemoryMultiplier(is));
    }

    public boolean isActive(ItemStack item) {
        return active;
    }

    public float getQualityMultiplier(ItemStack item) {
        return 1.0F;
    }

    public float getSpeedMultiplier(ItemStack item) {
        return 1.0F;
    }

    public float getMemoryMultiplier(ItemStack item) {
        return 1.0F;
    }

}
