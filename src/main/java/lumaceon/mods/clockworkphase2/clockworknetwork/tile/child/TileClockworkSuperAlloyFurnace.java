package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.TileClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.recipe.ExperimentalAlloyRecipes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileClockworkSuperAlloyFurnace extends TileClockworkNetworkMachine
{
    private ItemStack[] dummyExIngot = new ItemStack[] {new ItemStack(ModItems.experimentalIngot.getItem())};
    private ItemStack[] dummyEtIngot = new ItemStack[] {new ItemStack(ModItems.ingotEternium.getItem())};
    private ItemStack[] dummyMoIngot = new ItemStack[] {new ItemStack(ModItems.ingotMomentium.getItem())};
    private ItemStack[] dummyPaIngot = new ItemStack[] {new ItemStack(ModItems.ingotParadoxium.getItem())};

    public TileClockworkSuperAlloyFurnace() {
        inventory = new ItemStack[6];
    }

    @Override
    public boolean canWork() { //TODO check for duplicates.
        for(int n = 0; n < 6; n++)
            if(inventory[n] == null)
                return false;
        if(ExperimentalAlloyRecipes.getNumberOfValidMetals(ExperimentalAlloyRecipes.eterniumRecipe, inventory) == 6)
            return canExportAll(dummyEtIngot);
        else if(ExperimentalAlloyRecipes.getNumberOfValidMetals(ExperimentalAlloyRecipes.momentiumRecipe, inventory) == 6)
            return canExportAll(dummyMoIngot);
        else if(ExperimentalAlloyRecipes.getNumberOfValidMetals(ExperimentalAlloyRecipes.paradoxiumRecipe, inventory) == 6)
            return canExportAll(dummyPaIngot);
        return canExportAll(dummyExIngot);
    }

    @Override
    public void work()
    {
        if(this.canWork())
        {
            if(ExperimentalAlloyRecipes.getNumberOfValidMetals(ExperimentalAlloyRecipes.eterniumRecipe, inventory) == 6)
            {
                ItemStack itemstack = new ItemStack(ModItems.ingotEternium.getItem());
                exportAll(new ItemStack[] {itemstack});

                for(int n = 0; n < 6; n++)
                {
                    --this.inventory[n].stackSize;
                    if(this.inventory[n].stackSize <= 0)
                        this.inventory[n] = null;
                }
            }
            else if(ExperimentalAlloyRecipes.getNumberOfValidMetals(ExperimentalAlloyRecipes.momentiumRecipe, inventory) == 6)
            {
                ItemStack itemstack = new ItemStack(ModItems.ingotMomentium.getItem());
                exportAll(new ItemStack[] {itemstack});

                for(int n = 0; n < 6; n++)
                {
                    --this.inventory[n].stackSize;
                    if(this.inventory[n].stackSize <= 0)
                        this.inventory[n] = null;
                }
            }
            else if(ExperimentalAlloyRecipes.getNumberOfValidMetals(ExperimentalAlloyRecipes.paradoxiumRecipe, inventory) == 6)
            {
                ItemStack itemstack = new ItemStack(ModItems.ingotParadoxium.getItem());
                exportAll(new ItemStack[] {itemstack});

                for(int n = 0; n < 6; n++)
                {
                    --this.inventory[n].stackSize;
                    if(this.inventory[n].stackSize <= 0)
                        this.inventory[n] = null;
                }
            }
            else
            {
                ItemStack itemstack = new ItemStack(ModItems.experimentalIngot.getItem());

                //Setup information display
                for(int n = 0; n < 6; n++)
                    NBTHelper.STRING.set(itemstack, "ingot_" + n, this.inventory[n].getDisplayName());
                NBTHelper.INT.set(itemstack, "percent_eternium", (int) ((ExperimentalAlloyRecipes.getNumberOfValidMetals(ExperimentalAlloyRecipes.eterniumRecipe, inventory) / 6.0F) * 100.0F));
                NBTHelper.INT.set(itemstack, "percent_momentium", (int) ((ExperimentalAlloyRecipes.getNumberOfValidMetals(ExperimentalAlloyRecipes.momentiumRecipe, inventory) / 6.0F) * 100.0F));
                NBTHelper.INT.set(itemstack, "percent_paradoxium", (int) ((ExperimentalAlloyRecipes.getNumberOfValidMetals(ExperimentalAlloyRecipes.paradoxiumRecipe, inventory) / 6.0F) * 100.0F));

                exportAll(new ItemStack[] {itemstack});
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
}
