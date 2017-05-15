package lumaceon.mods.clockworkphase2.tile.machine;

import lumaceon.mods.clockworkphase2.api.capabilities.EnergyStorageModular;
import lumaceon.mods.clockworkphase2.api.capabilities.ItemStackHandlerClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.temporal.Echo;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITimeSink;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITimezoneRelay;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.block.machine.BlockClockworkMachine;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classes extending this should also set the slots field after calling the super constructor.
 */
public abstract class TileClockworkMachine extends TileMod implements ISidedInventory, ITickable, ITimeSink
{
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;
    @CapabilityInject(IEnergyStorage.class)
    static Capability<IEnergyStorage> ENERGY_STORAGE_CAPABILITY = null;

    SidedInvWrapper invUP = new SidedInvWrapper(this, EnumFacing.UP);
    SidedInvWrapper invDOWN = new SidedInvWrapper(this, EnumFacing.DOWN);
    SidedInvWrapper invNORTH = new SidedInvWrapper(this, EnumFacing.NORTH);
    SidedInvWrapper invSOUTH = new SidedInvWrapper(this, EnumFacing.SOUTH);
    SidedInvWrapper invEAST = new SidedInvWrapper(this, EnumFacing.EAST);
    SidedInvWrapper invWEST = new SidedInvWrapper(this, EnumFacing.WEST);

    public int[] slotsUP = new int[0];
    public int[] slotsDOWN = new int[0];
    public int[] slotsFRONT = new int[0];
    public int[] slotsBACK = new int[0];
    public int[] slotsRIGHT = new int[0];
    public int[] slotsLEFT = new int[0];

    public EnergyStorageModular energyStorage = new EnergyStorageModular(1);
    public Slot[] slots; //Must remain in the same order as the inventory.
    protected ItemStack[] inventory;
    protected int inventoryStackSizeLimit = 64;

    public ItemStack itemBlock;
    protected int progressTimer = 0;
    public int quality;
    public int speed;
    public int tier;
    protected int speedOfMachine = 50; //Must be greater than 0. Higher = more energy cost but more progression.
    protected int progressForOperation = 10000;

    /* ~TEMPORAL FIELDS~ */

    private boolean isInTemporalMode = false;
    public boolean hasReceivedTemporalUpgrade = false;

    protected int energyPerTick;
    protected int progressPerTick;
    protected double energyPerOperation;
    protected double ticksPerOperation;

    /* ~ANTI FIELDS */

    private boolean isInAntiMode = false;
    public boolean hasReceivedAntiUpgrade = false;
    protected Echo echoType;
    protected int echoCount = 0;

    public TileClockworkMachine(int inventorySize, int maxStackSize, int speedOfMachine, int progressForOperation, Echo echoType)
    {
        this.inventory = new ItemStack[inventorySize];
        this.slots = new Slot[inventorySize];
        this.inventoryStackSizeLimit = maxStackSize;
        this.speedOfMachine = speedOfMachine;
        this.progressForOperation = progressForOperation;
        this.echoType = echoType;
    }

    @Override
    public void update()
    {
        boolean isDirty = false;
        if(!this.worldObj.isRemote)
        {
            int energyCostPerTick = getEnergyCostPerTick();
            if(isOperable(energyCostPerTick))
            {
                if(!isInTemporalMode)
                {
                    if(standardWorkUpdateTick(energyCostPerTick))
                    {
                        isDirty = true;
                    }
                }
                else
                {
                    long timeAvailable = getTimeAvailable();
                    int maxActions = Math.min((int) Math.floor(energyStorage.getEnergyStored() / energyPerOperation), (int) Math.floor(timeAvailable / ticksPerOperation));
                    if(maxActions > 0)
                    {
                        int actionsCompleted = temporalActions(isInAntiMode, maxActions);
                        if(actionsCompleted > 0)
                        {
                            energyStorage.extractEnergy((int) (energyPerOperation * actionsCompleted), false);
                            spendTime((long) (actionsCompleted * ticksPerOperation));
                            isDirty = true;
                        }
                    }
                    else if(standardWorkUpdateTick(energyCostPerTick))
                    {
                        isDirty = true;
                    }
                }
            }
        }

        if(isDirty)
            markDirty();
    }

