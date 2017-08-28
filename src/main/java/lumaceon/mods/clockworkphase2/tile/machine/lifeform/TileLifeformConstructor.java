package lumaceon.mods.clockworkphase2.tile.machine.lifeform;

import lumaceon.mods.clockworkphase2.api.EntityStack;
import lumaceon.mods.clockworkphase2.capabilities.entitycontainer.IEntityContainer;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotNonFullEntityContainer;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import lumaceon.mods.clockworkphase2.recipe.EntityConstructionRecipes;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import lumaceon.mods.clockworkphase2.util.LogHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class TileLifeformConstructor extends TileClockworkMachine
{
    @CapabilityInject(IEntityContainer.class)
    public static final Capability<IEntityContainer> ENTITY_CONTAINER = null;

    public EntityConstructionRecipes.EntityConstructionRecipe activeRecipe = EntityConstructionRecipes.INSTANCE.getRecipe("chicken");

    public TileLifeformConstructor() {
        super(11, 64, 100, 200000);
        this.slots = new Slot[]
                {
                        new SlotNonFullEntityContainer(this, 0, 50, 62),
                        new Slot(this, 1, 32, 6),
                        new Slot(this, 2, 50, 6),
                        new Slot(this, 3, 68, 6),
                        new Slot(this, 4, 32, 24),
                        new Slot(this, 5, 50, 24),
                        new Slot(this, 6, 68, 24),
                        new Slot(this, 7, 32, 42),
                        new Slot(this, 8, 50, 42),
                        new Slot(this, 9, 68, 42),
                        new SlotNever(this, 10, 191, 59)
                };
        EXPORT_SLOTS = new int[] { 10 };
    }

    @Override
    public boolean canWork()
    {
        if(activeRecipe == null)
            return false;

        //Block directly above this one must be null or replaceable.
        IBlockState blockState = this.getWorld().getBlockState(this.getPos().up());
        if(blockState == null || blockState.getBlock().isReplaceable(this.getWorld(), this.getPos().up()))
        {
            return matchesRecipe();
        }

        //Alternatively, we can allow it to work if we're trying to export to an entity container.
        ItemStack stack = this.getStackInSlot(0);
        if(!stack.isEmpty())
        {
            IEntityContainer entityContainer = stack.getCapability(ENTITY_CONTAINER, EnumFacing.DOWN);
            if(entityContainer != null && entityContainer.getCapacity() > entityContainer.getEntityCount())
            {
                return matchesRecipe() && this.exportItem(stack.copy(), EXPORT_SLOTS, true).isEmpty();
            }
        }

        return false;
    }

    private boolean matchesRecipe()
    {
        NonNullList<ItemStack> craftingInventory = NonNullList.withSize(9, ItemStack.EMPTY);
        for(int i = 1; i < 10; i++)
        {
            craftingInventory.set(i-1, inventory.get(i));
        }

        return activeRecipe.matchesRecipe(craftingInventory);
    }

    @Override
    public void completeAction()
    {
        if(activeRecipe == null)
            return;

        NonNullList<ItemStack> craftingInventory = NonNullList.withSize(9, ItemStack.EMPTY);
        for(int i = 1; i < 10; i++)
        {
            craftingInventory.set(i-1, inventory.get(i));
        }

        Entity outputEntity = null;
        try {
            outputEntity = (Entity) activeRecipe.constructor.newInstance(this.getWorld());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            LogHelper.info("Problem instantiating new entity of type: " + activeRecipe.output.getName());
        }

        if(outputEntity == null)
            return;

        ItemStack output = getStackInSlot(0);
        if(!output.isEmpty())
        {
            output = output.splitStack(1);

            IEntityContainer entityContainer = output.getCapability(ENTITY_CONTAINER, EnumFacing.DOWN);
            if(entityContainer == null || entityContainer.getEntityCount() >= entityContainer.getCapacity())
            {
                output = ItemStack.EMPTY;
            }
            else
            {
                entityContainer.addEntity(new EntityStack(outputEntity.serializeNBT()));
            }
        }

        //Spend the input items of the recipe.
        NonNullList<ItemStack> unpaidItems = activeRecipe.inputItems;
        for(ItemStack payment : unpaidItems)
        {
            int requiredPayment = payment.getCount();
            for(int i = 1; i < 10 && requiredPayment > 0; i++)
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

        if(!output.isEmpty())
        {
            ArrayList<ItemStack> exports = new ArrayList<>(1);
            exports.add(output);
            outputItems(exports);
        }
        else
        {
            outputEntity.setPosition(this.pos.getX() + 0.5, this.pos.getY() + 1.0, this.pos.getZ() + 0.5);
            world.spawnEntity(outputEntity);
        }
    }

    @Override
    public int temporalActions(int maxNumberOfActions)
    {
        if(canWork())
        {
            completeAction();
            return 1;
        }
        return 0;
    }
}
