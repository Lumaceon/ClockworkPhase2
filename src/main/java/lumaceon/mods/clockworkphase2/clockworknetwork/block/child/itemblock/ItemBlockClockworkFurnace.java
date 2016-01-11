package lumaceon.mods.clockworkphase2.clockworknetwork.block.child.itemblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyTableComponents;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.util.AssemblyHelper;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotItemSpecific;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ItemBlockClockworkFurnace extends ItemBlock implements IAssemblable, IClockworkConstruct
{
    public ItemBlockClockworkFurnace(Block p_i45328_1_) {
        super(p_i45328_1_);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addClockworkConstructInformation(is, player, list, flag);
    }

    @Override
    public ResourceLocation getGUIBackground(ContainerAssemblyTable container) {
        return Textures.GUI.ASSEMBLY_TABLE;
    }

    @Override
    public InventoryAssemblyTableComponents getGUIInventory(ContainerAssemblyTable container) {
        InventoryAssemblyTableComponents inventory = new InventoryAssemblyTableComponents(container, 2, 1);
        AssemblyHelper.GET_GUI_INVENTORY.loadStandardComponentInventory(container, inventory);
        return inventory;
    }

    @Override
    public Slot[] getContainerSlots(IInventory inventory)
    {
        return new Slot[]
                {
                        new SlotItemSpecific(inventory, 0, 120, 30, ModItems.mainspring),
                        new SlotItemSpecific(inventory, 1, 120, 54, ModItems.clockworkCore),
                };
    }

    @Override
    public void saveComponentInventory(ContainerAssemblyTable container) {
        AssemblyHelper.SAVE_COMPONENT_INVENTORY.saveComponentInventory(container);
    }

    @Override
    public void onInventoryChange(ContainerAssemblyTable container) {
        AssemblyHelper.ON_INVENTORY_CHANGE.assembleClockworkConstruct(container, 0, 1);
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
    public int getTier(ItemStack item) {
        return ClockworkHelper.getTier(item);
    }

    @Override
    public int getTension(ItemStack item) {
        return ClockworkHelper.getTension(item);
    }

    @Override
    public int getMaxTension(ItemStack item) {
        return ClockworkHelper.getMaxTension(item);
    }

    @Override
    public void setTension(ItemStack item, int tension) {
        ClockworkHelper.setTension(item, tension);
    }

    @Override
    public void setTier(ItemStack item, int tier) {
        ClockworkHelper.setTier(item, tier);
    }

    @Override
    public int addTension(ItemStack item, int tension) {
        return ClockworkHelper.addTension(item, tension);
    }

    @Override
    public int consumeTension(ItemStack item, int tension) {
        return ClockworkHelper.consumeTension(item, tension);
    }

    @Override
    public void addConstructInformation(ItemStack item, EntityPlayer player, List list)
    {
        int quality = getQuality(item);
        int speed = getSpeed(item);

        if(speed <= 0 || quality <= 0 || ((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50)) == 0)
        {
            list.add(Colors.RED + "NON-FUNCTIONAL");
            return;
        }

        int timePerSmelt = 10000 / ((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50));
        if(timePerSmelt >= 20)
            list.add(Colors.WHITE + "Time Per Smelt: " + Colors.GOLD + TimeConverter.parseNumber(timePerSmelt, 1));
        else if(timePerSmelt != 1)
            list.add(Colors.WHITE + "Time Per Smelt: " + Colors.GOLD + timePerSmelt + " Ticks");
        else
            list.add(Colors.WHITE + "Time Per Smelt: " + Colors.GOLD + "1 Tick");
        list.add(Colors.WHITE + "~Tension Used Per Operation: " + Colors.GOLD + (ClockworkHelper.getTensionCostFromStats((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50), quality, speed) / 50) * timePerSmelt);
        list.add(Colors.WHITE + "~Tension Used Per Second: " + Colors.GOLD + (ClockworkHelper.getTensionCostFromStats((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 50), quality, speed) / 50) * 20);
    }
}
