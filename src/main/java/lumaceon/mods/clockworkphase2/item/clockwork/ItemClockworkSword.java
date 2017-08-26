package lumaceon.mods.clockworkphase2.item.clockwork;

import com.google.common.collect.Multimap;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.capabilities.EnergyStorageModular;
import lumaceon.mods.clockworkphase2.api.capabilities.ItemStackHandlerClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockwork;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotItemSpecific;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.util.Colors;
import lumaceon.mods.clockworkphase2.util.ISimpleNamed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.List;

public class ItemClockworkSword extends ItemSword implements IAssemblable, IClockwork, ISimpleNamed
{
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;
    @CapabilityInject(IEnergyStorage.class)
    static Capability<IEnergyStorage> ENERGY_STORAGE_CAPABILITY = null;

    String simpleName;

    public ItemClockworkSword(int maxStack, int maxDamage, String name)
    {
        super(ModItems.clockworkMaterial);
        this.setMaxStackSize(maxStack);
        this.setMaxDamage(maxDamage);
        this.setNoRepair();
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.simpleName = name;
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
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

        int quality = getQuality(stack);
        int speed = getSpeed(stack);
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
        {
            tooltip.add("");

            tooltip.add(Colors.WHITE + "Attack Damage: " + Colors.GOLD + (int) (speed / 25.0F));
            tooltip.add(Colors.WHITE + "Energy Per Attack: " + Colors.GOLD + ClockworkHelper.getTensionCostFromStats(ConfigValues.BASE_TENSION_COST_PER_ATTACK, quality, speed));
        }
        else
        {
            tooltip.add("");
            tooltip.add(Colors.BLUE + "Shift - Construct Details");
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
        {
            tooltip.add("");

            String color = InformationDisplay.getColorFromComponentStat(quality);
            tooltip.add(Colors.WHITE + "Quality: " + color + quality);

            color = InformationDisplay.getColorFromComponentStat(speed);
            tooltip.add(Colors.WHITE + "Speed: " + color + speed);
        }
        else
        {
            tooltip.add(Colors.BLUE + "Ctrl - Clockwork Stats");
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if(attacker instanceof EntityPlayer)
        {
            IEnergyStorage energyStorage = stack.getCapability(ENERGY_STORAGE_CAPABILITY, EnumFacing.DOWN);
            if(energyStorage != null)
            {
                int energy = energyStorage.getEnergyStored();
                if(energy <= 0)
                {
                    target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 0.0F);
                    return true;
                }
                int quality = getQuality(stack);
                int speed = getSpeed(stack);
                int energyCost = ClockworkHelper.getTensionCostFromStats(ConfigValues.BASE_TENSION_COST_PER_ATTACK, quality, speed);

                energyStorage.extractEnergy(energyCost, false);
            }
        }
        return true;
    }

    @Override
    public float getDamageVsEntity() {
        return 1;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack is, World world, IBlockState state, BlockPos pos, EntityLivingBase playerIn) {
        return true;
    }

    @Override
    public ResourceLocation getGUIBackground(ContainerAssemblyTable container) {
        return Textures.GUI.ASSEMBLY_TABLE_CONSTRUCT;
    }

    @Override
    public Slot[] getContainerSlots(IInventory inventory)
    {
        return new Slot[]
                {
                        new SlotItemSpecific(inventory, 0, 160, 41, ModItems.mainspring),
                        new SlotItemSpecific(inventory, 1, 125, 41, ModItems.clockworkCore)
                };
    }

    @Override
    public int getTier(ItemStack item) {
        return ClockworkHelper.getTier(item);
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
    public String getSimpleName() {
        return simpleName;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if(stack == null)
        {
            if(equipmentSlot == EntityEquipmentSlot.MAINHAND)
            {
                multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0, 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
            }
            return multimap;
        }

        int energy;
        IEnergyStorage energyStorage = stack.getCapability(ENERGY_STORAGE_CAPABILITY, EnumFacing.DOWN);
        if(energyStorage == null)
            energy = 0;
        else
            energy = energyStorage.getEnergyStored();

        int quality = getQuality(stack);
        int speed = getSpeed(stack);
        int energyCost = ClockworkHelper.getTensionCostFromStats(ConfigValues.BASE_TENSION_COST_PER_ATTACK, quality, speed);
        if(energyCost > energy)
        {
            if(equipmentSlot == EntityEquipmentSlot.MAINHAND)
            {
                multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0, 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
            }
            return multimap;
        }

        if(equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (int) (speed / 25.0), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new ClockworkSwordCapabilityProvider(stack);
    }

    private static class ClockworkSwordCapabilityProvider implements ICapabilitySerializable<NBTTagCompound>
    {
        @CapabilityInject(IEnergyStorage.class)
        static Capability<IEnergyStorage> ENERGY_STORAGE_CAPABILITY = null;
        @CapabilityInject(IItemHandler.class)
        static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;

        EnergyStorageModular energyStorage;
        ItemStackHandlerClockworkConstruct inventory;

        public ClockworkSwordCapabilityProvider(ItemStack stack) {
            inventory = new ItemStackHandlerClockworkConstruct(2, stack);
            energyStorage = new EnergyStorageModular(1, stack);
        }

        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability != null && capability == ENERGY_STORAGE_CAPABILITY || capability == ITEM_HANDLER_CAPABILITY;
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

            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setTag("inventory", inventory.serializeNBT());
            tag.setInteger("energy", energyStorage.getEnergyStored());
            tag.setInteger("max_capacity", energyStorage.getMaxEnergyStored());
            return tag;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            inventory.deserializeNBT((NBTTagCompound) nbt.getTag("inventory"));
            energyStorage.setMaxCapacity(nbt.getInteger("max_capacity"));
            energyStorage.receiveEnergy(nbt.getInteger("energy"), false);
        }
    }
}
