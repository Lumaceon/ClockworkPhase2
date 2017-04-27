package lumaceon.mods.clockworkphase2.item.clockwork.component;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.MainspringMetalRegistry;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblableButtons;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IMainspring;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotMainspringMetal;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageMainspringButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMainspring extends ItemClockworkPhase implements IAssemblableButtons, IMainspring
{
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

    public int maxTension = ConfigValues.MAX_MAINSPRING_TENSION;

    public ItemMainspring(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addMainspringInformation(is, list);
    }

    @Override
    public int getMaximumPossibleCapacity(ItemStack item) {
        return maxTension;
    }

    @Override
    public int getCurrentCapacity(ItemStack item)
    {
        IItemHandler cap = item.getCapability(ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        if(cap != null && cap instanceof ItemStackHandlerMainspring)
            return ((ItemStackHandlerMainspring) cap).getCapacity();
        return 0;
    }

    @Override
    public ResourceLocation getGUIBackground(ContainerAssemblyTable container) {
        return Textures.GUI.ASSEMBLY_TABLE_MAINSPRING_ADD;
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
            IMainspring mainspring = (IMainspring) mainItem.getItem();
            int baseValues = 0;
            for(int n = 0; n < container.componentInventory.getSizeInventory(); n++)
            {
                ItemStack item = container.componentInventory.getStackInSlot(n);
                if(item != null)
                    baseValues += MainspringMetalRegistry.getValue(item);
            }
            if(baseValues > 0)
            {
                IItemHandler handler = mainItem.getCapability(ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
                if(handler != null && handler instanceof ItemStackHandlerMainspring)
                {
                    int currentMaxTension = ((ItemStackHandlerMainspring) handler).getCapacity();
                    int newMaxTension = currentMaxTension + baseValues;
                    if(currentMaxTension == mainspring.getMaximumPossibleCapacity(mainItem))
                        return;
                    if(newMaxTension > mainspring.getMaximumPossibleCapacity(mainItem))
                        newMaxTension = mainspring.getMaximumPossibleCapacity(mainItem);
                    if(mainItem.getMaxDamage() == 0 || mainspring.getMaximumPossibleCapacity(mainItem) / mainItem.getMaxDamage() == 0)
                        mainItem.setItemDamage(0);
                    else
                        mainItem.setItemDamage(mainItem.getMaxDamage() - newMaxTension / (mainspring.getMaximumPossibleCapacity(mainItem) / mainItem.getMaxDamage()));

                    ((ItemStackHandlerMainspring) handler).setCapacity(newMaxTension);
                    for(int n = 0; n < container.componentInventory.getSizeInventory(); n++)
                        container.componentInventory.setInventorySlotContents(n, null);
                }
            }
        }
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new MainspringCapabilityProvider();
    }

    private static class MainspringCapabilityProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        @CapabilityInject(IItemHandler.class)
        static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

        ItemStackHandlerMainspring inventory = new ItemStackHandlerMainspring(8);

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability != null && capability == ITEM_HANDLER_CAPABILITY;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability != null && capability == ITEM_HANDLER_CAPABILITY)
                return ITEM_HANDLER_CAPABILITY.cast(inventory);
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("inventory", inventory.serializeNBT());
            return tag;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            inventory.deserializeNBT((NBTTagCompound) nbt.getTag("inventory"));
        }
    }

    private static class ItemStackHandlerMainspring extends ItemStackHandler
    {
        int capacity = 1000;

        private ItemStackHandlerMainspring(int size) {
            super(size);
        }

        public int getCapacity() {
            return capacity;
        }

        public void setCapacity(int capacity) {
            this.capacity = capacity;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound nbt = super.serializeNBT();
            nbt.setInteger("CP2_MS_capacity", capacity);
            return nbt;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            super.deserializeNBT(nbt);
            if(nbt.hasKey("CP2_MS_capacity"))
                capacity = nbt.getInteger("CP2_MS_capacity");
        }
    }
}
