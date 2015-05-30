package lumaceon.mods.clockworkphase2.item.components.tool.temporal.function.passive;

import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.item.components.tool.temporal.function.ItemTemporalFunction;
import net.minecraft.item.ItemStack;

public class ItemTemporalFunctionSmelt extends ItemTemporalFunction
{
    public ItemTemporalFunctionSmelt(int maxStack, int maxDamage, String unlocalizedName, boolean isActiveFunction) {
        super(maxStack, maxDamage, unlocalizedName, isActiveFunction);
    }

    @Override
    public long getTimeSandCostPerApplication(ItemStack item) {
        return TimeConverter.MINUTE;
    }

    @Override
    public long getTimeSandCostPerBlock(ItemStack item) {
        return 10;
    }

    @Override
    public float getSpeedMultiplier(ItemStack item) {
        return 1.2F;
    }

    @Override
    public int getColorRed(ItemStack item) {
        return 255;
    }

    @Override
    public int getColorGreen(ItemStack item) {
        return 90;
    }

    @Override
    public int getColorBlue(ItemStack item) {
        return 0;
    }
}
