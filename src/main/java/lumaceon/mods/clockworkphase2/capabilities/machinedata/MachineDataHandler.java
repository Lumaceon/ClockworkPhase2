package lumaceon.mods.clockworkphase2.capabilities.machinedata;

import lumaceon.mods.clockworkphase2.util.FluidTankSided;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants;

public class MachineDataHandler implements IMachineDataHandler
{
    public FluidTankSided[] fluidTanks = new FluidTankSided[0];

    public int[] slotsUP = new int[0];
    public int[] slotsDOWN = new int[0];
    public int[] slotsFRONT = new int[0];
    public int[] slotsBACK = new int[0];
    public int[] slotsRIGHT = new int[0];
    public int[] slotsLEFT = new int[0];

    private boolean isInTemporalMode = false;

    @Override
    public void setSlotsForDirection(int[] slots, EnumFacing direction)
    {
        if(direction.equals(EnumFacing.UP))
            slotsUP = slots;
        if(direction.equals(EnumFacing.DOWN))
            slotsDOWN = slots;
        if(direction.equals(EnumFacing.NORTH))
            slotsFRONT = slots;
        if(direction.equals(EnumFacing.SOUTH))
            slotsBACK = slots;
        if(direction.equals(EnumFacing.EAST))
            slotsRIGHT = slots;
        if(direction.equals(EnumFacing.WEST))
            slotsLEFT = slots;
    }

    @Override
    public int[] getSlotsForDirection(EnumFacing direction)
    {
        if(direction.equals(EnumFacing.UP))
            return slotsUP;
        else if(direction.equals(EnumFacing.DOWN))
            return slotsDOWN;
        else if(direction.equals(EnumFacing.NORTH))
            return slotsFRONT;
        else if(direction.equals(EnumFacing.SOUTH))
            return slotsBACK;
        else if(direction.equals(EnumFacing.EAST))
            return slotsRIGHT;
        else if(direction.equals(EnumFacing.WEST))
            return slotsLEFT;
        return new int[0];
    }

    @Override
    public void setFluidTanks(FluidTankSided[] fluidTanks) {
        this.fluidTanks = fluidTanks;
    }

    @Override
    public FluidTankSided[] getFluidTanks() {
        return fluidTanks;
    }

    @Override
    public void setIsTemporal(boolean isTemporal) {
        this.isInTemporalMode = isTemporal;
    }

    @Override
    public boolean getIsTemporal() {
        return isInTemporalMode;
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setIntArray("slots_up", slotsUP);
        nbt.setIntArray("slots_down", slotsDOWN);
        nbt.setIntArray("slots_front", slotsFRONT);
        nbt.setIntArray("slots_back", slotsBACK);
        nbt.setIntArray("slots_right", slotsRIGHT);
        nbt.setIntArray("slots_left", slotsLEFT);
        nbt.setBoolean("is_temporal", isInTemporalMode);

        if(fluidTanks != null && fluidTanks.length > 0)
        {
            NBTTagList list = new NBTTagList();
            for(FluidTankSided tank : fluidTanks)
            {
                NBTTagCompound tag = new NBTTagCompound();
                tank.writeToNBT(tag);
                list.appendTag(tag);
            }
            nbt.setTag("fluid_tanks", list);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        slotsUP = nbt.getIntArray("slots_up");
        slotsDOWN = nbt.getIntArray("slots_down");
        slotsFRONT = nbt.getIntArray("slots_front");
        slotsBACK = nbt.getIntArray("slots_back");
        slotsRIGHT = nbt.getIntArray("slots_right");
        slotsLEFT = nbt.getIntArray("slots_left");
        isInTemporalMode = nbt.getBoolean("is_temporal");

        if(nbt.hasKey("fluid_tanks"))
        {
            NBTTagList list = nbt.getTagList("fluid_tanks", Constants.NBT.TAG_COMPOUND);
            fluidTanks = new FluidTankSided[list.tagCount()];
            for(int i = 0; i < list.tagCount(); i++)
            {
                fluidTanks[i] = new FluidTankSided(1);
                NBTTagCompound tag = list.getCompoundTagAt(i);
                fluidTanks[i].readFromNBT(tag);
            }
        }
    }
}
