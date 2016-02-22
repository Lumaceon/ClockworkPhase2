package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.TileClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.recipe.SuperAlloyRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.oredict.OreDictionary;

public class TileClockworkSuperAlloyFurnace extends TileClockworkNetworkMachine
{
    public TileClockworkSuperAlloyFurnace() {
        inventory = new ItemStack[6];
    }

    @Override
    public boolean canWork() { //TODO check for duplicates.
        for(int n = 0; n < 6; n++)
            if(inventory[n] == null)
                return false;
        return true;
        /*if(hasAllItems(SuperAlloyRecipes.eterniumRecipe))
            return true;
        else if(hasAllItems(SuperAlloyRecipes.momentiumRecipe))
            return true;
        else
            return hasAllItems(SuperAlloyRecipes.capriciumRecipe);*/
    }

    @Override
    public void work()
    {
        if(this.canWork())
        {
            if(hasAllItems(SuperAlloyRecipes.eterniumRecipe))
            {
                ItemStack itemstack = new ItemStack(ModItems.ingotEternium.getItem());

                if(this.inventory[6] == null)
                    this.inventory[6] = itemstack;
                else if(this.inventory[6].getItem() == itemstack.getItem())
                    this.inventory[6].stackSize += itemstack.stackSize;

                for(int n = 0; n < 6; n++)
                {
                    --this.inventory[n].stackSize;
                    if(this.inventory[n].stackSize <= 0)
                        this.inventory[n] = null;
                }
            }
            else if(hasAllItems(SuperAlloyRecipes.momentiumRecipe))
            {
                ItemStack itemstack = new ItemStack(ModItems.ingotMomentium.getItem());

                if(this.inventory[6] == null)
                    this.inventory[6] = itemstack;
                else if(this.inventory[6].getItem() == itemstack.getItem())
                    this.inventory[6].stackSize += itemstack.stackSize;

                for(int n = 0; n < 6; n++)
                {
                    --this.inventory[n].stackSize;
                    if(this.inventory[n].stackSize <= 0)
                        this.inventory[n] = null;
                }
            }
            else if(hasAllItems(SuperAlloyRecipes.capriciumRecipe))
            {
                ItemStack itemstack = new ItemStack(ModItems.ingotParadoxium.getItem());

                if(this.inventory[6] == null)
                    this.inventory[6] = itemstack;
                else if(this.inventory[6].getItem() == itemstack.getItem())
                    this.inventory[6].stackSize += itemstack.stackSize;

                for(int n = 0; n < 6; n++)
                {
                    --this.inventory[n].stackSize;
                    if(this.inventory[n].stackSize <= 0)
                        this.inventory[n] = null;
                }
            }
            else
            {
                for(int n = 0; n < 6; n++)
                {
                    --this.inventory[n].stackSize;
                    if(this.inventory[n].stackSize <= 0)
                        this.inventory[n] = null;
                }
            }
        }
    }

    @Override
    public ClockworkNetworkContainer getGui() {
        return ClockworkPhase2.proxy.getClockworkNetworkGui(this, 4);
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[] {0, 1, 2, 3, 4, 5, 6};
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, EnumFacing side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, EnumFacing side) {
        return false;
    }

    public boolean hasAllItems(String[] items)
    {
        int foundItems = 0;
        for(String s : items)
            for(int n = 0; n < 6; n++)
                if(OreDictionary.itemMatches(inventory[n], OreDictionary.getOres(s).get(0), false))
                {
                    ++foundItems;
                    break;
                }

        return foundItems == 6;
    }
}