    /**
     * Performs standard work.
     *
     * @return True if this changed stuff, false if not.
     */
    private boolean standardWorkUpdateTick(int energyCostPerTick)
    {
        if(canWork(isInAntiMode))
        {
            if(energyStorage.extractEnergy(energyCostPerTick, false) >= energyCostPerTick)
            {
                this.progressTimer += progressPerTick;
                if(progressTimer >= progressForOperation)
                {
                    progressTimer -= progressForOperation;
                    completeAction(isInAntiMode);
                }
            }
            return true;
        }
        return false;
    }

    public boolean isInAntiMode() {
        return isInAntiMode;
    }

    public boolean isInTemporalMode() {
        return isInTemporalMode;
    }

    public void toggleTemporalMode()
    {
        if(!worldObj.isRemote)
        {
            isInTemporalMode = !isInTemporalMode;
            markDirty();
            worldObj.notifyBlockUpdate(pos, getBlockType().getStateFromMeta(getBlockMetadata()), getBlockType().getActualState(getBlockType().getStateFromMeta(getBlockMetadata()), worldObj, getPos()), 3);
            worldObj.setBlockState(pos, getBlockType().getActualState(getBlockType().getStateFromMeta(getBlockMetadata()), worldObj, getPos()));
            worldObj.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
        }
    }

    public void toggleAntiMode()
    {
        if(!worldObj.isRemote)
        {
            isInAntiMode = !isInAntiMode;
            markDirty();
            worldObj.notifyBlockUpdate(pos, getBlockType().getStateFromMeta(getBlockMetadata()), getBlockType().getActualState(getBlockType().getStateFromMeta(getBlockMetadata()), worldObj, getPos()), 3);
            worldObj.setBlockState(pos, getBlockType().getActualState(getBlockType().getStateFromMeta(getBlockMetadata()), worldObj, getPos()));
            worldObj.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
        }
    }

    public boolean isOperable(int energyCost) {
        return energyStorage.getEnergyStored() >= energyCost && speed > 0 && quality > 0;
    }

    /**
     * @return The number of echoes this tile has access to.
     */
    public int getEchoesAvailable()
    {
        int echoes = this.echoCount;
        TileEntity te;
        for(int i = 0; i < 6; i++)
        {
            te = worldObj.getTileEntity(this.getPos().offset(EnumFacing.getFront(i)));
            if(te != null && te instanceof ITimezoneRelay)
            {
                ITimezone tz = ((ITimezoneRelay) te).getTimezone();
                if(tz != null)
                    echoes += tz.getEchoCountForType(echoType);
            }
        }
        return echoes;
    }

    /**
     * Consumes echoes from both this tile, and any timezones available.
     * @param count Number of echoes to consume.
     * @return The number of echoes that weren't consumed, or 0 if all were.
     */
    protected int consumeEchoes(int count)
    {
        count -= Math.min(count, echoCount);

        TileEntity te;
        for(int i = 0; i < 6; i++)
        {
            if(count <= 0)
                return 0;
            te = worldObj.getTileEntity(this.getPos().offset(EnumFacing.getFront(i)));
            if(te != null && te instanceof ITimezoneRelay)
            {
                ITimezone tz = ((ITimezoneRelay) te).getTimezone();
                if(tz != null)
                {
                    count -= tz.extractEchoes(echoType, count);
                }
            }
        }

        return count;
    }

    /**
     * Produces echoes, prioritizing timezones as storage.
     * @param count Number of echoes to produce.
     * @return Number of echoes that weren't produced.
     */
    protected int produceEchoes(int count)
    {
        TileEntity te;
        for(int i = 0; i < 6; i++)
        {
            if(count <= 0)
                return 0;
            te = worldObj.getTileEntity(this.getPos().offset(EnumFacing.getFront(i)));
            if(te != null && te instanceof ITimezoneRelay)
            {
                ITimezone tz = ((ITimezoneRelay) te).getTimezone();
                if(tz != null)
                {
                    count -= tz.insertEchoes(echoType, count);
                }
            }
        }

        if(count > 0)
        {
            int extras = Math.min(count, 10 - echoCount);
            echoCount += extras;
            count -= extras;
        }

        return count;
    }

    public long getTimeAvailable()
    {
        long time = 0;
        TileEntity te;
        for(int i = 0; i < 6; i++)
        {
            te = worldObj.getTileEntity(this.getPos().offset(EnumFacing.getFront(i)));
            if(te != null && te instanceof ITimezoneRelay)
            {
                ITimezone tz = ((ITimezoneRelay) te).getTimezone();
                if(tz != null)
                    time += tz.getTimeInTicks();
            }
        }
        return time;
    }

