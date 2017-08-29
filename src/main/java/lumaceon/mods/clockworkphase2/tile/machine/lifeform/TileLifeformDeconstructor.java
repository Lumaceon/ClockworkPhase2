package lumaceon.mods.clockworkphase2.tile.machine.lifeform;

import lumaceon.mods.clockworkphase2.capabilities.entitycontainer.IEntityContainer;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotNever;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class TileLifeformDeconstructor extends TileClockworkMachine
{
    @CapabilityInject(IEntityContainer.class)
    public static final Capability<IEntityContainer> ENTITY_CONTAINER = null;

    public TileLifeformDeconstructor() {
        super(11, 64, 50, 20000);
        this.slots = new Slot[]
                {
                    new SlotNonEmptyEntityContainerOrBrain(this, 0, 44, 18),
                    new SlotNever(this, 1, 115, 34),
                    new SlotNever(this, 2, 115, 34),
                    new SlotNever(this, 3, 115, 34),
                    new SlotNever(this, 4, 115, 34),
                    new SlotNever(this, 5, 115, 34),
                    new SlotNever(this, 6, 115, 34),
                    new SlotNever(this, 7, 115, 34),
                    new SlotNever(this, 8, 115, 34),
                    new SlotNever(this, 9, 115, 34),
                    new SlotNever(this, 10, 115, 34) //For emptied entity containers.
                };
        EXPORT_SLOTS = new int[] { 1 };
    }

    @Override
    public boolean canWork() {
        return false;
    }

    @Override
    public void completeAction() {

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
            if(!is.isEmpty() && is.hasCapability(ENTITY_CONTAINER, EnumFacing.DOWN))
            {
                IEntityContainer container = is.getCapability(ENTITY_CONTAINER, EnumFacing.DOWN);
                if(container != null && container.getEntityCount() > 0)
                {
                    return true;
                }
            }
            //TODO OR brain.
            return false;
        }
    }

}
