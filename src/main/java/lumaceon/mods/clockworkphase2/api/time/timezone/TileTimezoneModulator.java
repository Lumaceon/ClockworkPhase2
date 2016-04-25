package lumaceon.mods.clockworkphase2.api.time.timezone;

import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;

public class TileTimezoneModulator extends TileClockworkPhase implements ITickable
{
    public ItemStack timezoneModulatorStack = null;
    protected ITimezoneProvider timezone;
    protected BlockPos timezonePosition;

    @Override
    public void update()
    {
        if(timezoneModulatorStack != null)
            timezoneModulatorStack.getItem().onUpdate(timezoneModulatorStack, worldObj, null, 0, false);
    }

    public boolean onRightClickWithModulatorStack(EntityPlayer player, ItemStack stack)
    {
        if(timezoneModulatorStack != null || player == null)
            return false;

        TimezoneModulation modulation = ((ITimezoneModulationItem) stack.getItem()).createTimezoneModulation(stack, worldObj, this);
        if(modulation == null)
            return false;

        ITimezoneProvider timezoneProvider = getTimezoneProvider();
        if(timezoneProvider == null)
            return false;

        Timezone timezone = timezoneProvider.getTimezone();
        if(timezone != null)
        {
            timezoneModulatorStack = stack;
            player.inventory.setInventorySlotContents(player.inventory.currentItem, null);
            timezone.addTimezoneModulation(modulation);
            return true;
        }

        return false;
    }

    public boolean onRightClickWithEmptyHand(EntityPlayer player)
    {
        if(timezoneModulatorStack == null || player == null)
            return false;
        player.inventory.setInventorySlotContents(player.inventory.currentItem, timezoneModulatorStack);
        timezoneModulatorStack = null;
        return true;
    }

    public ITimezoneProvider getTimezoneProvider()
    {
        BlockPos tilePosition = this.getPos();
        int xCoordinate = tilePosition.getX();
        int yCoordinate = tilePosition.getY();
        int zCoordinate = tilePosition.getZ();
        int tz_x = timezonePosition.getX();
        int tz_z = timezonePosition.getZ();

        //Check to see if the timezone is stored in the parameter and is in range. If so, we can stop here.
        if(timezone != null && Math.sqrt(Math.pow(tz_x - xCoordinate, 2) + Math.pow(tz_z - zCoordinate, 2)) <= timezone.getRange())
            return timezone;

        //Timezone parameter was either null or out of range, try and find it via stored coordinates.
        TileEntity te = worldObj.getTileEntity(timezonePosition);
        if(te != null && te instanceof ITimezoneProvider)
        {
            if(Math.sqrt(Math.pow(tz_x - xCoordinate, 2) + Math.pow(tz_z - zCoordinate, 2)) > timezone.getRange())
                return null;
            timezone = (ITimezoneProvider) te;
            return timezone;
        }

        //None of the parameters were helpful; try to find one in the area.
        ITimezoneProvider timezone = TimezoneHandler.getTimeZone(xCoordinate, yCoordinate, zCoordinate, worldObj);
        if(timezone != null)
            this.timezone = timezone;
        if(timezone == null || Math.sqrt(Math.pow(tz_x - xCoordinate, 2) + Math.pow(tz_z - zCoordinate, 2)) <= timezone.getRange())
            return null;
        return timezone;
    }
}
