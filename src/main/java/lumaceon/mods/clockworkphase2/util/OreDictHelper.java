package lumaceon.mods.clockworkphase2.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHelper
{
    public static boolean itemsMatch(ItemStack stack1, ItemStack stack2)
    {
        int[] ids1 = OreDictionary.getOreIDs(stack1);
        int[] ids2 = OreDictionary.getOreIDs(stack2);
        for(int id1 : ids1)
        {
            for(int id2 : ids2)
            {
                if(id1 == id2)
                    return true;
            }
        }
        return false;
    }
}
