package lumaceon.mods.clockworkphase2.inventory.assemblyslot;

import lumaceon.mods.clockworkphase2.api.item.ITemporalToolModule;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AssemblySlotTemporalModule extends AssemblySlotClockworkPhase
{
    public AssemblySlotTemporalModule(ResourceLocation defaultTexture, float centerX, float centerY) {
        super(defaultTexture, centerX, centerY);
    }

    @Override
    public boolean isItemValid(ItemStack itemToInsert) {
        return itemToInsert.getItem() instanceof ITemporalToolModule;
    }
}
