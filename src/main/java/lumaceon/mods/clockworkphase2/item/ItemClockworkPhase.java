package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemClockworkPhase extends Item
{
    public ItemClockworkPhase(int maxStack, int maxDamage, String registryName)
    {
        super();
        this.setMaxStackSize(maxStack);
        this.setMaxDamage(maxDamage);
        this.setNoRepair();
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setRegistryName(registryName);
        this.setUnlocalizedName(registryName);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }

    @Override
    public String getUnlocalizedName(ItemStack is) {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }
}
