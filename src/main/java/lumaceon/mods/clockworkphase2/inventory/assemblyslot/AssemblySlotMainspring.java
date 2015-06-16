package lumaceon.mods.clockworkphase2.inventory.assemblyslot;

import lumaceon.mods.clockworkphase2.api.assembly.AssemblySlot;
import lumaceon.mods.clockworkphase2.item.components.tool.ItemMainspring;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AssemblySlotMainspring extends AssemblySlot
{
    public AssemblySlotMainspring(ResourceLocation defaultTexture) {
        super(defaultTexture);
    }
    public AssemblySlotMainspring(ResourceLocation defaultTexture, float xLoc, float yLoc) {
        super(defaultTexture, xLoc, yLoc);
    }

    @Override
    public boolean isItemValid(ItemStack item) {
        return item != null && item.getItem() instanceof ItemMainspring;
    }
}