    public void spendTime(long timeToSpend)
    {
        TileEntity te;
        int i = 0;
        while(i < 6 && timeToSpend > 0)
        {
            te = worldObj.getTileEntity(this.getPos().offset(EnumFacing.getFront(i)));
            if(te != null && te instanceof ITimezoneRelay)
            {
                ITimezone tz = ((ITimezoneRelay) te).getTimezone();
                if(tz != null)
                {
                    timeToSpend -= tz.extractTime(timeToSpend);
                }
            }
            i++;
        }
    }

    public int getEnergyCostPerTick() {
        return energyPerTick;
    }

    public int getProgressPerTick() {
        return progressPerTick;
    }

    public double getEnergyCostPerOperation() {
        return energyPerOperation;
    }

    public double getTicksPerOperation() {
        return ticksPerOperation;
    }

    @SideOnly(Side.CLIENT)
    public int getProgressScaled(int maxValue) {
        return progressTimer * maxValue / progressForOperation;
    }

    /**
     * Called when speed and/or quality are changed to update various other mathematical values that are stored for the
     * sake of code efficiency.
     */
    public void onUpdateSpeedOrEfficiencyOfMachine()
    {
        //Because the exponential gain will make the timer go up faster, we scale the base cost itself.
        //The base cost should be relative to the amount of work done.
        energyPerTick = ClockworkHelper.getTensionCostFromStatsMachine((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * speedOfMachine), quality, speed) / speedOfMachine;
        progressPerTick = (int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * speedOfMachine);
        if(progressPerTick > 0)
        {
            ticksPerOperation = ((double) progressForOperation / (double) progressPerTick);
            energyPerOperation = ticksPerOperation * (double) energyPerTick;
        }
        else
        {
            ticksPerOperation = Double.MAX_VALUE;
            energyPerOperation = Double.MAX_VALUE;
        }
    }

    /**
     * Called each tick to see if this machine can work. Energy cost is automatically handled, and this method will only
     * be called when there is enough energy to work this tick.
     *
     * Good things to check for:
     * -Is there an item that can be 'processed'?
     * -Can the exports be placed somewhere?
     * -Are there enough echoes to complete a reverse action (if isReversed is true).
     *
     * @param isReversed If true, check if a reverse action can be completed (also check for echoes).
     * @return Whether or not this machine can work during this tick.
     */
    public abstract boolean canWork(boolean isReversed);

    /**
     * Processes a completed action, such as a furnace smelting an ingot. This confirms the action can be completely
     * via the canWork method.
     *
     * @param isReversed If true, complete a reverse action: consume echoes here.
     */
    public abstract void completeAction(boolean isReversed);

    /**
     * Processes up to the specified number of actions instantly. Unlike completeAction, there is no guarantee the
     * canWork method will be true for each action (or at all).
     *
     * This method is not responsible for consumption of energy or time. It is the responsibility of the method caller
     * to consume these according to the return value. Anything else that would be consumed (additional items, fluid)
     * can be consumed within this method.
     *
     * @param isReversed If true, complete reverse actions; check for and consume echoes as well.
     * @param maxNumberOfActions The number of actions we have both the time and energy to complete.
     * @return The number of actions successfully completed.
     */
    public abstract int temporalActions(boolean isReversed, int maxNumberOfActions);

    public abstract boolean canExportToDirection(EnumFacing direction);

    /**
     * Attempt to export the given stack (usually a custom recipe result) to the given slots.
     * @return Leftover items that weren't added to the slots, or null if all found a home.
     */
    public ItemStack exportItem(ItemStack export, int[] slots, boolean simulate)
    {
        int simulatedExportStackSize = export.stackSize;
        for(int i : slots)
        {
            ItemStack stackInSlot = getStackInSlot(i);
            if(stackInSlot == null)
            {
                if(!simulate)
                    setInventorySlotContents(i, export);
                return null;
            }
            else if(ItemHandlerHelper.canItemStacksStack(stackInSlot, export))
            {
                int maxStack = Math.min(this.getInventoryStackLimit(), stackInSlot.getMaxStackSize());
                int amountToMove = Math.min(maxStack - stackInSlot.stackSize, export.stackSize);
                if(!simulate)
                {
                    stackInSlot.stackSize += amountToMove;
                    export.stackSize -= amountToMove;
                    if(export.stackSize <= 0)
                        return null;
                }
                else
                {
                    simulatedExportStackSize -= amountToMove;
                    if(simulatedExportStackSize <= 0)
                        return null;
                }
            }
        }

        if(simulate)
        {
            ItemStack ret = export.copy();
            ret.stackSize = simulatedExportStackSize;
            return ret;
        }
        else
        {
            markDirty();
        }
        return export;
    }

