package lumaceon.mods.clockworkphase2.inventory.assemblyslot;

import lumaceon.mods.clockworkphase2.item.components.tool.ItemClockworkCore;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AssemblySlotClockworkCore extends AssemblySlotClockworkPhase
{
    public AssemblySlotClockworkCore(ResourceLocation defaultTexture) {
        super(defaultTexture);
    }
    public AssemblySlotClockworkCore(ResourceLocation defaultTexture, float xLoc, float yLoc) {
        super(defaultTexture, xLoc, yLoc);
    }

    @Override
    public boolean isItemValid(ItemStack item) {
        return item != null && item.getItem() instanceof ItemClockworkCore;
    }
}
