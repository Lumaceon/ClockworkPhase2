package lumaceon.mods.clockworkphase2.tile.machine;

import lumaceon.mods.clockworkphase2.api.capabilities.EnergyStorageModular;
import lumaceon.mods.clockworkphase2.api.capabilities.ItemStackHandlerClockworkConstruct;
import lumaceon.mods.clockworkphase2.api.temporal.ITemporalTile;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITemporalRelay;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITimeSink;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.block.machine.BlockClockworkMachine;
import lumaceon.mods.clockworkphase2.capabilities.machinedata.IMachineDataHandler;
import lumaceon.mods.clockworkphase2.item.temporal.ItemMachineUpgrade;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import lumaceon.mods.clockworkphase2.util.FluidTankSided;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classes extending this should also set the slots field after calling the super constructor.
 */
@SuppressWarnings("deprecation")
public abstract class TileClockworkMachine extends TileMod implements ISidedInventory, ITickable, ITimeSink, ITemporalTile
{
    @CapabilityInject(IItemHandler.class)
    static Capability<IItemHandler> ITEM_HANDLER_CAPABILITY = null;
    @CapabilityInject(IEnergyStorage.class)
    static Capability<IEnergyStorage> ENERGY_STORAGE_CAPABILITY = null;
    @CapabilityInject(IMachineDataHandler.class)
    public static final Capability<IMachineDataHandler> MACHINE_DATA = null;

    protected int[] EXPORT_SLOTS = new int[] {};

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

    public FluidTankSided[] fluidTanks = new FluidTankSided[0];

    public EnergyStorageModular energyStorage;
    public Slot[] slots; //Must remain in the same order as the inventory.
    protected NonNullList<ItemStack> inventory;
    protected int inventoryStackSizeLimit = 64;

    public ItemStack itemBlock = ItemStack.EMPTY;

    protected int progressTimer = 0;
    public int quality;
    public int speed;
    public int tier;
    protected int speedOfMachine = 50; //Must be greater than 0. Higher = more energy cost but more progression.
    protected int progressForOperation = 10000;

    public long internalTimer = 0;

    /* ~TEMPORAL FIELDS~ */

    private boolean isInTemporalMode = false;
    public boolean hasReceivedTemporalUpgrade = false;

    protected int energyPerTick;
    protected int progressPerTick;
    protected double energyPerOperation;
    protected double ticksPerOperation;

    public TileClockworkMachine(int inventorySize, int maxStackSize, int speedOfMachine, int progressForOperation)
    {
        this.inventory = NonNullList.withSize(inventorySize, ItemStack.EMPTY);
        this.slots = new Slot[inventorySize];
        this.inventoryStackSizeLimit = maxStackSize;
        this.speedOfMachine = speedOfMachine;
        this.progressForOperation = progressForOperation;
        this.energyStorage = new EnergyStorageModular(0);
    }

