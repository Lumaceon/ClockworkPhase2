package lumaceon.mods.clockworkphase2.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import javax.annotation.Nullable;
import java.util.Map;

public class AntiRecipes
{
    @Nullable
    public static ItemStack getSmeltingResult(ItemStack itemToBeReversed)
    {
        for(Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet())
        {
            if(compareItemStacks(itemToBeReversed, entry.getValue()))
            {
                return entry.getKey();
            }
        }

        return null;
    }

    public static int getNumberOfItemsToReverseSmelt(ItemStack itemToBeReversed)
    {
        for(Map.Entry<ItemStack, ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet())
        {
            if(compareItemStacks(itemToBeReversed, entry.getValue()))
            {
                return entry.getValue().stackSize;
            }
        }
        return 1;
    }

    /**
     * Copied from FurnaceRecipes.
     */
    private static boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
    }
}
