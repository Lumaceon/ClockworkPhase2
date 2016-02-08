package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.recipe.AlloyRecipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class TileClockworkAlloyFurnace extends TileClockworkNetworkMachine
{
    public TileClockworkAlloyFurnace() {
        inventory = new ItemStack[3];
    }

    @Override
    public boolean canWork() {
        AlloyRecipes.AlloyRecipe recipe = AlloyRecipes.getRecipe(inventory[0], inventory[1]);
        return recipe != null && (inventory[2] == null || (OreDictionary.itemMatches(recipe.output, inventory[2], false) && inventory[2].stackSize + recipe.output.stackSize <= 64));
    }

    @Override
    public void work() {
        AlloyRecipes.AlloyRecipe recipe = AlloyRecipes.getRecipe(inventory[0], inventory[1]);
        if(recipe != null)
        {
            if(recipe.first.itemMatches(inventory[0]))
                inventory[0].stackSize -= recipe.first.ratio;
            if(recipe.second.itemMatches(inventory[0]))
                inventory[0].stackSize -= recipe.second.ratio;
            if(recipe.first.itemMatches(inventory[1]))
                inventory[1].stackSize -= recipe.first.ratio;
            if(recipe.second.itemMatches(inventory[1]))
                inventory[1].stackSize -= recipe.second.ratio;

            if(inventory[0] != null && inventory[0].stackSize <= 0)
                inventory[0] = null;
            if(inventory[1] != null && inventory[1].stackSize <= 0)
                inventory[1] = null;

            if(inventory[2] == null)
                inventory[2] = recipe.output.copy();
            else
                inventory[2].stackSize += recipe.output.stackSize;
        }
    }

    @Override
    public ClockworkNetworkContainer getGui() {
        return ClockworkPhase2.proxy.getClockworkNetworkGui(this, 5);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[] { 0, 1, 2 };
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return false;
    }
}
