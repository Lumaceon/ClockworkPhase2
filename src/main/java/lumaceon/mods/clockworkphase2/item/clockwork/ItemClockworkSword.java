package lumaceon.mods.clockworkphase2.item.clockwork;

import com.google.common.collect.Multimap;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyTableComponents;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.util.AssemblyHelper;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotItemSpecific;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.UUID;

public class ItemClockworkSword extends ItemSword implements IAssemblable, IClockworkConstruct
{
    private static UUID attributeModID = UUID.fromString("0ef1758c-bc12-4c99-afca-5a43a9adf2c6");

    public ItemClockworkSword(int maxStack, int maxDamage, String registryName)
    {
        super(ModItems.clockworkMaterial);
        this.setMaxStackSize(maxStack);
        this.setMaxDamage(maxDamage);
        this.setNoRepair();
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setRegistryName(registryName);
        this.setUnlocalizedName(registryName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addClockworkConstructInformation(is, player, list, true);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if(attacker instanceof EntityPlayer)
        {
            int currentTension = getTension(stack);
            if(currentTension <= 0)
            {
                target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 0.0F);
                return true;
            }
            int quality = getQuality(stack);
            int speed = getSpeed(stack);
            //target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), speed / 25.0F);
            int tensionCost = ClockworkHelper.getTensionCostFromStats(ConfigValues.BASE_TENSION_COST_PER_ATTACK, quality, speed);

            consumeTension(stack, tensionCost);
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
    public InventoryAssemblyTableComponents getGUIInventory(ContainerAssemblyTable container) {
        InventoryAssemblyTableComponents inventory = new InventoryAssemblyTableComponents(container, 10, 1);
        AssemblyHelper.GET_GUI_INVENTORY.loadStandardComponentInventory(container, inventory);
        return inventory;
    }

    @Override
    public Slot[] getContainerSlots(IInventory inventory)
    {
        return new Slot[]
                {
                        new SlotItemSpecific(inventory, 0, 160, 41, ModItems.mainspring.getItem()),
                        new SlotItemSpecific(inventory, 1, 125, 41, ModItems.clockworkCore.getItem())
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

        list.add(Colors.WHITE + "Attack Damage: " + Colors.GOLD + (int) (speed / 25.0F));
        list.add(Colors.WHITE + "Tension Per Attack: " + Colors.GOLD + ClockworkHelper.getTensionCostFromStats(ConfigValues.BASE_TENSION_COST_PER_ATTACK, quality, speed));
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
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }

    @Override
    public String getUnlocalizedName(ItemStack is) {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack)
    {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if(stack == null)
        {
            if(equipmentSlot == EntityEquipmentSlot.MAINHAND)
            {
                multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName());
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0, 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
            }
            return multimap;
        }

        int quality = getQuality(stack);
        int speed = getSpeed(stack);
        int tension = getTension(stack);
        int tensionCost = ClockworkHelper.getTensionCostFromStats(ConfigValues.BASE_TENSION_COST_PER_ATTACK, quality, speed);
        if(tensionCost > tension)
        {
            if(equipmentSlot == EntityEquipmentSlot.MAINHAND)
            {
                multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName());
                multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 0, 0));
                multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
            }
            return multimap;
        }

        if(equipmentSlot == EntityEquipmentSlot.MAINHAND)
        {
            multimap.removeAll(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName());
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (int) (speed / 25.0), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getAttributeUnlocalizedName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
}
