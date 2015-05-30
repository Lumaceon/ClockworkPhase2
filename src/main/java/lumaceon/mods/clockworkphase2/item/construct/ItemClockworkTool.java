package lumaceon.mods.clockworkphase2.item.construct;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.item.IAssemblable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyComponents;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.item.temporal.ITemporalableTool;
import lumaceon.mods.clockworkphase2.api.item.ITimeSand;
import lumaceon.mods.clockworkphase2.api.util.*;
import lumaceon.mods.clockworkphase2.api.util.internal.Colors;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotClockwork;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotMainspring;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotTemporalCore;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotTemporalFunction;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import lumaceon.mods.clockworkphase2.lib.NBTTags;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageStandardParticleSpawn;
import lumaceon.mods.clockworkphase2.util.AssemblyHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;
import java.util.Set;

public class ItemClockworkTool extends ItemTool implements IAssemblable, IClockworkConstruct, ITimeSand, ITemporalableTool
{
    public ItemClockworkTool(float var1, ToolMaterial toolMaterial, Set set, String unlocalizedName)
    {
        super(var1, toolMaterial, set);
        this.setMaxStackSize(1);
        this.setMaxDamage(20);
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

        if(isTemporal(is) && getTimeSand(is) <= 0)
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

            if(isTemporal(stack) && getTimeSand(stack) <= 0)
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
        int memory = clockworkConstruct.getMemory(is);
        int tensionCost = ClockworkHelper.getTensionCostFromStats(Defaults.TENSION.perBlock, quality, speed);

        consumeTension(is, tensionCost);

        if((isTemporal(is) && TemporalToolHelper.getTimeSand(is) <= 0) || !(entity instanceof EntityPlayer))
            return true;

        consumeTimeSand(is, (EntityPlayer) entity, TemporalToolHelper.getTimeSandCostFromToolUsage(is));

        if(memory > 0 && !world.isRemote)
        {
            EntityPlayer player = (EntityPlayer)entity;
            int chance = TimeSandHelper.getTimeSandChance(player.experienceLevel);
            if(world.rand.nextInt(chance) == 0)
            {
                addTimeSand(is, player, ClockworkHelper.getTimeSandFromStats(memory));
                PacketHandler.INSTANCE.sendToAllAround(new MessageStandardParticleSpawn(x + 0.5, y + 0.5, z + 0.5, 0, 20), new NetworkRegistry.TargetPoint(world.provider.dimensionId, x + 0.5, y + 0.5, z + 0.5, 64));
            }
        }
        return true;
    }

    @Override
    public InventoryAssemblyComponents createComponentInventory(IAssemblyContainer container)
    {
        ItemStack mainItem = container.getMainInventory().getStackInSlot(0);
        int invSize = 2;
        if(mainItem != null && mainItem.getItem() instanceof ITemporalableTool && ((ITemporalableTool) mainItem.getItem()).isTemporal(mainItem))
        {
            invSize += 2;
            invSize += ((ITemporalableTool) mainItem.getItem()).getNumberOfPassiveTemporalFunctions(mainItem);
        }

        InventoryAssemblyComponents inventory = new InventoryAssemblyComponents(container, invSize, 1);
        AssemblyHelper.CREATE_COMPONENT_INVENTORY.loadStandardComponentInventory(container, inventory);
        return inventory;
    }

    @Override
    public Slot[] getContainerSlots(IAssemblyContainer container, IInventory inventory)
    {
        ItemStack mainItem = container.getMainInventory().getStackInSlot(0);
        if(mainItem != null && mainItem.getItem() instanceof ITemporalableTool && ((ITemporalableTool) mainItem.getItem()).isTemporal(mainItem))
        {
            Slot[] newSlots = new Slot[inventory.getSizeInventory()];
            newSlots[0] = new SlotMainspring(inventory, 0, 20, 20);
            newSlots[1] = new SlotClockwork(inventory, 1, 20, 40);
            newSlots[2] = new SlotTemporalCore(inventory, 2, 40, 40);
            newSlots[3] = new SlotTemporalFunction(inventory, 3, 40, 60, true);
            for(int n = 4; n < inventory.getSizeInventory(); n++)
                newSlots[n] = new SlotTemporalFunction(inventory, n, 100 + 20 * ((n - 4) % 2), 100 + 20 * (n - 4), false);
            return newSlots;
        }

        return new Slot[]
                {
                        new SlotMainspring(inventory, 0, 20, 20),
                        new SlotClockwork(inventory, 1, 20, 40),
                };
    }

