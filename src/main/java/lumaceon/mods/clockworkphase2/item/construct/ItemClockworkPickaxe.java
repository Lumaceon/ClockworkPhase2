package lumaceon.mods.clockworkphase2.item.construct;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyComponents;
import lumaceon.mods.clockworkphase2.api.item.IAssemblable;
import lumaceon.mods.clockworkphase2.api.item.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.item.ITimeSand;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.client.gui.slot.SlotClockwork;
import lumaceon.mods.clockworkphase2.client.gui.slot.SlotItemSpecific;
import lumaceon.mods.clockworkphase2.client.gui.slot.SlotMainspring;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import lumaceon.mods.clockworkphase2.lib.NBTTags;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageStandardParticleSpawn;
import lumaceon.mods.clockworkphase2.util.AssemblyHelper;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.TimeSandHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

import java.util.List;

public class ItemClockworkPickaxe extends ItemPickaxe implements IAssemblable, IClockworkConstruct, ITimeSand
{
    public ItemClockworkPickaxe(ToolMaterial material, String unlocalizedName)
    {
        super(material);
        this.setUnlocalizedName(unlocalizedName);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addClockworkConstructInformation(is, player, list, flag);
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

        IClockworkConstruct clockworkConstruct = (IClockworkConstruct) is.getItem();
        int currentTension = clockworkConstruct.getTension(is);
        if(currentTension <= 0)
            return true;

        int quality = clockworkConstruct.getQuality(is);
        int speed = clockworkConstruct.getSpeed(is);
        int memory = clockworkConstruct.getMemory(is);
        int tensionCost = ClockworkHelper.getTensionCostFromStats(Defaults.TENSION.perBlock, quality, speed);

        consumeTension(is, tensionCost);

        if(memory > 0 && !world.isRemote && getDigSpeed(is, block, world.getBlockMetadata(x, y, z)) > 1 && entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer)entity;
            int chance = TimeSandHelper.getTimeSandChance(player.experienceLevel);
            if(world.rand.nextInt(chance) == 0)
            {
                addTimeSand(is, ClockworkHelper.getTimeSandFromStats(memory));
                PacketHandler.INSTANCE.sendToAllAround(new MessageStandardParticleSpawn(x + 0.5, y + 0.5, z + 0.5, 0, 20), new NetworkRegistry.TargetPoint(world.provider.dimensionId, x + 0.5, y + 0.5, z + 0.5, 64));
            }
        }
        return true;
    }

    @Override
    public InventoryAssemblyComponents createComponentInventory(IAssemblyContainer container)
    {
        InventoryAssemblyComponents inventory = new InventoryAssemblyComponents(container, 2, 1);
        AssemblyHelper.CREATE_COMPONENT_INVENTORY.loadStandardComponentInventory(container, inventory);
        return inventory;
    }

    @Override
    public Slot[] getContainerSlots(IInventory inventory)
    {
        return new Slot[]
                {
                        new SlotMainspring(inventory, 0, 0, 0),
                        new SlotClockwork(inventory, 1, 0, 18)
                };
    }

    @Override
    public void onComponentChange(IAssemblyContainer container) {
        AssemblyHelper.COMPONENT_CHANGE.assembleClockworkConstruct(container, 0, 1);
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
    public int addTension(ItemStack item, int tension) {
        return ClockworkHelper.addTension(item, tension);
    }

    @Override
    public int consumeTension(ItemStack item, int tension) {
        return ClockworkHelper.consumeTension(item, tension);
    }

    @Override
    public void addRelevantInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addClockworkToolInformation(item, player, list);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }

    @Override
    public String getUnlocalizedName(ItemStack is)
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry)
    {
        this.itemIcon = registry.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }

    @Override
    public long getTimeSand(ItemStack item) {
        return TimeSandHelper.getTimeSand(item);
    }

    @Override
    public long getMaxTimeSand(ItemStack item) {
        return TimeConverter.DAY;
    }

    @Override
    public void setTimeSand(ItemStack item, long timeSand) {
        TimeSandHelper.setTimeSand(item, timeSand);
    }

    @Override
    public long addTimeSand(ItemStack item, long amount) {
        return TimeSandHelper.addTimeSand(item, amount);
    }

    @Override
    public long consumeTimeSand(ItemStack item, long amount) {
        return TimeSandHelper.consumeTimeSand(item, amount);
    }
}
