package lumaceon.mods.clockworkphase2.item.clockwork.component;

import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.capabilities.ItemStackHandlerClockworkCore;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotClockworkComponent;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class ItemClockworkCore extends ItemClockworkPhase implements IAssemblable, IClockwork
{
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

    public ItemClockworkCore(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        InformationDisplay.addClockworkInformation(stack, tooltip);
    }

    @Override
    @Nullable
    public NBTTagCompound getNBTShareTag(ItemStack stack)
    {
        NBTTagCompound nbt = stack.getTagCompound();
        if(nbt == null)
        {
            nbt = new NBTTagCompound();
        }

        ItemStackHandlerClockworkCore cw = (ItemStackHandlerClockworkCore) stack.getCapability(ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        if(cw != null)
        {
            nbt.setInteger("cw_speed", cw.getSpeed());
            nbt.setInteger("cw_quality", cw.getQuality());
            nbt.setInteger("cw_tier", cw.getTier());
        }

        return nbt;
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
    public ResourceLocation getGUIBackground(ContainerAssemblyTable container) {
        return Textures.GUI.ASSEMBLY_TABLE_GEARS;
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
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new ClockworkCoreCapabilityProvider();
    }

    private static class ClockworkCoreCapabilityProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        @CapabilityInject(IItemHandler.class)
        static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

        ItemStackHandlerClockworkCore inventory = new ItemStackHandlerClockworkCore(10);

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
}