    public void changeIO(EnumFacing direction, int slotID, boolean activate)
    {
        if(!activate)
        {
            if(direction.equals(EnumFacing.UP))
                slotsUP = deactivate(slotsUP, slotID);
            if(direction.equals(EnumFacing.DOWN))
                slotsDOWN = deactivate(slotsDOWN, slotID);
            if(direction.equals(EnumFacing.NORTH))
                slotsFRONT = deactivate(slotsFRONT, slotID);
            if(direction.equals(EnumFacing.SOUTH))
                slotsBACK = deactivate(slotsBACK, slotID);
            if(direction.equals(EnumFacing.EAST))
                slotsRIGHT = deactivate(slotsRIGHT, slotID);
            if(direction.equals(EnumFacing.WEST))
                slotsLEFT = deactivate(slotsLEFT, slotID);
        }
        else
        {
            if(direction.equals(EnumFacing.UP))
                slotsUP = activate(slotsUP, slotID);
            if(direction.equals(EnumFacing.DOWN))
                slotsDOWN = activate(slotsDOWN, slotID);
            if(direction.equals(EnumFacing.NORTH))
                slotsFRONT = activate(slotsFRONT, slotID);
            if(direction.equals(EnumFacing.SOUTH))
                slotsBACK = activate(slotsBACK, slotID);
            if(direction.equals(EnumFacing.EAST))
                slotsRIGHT = activate(slotsRIGHT, slotID);
            if(direction.equals(EnumFacing.WEST))
                slotsLEFT = activate(slotsLEFT, slotID);
        }
        markDirty();
    }

    private int[] activate(int[] slots, int slotID)
    {
        for(int slot : slots)
            if(slot == slotID)
                return slots;

        int[] ret = Arrays.copyOf(slots, slots.length + 1);
        ret[ret.length - 1] = slotID;
        return ret;
    }

    private int[] deactivate(int[] slots, int slotID)
    {
        ArrayList<Integer> retList = new ArrayList<Integer>(slots.length);
        boolean found = false;

        for(int slot : slots)
        {
            if(slot != slotID)
                retList.add(slot);
            else
                found = true;
        }

        if(!found)
            return slots;

        int[] ret = new int[retList.size()];
        for(int i = 0; i < retList.size(); i++)
        {
            int temp = retList.get(i);
            ret[i] = temp;
        }

        return ret;
    }

