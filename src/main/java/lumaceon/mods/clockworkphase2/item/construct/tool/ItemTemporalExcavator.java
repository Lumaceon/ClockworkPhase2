package lumaceon.mods.clockworkphase2.item.construct.tool;

import com.google.common.collect.Sets;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.assembly.InventoryAssemblyTableComponents;
import lumaceon.mods.clockworkphase2.api.item.IKeybindActivation;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.item.clockwork.IMainspring;
import lumaceon.mods.clockworkphase2.api.util.AssemblyHelper;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.api.util.InformationDisplay;
import lumaceon.mods.clockworkphase2.api.util.TimeHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTTags;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotItemSpecific;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotToolUpgrade;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.ItemToolUpgradeArea;
import lumaceon.mods.clockworkphase2.item.components.clockworktool.ItemToolUpgradeTemporalInfuser;
import lumaceon.mods.clockworkphase2.lib.Defaults;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.util.RayTraceHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Set;

public class ItemTemporalExcavator extends ItemTool implements IAssemblable, IClockworkConstruct, IKeybindActivation
{
    private static final Set field_150915_c = Sets.newHashSet(new Block[]{Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail, Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium, Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin});

    public ItemTemporalExcavator(ToolMaterial p_i45333_2_, String unlocalizedName)
    {
        super(0, p_i45333_2_, field_150915_c);
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setUnlocalizedName(unlocalizedName);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack is, EntityPlayer player, List list, boolean flag) {
        String color = InformationDisplay.getColorFromTension(getTension(is), getMaxTension(is));
        list.add("Tension: " + color + getTension(is) + "/" + getMaxTension(is));
    }

    @Override
    public float getStrVsBlock(ItemStack is, Block block)
    {
        float strengthVsBlock = 0.0F;

        int tension = NBTHelper.INT.get(is, NBTTags.CURRENT_TENSION);
        if(tension <= 0)
            return 0.0F;

        if(is != null)
        {
            ItemStack[] items = NBTHelper.INVENTORY.get(is, NBTTags.COMPONENT_INVENTORY);
            if(items != null)
            {
                for(int n = 0; n < 3 && n < items.length; n++)
                {
                    ItemStack item = items[n];
                    if(item != null && item.getItem() instanceof ItemClockworkTool)
                        strengthVsBlock = Math.max(strengthVsBlock, item.getItem().getStrVsBlock(item, block));
                }
                for(int n = 3; n < items.length; n++)
                {
                    ItemStack item = items[n];
                    if(item != null && item.getItem() instanceof ItemToolUpgradeTemporalInfuser)
                        if(((ItemToolUpgradeTemporalInfuser) item.getItem()).getActive(item, is))
                            return 10000000F;
                }
            }
        }
        return strengthVsBlock;
    }

