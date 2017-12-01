package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.api.capabilities.ITimeStorage;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITemporalRelay;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.temporal.timezone.TimezoneInternalStorage;
import lumaceon.mods.clockworkphase2.capabilities.timestorage.CapabilityTimeStorage;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.CapabilityTemporalToolbelt;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.ITemporalToolbeltHandler;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import lumaceon.mods.clockworkphase2.util.FluidTankSided;
import lumaceon.mods.clockworkphase2.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class ItemBugSwatter extends ItemClockworkPhase
{
    public static BlockPos start, end;

    //The following is a serious of variables
    public static int debugInteger1 = 0;
    public static int debugInteger2 = 0;
    public static int debugInteger3 = 0;
    public static long debugLong1 = 0;
    public static long debugLong2 = 0;
    public static long debugLong3 = 0;
    public static double debugFloatingPoint1 = 0.0;
    public static double debugFloatingPoint2 = 0.0;
    public static double debugFloatingPoint3 = 0.0;
    public static boolean debugBoolean1 = false;
    public static boolean debugBoolean2 = false;
    public static boolean debugBoolean3 = false;
    public static String debugString1 = "";
    public static String debugString2 = "";
    public static String debugString3 = "";

    public ItemBugSwatter(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!player.isSneaking())
        {
            resetDebugValues();
        }
        else
        {
            TileEntity te = worldIn.getTileEntity(pos);
            if(te != null && te instanceof TileClockworkMachine)
            {
                ((TileClockworkMachine) te).energyStorage.receiveEnergy(100000, false);
                LogHelper.info(String.valueOf(((TileClockworkMachine) te).getTicksPerOperation()));
                if(((TileClockworkMachine) te).fluidTanks.length > 0)
                {
                    FluidTankSided tank = ((TileClockworkMachine) te).fluidTanks[0];
                    if(tank != null)
                    {
                        LogHelper.info(tank.toString());
                        FluidStack fs = tank.getFluid();
                        if(fs != null)
                            LogHelper.info(fs.toString());
                    }
                }
            }
            else if(te != null && te instanceof ITemporalRelay)
            {
                List<ITimezone> timezones = ((ITemporalRelay) te).getTimezones();
                int index = 1;
                for(ITimezone timezone : timezones)
                {
                    if(timezone instanceof TimezoneInternalStorage)
                    {
                        LogHelper.info(String.valueOf("Timezone " + index + " (internal): " + timezone.getTimeInTicks()));
                    }
                    else
                    {
                        LogHelper.info(String.valueOf("Timezone " + index + ": " + timezone.getTimeInTicks()));
                    }
                    ++index;
                }
            }
            else if(te != null && te.hasCapability(CapabilityTimeStorage.TIME_STORAGE_CAPABILITY, EnumFacing.DOWN))
            {
                ITimeStorage timeStorage = te.getCapability(CapabilityTimeStorage.TIME_STORAGE_CAPABILITY, EnumFacing.DOWN);
                if(timeStorage != null)
                {
                    System.out.println(timeStorage.getTimeInTicks() + " / " + timeStorage.getMaxCapacity());
                }
            }
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
    {
        ItemStack is = player.getHeldItem(handIn);
        if(!player.isSneaking())
        {
            resetDebugValues();
        }
        else
        {

        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, is);
    }

    void resetDebugValues()
    {
        debugInteger1 = debugInteger2 = debugInteger3 = 0;
        debugLong1 = debugLong2 = debugLong3 = 0;
        debugFloatingPoint1 = debugFloatingPoint2 = debugFloatingPoint3 = 0.0;
        debugBoolean1 = debugBoolean2 = debugBoolean3 = false;
        debugString1 = debugString2 = debugString3 = "";
        LogHelper.info("Debug values have been reset");
    }
}
