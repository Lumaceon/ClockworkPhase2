package lumaceon.mods.clockworkphase2.inventory.assemblyslot;

import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AssemblySlotClockworkComponent extends AssemblySlotClockworkPhase
{
    public AssemblySlotClockworkComponent(ResourceLocation defaultTexture, float x, float y) {
        super(defaultTexture, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack itemToInsert) {
        return itemToInsert.getItem() instanceof IClockworkComponent;
    }
}
