package lumaceon.mods.clockworkphase2.block.itemblock;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.capabilities.EnergyStorageModular;
import lumaceon.mods.clockworkphase2.api.capabilities.ItemStackHandlerClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.capabilities.machinedata.IMachineDataHandler;
import lumaceon.mods.clockworkphase2.capabilities.machinedata.MachineDataHandler;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotItemSpecific;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockMachine extends ItemBlock implements IAssemblable
{
    @CapabilityInject(IEnergyStorage.class)
    static Capability<IEnergyStorage> ENERGY_STORAGE_CAPABILITY = null;

    public ItemBlockMachine(Block block) {
        super(block);
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        IEnergyStorage energyCap = stack.getCapability(ENERGY_STORAGE_CAPABILITY, EnumFacing.DOWN);
        if(energyCap != null)
        {
            InformationDisplay.addEnergyInformation(energyCap, tooltip);
        }
    }

    @Override
    public ResourceLocation getGUIBackground(ContainerAssemblyTable container) {
        return Textures.GUI.ASSEMBLY_TABLE_MACHINE_TEMPORAL_UPGRADE;
    }

    @Override
    public Slot[] getContainerSlots(IInventory inventory)
    {
        return new Slot[]
                {
                        new SlotItemSpecific(inventory, 0, 160, 41, ModItems.mainspring),
                        new SlotItemSpecific(inventory, 1, 125, 41, ModItems.clockworkCore),
                        new SlotItemSpecific(inventory, 2, 143, 41, ModItems.temporalMachineConduit)
                };
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new CapabilityProvider(stack);
    }

    private static class CapabilityProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        @CapabilityInject(IItemHandler.class)
        static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;
        @CapabilityInject(IMachineDataHandler.class)
        public static final Capability<IMachineDataHandler> MACHINE_DATA = null;

        EnergyStorageModular energyStorage;
        ItemStackHandlerClockworkConstruct inventory;
        MachineDataHandler machineData;

        public CapabilityProvider(ItemStack stack) {
            inventory = new ItemStackHandlerClockworkConstruct(4, stack);
            energyStorage = new EnergyStorageModular(1);
            machineData = new MachineDataHandler();
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability != null && capability == ENERGY_STORAGE_CAPABILITY || capability == ITEM_HANDLER_CAPABILITY || capability == MACHINE_DATA;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
        {
            if(capability == null)
                return null;

            if(capability == ENERGY_STORAGE_CAPABILITY)
                return ENERGY_STORAGE_CAPABILITY.cast(energyStorage);
            else if(capability == ITEM_HANDLER_CAPABILITY)
                return ITEM_HANDLER_CAPABILITY.cast(inventory);
            else if(capability == MACHINE_DATA)
                return MACHINE_DATA.cast(machineData);

            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("inventory", inventory.serializeNBT());
            tag.setInteger("energy", energyStorage.getEnergyStored());
            tag.setInteger("max_capacity", energyStorage.getMaxEnergyStored());
            tag.setTag("machine_data", machineData.serializeNBT());
            return tag;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            if(nbt.hasKey("inventory"))
                inventory.deserializeNBT((NBTTagCompound) nbt.getTag("inventory"));
            if(nbt.hasKey("max_capacity"))
                energyStorage.setMaxCapacity(nbt.getInteger("max_capacity"));
            if(nbt.hasKey("energy"))
                energyStorage.receiveEnergy(nbt.getInteger("energy"), false);
            if(nbt.hasKey("machine_data"))
                machineData.deserializeNBT((NBTTagCompound) nbt.getTag("machine_data"));
        }
    }
}
