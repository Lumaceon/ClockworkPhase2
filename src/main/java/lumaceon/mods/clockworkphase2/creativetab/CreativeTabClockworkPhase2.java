package lumaceon.mods.clockworkphase2.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CreativeTabClockworkPhase2 extends CreativeTabs
{
    public CreativeTabClockworkPhase2(String label)
    {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(Items.CLOCK);
    }
}
