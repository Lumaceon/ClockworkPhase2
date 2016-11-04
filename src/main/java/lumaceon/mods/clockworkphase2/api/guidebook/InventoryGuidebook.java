package lumaceon.mods.clockworkphase2.api.guidebook;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.List;

public class InventoryGuidebook implements IInventory
{
    public GuidebookRegistry.GuidebookRecipe guidebookRecipe;
    public ItemStack[] items;

    public InventoryGuidebook(GuidebookRegistry.GuidebookRecipe recipe)
    {
        this.guidebookRecipe = recipe;
        IRecipe r = recipe.recipe;

        if(r != null)
        {
            items = new ItemStack[r.getRecipeSize() + 1];
            if(r instanceof ShapedOreRecipe)
            {
                Object[] input = ((ShapedOreRecipe) r).getInput().clone();
                for(int n = 0; n < input.length; n++)
                {
                    Object temp = input[n];
                    if(temp != null)
                    {
                        if(temp instanceof ItemStack)
                            items[n] = (ItemStack) temp;
                        else if(temp instanceof List && !((List) temp).isEmpty())
                        {
                            Object t = ((List) temp).get(0);
                            if(t != null && t instanceof ItemStack)
                                items[n] = (ItemStack) t;
                        }
                    }
                }
                items[items.length - 1] = r.getRecipeOutput();
            }
            else if(r instanceof ShapelessOreRecipe)
            {
                Object[] input = ((ShapelessOreRecipe) r).getInput().toArray().clone();
                for(int n = 0; n < input.length; n++)
                {
                    Object temp = input[n];
                    if(temp != null)
                    {
                        if(temp instanceof ItemStack)
                            items[n] = (ItemStack) temp;
                        else if(temp instanceof List && !((List) temp).isEmpty())
                        {
                            Object t = ((List) temp).get(0);
                            if(t != null && t instanceof ItemStack)
                                items[n] = (ItemStack) t;
                        }
                    }
                }
                items[items.length - 1] = r.getRecipeOutput();
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return items == null ? 0 : items.length;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return items == null ? null : items[index];
    }

    @Override
    public ItemStack decrStackSize(int index, int count) { return null; }
    @Override
    public ItemStack removeStackFromSlot(int index) { return null; }
    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {}
    @Override
    public int getInventoryStackLimit() { return 64; }
    @Override
    public void clear() {}
    @Override
    public boolean isUseableByPlayer(EntityPlayer player) { return false; }
    @Override
    public void markDirty() {}
    @Override
    public void openInventory(EntityPlayer player) {}
    @Override
    public void closeInventory(EntityPlayer player) {}
    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) { return true; }
    @Override
    public int getField(int id) { return 0; }
    @Override
    public void setField(int id, int value) {}
    @Override
    public int getFieldCount() { return 0; }
    @Override
    public String getName() { return null; }
    @Override
    public boolean hasCustomName() { return false; }
    @Override
    public ITextComponent getDisplayName() { return null; }
}
