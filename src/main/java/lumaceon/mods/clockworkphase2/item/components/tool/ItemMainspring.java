package lumaceon.mods.clockworkphase2.item.components.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.MainspringMetalRegistry;
import lumaceon.mods.clockworkphase2.api.assembly.AssemblySlot;
import lumaceon.mods.clockworkphase2.api.item.IAssemblable;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IMainspring;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.inventory.assemblyslot.AssemblySlotMainspringMetal;
import lumaceon.mods.clockworkphase2.inventory.assemblyslot.AssemblySlotMainspringWind;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.util.AssemblyHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemMainspring extends ItemClockworkPhase implements IAssemblable, IMainspring
{
    public int maxTension = 1000000;

    public ItemMainspring(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addMainspringInformation(is, list);
    }

    @Override
    public AssemblySlot[] initializeSlots(ItemStack workItem)
    {
        AssemblySlot[] slots = new AssemblySlot[]
                {
                        new AssemblySlotMainspringMetal(Textures.ITEM.CLOCKWORK_CORE, 0.3F, 0.3F),
                        new AssemblySlotMainspringMetal(Textures.ITEM.CLOCKWORK_CORE, 0.5F, 0.3F),
                        new AssemblySlotMainspringMetal(Textures.ITEM.CLOCKWORK_CORE, 0.7F, 0.3F),
                        new AssemblySlotMainspringMetal(Textures.ITEM.CLOCKWORK_CORE, 0.3F, 0.5F),
                        new AssemblySlotMainspringMetal(Textures.ITEM.CLOCKWORK_CORE, 0.7F, 0.5F),
                        new AssemblySlotMainspringMetal(Textures.ITEM.CLOCKWORK_CORE, 0.3F, 0.7F),
                        new AssemblySlotMainspringMetal(Textures.ITEM.CLOCKWORK_CORE, 0.5F, 0.7F),
                        new AssemblySlotMainspringMetal(Textures.ITEM.CLOCKWORK_CORE, 0.7F, 0.7F),
                        new AssemblySlotMainspringWind(Textures.MISC.VALID, 0.5F, 0.5F, 2.0F, 2.0F)
                };
        AssemblyHelper.INITIALIZE_SLOTS.loadStandardComponentInventory(workItem, slots);
        return slots;
    }

    @Override
    public void onComponentChange(ItemStack workItem, AssemblySlot[] slots) {}

    @Override
    public void saveComponentInventory(ItemStack workItem, AssemblySlot[] slots) {
        AssemblyHelper.SAVE_COMPONENT_INVENTORY.saveNewComponentInventory(workItem, slots);
    }

    public void addMetal(AssemblySlot[] slots, ItemStack workItem)
    {
        int baseValues = 0;
        for(AssemblySlot slot : slots)
        {
            ItemStack item = slot.getItemStack();
            if(item != null)
                baseValues += MainspringMetalRegistry.getValue(item);
        }
        if(baseValues > 0)
        {
            int currentMaxTension = NBTHelper.INT.get(workItem, NBTTags.MAX_TENSION);
            int newMaxTension = currentMaxTension + baseValues;
            if(currentMaxTension == getMaxSize(workItem))
                return;
            if(newMaxTension > getMaxSize(workItem))
                newMaxTension = getMaxSize(workItem);
            if(workItem.getMaxDamage() == 0 || getMaxSize(workItem) / workItem.getMaxDamage() == 0)
                workItem.setItemDamage(0);
            else
                workItem.setItemDamage(workItem.getMaxDamage() - newMaxTension / (getMaxSize(workItem) / workItem.getMaxDamage()));

            NBTHelper.INT.set(workItem, NBTTags.MAX_TENSION,  newMaxTension);
            for(AssemblySlot slot : slots)
                if(slot.getItemStack() != null)
                    slot.setItemStack(null);
        }
    }

    @Override
    public int getMaxSize(ItemStack item) {
        return maxTension;
    }

    @Override
    public int getTension(ItemStack item) {
        return ClockworkHelper.getMaxTension(item);
    }
}
