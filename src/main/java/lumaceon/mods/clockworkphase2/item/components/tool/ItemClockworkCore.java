package lumaceon.mods.clockworkphase2.item.components.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.assembly.AssemblySlot;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyComponents;
import lumaceon.mods.clockworkphase2.api.item.IAssemblable;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.inventory.assemblyslot.AssemblySlotClockworkComponent;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.util.AssemblyHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemClockworkCore extends ItemClockworkPhase implements IAssemblable, IClockwork
{
    public ItemClockworkCore(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addClockworkInformation(is, list);
    }

    @Override
    public AssemblySlot[] initializeSlots(ItemStack assemblyItem)
    {
        AssemblySlot[] slots = new AssemblySlot[]
                {
                        new AssemblySlotClockworkComponent(Textures.ITEM.WOOD_GEAR, 0.3F, 0.3F),
                        new AssemblySlotClockworkComponent(Textures.ITEM.WOOD_GEAR, 0.5F, 0.3F),
                        new AssemblySlotClockworkComponent(Textures.ITEM.WOOD_GEAR, 0.7F, 0.3F),
                        new AssemblySlotClockworkComponent(Textures.ITEM.WOOD_GEAR, 0.3F, 0.5F),
                        new AssemblySlotClockworkComponent(Textures.ITEM.WOOD_GEAR, 0.5F, 0.5F),
                        new AssemblySlotClockworkComponent(Textures.ITEM.WOOD_GEAR, 0.7F, 0.5F),
                        new AssemblySlotClockworkComponent(Textures.ITEM.WOOD_GEAR, 0.3F, 0.7F),
                        new AssemblySlotClockworkComponent(Textures.ITEM.WOOD_GEAR, 0.5F, 0.7F),
                        new AssemblySlotClockworkComponent(Textures.ITEM.WOOD_GEAR, 0.7F, 0.7F)
                };
        AssemblyHelper.INITIALIZE_SLOTS.loadStandardComponentInventory(assemblyItem, slots);
        return slots;
    }

    @Override
    public void onComponentChange(ItemStack workItem, AssemblySlot[] slots) {
        AssemblyHelper.COMPONENT_CHANGE.assembleClockwork(workItem, slots);
    }

    @Override
    public void saveComponentInventory(ItemStack workItem, AssemblySlot[] slots) {
        AssemblyHelper.SAVE_COMPONENT_INVENTORY.saveNewComponentInventory(workItem, slots);
    }

    /*@Override
    public Slot[] initializeSlots(IAssemblyContainer container, IInventory inventory)
    {
        return new Slot[]
                {
                        new SlotClockworkComponent(inventory, 0, 0, 0),
                        new SlotClockworkComponent(inventory, 1, 0, 18),
                        new SlotClockworkComponent(inventory, 2, 0, 36),
                        new SlotClockworkComponent(inventory, 3, 0, 54),
                        new SlotClockworkComponent(inventory, 4, 0, 70),
                        new SlotClockworkComponent(inventory, 5, 0, 100),
                        new SlotClockworkComponent(inventory, 6, 18, 0),
                        new SlotClockworkComponent(inventory, 7, 18, 18),
                        new SlotClockworkComponent(inventory, 8, 18, 36),
                        new SlotClockworkComponent(inventory, 9, 18, 54)
                };
    }*/

    /*@Override
    public void initButtons(List buttonList, IAssemblyContainer container, int guiLeft, int guiTop) {}

    @Override
    public void onButtonActivated(int buttonID, List buttonList) {}

    @Override
    public ResourceLocation getBackgroundTexture(IAssemblyContainer container) {
        return Textures.GUI.DEFAULT_ASSEMBLY_TABLE;
    }*/

    @Override
    public int getQuality(ItemStack item) {
        return ClockworkHelper.getQuality(item);
    }

    @Override
    public int getSpeed(ItemStack item) {
        return ClockworkHelper.getSpeed(item);
    }

    @Override
    public int getMemory(ItemStack item) {
        return ClockworkHelper.getMemory(item);
    }
}
