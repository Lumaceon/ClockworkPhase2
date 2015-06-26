package lumaceon.mods.clockworkphase2.item.components.tool.temporal;

import lumaceon.mods.clockworkphase2.api.item.ITemporalToolModule;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.item.ItemStack;

public class ItemTemporalToolModuleSmelt extends ItemClockworkPhase implements ITemporalToolModule
{
    public ItemTemporalToolModuleSmelt(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public boolean isEnabled(ItemStack item) {
        return NBTHelper.BOOLEAN.get(item, NBTTags.ACTIVE);
    }

    @Override
    public float getQualityMultiplier(ItemStack item) {
        return 1F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack item) {
        return 1.2F;
    }

    @Override
    public float getMemoryMultiplier(ItemStack item) {
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
