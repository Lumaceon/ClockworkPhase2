package lumaceon.mods.clockworkphase2.tile.machine;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotFluidContainer;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import lumaceon.mods.clockworkphase2.recipe.CrystallizerRecipes;
import lumaceon.mods.clockworkphase2.util.FluidTankSided;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class TileClockworkCrystallizer extends TileClockworkMachine
{
    public TileClockworkCrystallizer()
    {
        super(8, 64, 10, 1000000);

        this.slots = new Slot[]
        {
                new Slot(this, 0, 30, 16),
                new Slot(this, 1, 48, 16),
                new Slot(this, 2, 30, 34),
                new Slot(this, 3, 48, 34),
                new Slot(this, 4, 30, 52),
                new Slot(this, 5, 48, 52),
                new SlotFluidContainer(this, 6, 132, 60),
                new SlotNever(this, 7, 112, 34)
        };

        this.fluidTanks = new FluidTankSided[1];
        this.fluidTanks[0] = new FluidTankSided(10000);
        this.fluidTanks[0].setTileEntity(this);
        EXPORT_SLOTS = new int[] { 7 };
    }

    @Override
    public void update()
    {
        ItemStack stack = inventory.get(6);
        if(!stack.isEmpty() && stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, EnumFacing.DOWN))
        {
            IFluidHandlerItem fh = FluidUtil.getFluidHandler(stack);
            FluidStack fluid = FluidUtil.getFluidContained(stack);
            if(fh != null && fluid != null)
            {
                int amountFilled = fluidTanks[0].fillInternal(fluid, false);
                FluidStack amountDrained = fh.drain(amountFilled, true);

                if(amountDrained != null && amountDrained.amount > 0)
                {
                    fluidTanks[0].fillInternal(amountDrained, true);
                    inventory.set(6, fh.getContainer());
                }
            }
        }
        super.update();
    }

    @Override
    public boolean canWork()
    {
        NonNullList<ItemStack> craftingInventory = NonNullList.withSize(6, ItemStack.EMPTY);
        for(int i = 0; i < 6; i++)
        {
            craftingInventory.set(i, inventory.get(i));
        }

        FluidStack fluid = fluidTanks[0].getFluid();
        CrystallizerRecipes.CrystallizerRecipe recipe = CrystallizerRecipes.instance.getRecipe(craftingInventory, fluid);

        if(recipe == null)
            return false;

        ItemStack output = recipe.getOutput(craftingInventory, fluid);
        if(output.isEmpty())
            return false;

        return this.exportItem(output.copy(), EXPORT_SLOTS, true).isEmpty();
    }

    @Override
    public void completeAction()
    {
        NonNullList<ItemStack> craftingInventory = NonNullList.withSize(6, ItemStack.EMPTY);
        for(int i = 0; i < 6; i++)
        {
            craftingInventory.set(i, inventory.get(i));
        }

        FluidStack fluid = fluidTanks[0].getFluid();
        CrystallizerRecipes.CrystallizerRecipe recipe = CrystallizerRecipes.instance.getRecipe(craftingInventory, fluid);

        if(recipe == null)
            return;

        ItemStack output = recipe.getOutput(craftingInventory, fluid);

        if(output.isEmpty())
            return;

        //Spend the input items of the recipe.
        NonNullList<ItemStack> unpaidItems = recipe.inputItems;
        for(ItemStack payment : unpaidItems)
        {
            int requiredPayment = payment.getCount();
            for(int i = 0; i < 6 && requiredPayment > 0; i++)
            {
                ItemStack is = inventory.get(i);
                if(!is.isEmpty() && OreDictionary.itemMatches(payment, is, false))
                {
                    int amountToRemove = Math.min(requiredPayment, is.getCount());
                    is.shrink(amountToRemove);
                    if(is.getCount() <= 0)
                    {
                        inventory.set(i, ItemStack.EMPTY);
                    }

                    requiredPayment -= amountToRemove;
                }
            }
        }

        //If the recipe calls for fluid, spend it.
        if(recipe.fluidInventory != null)
        {
            fluidTanks[0].drainInternal(recipe.fluidInventory.amount, true);
        }

        ArrayList<ItemStack> exports = new ArrayList<>(1);
        exports.add(output.copy());
        outputItems(exports, this.inventory.get(7));
    }

    @Override
    public int temporalActions(int maxNumberOfActions)
    {
        //Technically, this is inefficient, but it's much simpler this way.
        int actionsCompleted = 0;
        while(canWork() && actionsCompleted < maxNumberOfActions)
        {
            completeAction();
            actionsCompleted += 1;
        }
        return actionsCompleted;
    }
}
