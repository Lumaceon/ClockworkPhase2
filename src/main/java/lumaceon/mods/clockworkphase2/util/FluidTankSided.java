package lumaceon.mods.clockworkphase2.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

public class FluidTankSided extends FluidTank
{
    boolean up, down, left, right, back = false;

    public FluidTankSided(int capacity) {
        super(capacity);
    }

    public FluidTankSided(@Nullable FluidStack fluidStack, int capacity) {
        super(fluidStack, capacity);
    }

    public FluidTankSided(Fluid fluid, int amount, int capacity) {
        super(fluid, amount, capacity);
    }

    public void setSideAvailable(EnumFacing direction, boolean available)
    {
        if(direction.equals(EnumFacing.UP))
            up = available;
        if(direction.equals(EnumFacing.DOWN))
            down = available;
        if(direction.equals(EnumFacing.SOUTH))
            back = available;
        if(direction.equals(EnumFacing.WEST))
            left = available;
        if(direction.equals(EnumFacing.EAST))
            right = available;
    }

    public boolean isAvailableForSide(EnumFacing direction)
    {
        if(direction.equals(EnumFacing.UP) && up)
            return true;
        if(direction.equals(EnumFacing.DOWN) && down)
            return true;
        if(direction.equals(EnumFacing.SOUTH) && back)
            return true;
        if(direction.equals(EnumFacing.WEST) && left)
            return true;
        if(direction.equals(EnumFacing.EAST) && right)
            return true;
        return false;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        NBTTagCompound tag = super.writeToNBT(nbt);

        tag.setInteger("capacity", capacity);

        tag.setBoolean("up", up);
        tag.setBoolean("down", down);
        tag.setBoolean("left", left);
        tag.setBoolean("right", right);
        tag.setBoolean("back", back);

        return tag;
    }

    @Override
    public FluidTank readFromNBT(NBTTagCompound nbt)
    {
        FluidTank tank = super.readFromNBT(nbt);

        if(nbt.hasKey("capacity"))
            this.capacity = nbt.getInteger("capacity");


        if(nbt.hasKey("up"))
            up = nbt.getBoolean("up");

        if(nbt.hasKey("down"))
            down = nbt.getBoolean("down");

        if(nbt.hasKey("left"))
            left = nbt.getBoolean("left");

        if(nbt.hasKey("right"))
            right = nbt.getBoolean("right");

        if(nbt.hasKey("back"))
            back = nbt.getBoolean("back");


        return tank;
    }
}
