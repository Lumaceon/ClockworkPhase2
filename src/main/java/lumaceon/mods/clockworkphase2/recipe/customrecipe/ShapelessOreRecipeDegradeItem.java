package lumaceon.mods.clockworkphase2.recipe.customrecipe;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapelessOreRecipeDegradeItem extends ShapelessOreRecipe
{
    public Item itemToDegrade;

    public ShapelessOreRecipeDegradeItem(Block result, Item itemToDegrade, Object... recipe) {
        super(result, recipe);
        this.itemToDegrade = itemToDegrade;
    }
    public ShapelessOreRecipeDegradeItem(Item result, Item itemToDegrade, Object... recipe) {
        super(result, recipe);
        this.itemToDegrade = itemToDegrade;
    }
    public ShapelessOreRecipeDegradeItem(ItemStack result, Item itemToDegrade, Object... recipe) {
        super(result, recipe);
        this.itemToDegrade = itemToDegrade;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean matches(InventoryCrafting var1, World world)
    {
        ArrayList<Object> required = new ArrayList<Object>(input);

        for (int x = 0; x < var1.getSizeInventory(); x++)
        {
            ItemStack slot = var1.getStackInSlot(x);

            if (slot != null)
            {
                boolean inRecipe = false;
                Iterator<Object> req = required.iterator();

                while(req.hasNext())
                {
                    boolean match = false;

                    Object next = req.next();

                    if(next instanceof ItemStack)
                    {
                        match = itemMatches((ItemStack) next, slot);
                    }
                    else if(next instanceof List)
                    {
                        Iterator<ItemStack> itr = ((List<ItemStack>)next).iterator();
                        while(itr.hasNext() && !match)
                        {
                            match = itemMatches(itr.next(), slot);
                        }
                    }

                    if (match)
                    {
                        inRecipe = true;
                        required.remove(next);
                        break;
                    }
                }

                if (!inRecipe)
                {
                    return false;
                }
            }
        }

        return required.isEmpty();
    }

    public boolean itemMatches(ItemStack target, ItemStack input) {
        return !(input == null && target != null || input != null && target == null) && target != null && (target.getItem() == input.getItem());
    }

    @Override
    public ItemStack[] getRemainingItems(InventoryCrafting inv)
    {
        ItemStack[] ret = new ItemStack[inv.getSizeInventory()];
        for(int n = 0; n < ret.length; n++)
        {
            ItemStack i = inv.getStackInSlot(n);
            if(i != null && i.getItem().equals(itemToDegrade))
            {
                if(i.getItemDamage() == i.getMaxDamage() - 1)
                    continue;
                ItemStack stack = new ItemStack(i.getItem());
                stack.setItemDamage(i.getItemDamage() + 1);
                ret[n] = stack;
            }
        }
        return ret;
    }
}
