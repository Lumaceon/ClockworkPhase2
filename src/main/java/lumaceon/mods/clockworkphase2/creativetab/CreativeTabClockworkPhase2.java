package lumaceon.mods.clockworkphase2.creativetab;

import lumaceon.mods.clockworkphase2.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabClockworkPhase2 extends CreativeTabs
{
    public static ItemStack hourglass;
    public CreativeTabClockworkPhase2(String label)
    {
        super(label);
    }

    @Override
    public ItemStack getTabIconItem()
    {
        if(hourglass == null)
            hourglass = new ItemStack(ModItems.temporalHourglass);

        return hourglass;
    }
}
