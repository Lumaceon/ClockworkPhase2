package lumaceon.mods.clockworkphase2.inventory.assemblyslot;

import lumaceon.mods.clockworkphase2.api.assembly.AssemblySlot;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public abstract class AssemblySlotClockworkPhase extends AssemblySlot
{
    public AssemblySlotClockworkPhase(ResourceLocation defaultTexture) {
        super(defaultTexture);
    }
    public AssemblySlotClockworkPhase(ResourceLocation defaultTexture, float xCenter, float yCenter) {
        super(defaultTexture, xCenter, yCenter);
    }
    public AssemblySlotClockworkPhase(ResourceLocation defaultTexture, float xCenter, float yCenter, float xSize, float ySize) {
        super(defaultTexture, xCenter, yCenter, xSize, ySize);
    }

    public ResourceLocation getMouseOverIcon(EntityPlayer player, ItemStack workItem, ItemStack heldItem)
    {
        if(heldItem != null && isItemValid(heldItem))
            return Textures.MISC.VALID;
        else
            return Textures.MISC.INVALID;
    }
}
