package lumaceon.mods.clockworkphase2.tile.machine;

import lumaceon.mods.clockworkphase2.init.Echoes;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import lumaceon.mods.clockworkphase2.recipe.AntiRecipes;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class TileClockworkFurnace extends TileClockworkMachine implements ITickable
{
    private static final int[] EXPORT_SLOTS = new int[] { 1 };

    public TileClockworkFurnace()
    {
        super(2, 64, 50, 10000, Echoes.smelt);
        this.slots = new Slot[] { new Slot(this, 0, 56, 25), new SlotNever(this, 1, 116, 34) };
    }

    @Override
    public boolean canWork(boolean isReversed)
    {
        if(this.inventory[0] == null)
            return false;

        ItemStack itemstack;
        int numberOfItemsToReverse = 1;
        if(isReversed)
        {
            if(getEchoesAvailable() < 1)
                return false;

            itemstack = AntiRecipes.getSmeltingResult(this.inventory[0]);
            if(itemstack != null)
                itemstack = new ItemStack(itemstack.getItem());
            numberOfItemsToReverse = AntiRecipes.getNumberOfItemsToReverseSmelt(this.inventory[0]);
        }
        else
            itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inventory[0]);

        if(itemstack == null || numberOfItemsToReverse > this.inventory[0].stackSize)
            return false;

        itemstack = itemstack.copy();
        return this.exportItem(itemstack, EXPORT_SLOTS, true) == null;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void completeAction(boolean isReversed)
    {
        ItemStack itemstack;
        int numberOfItemsToReverse = 1;
        if(isReversed)
        {
            itemstack = AntiRecipes.getSmeltingResult(this.inventory[0]).copy();
            itemstack = new ItemStack(itemstack.getItem());
            numberOfItemsToReverse = AntiRecipes.getNumberOfItemsToReverseSmelt(this.inventory[0]);
        }
        else
            itemstack = FurnaceRecipes.instance().getSmeltingResult(this.inventory[0]).copy();

        this.inventory[0].stackSize -= numberOfItemsToReverse;
        if(this.inventory[0].stackSize <= 0)
            this.inventory[0] = null;

        if(isReversed)
            consumeEchoes(1);
        else
            produceEchoes(1);

        ArrayList<ItemStack> items = new ArrayList<ItemStack>(1);
        items.add(itemstack);
        outputItems(items, this.inventory[1]);
    }

    @Override
    public int temporalActions(boolean isReversed, int maxNumberOfActions)
    {
        ItemStack smelt = inventory[0];
        if(smelt == null || smelt.stackSize <= 0)
            return 0;

        ItemStack result;
        int numberOfItemsToReverse = 1;
        if(isReversed)
        {
            result = AntiRecipes.getSmeltingResult(smelt);
            if(result != null)
                result = new ItemStack(result.getItem());
            numberOfItemsToReverse = AntiRecipes.getNumberOfItemsToReverseSmelt(smelt);
        }
        else
            result = FurnaceRecipes.instance().getSmeltingResult(smelt);

        if(result == null)
            return 0;

        int actualMaxActions;
        if(isReversed)
        {
            actualMaxActions = Math.min(maxNumberOfActions, (int) Math.floor((double) smelt.stackSize / (double) numberOfItemsToReverse));
            int echoes = getEchoesAvailable();
            if(echoes < actualMaxActions)
                actualMaxActions = echoes;
        }
        else
            actualMaxActions = Math.min(maxNumberOfActions, smelt.stackSize);

        if(actualMaxActions <= 0)
            return 0;

        //Some recipes may output greater-than-one stack size, or to an Item with a lower max stack size.
        //"True stack size" represents the total number of the resulting item.
        int trueStackSize = result.stackSize * actualMaxActions;
        int tempTrueSize = trueStackSize;
        ArrayList<ItemStack> outputStacks = new ArrayList<ItemStack>();
        while(tempTrueSize > 0)
        {
            int stackSize = Math.min(result.getMaxStackSize(), tempTrueSize);
            ItemStack tempItem = result.copy();
            tempItem.stackSize = stackSize;
            outputStacks.add(tempItem);
            tempTrueSize -= stackSize;
        }

        ItemStack outputSlot = getStackInSlot(1);
        outputStacks = outputItems(outputStacks, outputSlot); //Do the actual output.

        int remainingStack = 0;
        for(ItemStack s : outputStacks)
            if(s != null)
                remainingStack += s.stackSize;

        int completeActions = (int) Math.ceil((double) (trueStackSize - remainingStack) / (double) result.stackSize);

        smelt.stackSize -= completeActions * numberOfItemsToReverse;
        if(smelt.stackSize <= 0) { smelt = null; }
        setInventorySlotContents(0, smelt);

        if(isReversed)
            consumeEchoes(completeActions);
        else
            produceEchoes(completeActions);

        return completeActions;
    }

    @Override
    public boolean canExportToDirection(EnumFacing direction)
    {
        int[] slots = getSlotsForFace(direction);
        for(int i : slots)
            if(i == 1)
                return true;
        return false;
    }

    private ArrayList<ItemStack> outputItems(ArrayList<ItemStack> items, @Nullable ItemStack itemsInOutputSlot)
    {
        for(int i = 0; i < 6; i++)
        {
            if(items.isEmpty())
                break; //If we're coming back here and the output is empty, there's no need to keep looping.

            EnumFacing direction = EnumFacing.getFront(i);
            if(canExportToDirection(direction))
            {
                TileEntity te = worldObj.getTileEntity(pos.offset(direction));
                if(te != null)
                {
                    direction = direction.getOpposite();
                    IItemHandler cap = te.getCapability(ITEM_HANDLER_CAPABILITY, direction);
                    if(cap != null)
                    {
                        if(itemsInOutputSlot != null)
                            setInventorySlotContents(1, ItemHandlerHelper.insertItem(cap, itemsInOutputSlot, false));

                        for(int n = 0; n < items.size(); n++)
                        {
                            ItemStack temp = items.get(n);
                            ItemStack leftover = ItemHandlerHelper.insertItem(cap, temp, false);
                            if(leftover != null)
                            {
                                items.set(n, leftover);
                                break; //If there's leftover, that implies the target is full, so we can stop.
                            }
                            else
                            {
                                items.remove(n);
                                n--; //Since removing an element shifts the array, make sure we do too.
                            }
                        }
                    }
                }
            }
        }

        if(!items.isEmpty())
        {
            for(int n = 0; n < items.size(); n++)
            {
                ItemStack temp = items.get(n);
                ItemStack leftover = exportItem(temp, EXPORT_SLOTS, false);
                if(leftover != null)
                {
                    items.set(n, leftover);
                    break; //If there's leftover, that implies the target is full, so we can stop.
                }
                else
                {
                    items.remove(n);
                    n--; //Since removing an element shifts the array, make sure we do too.
                }
            }
        }

        return items;
    }
}
