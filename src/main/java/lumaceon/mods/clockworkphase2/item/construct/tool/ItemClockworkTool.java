package lumaceon.mods.clockworkphase2.item.construct.tool;

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
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Set;

/**
 * Credits to the Tinker's Construct developers for a lot of the AOE code.
 */
public abstract class ItemClockworkTool extends ItemTool implements IAssemblable, IClockworkConstruct
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
    public void onUpdate(ItemStack is, World world, Entity owner, int p_77663_4_, boolean p_77663_5_) {
        if(is != null && NBTHelper.hasTag(is, NBTTags.IS_COMPONENT))
            NBTHelper.removeTag(is, NBTTags.IS_COMPONENT);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player)
    {
        Block block = player.worldObj.getBlockState(pos).getBlock();
        IBlockState blockState = player.worldObj.getBlockState(pos);
        if(block == null || !isEffective(block, blockState)) //The tool is ineffective or non-functional.
            return super.onBlockStartBreak(stack, pos, player);

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
            return super.onBlockStartBreak(stack, pos, player);

        MovingObjectPosition mop = RayTraceHelper.rayTrace(player.worldObj, player, false, 4.5);
        if(mop == null)
            return super.onBlockStartBreak(stack, pos, player);

        int xRadius = areaRadius;
        int yRadius = areaRadius;
        int zRadius = 1;
        switch(mop.sideHit.getIndex())
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

        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        for(int x1 = x - xRadius + 1; x1 <= x + xRadius - 1; x1++)
            for(int y1 = y - yRadius + 1; y1 <= y + yRadius - 1; y1++)
                for(int z1 = z - zRadius + 1; z1 <= z + zRadius - 1; z1++)
                {
                    if((x1 == x && y1 == y && z1 == z) || super.onBlockStartBreak(stack, new BlockPos(x1, y1, z1), player))
                        continue;
                    aoeBlockBreak(stack, player.worldObj, new BlockPos(x1, y1, z1), player);
                }
        return false;
    }

    private void aoeBlockBreak(ItemStack stack, World world, BlockPos pos, EntityPlayer player)
    {
        if(world.isAirBlock(pos))
            return;

        if(!(player instanceof EntityPlayerMP))
            return;
        EntityPlayerMP playerMP = (EntityPlayerMP) player;

        Block block = world.getBlockState(pos).getBlock();
        IBlockState blockState = world.getBlockState(pos);
        if(!isEffective(block, blockState) || !ForgeHooks.canHarvestBlock(block, player, world, pos))
            return;

        int event = ForgeHooks.onBlockBreakEvent(world, playerMP.theItemInWorldManager.getGameType(), playerMP, pos);
        if(event == -1)
            return;

        stack.onBlockDestroyed(world, block, pos, player);
        if(!world.isRemote)
        {
            block.onBlockHarvested(world, pos, blockState, player);
            if(block.removedByPlayer(world, pos, player, true))
            {
                block.onBlockDestroyedByPlayer(world, pos, blockState);
                block.harvestBlock(world, player, pos, blockState, world.getTileEntity(pos));
                block.dropXpOnBlockBreak(world, pos, event);
            }
            playerMP.playerNetServerHandler.sendPacket(new S23PacketBlockChange(world, pos));
        }
        else //CLIENT
        {
            //world.playAuxSFX(2001, pos, Block.getIdFromBlock(block) + (metadata << 12));
            if(block.removedByPlayer(world, pos, player, true))
                block.onBlockDestroyedByPlayer(world, pos, blockState);
            ItemStack itemstack = player.getCurrentEquippedItem();
            if(itemstack != null)
            {
                itemstack.onBlockDestroyed(world, block, pos, player);
                if(itemstack.stackSize == 0)
                    player.destroyCurrentEquippedItem();
            }
            Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, Minecraft.getMinecraft().objectMouseOver.sideHit));
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
    public float getStrVsBlock(ItemStack is, Block block)
    {
        boolean correctMaterial = false;
        for(Material mat : getEffectiveMaterials())
            if(mat.equals(block.getMaterial()))
                correctMaterial = true;

        if(!correctMaterial)
            return 1.0F;

        int tension = NBTHelper.INT.get(is, NBTTags.CURRENT_TENSION);
        if(tension <= 0 && (!NBTHelper.hasTag(is, NBTTags.IS_COMPONENT) || !NBTHelper.BOOLEAN.get(is, NBTTags.IS_COMPONENT)))
            return 0.0F;

        int speed = NBTHelper.INT.get(is, NBTTags.SPEED);
        if(speed <= 0)
            return 0.0F;

        return (float) speed / 25;
    }

    @Override
    public float getDigSpeed(ItemStack is, net.minecraft.block.state.IBlockState state)
    {
        for(String type : is.getItem().getToolClasses(is))
            if(state.getBlock().isToolEffective(type, state))
            {
                int tension = NBTHelper.INT.get(is, NBTTags.CURRENT_TENSION);
                if(tension <= 0 && (!NBTHelper.hasTag(is, NBTTags.IS_COMPONENT) || !NBTHelper.BOOLEAN.get(is, NBTTags.IS_COMPONENT)))
                    return 0.0F;

                int speed = NBTHelper.INT.get(is, NBTTags.SPEED);
                if(speed <= 0)
                    return 0.0F;

                return (float) speed / 25;
            }
        return getStrVsBlock(is, state.getBlock());
    }

    public abstract String getHarvestType();
    public abstract Material[] getEffectiveMaterials();

    public boolean isEffective(Block block, IBlockState blockState) {
        return this.getHarvestType().equals(block.getHarvestTool(blockState)) || isEffective(block.getMaterial());
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
    public boolean onBlockDestroyed(ItemStack is, World world, Block block, BlockPos pos, EntityLivingBase playerIn)
    {
        if(block.getBlockHardness(world, pos) <= 0 || !(is.getItem() instanceof IClockworkConstruct))
            return true;

        if(!isEffective(block, world.getBlockState(pos)))
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
                        new SlotItemSpecific(inventory, 0, 120, 30, ModItems.mainspring.getItem()),
                        new SlotItemSpecific(inventory, 1, 120, 54, ModItems.clockworkCore.getItem())
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
