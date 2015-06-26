package lumaceon.mods.clockworkphase2.item.timezonemodule;

import lumaceon.mods.clockworkphase2.api.item.ITimezoneModule;
import lumaceon.mods.clockworkphase2.api.util.internal.NBTHelper;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class ItemTimezoneModuleTank extends ItemClockworkPhase implements ITimezoneModule
{
    public ItemTimezoneModuleTank(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    public int getMaxCapacity(ItemStack item, int tankIndex)
    {
        return 100000000;
    }

    public int fill(ItemStack item, FluidStack resource, boolean doFill)
    {
        if(resource == null)
            return 0;

        Fluid fluid;
        int firstEmpty = -1;
        int matchIndex = 0;
        boolean match = false;
        for(int n = 0; n < 9; n++)
        {
            if(NBTHelper.hasTag(item, "fluid_name_" + n))
            {
                fluid = FluidRegistry.getFluid(NBTHelper.STRING.get(item, "fluid_name_" + n));
                if(resource.getFluid().equals(fluid))
                {
                    match = true;
                    matchIndex = n;
                    break;
                }
            }
            else if(firstEmpty == -1)
                firstEmpty = n;
        }

        int fluidAmount;

        if(match)
        {
            fluidAmount = NBTHelper.INT.get(item, "fluid_amount_" + matchIndex);
            if(!doFill)
                return Math.min(getMaxCapacity(item, matchIndex) - fluidAmount, resource.amount);
            NBTHelper.INT.set(item, "fluid_amount_" + matchIndex, Math.min(getMaxCapacity(item, matchIndex), resource.amount + fluidAmount));
            return Math.min(getMaxCapacity(item, matchIndex) - fluidAmount, resource.amount);
        }
        else if(firstEmpty != -1)
        {
            if(!doFill)
                return Math.min(getMaxCapacity(item, firstEmpty), resource.amount);
            NBTHelper.STRING.set(item, "fluid_name_" + firstEmpty, FluidRegistry.getFluidName(resource.fluidID));
            NBTHelper.INT.set(item, "fluid_amount_" + firstEmpty, Math.min(getMaxCapacity(item, firstEmpty), resource.amount));
            return Math.min(getMaxCapacity(item, firstEmpty), resource.amount);
        }
        return 0;
    }

    public FluidStack drain(ItemStack item, int maxDrain, boolean doDrain, String extractorTargetFluid)
    {
        Fluid targetFluid = FluidRegistry.getFluid(extractorTargetFluid);
        FluidStack stack;
        for(int n = 0; n < 9; n++)
        {
            if(!NBTHelper.hasTag(item, "fluid_name_" + n))
                continue;

            Fluid fluid = FluidRegistry.getFluid(NBTHelper.STRING.get(item, "fluid_name_" + n));
            if(fluid == null || !fluid.equals(targetFluid))
                continue;
            int fluidAmount = NBTHelper.INT.get(item, "fluid_amount_" + n);

            int drained = maxDrain;
            if(fluidAmount < drained)
                drained = fluidAmount;

            stack = new FluidStack(fluid, drained);
            if(doDrain)
            {
                NBTHelper.INT.set(item, "fluid_amount_" + n, fluidAmount - drained);
                if(fluidAmount - drained <= 0)
                    NBTHelper.removeTag(item, "fluid_name_" + n);
            }
            return stack;
        }
        return null;
    }

    public FluidStack getFluid(ItemStack item, int tankIndex)
    {
        if(!NBTHelper.hasTag(item, "fluid_name_" + tankIndex))
            return null;

        Fluid fluid = FluidRegistry.getFluid(NBTHelper.STRING.get(item, "fluid_name_" + tankIndex));
        int fluidAmount = NBTHelper.INT.get(item, "fluid_amount_" + tankIndex);
        return new FluidStack(fluid, fluidAmount);
    }

    public FluidTankInfo[] getTankInfo(ItemStack item)
    {
        FluidTankInfo[] tankInfo = new FluidTankInfo[9];
        for(int n = 0; n < tankInfo.length; n++)
        {
            FluidStack stack = getFluid(item, n);
            if(stack != null)
                tankInfo[n] = new FluidTankInfo(stack, getMaxCapacity(item, n));
            else
                tankInfo[n] = new FluidTankInfo(null, 0);
        }
        return tankInfo;
    }

    @Override
    public ResourceLocation getGlyphTexture(ItemStack item) {
        return Textures.PARTICLE.TIME_SAND;
    }

    @Override
    public int getColorRed(ItemStack item) {
        return 40;
    }

    @Override
    public int getColorGreen(ItemStack item) {
        return 40;
    }

    @Override
    public int getColorBlue(ItemStack item) {
        return 255;
    }
}
