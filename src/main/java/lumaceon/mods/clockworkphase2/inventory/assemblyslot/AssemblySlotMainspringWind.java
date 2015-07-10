package lumaceon.mods.clockworkphase2.inventory.assemblyslot;

import lumaceon.mods.clockworkphase2.api.assembly.AssemblySlot;
import lumaceon.mods.clockworkphase2.item.components.tool.ItemMainspring;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class AssemblySlotMainspringWind extends AssemblySlotClockworkPhase
{
    public AssemblySlotMainspringWind(ResourceLocation defaultTexture) {
        super(defaultTexture);
    }

    public AssemblySlotMainspringWind(ResourceLocation defaultTexture, float xCenter, float yCenter) {
        super(defaultTexture, xCenter, yCenter);
    }

    public AssemblySlotMainspringWind(ResourceLocation defaultTexture, float xCenter, float yCenter, float xSize, float ySize) {
        super(defaultTexture, xCenter, yCenter, xSize, ySize);
    }

    public void onClick(World world, int x, int y, int z, EntityPlayer player, ItemStack heldItem, ItemStack workItem, AssemblySlot[] slots)
    {
        if(workItem.getItem() instanceof ItemMainspring)
        {
            ((ItemMainspring) workItem.getItem()).addMetal(slots, workItem);
            ((ItemMainspring) workItem.getItem()).onComponentChange(workItem, slots);
            ((ItemMainspring) workItem.getItem()).saveComponentInventory(workItem, slots);
        }
    }

    @Override
    public boolean isItemValid(ItemStack itemToInsert) {
        return false;
    }

    @Override
    public ResourceLocation getMouseOverIcon(EntityPlayer player, ItemStack workItem, ItemStack heldItem) {
        return null;
    }
}
