package lumaceon.mods.clockworkphase2.item.components;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyComponents;
import lumaceon.mods.clockworkphase2.api.item.IAssemblable;
import lumaceon.mods.clockworkphase2.api.item.IClockwork;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.client.gui.slot.SlotClockworkComponent;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.util.AssemblyHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ItemClockworkCore extends ItemClockworkPhase implements IAssemblable, IClockwork
{
    public ItemClockworkCore(int maxStack, int maxDamage, String unlocalizedName)
    {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addClockworkInformation(is, list);
    }

    @Override
    public InventoryAssemblyComponents createComponentInventory(IAssemblyContainer container)
    {
        InventoryAssemblyComponents inventory = new InventoryAssemblyComponents(container, 10, 1);
        AssemblyHelper.CREATE_COMPONENT_INVENTORY.loadStandardComponentInventory(container, inventory);
        return inventory;
    }

    @Override
    public Slot[] getContainerSlots(IInventory inventory)
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
    }

    @Override
    public void onComponentChange(IAssemblyContainer container) {
        AssemblyHelper.COMPONENT_CHANGE.assembleClockwork(container);
    }

    @Override
    public void saveComponentInventory(IAssemblyContainer container) {
        AssemblyHelper.SAVE_COMPONENT_INVENTORY.saveNewComponentInventory(container);
    }

    @Override
    public void initButtons(List buttonList, IAssemblyContainer container, int guiLeft, int guiTop) {}

    @Override
    public void onButtonActivated(int buttonID, List buttonList) {}

    @Override
    public ResourceLocation getBackgroundTexture(IAssemblyContainer container) {
        return Textures.GUI.DEFAULT_ASSEMBLY_TABLE;
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
    public int getMemory(ItemStack item) {
        return ClockworkHelper.getMemory(item);
    }
}
