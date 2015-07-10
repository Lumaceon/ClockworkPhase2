package lumaceon.mods.clockworkphase2.inventory.assemblyslot;

import lumaceon.mods.clockworkphase2.api.MainspringMetalRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AssemblySlotMainspringMetal extends AssemblySlotClockworkPhase
{
    public AssemblySlotMainspringMetal(ResourceLocation defaultTexture, float xCenter, float yCenter, float xSize, float ySize) {
        super(defaultTexture, xCenter, yCenter, xSize, ySize);
    }

    public AssemblySlotMainspringMetal(ResourceLocation defaultTexture, float xCenter, float yCenter) {
        super(defaultTexture, xCenter, yCenter);
    }

    public AssemblySlotMainspringMetal(ResourceLocation defaultTexture) {
        super(defaultTexture);
    }

    @Override
    public boolean isItemValid(ItemStack itemToInsert) {
        return MainspringMetalRegistry.getValue(itemToInsert) > 0;
    }
}
