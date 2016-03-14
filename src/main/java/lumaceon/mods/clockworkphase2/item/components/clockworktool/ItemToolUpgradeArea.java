package lumaceon.mods.clockworkphase2.item.components.clockworktool;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.item.ItemStack;

public class ItemToolUpgradeArea extends ItemClockworkPhase implements IToolUpgrade
{
    public ItemToolUpgradeArea(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public void setActive(ItemStack upgradeStack, ItemStack toolStack, boolean active) {
        NBTHelper.BOOLEAN.set(upgradeStack, NBTTags.ACTIVE, active);
    }

    @Override
    public boolean getActive(ItemStack upgradeStack, ItemStack toolStack) {
        return NBTHelper.BOOLEAN.get(upgradeStack, NBTTags.ACTIVE);
    }

    public int getAreaRadius(ItemStack stack) {
        return NBTHelper.hasTag(stack, "area_radius") ? NBTHelper.INT.get(stack, "area_radius") : 2;
    }
}
