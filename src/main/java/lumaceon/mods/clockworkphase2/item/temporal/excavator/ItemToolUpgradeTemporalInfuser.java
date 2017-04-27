package lumaceon.mods.clockworkphase2.item.temporal.excavator;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import lumaceon.mods.clockworkphase2.util.NBTHelper;
import lumaceon.mods.clockworkphase2.util.NBTTags;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.item.ItemStack;

public class ItemToolUpgradeTemporalInfuser extends ItemToolUpgrade implements IToolUpgrade
{
    public ItemToolUpgradeTemporalInfuser(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }
}
