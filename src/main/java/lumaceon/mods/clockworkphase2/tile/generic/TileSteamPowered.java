package lumaceon.mods.clockworkphase2.tile.generic;

import lumaceon.mods.clockworkphase2.init.ModFluids;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileSteamPowered extends TileClockworkPhase implements IFluidHandler
{
    public int steamUsePerTick = 5;
    public int maxSteamBuffer = 1000;
    public int steamBuffer = 0;

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill)
    {
        if(canFill(from, resource.getFluid()))
        {
            int amount = Math.min(maxSteamBuffer - steamBuffer, resource.amount);
            if(doFill)
                steamBuffer += amount;
            return amount;
        }
        return 0;
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) { return null; }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) { return null; }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid) {
        return false;//fluid.equals(ModFluids.steam); //TODO - OreDictionary support
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid) { return false; }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        return new FluidTankInfo[] {new FluidTankInfo(new FluidStack(ModFluids.liquidTemporium, 0), steamUsePerTick)};
    }
}
