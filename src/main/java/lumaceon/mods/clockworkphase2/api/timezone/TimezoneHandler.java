package lumaceon.mods.clockworkphase2.api.timezone;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.*;

public class TimezoneHandler
{
    @CapabilityInject(ITimezone.class)
    public static final Capability<ITimezone> TIMEZONE = null;

    /**
     * Returns all timezones available for the given location on the given world, or an empty list if none exist.
     * @return The first registered timezone available at the location, or an empty list if none are.
     */
    public static List<ITimezone> getTimezonesFromWorldPosition(double x, double y, double z, World world)
    {
        ArrayList<ITimezone> ret = new ArrayList<>();
        Collection<ITimezone> timezoneList = timezones.values();
        for(ITimezone timezone : timezoneList)
        {
            boolean inRange = timezone.isInRange(x, y, z, world);
            if(inRange)
            {
                ret.add(timezone);
            }
        }
        return ret;
    }

    public static List<ITimezone> getTimezonesFromWorldPosition(double x, double y, double z, int dimensionId)
    {
        World world = DimensionManager.getWorld(dimensionId);
        if(world == null)
            return new ArrayList<>(0);

        return getTimezonesFromWorldPosition(x, y, z, world);
    }

    /**
     * Returns the first timezone available for the given location on the given world, or null if none exist.
     * @return The first registered timezone available at the location, or null if none are.
     */
    public static ITimezone getFirstTimezoneFromWorldPosition(double x, double y, double z, World world)
    {
        Collection<ITimezone> timezoneList = timezones.values();
        for(ITimezone timezone : timezoneList)
        {
            boolean inRange = timezone.isInRange(x, y, z, world);
            if(inRange)
            {
                return timezone;
            }
        }
        return null;
    }

    /**
     * Returns the first timezone available for the given location on the given world, or null if none exist.
     * @return The first registered timezone available at the location, or null if none are.
     */
    public static ITimezone getFirstTimezoneFromWorldPosition(double x, double y, double z, int dimensionId)
    {
        World world = DimensionManager.getWorld(dimensionId);
        if(world == null)
            return null;

        return getFirstTimezoneFromWorldPosition(x, y, z, world);
    }

    public static HashMap<int[], ITimezone> timezones = new HashMap<>(20);
    public static class INTERNAL
    {
        public static boolean alreadyExists(TileEntity te) {
            BlockPos pos = te.getPos();
            return timezones.containsKey(new int[] { pos.getX(), pos.getY(), pos.getZ(), te.getWorld().provider.getDimension() });
        }

        /**
         * Registers a timezone at the specified world and location. This automatically checks to see if one exists in
         * the same point of origin, but it does allow timezones to overlap.
         */
        public static void registerTimezone(TileEntity te)
        {
            ITimezone timezone = te.getCapability(TIMEZONE, EnumFacing.DOWN);
            if(timezone == null)
                return;

            if(alreadyExists(te))
                return;

            BlockPos pos = te.getPos();
            timezones.put(new int[] { pos.getX(), pos.getY(), pos.getZ(), te.getWorld().provider.getDimension() }, timezone);
        }

        /**
         * Called very infrequently (about once every minute) to check each timezone and make sure its actually has a
         * tile entity associated with it. If not, it will remove it from the registry.
         *
         * This should also be called when your tile entity has either been moved or destroyed.
         */
        public static void timezoneCleanup()
        {
            ArrayList<int[]> itemsToRemove = new ArrayList<>();

            Set<Map.Entry<int[], ITimezone>> entries = timezones.entrySet();
            for(Map.Entry<int[], ITimezone> e : entries)
            {
                if(e != null && e.getKey() != null && e.getKey().length == 4 && e.getValue() != null)
                {
                    int[] position = e.getKey();
                    BlockPos pos = new BlockPos(position[0], position[1], position[2]);
                    World world = DimensionManager.getWorld(position[3]);
                    TileEntity te = world.getTileEntity(pos);
                    if(te != null)
                    {
                        ITimezone timezone = te.getCapability(TIMEZONE, EnumFacing.DOWN);
                        if(timezone != null && timezone.equals(e.getValue()))
                        {
                            continue;
                        }
                    }
                }

                if(e != null && e.getKey() != null)
                {
                    itemsToRemove.add(e.getKey());
                }
            }

            for(int i = 0; i < itemsToRemove.size(); i++)
            {
                timezones.remove(itemsToRemove.get(i));
                itemsToRemove.remove(i);
                --i;
            }
        }
    }
}
