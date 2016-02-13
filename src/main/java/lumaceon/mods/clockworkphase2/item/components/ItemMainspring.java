package lumaceon.mods.clockworkphase2.item.components;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.MainspringMetalRegistry;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblableButtons;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyTableComponents;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IMainspring;
import lumaceon.mods.clockworkphase2.api.util.AssemblyHelper;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotMainspringMetal;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageMainspringButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemMainspring extends ItemClockworkPhase implements IAssemblableButtons, IMainspring
{
    public int maxTension = 1000000;

    public ItemMainspring(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addMainspringInformation(is, list);
    }

    @Override
    public int getMaxSize(ItemStack item) {
        return maxTension;
    }

    @Override
    public int getTension(ItemStack item) {
        return ClockworkHelper.getMaxTension(item);
    }

    @Override
    public ResourceLocation getGUIBackground(ContainerAssemblyTable container) {
        return Textures.GUI.ASSEMBLY_TABLE_MAINSPRING;
    }

    @Override
    public InventoryAssemblyTableComponents getGUIInventory(ContainerAssemblyTable container) {
        InventoryAssemblyTableComponents inventory = new InventoryAssemblyTableComponents(container, 8, 1);
        AssemblyHelper.GET_GUI_INVENTORY.loadStandardComponentInventory(container, inventory);
        return inventory;
    }

    @Override
    public Slot[] getContainerSlots(IInventory inventory) {
        return new Slot[]
                {
                        new SlotMainspringMetal(inventory, 0, 124, 50),
                        new SlotMainspringMetal(inventory, 1, 142, 50),
                        new SlotMainspringMetal(inventory, 2, 160, 50),
                        new SlotMainspringMetal(inventory, 3, 160, 68),
                        new SlotMainspringMetal(inventory, 4, 160, 86),
                        new SlotMainspringMetal(inventory, 5, 142, 86),
                        new SlotMainspringMetal(inventory, 6, 124, 86),
                        new SlotMainspringMetal(inventory, 7, 124, 68),
                };
    }

    @Override
    public void saveComponentInventory(ContainerAssemblyTable container) {
        AssemblyHelper.SAVE_COMPONENT_INVENTORY.saveComponentInventory(container);
    }

    @Override
    public void onInventoryChange(ContainerAssemblyTable container) {} //NOOP

    @Override
    public void initButtons(List buttonList, ContainerAssemblyTable container, int guiLeft, int guiTop)
    {
        ClockworkPhase2.proxy.initializeButtonsViaProxy(0, buttonList, container, guiLeft, guiTop);
    }

    @Override
    public void onButtonClicked(int buttonID, List buttonList) {
        PacketHandler.INSTANCE.sendToServer(new MessageMainspringButton());
    }

    public static void onButtonClickedServer(ContainerAssemblyTable container)
    {
        ItemStack mainItem = container.mainInventory.getStackInSlot(0);
        if(mainItem != null && mainItem.getItem() instanceof ItemMainspring)
        {
            int baseValues = 0;
            for(int n = 0; n < container.componentInventory.getSizeInventory(); n++)
            {
                ItemStack item = container.componentInventory.getStackInSlot(n);
                if(item != null)
                    baseValues += MainspringMetalRegistry.getValue(item);
            }
            if(baseValues > 0)
            {
                int currentMaxTension = NBTHelper.INT.get(mainItem, NBTTags.MAX_TENSION);
                int newMaxTension = currentMaxTension + baseValues;
                if(currentMaxTension == Defaults.TENSION.maxMainspringTension)
                    return;
                if(newMaxTension > Defaults.TENSION.maxMainspringTension)
                    newMaxTension = Defaults.TENSION.maxMainspringTension;
                if(mainItem.getMaxDamage() == 0 || Defaults.TENSION.maxMainspringTension / mainItem.getMaxDamage() == 0)
                    mainItem.setItemDamage(0);
                else
                    mainItem.setItemDamage(mainItem.getMaxDamage() - newMaxTension / (Defaults.TENSION.maxMainspringTension / mainItem.getMaxDamage()));

                NBTHelper.INT.set(mainItem, NBTTags.MAX_TENSION,  newMaxTension);
                for(int n = 0; n < container.componentInventory.getSizeInventory(); n++)
                    container.componentInventory.setInventorySlotContents(n, null);
            }
        }
    }
}