    @Override
    public float getDigSpeed(ItemStack is, net.minecraft.block.state.IBlockState state)
    {
        float strengthVsBlock = 0.0F;

        int tension = NBTHelper.INT.get(is, NBTTags.CURRENT_TENSION);
        if(tension <= 0)
            return 0.0F;
        if(is != null)
        {
            ItemStack[] items = NBTHelper.INVENTORY.get(is, NBTTags.COMPONENT_INVENTORY);
            if(items != null)
            {
                for(int n = 0; n < 3 && n < items.length; n++)
                {
                    ItemStack item = items[n];
                    if(item != null && item.getItem() instanceof ItemClockworkTool)
                        strengthVsBlock = Math.max(strengthVsBlock, item.getItem().getDigSpeed(item, state));
                }
                for(int n = 3; n < items.length; n++)
                {
                    ItemStack item = items[n];
                    if(item != null && item.getItem() instanceof ItemToolUpgradeTemporalInfuser)
                        if(((ItemToolUpgradeTemporalInfuser) item.getItem()).getActive(item, is))
                            return 10000000F;
                }
            }
        }
        return strengthVsBlock;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack is, World world, Block block, BlockPos pos, EntityLivingBase playerIn)
    {
        float blockHardness = block.getBlockHardness(world, pos);
        if(blockHardness <= 0)
            return true;

        ItemStack mostSpeedyTool = null;
        float greatestStrength = 0.0F;
        ItemStack[] componentInventory = NBTHelper.INVENTORY.get(is, NBTTags.COMPONENT_INVENTORY);
        if(componentInventory != null)
            for(ItemStack component : componentInventory)
                if(component != null)
                {
                    if(component.getItem() instanceof ItemToolUpgradeTemporalInfuser)
                        if(((ItemToolUpgradeTemporalInfuser) component.getItem()).getActive(component, is))
                        {
                            TimeHelper.consumeTimeMostPossible(((EntityPlayer) playerIn).inventory, TimeHelper.timeToBreakBlock(world, pos, block, playerIn, is));
                            break;
                        }
                    if(component.getItem() instanceof ItemClockworkTool)
                    {
                        float strength = component.getItem().getStrVsBlock(component, block);
                        if(mostSpeedyTool == null || strength > greatestStrength)
                        {
                            mostSpeedyTool = component;
                            greatestStrength = strength;
                        }
                    }
                }

        if(mostSpeedyTool != null && mostSpeedyTool.getItem() instanceof IClockworkConstruct)
        {
            IClockworkConstruct clockworkConstruct = (IClockworkConstruct) mostSpeedyTool.getItem();

            int currentTension = getTension(is);
            if(currentTension <= 0)
                return true;

            int quality = clockworkConstruct.getQuality(mostSpeedyTool);
            int speed = clockworkConstruct.getSpeed(mostSpeedyTool);
            int tensionCost = ClockworkHelper.getTensionCostFromStats(Defaults.TENSION.perBlock, quality, speed);

            consumeTension(is, tensionCost);
        }
        return true;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player)
    {
        Block block = player.worldObj.getBlockState(pos).getBlock();
        IBlockState blockState = player.worldObj.getBlockState(pos);
        if(block == null || !isEffective(stack, block, blockState)) //The tool is ineffective or non-functional.
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
        if(!isEffective(stack, block, blockState) || !ForgeHooks.canHarvestBlock(block, player, world, pos))
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

    protected boolean isEffective(ItemStack is, Block block, IBlockState state)
    {
        if(is != null)
        {
            ItemStack[] componentInventory = NBTHelper.INVENTORY.get(is, NBTTags.COMPONENT_INVENTORY);
            if(componentInventory != null)
            {
                for(int n = 0; n < 3 && n < componentInventory.length; n++)
                {
                    ItemStack component = componentInventory[n];
                    if(component != null && component.getItem() instanceof ItemClockworkTool)
                        if(((ItemClockworkTool) component.getItem()).isEffective(block, state))
                            return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hitEntity(ItemStack p_77644_1_, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
        return true;
    }

    @Override
    public boolean canHarvestBlock(Block p_150897_1_) {
        return true;
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
        return Textures.GUI.ASSEMBLY_TABLE_TEMPORAL_EXCAVATOR;
    }

    @Override
    public InventoryAssemblyTableComponents getGUIInventory(ContainerAssemblyTable container) {
        InventoryAssemblyTableComponents inventory = new InventoryAssemblyTableComponents(container, 13, 1);
        AssemblyHelper.GET_GUI_INVENTORY.loadStandardComponentInventory(container, inventory);
        return inventory;
    }

    @Override
    public Slot[] getContainerSlots(IInventory inventory) {
        return new Slot[]
                {
                        new SlotItemSpecific(inventory, 0, 106, 59, ModItems.clockworkPickaxe.getItem()),
                        new SlotItemSpecific(inventory, 1, 142, 41, ModItems.clockworkAxe.getItem()),
                        new SlotItemSpecific(inventory, 2, 178, 59, ModItems.clockworkShovel.getItem()),
                        new SlotToolUpgrade(inventory, 3, 61, 106),
                        new SlotToolUpgrade(inventory, 4, 79, 106),
                        new SlotToolUpgrade(inventory, 5, 97, 106),
                        new SlotToolUpgrade(inventory, 6, 115, 106),
                        new SlotToolUpgrade(inventory, 7, 133, 106),
                        new SlotToolUpgrade(inventory, 8, 151, 106),
                        new SlotToolUpgrade(inventory, 9, 169, 106),
                        new SlotToolUpgrade(inventory, 10, 187, 106),
                        new SlotToolUpgrade(inventory, 11, 205, 106),
                        new SlotToolUpgrade(inventory, 12, 223, 106),
                };
    }

    @Override
    public void saveComponentInventory(ContainerAssemblyTable container) {
        AssemblyHelper.SAVE_COMPONENT_INVENTORY.saveComponentInventory(container);
    }

    @Override
    public void onInventoryChange(ContainerAssemblyTable container)
    {
        ItemStack mainItem = container.mainInventory.getStackInSlot(0);
        ItemStack pickaxe = container.componentInventory.getStackInSlot(0);
        ItemStack axe = container.componentInventory.getStackInSlot(1);
        ItemStack shovel = container.componentInventory.getStackInSlot(2);
        ItemStack[] items;
        int maxTension = 0;

        if(mainItem != null)
        {
            if(pickaxe != null)
            {
                items = NBTHelper.INVENTORY.get(pickaxe, NBTTags.COMPONENT_INVENTORY);
                ItemStack mainspring = items[0];
                if(mainspring != null && mainspring.getItem() instanceof IMainspring) //There is a mainspring.
                {
                    maxTension += NBTHelper.INT.get(mainspring, NBTTags.MAX_TENSION);
                    NBTHelper.INT.set(pickaxe, NBTTags.CURRENT_TENSION, 0);
                }
                NBTHelper.BOOLEAN.set(pickaxe, NBTTags.IS_COMPONENT, true);
            }
            if(axe != null)
            {
                items = NBTHelper.INVENTORY.get(axe, NBTTags.COMPONENT_INVENTORY);
                ItemStack mainspring = items[0];
                if(mainspring != null && mainspring.getItem() instanceof IMainspring) //There is a mainspring.
                {
                    maxTension += NBTHelper.INT.get(mainspring, NBTTags.MAX_TENSION);
                    NBTHelper.INT.set(axe, NBTTags.CURRENT_TENSION, 0);
                }
                NBTHelper.BOOLEAN.set(axe, NBTTags.IS_COMPONENT, true);
            }
            if(shovel != null)
            {
                items = NBTHelper.INVENTORY.get(shovel, NBTTags.COMPONENT_INVENTORY);
                ItemStack mainspring = items[0];
                if(mainspring != null && mainspring.getItem() instanceof IMainspring) //There is a mainspring.
                {
                    maxTension += NBTHelper.INT.get(mainspring, NBTTags.MAX_TENSION);
                    NBTHelper.INT.set(shovel, NBTTags.CURRENT_TENSION, 0);
                }
                NBTHelper.BOOLEAN.set(shovel, NBTTags.IS_COMPONENT, true);
            }

            NBTHelper.INT.set(mainItem, NBTTags.MAX_TENSION, maxTension);
            NBTHelper.INT.set(mainItem, NBTTags.CURRENT_TENSION, 0);
            mainItem.setItemDamage(mainItem.getMaxDamage());
        }
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
    public void addConstructInformation(ItemStack item, EntityPlayer player, List list) {}

    @Override
    public int getQuality(ItemStack item) {
        return 0;
    }

    @Override
    public int getSpeed(ItemStack item) {
        return 0;
    }

    @Override
    public void onKeyPressed(ItemStack item, EntityPlayer player) {
        player.openGui(ClockworkPhase2.instance, 4, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
    }
}
