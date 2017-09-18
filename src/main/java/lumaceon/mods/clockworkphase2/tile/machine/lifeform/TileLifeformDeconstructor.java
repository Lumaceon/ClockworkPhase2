package lumaceon.mods.clockworkphase2.tile.machine.lifeform;

import lumaceon.mods.clockworkphase2.capabilities.entitycontainer.IEntityContainer;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.List;

public class TileLifeformDeconstructor extends TileClockworkMachine
{
    @CapabilityInject(IEntityContainer.class)
    public static final Capability<IEntityContainer> ENTITY_CONTAINER = null;

    public TileLifeformDeconstructor() {
        super(11, 64, 50, 20000);
        this.slots = new Slot[]
                {
                    new SlotNonEmptyEntityContainerOrBrain(this, 0, 100, 5),
                    new SlotNever(this, 1, 28, 57),
                    new SlotNever(this, 2, 46, 57),
                    new SlotNever(this, 3, 64, 57),
                    new SlotNever(this, 4, 28, 75),
                    new SlotNever(this, 5, 46, 75),
                    new SlotNever(this, 6, 64, 75),
                    new SlotNever(this, 7, 82, 75),
                    new SlotNever(this, 8, 100, 75),
                    new SlotNever(this, 9, 118, 75),
                    new SlotNever(this, 10, 136, 57),
                    new SlotNever(this, 11, 154, 57),
                    new SlotNever(this, 12, 172, 57),
                    new SlotNever(this, 13, 136, 75),
                    new SlotNever(this, 14, 154, 75),
                    new SlotNever(this, 15, 172, 75),
                    new SlotNever(this, 16, 154, 5) //For emptied entity containers.
                };
        EXPORT_SLOTS = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };
    }

    @Override
    public boolean canWork()
    {
        List<Entity> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.pos.getX() - 2, this.pos.getY(), this.pos.getZ() - 2, + this.pos.getX() + 3, this.pos.getY() + 5, this.pos.getZ() + 3));

        for(Entity e : entities)
        {
            if(!(e instanceof EntityPlayer) && !e.isDead)
            {
                return true; //TODO account for exports.
            }
        }

        //Alternatively, entity containers.
        ItemStack stack = this.getStackInSlot(0);
        if(!stack.isEmpty())
        {
            IEntityContainer entityContainer = stack.getCapability(ENTITY_CONTAINER, EnumFacing.DOWN);
            if(entityContainer != null && entityContainer.getCapacity() > entityContainer.getEntityCount())
            {
                return this.exportItem(stack.copy(), new int[] { 16 }, true).isEmpty();
            }
            else if(stack.getItem().equals(ModItems.mobBrain))
            {
                return true; //TODO account for exports.
            }
        }

        return false;
    }

    @Override
    public void completeAction()
    {
        List<Entity> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.pos.getX() - 2, this.pos.getY(), this.pos.getZ() - 2, + this.pos.getX() + 3, this.pos.getY() + 5, this.pos.getZ() + 3));

        Entity entityForDeconstruction = null;
        for(Entity e : entities)
        {
            if(!(e instanceof EntityPlayer) && !e.isDead)
            {
                entityForDeconstruction = e;
                break;
            }
        }
    }

    @Override
    public int temporalActions(int maxNumberOfActions) {
        return 0;
    }

    public class SlotNonEmptyEntityContainerOrBrain extends Slot
    {
        public SlotNonEmptyEntityContainerOrBrain(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack is)
        {
            if(!is.isEmpty())
            {
                if(is.hasCapability(ENTITY_CONTAINER, EnumFacing.DOWN))
                {
                    IEntityContainer container = is.getCapability(ENTITY_CONTAINER, EnumFacing.DOWN);
                    if(container != null && container.getEntityCount() > 0)
                    {
                        return true;
                    }
                }
                else if(is.getItem().equals(ModItems.mobBrain))
                {
                    return true;
                }
            }
            return false;
        }
    }
}
