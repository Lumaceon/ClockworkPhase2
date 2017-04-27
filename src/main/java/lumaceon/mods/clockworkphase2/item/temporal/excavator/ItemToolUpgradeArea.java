package lumaceon.mods.clockworkphase2.item.temporal.excavator;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import net.minecraft.item.ItemStack;

public class ItemToolUpgradeArea extends ItemToolUpgrade implements IToolUpgrade
{
    public ItemToolUpgradeArea(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    public int getAreaRadius(ItemStack stack) {
        return 2;
    }
}
