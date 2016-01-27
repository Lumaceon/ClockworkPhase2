package lumaceon.mods.clockworkphase2.item.construct.tool;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyTableComponents;
import lumaceon.mods.clockworkphase2.api.item.IKeybindActivation;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.util.*;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotItemSpecific;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotToolUpgrade;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.ItemToolUpgradeArea;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.api.util.AssemblyHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.util.RayTraceHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;
import java.util.Set;

/**
 * Credits to Tinker's Construct for a lot of the AOE code.
 */
public abstract class ItemClockworkTool extends ItemTool implements IAssemblable, IClockworkConstruct, IKeybindActivation
{
    public ItemClockworkTool(float var1, ToolMaterial toolMaterial, Set set, String unlocalizedName) {
        super(var1, toolMaterial, set);
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setUnlocalizedName(unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        InformationDisplay.addClockworkConstructInformation(is, player, list, true);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player)
    {
        Block block = player.worldObj.getBlock(x, y, z);
        int metadata = player.worldObj.getBlockMetadata(x, y, z);
        if(block == null || !isEffective(block, metadata)) //The tool is ineffective or non-functional.
            return super.onBlockStartBreak(stack, x, y, z, player);

        boolean found = false;
        int areaRadius = 1;
        ItemStack[] components = NBTHelper.INVENTORY.get(stack, NBTTags.COMPONENT_INVENTORY);
        if(components != null)
            for(ItemStack item : components) //Loop through the tool's component inventory.
                if(item != null && item.getItem() instanceof ItemToolUpgradeArea && ((ItemToolUpgradeArea) item.getItem()).getActive(item, stack))
                {
                    areaRadius = ((ItemToolUpgradeArea) item.getItem()).getAreaRadius(item);
                    found = true;
                    break;
                }
        if(!found) //No area upgrade found.
            return super.onBlockStartBreak(stack, x, y, z, player);

        MovingObjectPosition mop = RayTraceHelper.rayTrace(player.worldObj, player, false, 4.5);
        if(mop == null)
            return super.onBlockStartBreak(stack, x, y, z, player);

        int xRadius = areaRadius;
        int yRadius = areaRadius;
        int zRadius = 1;
        switch(mop.sideHit)
        {
            case 0:
            case 1:
                yRadius = 1; //DEPTH
                zRadius = areaRadius;
                break;
            case 2:
            case 3:
                xRadius = areaRadius;
                zRadius = 1; //DEPTH
                break;
            case 4:
            case 5:
                xRadius = 1; //DEPTH
                zRadius = areaRadius;
                break;
        }

        for(int x1 = x - xRadius + 1; x1 <= x + xRadius - 1; x1++)
            for(int y1 = y - yRadius + 1; y1 <= y + yRadius - 1; y1++)
                for(int z1 = z - zRadius + 1; z1 <= z + zRadius - 1; z1++)
                {
                    if((x1 == x && y1 == y && z1 == z) || super.onBlockStartBreak(stack, x1, y1, z1, player))
                        continue;
                    aoeBlockBreak(stack, player.worldObj, x1, y1, z1, player);
                }
        return false;
    }

    private void aoeBlockBreak(ItemStack stack, World world, int x, int y, int z, EntityPlayer player)
    {
        if(world.isAirBlock(x, y, z))
            return;

        if(!(player instanceof EntityPlayerMP))
            return;
        EntityPlayerMP playerMP = (EntityPlayerMP) player;

        Block block = world.getBlock(x, y, z);
        int metadata = world.getBlockMetadata(x, y, z);
        if(!isEffective(block, metadata) || !ForgeHooks.canHarvestBlock(block, player, metadata))
            return;

        BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, playerMP.theItemInWorldManager.getGameType(), playerMP, x, y, z);
        if(event.isCanceled())
            return;

        stack.func_150999_a(world, block, x, y, z, player);
        if(!world.isRemote)
        {
            block.onBlockHarvested(world, x, y, z, metadata, player);
            if(block.removedByPlayer(world, player, x, y, z, true))
            {
                block.onBlockDestroyedByPlayer( world, x,y,z, metadata);
                block.harvestBlock(world, player, x,y,z, metadata);
                block.dropXpOnBlockBreak(world, x,y,z, event.getExpToDrop());
            }
            playerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
        }
        else //CLIENT
        {
            world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (metadata << 12));
            if(block.removedByPlayer(world, player, x, y, z, true))
                block.onBlockDestroyedByPlayer(world, x, y, z, metadata);
            ItemStack itemstack = player.getCurrentEquippedItem();
            if(itemstack != null)
            {
                itemstack.func_150999_a(world, block, x, y, z, player);
                if(itemstack.stackSize == 0)
                    player.destroyCurrentEquippedItem();
            }
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, x,y,z, Minecraft.getMinecraft().objectMouseOver.sideHit));
        }
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
        boolean correctMaterial = false;
        for(Material mat : getEffectiveMaterials())
            if(mat.equals(block.getMaterial()))
                correctMaterial = true;

        if(!correctMaterial)
            return 1.0F;

        float efficiency = super.func_150893_a(is, block);
        if(efficiency == 1.0F)
            return 1.0F;

        int tension = NBTHelper.INT.get(is, NBTTags.CURRENT_TENSION);
        if(tension <= 0)
            return 0.0F;

        int speed = NBTHelper.INT.get(is, NBTTags.SPEED);
        if(speed <= 0)
            return 0.0F;

        return (float) speed / 25;
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta)
    {
        if(ForgeHooks.isToolEffective(stack, block, meta))
        {
            int tension = NBTHelper.INT.get(stack, NBTTags.CURRENT_TENSION);
            if(tension <= 0)
                return 0.0F;

            int speed = NBTHelper.INT.get(stack, NBTTags.SPEED);
            if(speed <= 0)
                return 0.0F;

            return (float) speed / 25;
        }
        return func_150893_a(stack, block);
    }

    public abstract String getHarvestType();
    public abstract Material[] getEffectiveMaterials();

    public boolean isEffective(Block block, int meta) {
        return this.getHarvestType().equals(block.getHarvestTool(meta)) || isEffective(block.getMaterial());
    }

    public boolean isEffective(Material material) {
        for(Material m : getEffectiveMaterials())
            if(m == material)
                return true;
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack is, World world, Block block, int x, int y, int z, EntityLivingBase entity)
    {
        if(block.getBlockHardness(world, x, y, z) <= 0 || !(is.getItem() instanceof IClockworkConstruct))
            return true;

        if(!isEffective(block, world.getBlockMetadata(x, y, z)))
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
        InventoryAssemblyTableComponents inventory = new InventoryAssemblyTableComponents(container, 10, 1);
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
                        new SlotToolUpgrade(inventory, 2, 20, 20),
                        new SlotToolUpgrade(inventory, 3, 20, 40),
                        new SlotToolUpgrade(inventory, 4, 20, 60),
                        new SlotToolUpgrade(inventory, 5, 20, 80),
                        new SlotToolUpgrade(inventory, 6, 20, 100),
                        new SlotToolUpgrade(inventory, 7, 20, 120),
                        new SlotToolUpgrade(inventory, 8, 20, 140),
                        new SlotToolUpgrade(inventory, 9, 20, 160),
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
    public void onKeyPressed(ItemStack item, EntityPlayer player) {
        player.openGui(ClockworkPhase2.instance, 4, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
    }
}
