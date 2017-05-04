package lumaceon.mods.clockworkphase2.tile.machine;

import lumaceon.mods.clockworkphase2.api.capabilities.EnergyStorageModular;
import lumaceon.mods.clockworkphase2.api.util.ClockworkHelper;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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

public abstract class TileClockworkMachine extends TileMod implements ISidedInventory, ITickable
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

    protected int progressTimer = 0;
    protected int quality = 250;
    protected int speed = 500;
    protected int speedOfMachine = 50; //A good par is about 50 for simple machines. Must be greater than 0.
    protected int progressForOperation = 10000;

    public TileClockworkMachine()
    {
        slots = new Slot[0];
        inventory = new ItemStack[1];
    }

    public TileClockworkMachine(int inventorySize, int maxStackSize, int speedOfMachine, int parEnergyForOperation)
    {
        this.inventory = new ItemStack[inventorySize];
        this.inventoryStackSizeLimit = maxStackSize;
        this.speedOfMachine = speedOfMachine;
        this.progressForOperation = parEnergyForOperation;
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
                if(canWork())
                {
                    if(energyStorage.extractEnergy(energyCostPerTick, false) >= energyCostPerTick)
                    {
                        this.progressTimer += (int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * speedOfMachine);
                        if(progressTimer >= progressForOperation)
                        {
                            progressTimer -= progressForOperation;
                            completeAction();
                        }
                    }
                    isDirty = true;
                }
            }
        }

        if(isDirty)
            markDirty();
    }

    public boolean isOperable(int energyCost) {
        return energyStorage.getEnergyStored() >= energyCost && speed > 0 && quality > 0;
    }

    public int getEnergyCostPerTick() {
        //Because the exponential gain will make the timer go up faster, we scale the base cost itself.
        //The base cost should be relative to the amount of work done.
        return ClockworkHelper.getTensionCostFromStatsMachine((int) (ClockworkHelper.getStandardExponentialSpeedMultiplier(speed) * speedOfMachine), quality, speed) / speedOfMachine;
    }

    @SideOnly(Side.CLIENT)
    public int getProgressScaled(int maxValue) {
        return progressTimer * maxValue / progressForOperation;
    }

    public abstract boolean canWork();

    /**
     * Processes a completed action, such as a furnace smelting an ingot.
     */
    public abstract void completeAction();

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
        if(side.equals(EnumFacing.EAST))
            return slotsRIGHT;
        if(side.equals(EnumFacing.WEST))
            return slotsLEFT;
        return new int[0];
    }

    /**
     * Translates from a global EnumFacing to a local EnumFacing.
     * In the case of local, the faces are relative to the front, with north being the front face.
     */
    private EnumFacing rotate(EnumFacing facing) {
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