    public void setTileDataFromItemStack(ItemStack stack)
    {
        IEnergyStorage eStorage = stack.getCapability(ENERGY_STORAGE_CAPABILITY, EnumFacing.DOWN);
        if(eStorage != null)
        {
            energyStorage.setMaxCapacity(eStorage.getMaxEnergyStored());
        }

        IItemHandler cap = stack.getCapability(ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        if(cap != null && cap instanceof ItemStackHandlerClockworkConstruct)
        {
            ItemStackHandlerClockworkConstruct ccHandler = (ItemStackHandlerClockworkConstruct) cap;
            this.speed = ccHandler.getSpeed();
            this.quality = ccHandler.getQuality();
            this.tier = ccHandler.getTier();
        }

        this.itemBlock = stack;
        markDirty();
        onUpdateSpeedOrEfficiencyOfMachine();
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        return index < inventory.length ? inventory[index] : null;
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack is = getStackInSlot(index);
        if(is != null)
        {
            if(count >= is.stackSize)
            {
                setInventorySlotContents(index, null);
            }
            else
            {
                is = is.splitStack(count);
                if(is.stackSize == 0)
                {
                    setInventorySlotContents(index, null);
                }
            }
            markDirty();
        }
        return is;
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        ItemStack ret = inventory[index];
        inventory[index] = null;
        markDirty();
        return ret;
    }

    @Override
    public void setInventorySlotContents(int index, @Nullable ItemStack stack)
    {
        inventory[index] = stack;

        if(stack != null && stack.stackSize > this.getInventoryStackLimit())
            stack.stackSize = this.getInventoryStackLimit();
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return inventoryStackSizeLimit;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {}

    @Override
    public void closeInventory(EntityPlayer player) {}

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        //Confirm we have an array of slots and the index is in-bounds.
        if(slots == null || index >= slots.length)
            return false;

        return slots[index].isItemValid(stack);
    }

    @Override
    public int getField(int id)
    {
        switch(id)
        {
            case 0: //Progress Timer
                return progressTimer;
            case 1: //Energy
                return energyStorage.getEnergyStored();
            case 2: //Max Energy
                return energyStorage.getMaxEnergyStored();
        }
        return 0;
    }

    @Override
    public void setField(int id, int value)
    {
        switch(id)
        {
            case 0: //Progress Timer
                progressTimer = value;
                break;
            case 1: //Energy
                energyStorage.setEnergy(value);
                break;
            case 2: //Max Energy
                energyStorage.setMaxCapacity(value);
                break;
        }
    }

    @Override
    public int getFieldCount() {
        return 3;
    }

    @Override
    public void clear() {
        for(int i = 0; i < inventory.length; i++)
            inventory[i] = null;
        markDirty();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        side = rotate(side);
        if(side.equals(EnumFacing.UP))
            return slotsUP;
        if(side.equals(EnumFacing.DOWN))
            return slotsDOWN;
        if(side.equals(EnumFacing.NORTH))
            return slotsFRONT;
        if(side.equals(EnumFacing.SOUTH))
            return slotsBACK;
        if(side.equals(EnumFacing.WEST))
            return slotsRIGHT;
        if(side.equals(EnumFacing.EAST))
            return slotsLEFT;
        return new int[0];
    }

    /**
     * Translates from a global EnumFacing to a local EnumFacing.
     * In the case of local, the faces are relative to the front, with north being the front face.
     */
    private EnumFacing rotate(EnumFacing facing)
    {
        if(facing.equals(EnumFacing.UP) || facing.equals(EnumFacing.DOWN))
            return facing;

        EnumFacing direction = getBlockType().getStateFromMeta(this.getBlockMetadata()).getValue(BlockClockworkMachine.FACING);

        if(direction.equals(EnumFacing.NORTH))
            return facing;

        facing = facing.rotateAround(EnumFacing.Axis.Y);
        if(direction.equals(EnumFacing.WEST))
            return facing;

        facing = facing.rotateAround(EnumFacing.Axis.Y);
        if(direction.equals(EnumFacing.SOUTH))
            return facing;

        facing = facing.rotateAround(EnumFacing.Axis.Y);
        if(direction.equals(EnumFacing.EAST))
            return facing;

        return facing;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        //Confirm we have an array of slots and the index is in-bounds.
        if(slots == null || index >= slots.length)
            return false;

        //Confirm this index is accessible for the direction.
        boolean foundSlot = false;
        int[] validSlots = getSlotsForFace(direction);
        for(int slot : validSlots)
        {
            if(slot == index)
            {
                foundSlot = true;
                break;
            }
        }

        return foundSlot && slots[index].isItemValid(itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        int[] validSlots = getSlotsForFace(direction);
        for(int slot : validSlots)
            if(slot == index)
                return true;
        return false;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        if(inventory != null)
        {
            NBTTagList nbtList = new NBTTagList();
            for(int index = 0; index < inventory.length; index++)
            {
                if(inventory[index] != null)
                {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setByte("slot_index", (byte)index);
                    inventory[index].writeToNBT(tag);
                    nbtList.appendTag(tag);
                }
            }
            nbt.setTag("machine_inventory", nbtList);
        }

        nbt.setInteger("progress", progressTimer);
        if(energyStorage != null)
        {
            nbt.setInteger("energy", energyStorage.getEnergyStored());
            nbt.setInteger("max_energy", energyStorage.getMaxEnergyStored());
        }

        nbt.setInteger("quality", quality);
        nbt.setInteger("speed", speed);
        nbt.setInteger("tier", tier);

        if(itemBlock != null)
        {
            NBTTagCompound t = new NBTTagCompound();
            itemBlock.writeToNBT(t);
            nbt.setTag("clockwork_stack", t);
        }

        nbt.setIntArray("up_slot", slotsUP);
        nbt.setIntArray("down_slot", slotsDOWN);
        nbt.setIntArray("front_slot", slotsFRONT);
        nbt.setIntArray("back_slot", slotsBACK);
        nbt.setIntArray("left_slot", slotsLEFT);
        nbt.setIntArray("right_slot", slotsRIGHT);

        nbt.setBoolean("has_temporal_upgrade", hasReceivedTemporalUpgrade);
        nbt.setBoolean("is_temporal", isInTemporalMode);

        nbt.setBoolean("has_anti_upgrade", hasReceivedAntiUpgrade);
        nbt.setBoolean("is_anti", isInAntiMode);
        nbt.setInteger("echo_count", echoCount);

        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        if(nbt.hasKey("machine_inventory"))
        {
            NBTTagList tagList = nbt.getTagList("machine_inventory", 10);
            inventory = new ItemStack[getSizeInventory()];
            for(int i = 0; i < tagList.tagCount(); ++i)
            {
                NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
                byte slotIndex = tagCompound.getByte("slot_index");
                if(slotIndex >= 0 && slotIndex < inventory.length)
                    inventory[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
            }
        }

        if(nbt.hasKey("progress"))
            progressTimer = nbt.getInteger("progress");

        if(energyStorage == null)
            energyStorage = new EnergyStorageModular(1);

        if(nbt.hasKey("max_energy"))
            energyStorage.setMaxCapacity(nbt.getInteger("max_energy"));

        if(nbt.hasKey("energy"))
            energyStorage.setEnergy(nbt.getInteger("energy"));

        if(nbt.hasKey("speed"))
            speed = nbt.getInteger("speed");
        if(nbt.hasKey("quality"))
            quality = nbt.getInteger("quality");
        if(nbt.hasKey("tier"))
            tier = nbt.getInteger("tier");
        if(nbt.hasKey("clockwork_stack"))
        {
            NBTTagCompound t = (NBTTagCompound) nbt.getTag("clockwork_stack");
            itemBlock = ItemStack.loadItemStackFromNBT(t);
        }
        onUpdateSpeedOrEfficiencyOfMachine();

        if(nbt.hasKey("up_slot"))
            slotsUP = nbt.getIntArray("up_slot");
        if(nbt.hasKey("down_slot"))
            slotsDOWN = nbt.getIntArray("down_slot");
        if(nbt.hasKey("front_slot"))
            slotsFRONT = nbt.getIntArray("front_slot");
        if(nbt.hasKey("back_slot"))
            slotsBACK = nbt.getIntArray("back_slot");
        if(nbt.hasKey("left_slot"))
            slotsLEFT = nbt.getIntArray("left_slot");
        if(nbt.hasKey("right_slot"))
            slotsRIGHT = nbt.getIntArray("right_slot");

        if(nbt.hasKey("has_temporal_upgrade"))
            hasReceivedTemporalUpgrade = nbt.getBoolean("has_temporal_upgrade");
        if(nbt.hasKey("is_temporal"))
            isInTemporalMode = nbt.getBoolean("is_temporal");

        if(nbt.hasKey("has_anti_upgrade"))
            hasReceivedAntiUpgrade = nbt.getBoolean("has_anti_upgrade");
        if(nbt.hasKey("is_anti"))
            isInAntiMode = nbt.getBoolean("is_anti");
        if(nbt.hasKey("echo_count"))
            echoCount = nbt.getInteger("echo_count");
    }

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing) {
        return capability != null && capability == ITEM_HANDLER_CAPABILITY || capability == ENERGY_STORAGE_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
    {
        if(hasCapability(capability, facing))
        {
            if(capability == ENERGY_STORAGE_CAPABILITY)
            {
                return ENERGY_STORAGE_CAPABILITY.cast(energyStorage);
            }
            else
            {
                if(facing == EnumFacing.UP)
                    return ITEM_HANDLER_CAPABILITY.cast(invUP);
                if(facing == EnumFacing.DOWN)
                    return ITEM_HANDLER_CAPABILITY.cast(invDOWN);
                if(facing == EnumFacing.NORTH)
                    return ITEM_HANDLER_CAPABILITY.cast(invNORTH);
                if(facing == EnumFacing.SOUTH)
                    return ITEM_HANDLER_CAPABILITY.cast(invSOUTH);
                if(facing == EnumFacing.EAST)
                    return ITEM_HANDLER_CAPABILITY.cast(invEAST);
                if(facing == EnumFacing.WEST)
                    return ITEM_HANDLER_CAPABILITY.cast(invWEST);
            }
        }
        return super.getCapability(capability, facing);
    }
}
