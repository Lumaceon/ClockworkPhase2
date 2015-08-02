package lumaceon.mods.clockworkphase2.tile.steammachine;

import cpw.mods.fml.common.network.NetworkRegistry;
import lumaceon.mods.clockworkphase2.init.ModFluids;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTileStateChange;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhaseInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileBoiler extends TileClockworkPhaseInventory implements IFluidHandler
{
    public FluidStack water, steam;
    public final int maxCapacity = 16000;
    public int burnTimeRemaining = 0; //Each item in a vanilla furnace takes 200 ticks of burn time to smelt.

    public int tileState = 0;

    public TileBoiler() {
        this.inventory = new ItemStack[2];
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        NBTTagCompound waterNBT = new NBTTagCompound();
        water.writeToNBT(waterNBT);
        nbt.setTag("water_tank", waterNBT);

        NBTTagCompound steamNBT = new NBTTagCompound();
        steam.writeToNBT(steamNBT);
        nbt.setTag("steam_tank", steamNBT);

        nbt.setInteger("burn_time", burnTimeRemaining);
        nbt.setInteger("state", tileState);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        water = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbt.getTag("water_tank"));
        steam = FluidStack.loadFluidStackFromNBT((NBTTagCompound) nbt.getTag("steam_tank"));
        burnTimeRemaining = nbt.getInteger("burn_time");
        tileState = nbt.getInteger("state");
    }

    @Override
    public void setState(int state) {
        tileState = state;
    }

    @Override
    public void setStateAndUpdate(int state)
    {
        if(!worldObj.isRemote)
        {
            setState(state);
            PacketHandler.INSTANCE.sendToAllAround(new MessageTileStateChange(xCoord, yCoord, zCoord, state), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId, xCoord, yCoord, zCoord, 200));
        }
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        boolean wasPreviouslyBurning = this.burnTimeRemaining > 0;
        boolean isBurning = this.burnTimeRemaining > 0;
        boolean isDirty = false;

        if(!worldObj.isRemote)
        {
            if(water != null && water.amount > 0 && (steam == null || steam.amount < maxCapacity))//There is water and the steam tank is not full.
            {
                if(!isBurning && getStackInSlot(1) != null && TileEntityFurnace.getItemBurnTime(getStackInSlot(1)) / 8 > 0)
                {
                    ItemStack fuel = getStackInSlot(1);
                    this.burnTimeRemaining = TileEntityFurnace.getItemBurnTime(getStackInSlot(1)) / 8;
                    if(fuel.stackSize == 1)
                        setInventorySlotContents(1, fuel.getItem().getContainerItem(fuel));
                    else
                        decrStackSize(1, 1);
                    isBurning = true;
                    isDirty = true;
                }

                if(isBurning)
                {
                    int amount = Math.min(400, water.amount);
                    amount = Math.min(amount, maxCapacity - steam.amount);
                    water.amount -= amount;
                    steam.amount += amount;
                    burnTimeRemaining--;
                    isDirty = true;
                }
            }

            if(wasPreviouslyBurning != isBurning)
                setStateAndUpdate(isBurning ? 1 : 0);
        }

        if(isDirty)
            markDirty();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill)
    {
        if(canFill(from, resource.getFluid()))
        {
            if(water == null)
            {
                if(doFill)
                    water = new FluidStack(resource, resource.amount);
                return resource.amount;
            }
            else if(water.amount < maxCapacity)
            {
                int amountToAdd = water.amount + resource.amount > maxCapacity ? maxCapacity - water.amount : resource.amount;
                if(doFill)
                    water.amount += amountToAdd;
                return amountToAdd;
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain)
    {
        if(canDrain(from, resource.getFluid()))
        {
            if(steam != null && steam.amount > 0)
            {
                if(steam.amount >= resource.amount)
                {
                    if(doDrain)
                        steam.amount -= resource.amount;
                    return resource;
                }
                else
                {
                    FluidStack drained = new FluidStack(steam, steam.amount);
                    if(doDrain)
                        steam.amount = 0;
                    return drained;
                }
            }
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain)
    {
        if(canDrain(from, ModFluids.steam))
        {
            if(steam != null && steam.amount > 0)
            {
                if(steam.amount >= maxDrain)
                {
                    if(doDrain)
                        steam.amount -= maxDrain;
                    return new FluidStack(steam, maxDrain);
                }
                else
                {
                    FluidStack drained = new FluidStack(steam, steam.amount);
                    if(doDrain)
                        steam.amount = 0;
                    return drained;
                }
            }
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluid.equals(FluidRegistry.WATER) && !from.equals(ForgeDirection.UP);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return fluid.equals(ModFluids.steam) && from.equals(ForgeDirection.UP);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from)
    {
        return new FluidTankInfo[]
                {
                        new FluidTankInfo(water, maxCapacity),
                        new FluidTankInfo(steam, maxCapacity)
                };
    }
}