    @Override
    public void onComponentChange(IAssemblyContainer container)
    {
        AssemblyHelper.COMPONENT_CHANGE.assembleClockworkConstruct(container, 0, 1);
        if(container.getMainInventory().getStackInSlot(0) != null && isTemporal(container.getMainInventory().getStackInSlot(0)))
            AssemblyHelper.COMPONENT_CHANGE.assembleClockworkTemporalTool(container);
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
    public ResourceLocation getBackgroundTexture(IAssemblyContainer container){
        return Textures.GUI.DEFAULT_ASSEMBLY_TABLE;
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
    public int getMemory(ItemStack item) {
        return ClockworkHelper.getMemory(item);
    }

    @Override
    public void setTension(ItemStack item, int tension) {
        ClockworkHelper.setTension(item, tension);
    }

    @Override
    public void setHarvestLevels(ItemStack item, int harvestLevel) {}

    @Override
    public int addTension(ItemStack item, int tension) {
        return ClockworkHelper.addTension(item, tension);
    }

    @Override
    public int consumeTension(ItemStack item, int tension) {
        return ClockworkHelper.consumeTension(item, tension);
    }

    @Override
    public void addClockworkInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
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
    public long getTimeSand(ItemStack item) {
        return TimeSandHelper.getTimeSand(item);
    }

    @Override
    public long getMaxTimeSand(ItemStack item) {
        return TimeConverter.WEEK * 2;
    }

    @Override
    public void setTimeSand(ItemStack item, EntityPlayer player, long timeSand) {
        TimeSandHelper.setTimeSand(item, timeSand);
    }

    @Override
    public long addTimeSand(ItemStack item, EntityPlayer player, long amount)
    {
        long amountAdded = TimeSandHelper.addTimeSand(item, player, amount);
        if(!isTemporal(item) && getTimeSand(item) >= TimeConverter.WEEK)
        {
            setTemporal(item, true);
            player.addChatComponentMessage(new ChatComponentText(Colors.AQUA + "Your " + getItemStackDisplayName(item) + Colors.AQUA + " is" + Colors.AQUA + " beginning" + Colors.AQUA + " to" + Colors.AQUA + " radiate" + Colors.AQUA + " a" + Colors.AQUA + " temporal" + Colors.AQUA + " energy" + Colors.AQUA + " outside" + Colors.AQUA + " it's" + Colors.AQUA + " own."));
        }
        return amountAdded;
    }

    @Override
    public long consumeTimeSand(ItemStack item, EntityPlayer player, long amount)
    {
        long amountConsumed = TimeSandHelper.consumeTimeSand(item, player, amount);
        if(isTemporal(item) && !TemporalToolHelper.hasTemporalCore(item) && getTimeSand(item) < TimeConverter.WEEK)
        {
            setTemporal(item, false);
            player.addChatComponentMessage(new ChatComponentText(Colors.AQUA + "Your " + getItemStackDisplayName(item) + "'s" + Colors.AQUA + " temporal" + Colors.AQUA + " energy" + Colors.AQUA + " begins" + Colors.AQUA + " to" + Colors.AQUA + " fade."));
        }
        return amountConsumed;
    }

    @Override
    public boolean isTemporal(ItemStack item) {
        return NBTHelper.BOOLEAN.get(item, NBTTags.TEMPORAL_STATE);
    }

    @Override
    public void setTemporal(ItemStack item, boolean isTemporal) {
        NBTHelper.BOOLEAN.set(item, NBTTags.TEMPORAL_STATE, isTemporal);
    }

    @Override
    public int getNumberOfPassiveTemporalFunctions(ItemStack item) {
        return isTemporal(item) ? Math.max(NBTHelper.INT.get(item, NBTTags.FUNCTION_COUNT), 1) : 0;
    }

    @Override
    public void setNumberOfPassiveTemporalFunctions(ItemStack item, int numberOfFunctions) {
        NBTHelper.INT.set(item, NBTTags.FUNCTION_COUNT, numberOfFunctions);
    }

    @Override
    public void addTemporalInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addTemporalToolInformation(item, player, list);
    }
}
