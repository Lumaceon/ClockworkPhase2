package lumaceon.mods.clockworkphase2.capabilities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Extended to store multiple types of fluid in separate internal tanks. Each type of fluid may only be contained in a
 * single tank, so multiple tanks may not have the same type of fluid.
 */
public class FluidHandlerItemStackMultitank implements IFluidHandlerItem
{
    protected ItemStack container;
    protected NonNullList<FluidTank> tanks;
    protected int selectedTank = 0;

    public FluidHandlerItemStackMultitank(ItemStack container, @Nonnull NonNullList<FluidTank> tanks) {
        this.container = container;
        this.tanks = tanks;
    }

    public void setSelectedTank(int tankIndex) {
        selectedTank = tankIndex;
    }

    public int getSelectedTank() {
        return selectedTank;
    }

    public int selectNextTank()
    {
        if(selectedTank + 1 >= tanks.size())
        {
            selectedTank = 0;
        }
        else
        {
            ++selectedTank;
        }

        return selectedTank;
    }

    public int selectPreviousTank()
    {
        if(selectedTank - 1 < 0)
        {
            selectedTank = tanks.size() - 1;
        }
        else
        {
            --selectedTank;
        }

        return selectedTank;
    }

    @Nonnull
    @Override
    public ItemStack getContainer() {
        return this.container;
    }

    @Override
    public IFluidTankProperties[] getTankProperties()
    {
        IFluidTankProperties[] ret = new IFluidTankProperties[tanks.size()];
        for(int i = 0; i < ret.length; i++)
        {
            IFluidTankProperties[] temp = tanks.get(i).getTankProperties();
            if(temp == null || temp.length < 1)
            {
                ret[i] = new FluidTankProperties(null, 0);
            }
            else
            {
                ret[i] = temp[0];
            }
        }
        return ret;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill)
    {
        FluidTank tank = null;
        FluidTank firstEmpty = null;
        boolean alreadyHas = false;
        for(int i = 0; i < tanks.size(); i++)
        {
            tank = tanks.get(i);
            FluidStack fs = tank.getFluid();
            if(fs != null)
            {
                if(fs.isFluidEqual(resource))
                {
                    alreadyHas = true;
                    break;
                }
            }
            else if(firstEmpty == null)
            {
                firstEmpty = tank;
            }
        }

        if(alreadyHas)
        {
            return tank.fillInternal(resource, doFill);
        }
        else if(firstEmpty != null)
        {
            return firstEmpty.fillInternal(resource, doFill);
        }

        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain)
    {
        for(FluidTank tank : tanks)
        {
            FluidStack fs = tank.getFluid();
            if(fs != null && fs.isFluidEqual(resource))
            {
                return tank.drainInternal(resource, doDrain);
            }
        }

        return null;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain)
    {
        if(selectedTank < tanks.size())
        {
            FluidTank tank = tanks.get(selectedTank);
            FluidStack fs = tank.getFluid();
            if(fs != null)
            {
                return tank.drainInternal(maxDrain, doDrain);
            }
        }

        return null;
    }

    public NBTTagCompound serializeNBT()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for(FluidTank tank : tanks)
        {
            NBTTagCompound tag = new NBTTagCompound();
            tank.writeToNBT(tag);
            list.appendTag(tag);
        }
        nbt.setTag("fluid_tanks", list);

        nbt.setInteger("selected", selectedTank);

        return nbt;
    }

    public void deserializeNBT(NBTTagCompound nbt)
    {
        if(nbt == null)
            return;

        if(nbt.hasKey("fluid_tanks"))
        {
            NBTTagList list = nbt.getTagList("fluid_tanks", Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < list.tagCount(); i++)
            {
                NBTTagCompound tag = list.getCompoundTagAt(i);
                tanks.get(i).readFromNBT(tag);
            }
        }

        if(nbt.hasKey("selected"))
        {
            selectedTank = nbt.getInteger("selected");
        }
    }
}
