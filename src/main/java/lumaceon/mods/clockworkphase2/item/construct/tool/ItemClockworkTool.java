package lumaceon.mods.clockworkphase2.item.construct.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyTableComponents;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.util.*;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotItemSpecific;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.api.util.AssemblyHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;
import java.util.Set;

public class ItemClockworkTool extends ItemTool implements IAssemblable, IClockworkConstruct
{
    public ItemClockworkTool(float var1, ToolMaterial toolMaterial, Set set, String unlocalizedName)
    {
        super(var1, toolMaterial, set);
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setUnlocalizedName(unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addClockworkConstructInformation(is, player, list, flag);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass)
    {
        int harvestLevel = -1;
        if(toolClass.equals("pickaxe") && NBTHelper.hasTag(stack, NBTTags.HARVEST_LEVEL_PICKAXE))
             harvestLevel = Math.max(harvestLevel, NBTHelper.INT.get(stack, NBTTags.HARVEST_LEVEL_PICKAXE));
        if(toolClass.equals("axe") && NBTHelper.hasTag(stack, NBTTags.HARVEST_LEVEL_AXE))
            harvestLevel = Math.max(harvestLevel, NBTHelper.INT.get(stack, NBTTags.HARVEST_LEVEL_AXE));
        if(toolClass.equals("shovel") && NBTHelper.hasTag(stack, NBTTags.HARVEST_LEVEL_SHOVEL))
            harvestLevel = Math.max(harvestLevel, NBTHelper.INT.get(stack, NBTTags.HARVEST_LEVEL_SHOVEL));

        return harvestLevel;
    }

    @Override
    public float func_150893_a(ItemStack is, Block block)
    {
        float efficiency = super.func_150893_a(is, block);
        if(efficiency == 1.0F)
            return efficiency;

        int tension = NBTHelper.INT.get(is, NBTTags.CURRENT_TENSION);
        if(tension <= 0)
            return 1.0F;

        int speed = NBTHelper.INT.get(is, NBTTags.SPEED);
        if(speed <= 0)
            return 1.0F;

        return (float) speed / 25;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta)
    {
        if(ForgeHooks.isToolEffective(stack, block, meta))
        {
            int tension = NBTHelper.INT.get(stack, NBTTags.CURRENT_TENSION);
            if(tension <= 0)
                return 1.0F;

            int speed = NBTHelper.INT.get(stack, NBTTags.SPEED);
            if(speed <= 0)
                return 1.0F;

            return (float) speed / 25;
        }
        return func_150893_a(stack, block);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack is, World world, Block block, int x, int y, int z, EntityLivingBase entity)
    {
        if(block.getBlockHardness(world, x, y, z) <= 0 || !(is.getItem() instanceof IClockworkConstruct))
            return true;

        if(getDigSpeed(is, block, world.getBlockMetadata(x, y, z)) <= 1.0F)
            return true;

        IClockworkConstruct clockworkConstruct = (IClockworkConstruct) is.getItem();
        int currentTension = clockworkConstruct.getTension(is);
        if(currentTension <= 0)
            return true;

        int quality = clockworkConstruct.getQuality(is);
        int speed = clockworkConstruct.getSpeed(is);
        int tensionCost = ClockworkHelper.getTensionCostFromStats(Defaults.TENSION.perBlock, quality, speed);

        consumeTension(is, tensionCost);

        return true;
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
    public int getQuality(ItemStack item) {
        return ClockworkHelper.getQuality(item);
    }

    @Override
    public int getSpeed(ItemStack item) {
        return ClockworkHelper.getSpeed(item);
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
    public void addConstructInformation(ItemStack item, EntityPlayer player, List list) {
        InformationDisplay.addClockworkToolInformation(item, player, list);
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
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry) {
        this.itemIcon = registry.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
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
}
