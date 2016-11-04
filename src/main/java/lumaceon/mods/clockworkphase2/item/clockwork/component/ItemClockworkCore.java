package lumaceon.mods.clockworkphase2.item.clockwork.component;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyTableComponents;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import lumaceon.mods.clockworkphase2.api.util.AssemblyHelper;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotClockworkComponent;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemClockworkCore extends ItemClockworkPhase implements IAssemblable, IClockwork
{
    public ItemClockworkCore(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addClockworkInformation(is, list);
    }

    @Override
    public int getQuality(ItemStack item) {
        return ClockworkHelper.getQuality(item);
    }

    @Override
    public int getSpeed(ItemStack item) {
        return ClockworkHelper.getSpeed(item);
    }

    @Override
    public ResourceLocation getGUIBackground(ContainerAssemblyTable container) {
        return Textures.GUI.ASSEMBLY_TABLE_GEARS;
    }

    @Override
    public InventoryAssemblyTableComponents getGUIInventory(ContainerAssemblyTable container) {
        InventoryAssemblyTableComponents inventory = new InventoryAssemblyTableComponents(container, 10, 1);
        AssemblyHelper.GET_GUI_INVENTORY.loadStandardComponentInventory(container, inventory);
        return inventory;
    }

    @Override
    public Slot[] getContainerSlots(IInventory inventory) {
        return new Slot[]
                {
                        new SlotClockworkComponent(inventory, 0, 61, 106),
                        new SlotClockworkComponent(inventory, 1, 79, 106),
                        new SlotClockworkComponent(inventory, 2, 97, 106),
                        new SlotClockworkComponent(inventory, 3, 115, 106),
                        new SlotClockworkComponent(inventory, 4, 133, 106),
                        new SlotClockworkComponent(inventory, 5, 151, 106),
                        new SlotClockworkComponent(inventory, 6, 169, 106),
                        new SlotClockworkComponent(inventory, 7, 187, 106),
                        new SlotClockworkComponent(inventory, 8, 205, 106),
                        new SlotClockworkComponent(inventory, 9, 223, 106)
                };
    }

    @Override
    public void saveComponentInventory(ContainerAssemblyTable container) {
        AssemblyHelper.SAVE_COMPONENT_INVENTORY.saveComponentInventory(container);
    }

    @Override
    public void onInventoryChange(ContainerAssemblyTable container) {
        AssemblyHelper.ON_INVENTORY_CHANGE.assembleClockworkCore(container);
    }
}
