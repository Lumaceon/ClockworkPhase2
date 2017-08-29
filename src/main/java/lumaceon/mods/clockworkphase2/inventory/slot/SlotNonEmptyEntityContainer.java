package lumaceon.mods.clockworkphase2.inventory.slot;

import lumaceon.mods.clockworkphase2.capabilities.entitycontainer.IEntityContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class SlotNonEmptyEntityContainer extends Slot
{
    @CapabilityInject(IEntityContainer.class)
    public static final Capability<IEntityContainer> ENTITY_CONTAINER = null;

    public SlotNonEmptyEntityContainer(IInventory inventoryIn, int index, int xPosition, int yPosition) {
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
        return false;
    }
}
