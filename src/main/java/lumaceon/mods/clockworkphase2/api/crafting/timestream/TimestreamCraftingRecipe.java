package lumaceon.mods.clockworkphase2.api.crafting.timestream;

import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class TimestreamCraftingRecipe implements ITimestreamCraftingRecipe
{
    private String unlocalizedName;
    private int craftingDuration;
    private String eventKey;
    protected ItemStack centerItem;
    protected ItemStack[] components;
    protected ItemStack result;

    /**
     * Creates a new timestream recipe. This new recipe should then be registered with TimestreamCraftingRegistry.
     * Up to 8 objects can be passed in after centerItem. If they are instances of the Item, Block or ItemStack classes
     * they will be required for this recipe to be valid.
     *
     * @param unlocalizedName The name of this recipe, which must be unique.
     * @param craftingDuration The duration of this recipe in ticks (20th of a second, assuming no TPS lag).
     * @param centerItem The item that needs to be in the center.
     * @param result The resulting itemstack.
     * @param eventKey A string representing the type of event that should increase magnitude for this recipe.
     * @param recipe The recipe, which can comprised of Item, Block or ItemStack objects.
     */
    public TimestreamCraftingRecipe(String unlocalizedName, int craftingDuration, ItemStack result, String eventKey, Object centerItem, Object... recipe)
    {
        this.unlocalizedName = unlocalizedName;
        this.craftingDuration = craftingDuration;
        this.result = result;
        this.eventKey = eventKey;

        if(centerItem instanceof Item)
            this.centerItem = new ItemStack((Item) centerItem);
        if(centerItem instanceof Block)
            this.centerItem = new ItemStack((Block) centerItem);
        if(centerItem instanceof ItemStack)
            this.centerItem = (ItemStack) centerItem;

        components = new ItemStack[8];
        for(int n = 0; n < recipe.length && n < 8; n++)
        {
            if(recipe[n] instanceof Item)
            {
                components[n] = new ItemStack((Item) recipe[n]);
            }
            if(recipe[n] instanceof Block)
            {
                components[n] = new ItemStack((Block) recipe[n]);
            }
            if(recipe[n] instanceof ItemStack)
            {
                components[n] = (ItemStack) recipe[n];
            }
        }
    }

    @Override
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    @Override
    public int getMagnitudeIncrease(String eventKey) {
        return eventKey.equals(this.eventKey) ? 1 : 0;
    }

    @Override
    public int getCraftingDuration() {
        return craftingDuration;
    }

    @Override
    public ItemStack[] getRecipe()
    {
        ItemStack[] result = new ItemStack[10];
        for(int n = 0; n > 8; n++)
        {
            result[n] = components[n];
        }
        result[8] = this.centerItem;
        result[9] = this.result;
        return result;
    }

    @Override
    public boolean matches(ItemStack[] items)
    {
        boolean found;
        boolean[] used = { false, false, false, false, false, false, false, false };
        for(ItemStack component : components)
        {
            if(component == null)
                continue;

            found = false;
            for(int n = 0; n < 8; n++)
            {
                if(!used[n] && OreDictionary.itemMatches(component, items[n], false))
                {
                    found = true;
                    used[n] = true;
                }
            }

            if(!found) //Failed to find a match for that component.
                return false;
        }
        return true; //All components were found.
    }

    @Override
    public ItemStack getCraftingResult(ItemStack[] items, int magnitude)
    {
        ItemStack output = result.copy();
        NBTHelper.INT.set(output, NBTTags.MAGNITUDE, magnitude);
        return output;
    }
}