    @Override
    public void update()
    {
        ++internalTimer;
        boolean isDirty = false;
        if(!this.world.isRemote)
        {
            //Every so often, attempt to export any stacks remaining in the export slots.
            if(internalTimer % 50 == 0)
            {
                ArrayList<ItemStack> exportStacks = new ArrayList<>();
                for(int slot : EXPORT_SLOTS)
                {
                    ItemStack stack = getStackInSlot(slot);
                    if(!stack.isEmpty())
                    {
                        exportStacks.add(stack);
                    }
                    setInventorySlotContents(slot, ItemStack.EMPTY);
                }

                if(!exportStacks.isEmpty())
                {
                    outputItems(exportStacks);
                }
            }

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
                    int approximateTicksComplete = (int) ((double) progressTimer / (double) progressPerTick);
                    int maxActions = Math.min((int) Math.floor((energyStorage.getEnergyStored() + energyCostPerTick * approximateTicksComplete) / energyPerOperation), (int) Math.floor((timeAvailable + approximateTicksComplete) / ticksPerOperation));
                    if(maxActions > 0)
                    {
                        int actionsCompleted = temporalActions(maxActions);
                        if(actionsCompleted > 0)
                        {
                            //Extract the energy per operation, times the actions completed, minus the energy already spent.
                            energyStorage.extractEnergy((int) (energyPerOperation * actionsCompleted) - energyCostPerTick * approximateTicksComplete, false);
                            spendTime((long) (actionsCompleted * ticksPerOperation) - approximateTicksComplete);
                            progressTimer = 0;
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
        if(canWork())
        {
            if(energyStorage.extractEnergy(energyCostPerTick, false) >= energyCostPerTick)
            {
                this.progressTimer += progressPerTick;
                if(progressTimer >= progressForOperation)
                {
                    progressTimer -= progressForOperation;
                    completeAction();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hasReceivedTemporalUpgrade() {
        return hasReceivedTemporalUpgrade;
    }

    @Override
    public boolean isInTemporalMode() {
        return isInTemporalMode;
    }

    @Override
    public void toggleTemporalMode()
    {
        if(!world.isRemote && hasReceivedTemporalUpgrade)
        {
            isInTemporalMode = !isInTemporalMode;
            updateItemBlockData();
            markDirty();
            world.notifyBlockUpdate(pos, getBlockType().getStateFromMeta(getBlockMetadata()), getBlockType().getActualState(getBlockType().getStateFromMeta(getBlockMetadata()), world, getPos()), 3);
            world.setBlockState(pos, getBlockType().getActualState(getBlockType().getStateFromMeta(getBlockMetadata()), world, getPos()));
            world.markBlockRangeForRenderUpdate(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
            world.scheduleBlockUpdate(pos, getBlockType(), 0, 0);
        }
    }

    public boolean isOperable(int energyCost) {
        return energyStorage.getEnergyStored() >= energyCost && speed > 0 && quality > 0;
    }

    public long getTimeAvailable()
    {
        long time = 0;
        TileEntity te;
        for(int i = 0; i < 6; i++)
        {
            te = world.getTileEntity(this.getPos().offset(EnumFacing.getFront(i)));
            if(te != null && te instanceof ITemporalRelay)
            {
                List<ITimezone> timezones = ((ITemporalRelay) te).getTimezones();
                for(ITimezone timezone : timezones)
                    if(timezone != null)
                        time += timezone.getTimeInTicks();
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
            te = world.getTileEntity(this.getPos().offset(EnumFacing.getFront(i)));
            if(te != null && te instanceof ITemporalRelay)
            {
                List<ITimezone> timezones = ((ITemporalRelay) te).getTimezones();
                for(ITimezone timezone : timezones)
                {
                    if(timezone != null)
                    {
                        timeToSpend -= timezone.extractTime(timeToSpend);
                        if(timeToSpend <= 0)
                        {
                            return;
                        }
                    }
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
        energyPerTick = (ClockworkHelper.getTensionCostFromStatsMachine((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * 0.3 * speedOfMachine), quality, speed));

        if(energyPerTick < 1)
            energyPerTick = 1;

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
     *
     * @return Whether or not this machine can work during this tick.
     */
    public abstract boolean canWork();

    /**
     * Processes a completed action, such as a furnace smelting an ingot. This confirms the action can be completely
     * via the canWork method.
     */
    public abstract void completeAction();

    /**
     * Processes up to the specified number of actions instantly. Unlike completeAction, there is no guarantee the
     * canWork method will be true for each action (or at all).
     *
     * This method is not responsible for consumption of energy or time. It is the responsibility of the method caller
     * to consume these according to the return value. Anything else that would be consumed (additional items, fluid)
     * can be consumed within this method.
     *
     * @param maxNumberOfActions The number of actions we have both the time and energy to complete.
     * @return The number of actions successfully completed.
     */
    public abstract int temporalActions(int maxNumberOfActions);

    /**
     * Attempt to export the given stack (usually a custom recipe result) to the given slots.
     * @return Leftover items that weren't added to the slots, or null if all found a home.
     */
    public ItemStack exportItem(ItemStack export, int[] slots, boolean simulate)
    {
        int simulatedExportStackSize = export.getCount();
        for(int i : slots)
        {
            ItemStack stackInSlot = getStackInSlot(i);
            if(stackInSlot.isEmpty())
            {
                if(!simulate)
                    setInventorySlotContents(i, export);
                return ItemStack.EMPTY;
            }
            else if(ItemHandlerHelper.canItemStacksStack(stackInSlot, export))
            {
                int maxStack = Math.min(this.getInventoryStackLimit(), stackInSlot.getMaxStackSize());
                int amountToMove = Math.min(maxStack - stackInSlot.getCount(), export.getCount());
                if(!simulate)
                {
                    stackInSlot.grow(amountToMove);
                    export.shrink(amountToMove);
                    if(export.getCount() <= 0)
                        return ItemStack.EMPTY;
                }
                else
                {
                    simulatedExportStackSize -= amountToMove;
                    if(simulatedExportStackSize <= 0)
                        return ItemStack.EMPTY;
                }
            }
        }

        if(simulate)
        {
            ItemStack ret = export.copy();
            ret.setCount(simulatedExportStackSize);
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
        updateItemBlockData();
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

    public void changeTankIO(EnumFacing direction, int tankID, boolean activate)
    {
        if(fluidTanks.length > tankID)
        {
            FluidTankSided tank = fluidTanks[tankID];
            if(tank != null)
            {
                tank.setSideAvailable(direction, activate);
            }
        }

        updateItemBlockData();
        markDirty();
    }

    /**
     * Called after this tile is created in the world, so the data saved in the itemstack can be applied to the tile.
     */
    public void setTileDataFromItemStack(ItemStack stack)
    {
        IEnergyStorage e = stack.getCapability(ENERGY_STORAGE_CAPABILITY, EnumFacing.DOWN);
        if(e != null && e instanceof EnergyStorageModular)
            energyStorage = (EnergyStorageModular) e;

        IItemHandler cap = stack.getCapability(ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);
        if(cap != null)
        {
            if(cap instanceof ItemStackHandlerClockworkConstruct)
            {
                ItemStackHandlerClockworkConstruct ccHandler = (ItemStackHandlerClockworkConstruct) cap;
                this.speed = ccHandler.getSpeed();
                this.quality = ccHandler.getQuality();
                this.tier = ccHandler.getTier();
            }

            for(int i = 0; i < cap.getSlots(); i++)
            {
                ItemStack item = cap.getStackInSlot(i);
                if(!item.isEmpty())
                {
                    if(item.getItem() instanceof ItemMachineUpgrade)
                    {
                        hasReceivedTemporalUpgrade = true;
                    }
                }
            }
        }

        IMachineDataHandler data = stack.getCapability(MACHINE_DATA, EnumFacing.DOWN);
        if(data != null)
        {
            slotsUP = data.getSlotsForDirection(EnumFacing.UP);
            slotsDOWN = data.getSlotsForDirection(EnumFacing.DOWN);
            slotsFRONT = data.getSlotsForDirection(EnumFacing.NORTH);
            slotsBACK = data.getSlotsForDirection(EnumFacing.SOUTH);
            slotsLEFT = data.getSlotsForDirection(EnumFacing.WEST);
            slotsRIGHT = data.getSlotsForDirection(EnumFacing.EAST);
            isInTemporalMode = data.getIsTemporal();
            FluidTankSided[] tanks = data.getFluidTanks();
            if(tanks != null && tanks.length > 0)
            {
                fluidTanks = tanks;
            }
        }

        this.itemBlock = stack;
        markDirty();
        onUpdateSpeedOrEfficiencyOfMachine();
    }

    protected void updateItemBlockData()
    {
        if(itemBlock.isEmpty())
            return;

        IMachineDataHandler data = itemBlock.getCapability(MACHINE_DATA, EnumFacing.DOWN);
        if(data == null)
            return;

        data.setIsTemporal(isInTemporalMode);
        data.setSlotsForDirection(slotsUP, EnumFacing.UP);
        data.setSlotsForDirection(slotsDOWN, EnumFacing.DOWN);
        data.setSlotsForDirection(slotsFRONT, EnumFacing.NORTH);
        data.setSlotsForDirection(slotsBACK, EnumFacing.SOUTH);
        data.setSlotsForDirection(slotsLEFT, EnumFacing.WEST);
        data.setSlotsForDirection(slotsRIGHT, EnumFacing.EAST);
        data.setFluidTanks(fluidTanks);
    }

    /**
     * Used in exports to confirm that the slots are configured to allow exporting in the given direction.
     * Can mostly be ignored, if you set EXPORT_SLOTS properly.
     */
    private boolean canExportToDirection(EnumFacing direction)
    {
        int[] slots = getSlotsForFace(direction);
        for(int i : slots)
            for(int n : EXPORT_SLOTS)
                if(i == n)
                    return true;

        return false;
    }

    @Override
    public int getSizeInventory() {
        return inventory.size();
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index < inventory.size() ? inventory.get(index) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        ItemStack is = getStackInSlot(index);
        if(!is.isEmpty())
        {
            if(count >= is.getCount())
            {
                setInventorySlotContents(index, ItemStack.EMPTY);
            }
            else
            {
                is = is.splitStack(count);
                if(is.getCount() == 0)
                {
                    setInventorySlotContents(index, ItemStack.EMPTY);
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
        ItemStack ret = inventory.get(index);
        inventory.set(index, ItemStack.EMPTY);
        markDirty();
        return ret;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack)
    {
        inventory.set(index, stack);

        if(!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit())
            stack.setCount(this.getInventoryStackLimit());
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return inventoryStackSizeLimit;
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
    public boolean isEmpty()
    {
        for(ItemStack is : inventory)
            if(is != null)
                return false;
        return true;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
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
            default:
                return id - 3 < fluidTanks.length ? fluidTanks[id - 3].getFluidAmount() : 0;
        }
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
            default:
                if(fluidTanks.length > id - 3 && fluidTanks[id - 3] != null)
                {
                    FluidStack fluid = fluidTanks[id - 3].getFluid();
                    if(fluid != null)
                    {
                        fluid.amount = value;
                    }
                }
                break;
        }
    }

    @Override
    public int getFieldCount() {
        return 3 + fluidTanks.length;
    }

    @Override
    public void clear() {
        for(int i = 0; i < inventory.size(); i++)
            inventory.set(i, ItemStack.EMPTY);
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
        {
            return slotsUP;
        }
        if(side.equals(EnumFacing.DOWN))
        {
            return slotsDOWN;
        }
        if(side.equals(EnumFacing.NORTH))
        {
            return slotsFRONT;
        }
        if(side.equals(EnumFacing.SOUTH))
        {
            return slotsBACK;
        }
        if(side.equals(EnumFacing.WEST))
        {
            return slotsRIGHT;
        }
        if(side.equals(EnumFacing.EAST))
        {
            return slotsLEFT;
        }
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

    protected ArrayList<ItemStack> outputItems(ArrayList<ItemStack> items)
    {
        for(int i = 0; i < 6; i++)
        {
            if(items.isEmpty())
                break; //If we're coming back here and the output is empty, there's no need to keep looping.

            EnumFacing direction = EnumFacing.getFront(i);
            if(canExportToDirection(direction))
            {
                TileEntity te = world.getTileEntity(pos.offset(direction));
                if(te != null)
                {
                    direction = direction.getOpposite();
                    IItemHandler cap = te.getCapability(ITEM_HANDLER_CAPABILITY, direction);
                    if(cap != null)
                    {
                        //if(itemsInOutputSlot != null)
                        //    setInventorySlotContents(EXPORT_SLOTS[0], ItemHandlerHelper.insertItem(cap, itemsInOutputSlot, false));

                        //Try importing items until there's leftovers, which usually means the target inventory is full.
                        for(int n = 0; n < items.size(); n++)
                        {
                            ItemStack temp = items.get(n);
                            ItemStack leftover = ItemHandlerHelper.insertItem(cap, temp, false);
                            if(!leftover.isEmpty())
                            {
                                items.set(n, leftover);
                                break; //If there's leftover, that means the target is full, so we can stop.
                            }
                            else
                            {
                                items.remove(n);
                                n--;
                            }
                        }
                    }
                }
            }
        }

        if(!items.isEmpty())
        {
            for(int n = 0; n < items.size(); n++)
            {
                ItemStack temp = items.get(n);
                ItemStack leftover = exportItem(temp, EXPORT_SLOTS, false);
                if(!leftover.isEmpty())
                {
                    items.set(n, leftover);
                }
                else
                {
                    items.remove(n);
                    n--;
                }
            }
        }

        return items;
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

        if(!itemBlock.isEmpty())
        {
            NBTTagCompound t = new NBTTagCompound();
            itemBlock.writeToNBT(t);
            nbt.setTag("clockwork_stack", t);
        }

        if(inventory != null)
        {
            NBTTagList nbtList = new NBTTagList();
            for(int index = 0; index < inventory.size(); index++)
            {
                if(!inventory.get(index).isEmpty())
                {
                    NBTTagCompound tag = new NBTTagCompound();
                    tag.setByte("slot_index", (byte)index);
                    inventory.get(index).writeToNBT(tag);
                    nbtList.appendTag(tag);
                }
            }
            nbt.setTag("machine_inventory", nbtList);
        }

        nbt.setInteger("progress", progressTimer);

        nbt.setInteger("quality", quality);
        nbt.setInteger("speed", speed);
        nbt.setInteger("tier", tier);

        nbt.setIntArray("up_slot", slotsUP);
        nbt.setIntArray("down_slot", slotsDOWN);
        nbt.setIntArray("front_slot", slotsFRONT);
        nbt.setIntArray("back_slot", slotsBACK);
        nbt.setIntArray("left_slot", slotsLEFT);
        nbt.setIntArray("right_slot", slotsRIGHT);

        if(fluidTanks != null)
        {
            NBTTagList list = new NBTTagList();
            for(FluidTankSided tank : fluidTanks)
            {
                NBTTagCompound t = new NBTTagCompound();
                t = tank.writeToNBT(t);
                list.appendTag(t);
            }
            nbt.setTag("fluid_tanks", list);
        }

        nbt.setBoolean("has_temporal_upgrade", hasReceivedTemporalUpgrade);
        nbt.setBoolean("is_temporal", isInTemporalMode);

        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        if(nbt.hasKey("clockwork_stack"))
        {
            NBTTagCompound t = (NBTTagCompound) nbt.getTag("clockwork_stack");
            itemBlock = new ItemStack(t);
            IEnergyStorage e = itemBlock.getCapability(ENERGY_STORAGE_CAPABILITY, EnumFacing.DOWN);
            if(e != null && e instanceof EnergyStorageModular)
                energyStorage = (EnergyStorageModular) e;
        }
        else
        {
            energyStorage = new EnergyStorageModular(0);
        }

        if(nbt.hasKey("machine_inventory"))
        {
            NBTTagList tagList = nbt.getTagList("machine_inventory", 10);
            inventory = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
            for(int i = 0; i < tagList.tagCount(); ++i)
            {
                NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
                byte slotIndex = tagCompound.getByte("slot_index");
                if(slotIndex >= 0 && slotIndex < inventory.size())
                    inventory.set(slotIndex, new ItemStack(tagCompound));
            }
        }

        if(nbt.hasKey("progress"))
            progressTimer = nbt.getInteger("progress");

        if(nbt.hasKey("speed"))
            speed = nbt.getInteger("speed");
        if(nbt.hasKey("quality"))
            quality = nbt.getInteger("quality");
        if(nbt.hasKey("tier"))
            tier = nbt.getInteger("tier");
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

        if(nbt.hasKey("fluid_tanks"))
        {
            NBTTagList list = nbt.getTagList("fluid_tanks", Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < list.tagCount(); i++)
            {
                NBTTagCompound t = list.getCompoundTagAt(i);
                if(fluidTanks.length > i)
                {
                    fluidTanks[i] = (FluidTankSided) fluidTanks[i].readFromNBT(t);
                }
            }
        }

        if(nbt.hasKey("has_temporal_upgrade"))
            hasReceivedTemporalUpgrade = nbt.getBoolean("has_temporal_upgrade");
        if(nbt.hasKey("is_temporal"))
            isInTemporalMode = nbt.getBoolean("is_temporal");
    }

    @Override
    public boolean hasCapability(net.minecraftforge.common.capabilities.Capability<?> capability, net.minecraft.util.EnumFacing facing)
    {
        if(capability == null)
            return false;

        if(capability == ITEM_HANDLER_CAPABILITY || capability == ENERGY_STORAGE_CAPABILITY)
            return true;

        if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            EnumFacing localDirection = rotate(facing);
            for(FluidTankSided tank : fluidTanks)
            {
                if(tank != null && tank.isAvailableForSide(localDirection))
                    return true;
            }
        }

        return super.hasCapability(capability, facing);
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
            else if(capability == ITEM_HANDLER_CAPABILITY)
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
            else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            {
                EnumFacing localDirection = rotate(facing);
                for(FluidTankSided tank : fluidTanks)
                {
                    if(tank != null && tank.isAvailableForSide(localDirection))
                    {
                        return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
                    }
                }
            }
        }
        return super.getCapability(capability, facing);
    }
}
