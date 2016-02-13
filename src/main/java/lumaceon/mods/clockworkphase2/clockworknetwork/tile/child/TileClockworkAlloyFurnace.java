package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.TileClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.recipe.AlloyRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
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
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[] { 0, 1, 2 };
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return true;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return true;
    }
}
