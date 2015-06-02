package lumaceon.mods.clockworkphase2.item.components.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.MainspringMetalRegistry;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyComponents;
import lumaceon.mods.clockworkphase2.api.item.IAssemblable;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IMainspring;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotMainspringMetal;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageMainspringButton;
import lumaceon.mods.clockworkphase2.util.AssemblyHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

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
    public InventoryAssemblyComponents createComponentInventory(IAssemblyContainer container)
    {
        InventoryAssemblyComponents inventory = new InventoryAssemblyComponents(container, 9, 64);
        AssemblyHelper.CREATE_COMPONENT_INVENTORY.loadStandardComponentInventory(container, inventory);
        return inventory;
    }

    @Override
    public Slot[] getContainerSlots(IAssemblyContainer container, IInventory inventory)
    {
        return new Slot[]
                {
                        new SlotMainspringMetal(inventory, 0, 0, 0),
                        new SlotMainspringMetal(inventory, 1, 18, 0),
                        new SlotMainspringMetal(inventory, 2, 36, 0),
                        new SlotMainspringMetal(inventory, 3, 54, 0),
                        new SlotMainspringMetal(inventory, 4, 72, 0),
                        new SlotMainspringMetal(inventory, 5, 90, 0),
                        new SlotMainspringMetal(inventory, 6, 108, 0),
                        new SlotMainspringMetal(inventory, 7, 126, 0),
                };
    }

    @Override
    public void onComponentChange(IAssemblyContainer container) {}

    @Override
    public void saveComponentInventory(IAssemblyContainer container) {
        AssemblyHelper.SAVE_COMPONENT_INVENTORY.saveNewComponentInventory(container);
    }

    @Override
    public void initButtons(List buttonList, IAssemblyContainer container, int guiLeft, int guiTop) {
        ClockworkPhase2.proxy.initButtons(0, buttonList, container, guiLeft, guiTop);
    }

    @Override
    public void onButtonActivated(int buttonID, List buttonList) {
        PacketHandler.INSTANCE.sendToServer(new MessageMainspringButton());
    }

    public void onButtonServer(IAssemblyContainer container)
    {
        ItemStack mainItem = container.getMainInventory().getStackInSlot(0);
        if(mainItem != null && mainItem.getItem() instanceof ItemMainspring)
        {
            int baseValues = 0;
            InventoryAssemblyComponents components = container.getComponentInventory();
            for(int n = 0; n < components.getSizeInventory(); n++)
            {
                ItemStack item = components.getStackInSlot(n);
                if(item != null)
                {
                    baseValues += MainspringMetalRegistry.getValue(item);
                }
            }
            if(baseValues > 0)
            {
                int currentMaxTension = NBTHelper.INT.get(mainItem, NBTTags.MAX_TENSION);
                int newMaxTension = currentMaxTension + baseValues;
                if(currentMaxTension == getMaxSize(mainItem))
                    return;
                if(newMaxTension > getMaxSize(mainItem))
                    newMaxTension = getMaxSize(mainItem);
                if(mainItem.getMaxDamage() == 0 || getMaxSize(mainItem) / mainItem.getMaxDamage() == 0)
                    mainItem.setItemDamage(0);
                else
                    mainItem.setItemDamage(mainItem.getMaxDamage() - newMaxTension / (getMaxSize(mainItem) / mainItem.getMaxDamage()));

                NBTHelper.INT.set(mainItem, NBTTags.MAX_TENSION,  newMaxTension);
                for(int n = 0; n < components.getSizeInventory(); n++)
                {
                    components.setInventorySlotContents(n, null);
                }
            }
        }
    }

    @Override
    public ResourceLocation getBackgroundTexture(IAssemblyContainer container) {
        return Textures.GUI.DEFAULT_ASSEMBLY_TABLE;
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
