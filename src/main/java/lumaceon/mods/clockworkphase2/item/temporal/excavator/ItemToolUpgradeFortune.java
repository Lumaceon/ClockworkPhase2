package lumaceon.mods.clockworkphase2.item.temporal.excavator;

import lumaceon.mods.clockworkphase2.api.item.IToolUpgrade;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ItemToolUpgradeFortune extends ItemToolUpgrade implements IToolUpgrade
{
    public ItemToolUpgradeFortune(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public void setActive(ItemStack upgradeStack, ItemStack toolStack, boolean active)
    {
        super.setActive(upgradeStack, toolStack, active);
        if(active)
        {
            Enchantment enchantment = Enchantment.getEnchantmentByLocation("fortune");
            if(enchantment != null)
                toolStack.addEnchantment(enchantment, 3);
        }
        else if(toolStack.isItemEnchanted())
        {
            Enchantment enchantment = Enchantment.getEnchantmentByLocation("fortune");
            if(enchantment != null)
            {
                Map<Enchantment, Integer> enchantmentMap = EnchantmentHelper.getEnchantments(toolStack);
                enchantmentMap.remove(enchantment);
                EnchantmentHelper.setEnchantments(enchantmentMap, toolStack);
            }
        }
    }
}
