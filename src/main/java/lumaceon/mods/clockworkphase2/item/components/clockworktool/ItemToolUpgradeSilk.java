package lumaceon.mods.clockworkphase2.item.components.clockworktool;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.item.ItemStack;

public class ItemToolUpgradeSilk extends ItemClockworkPhase implements IToolUpgrade
{
    public ItemToolUpgradeSilk(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public void setActive(ItemStack item, boolean active) {
        NBTHelper.BOOLEAN.set(item, NBTTags.ACTIVE, active);
    }

    @Override
    public boolean getActive(ItemStack item) {
        return NBTHelper.BOOLEAN.get(item, NBTTags.ACTIVE);
    }

    @Override
    public float getQualityMultiplier(ItemStack item) {
        return 1.2F;
    }

    @Override
    public float getSpeedMultiplier(ItemStack item) {
        return 1F;
    }
}
