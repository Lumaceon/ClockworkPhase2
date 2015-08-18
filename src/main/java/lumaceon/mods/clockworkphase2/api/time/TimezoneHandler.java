package lumaceon.mods.clockworkphase2.api.time;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;

public class TimezoneHandler
{
    /**
     * Returns the timezone encompassing the given area on the given world, or null if none exist.
     * @param world The world to search.
     * @return A Timezone class that holds timesand and other time-streams.
     */
    public static ITimezone getTimeZone(double x, double y, double z, World world)
    {
        for(int[] timezone : timezones)
        {
            boolean sameWorld = world.provider.dimensionId == timezone[3];
            if(sameWorld)
            {
                TileEntity te = world.getTileEntity(timezone[0], timezone[1], timezone[2]);
                if(te != null && te instanceof ITimezone)
                {
                    ITimezone tz = (ITimezone) te;
                    double dist = Math.sqrt(Math.pow(x - timezone[0], 2) + Math.pow(z - timezone[2], 2));
                    boolean inRange = dist <= tz.getRange();

                    if(inRange)
                    {
                        return tz;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns the timezone encompassing the given area on the given dimension, or null if none exist.
     * @return A Timezone class that holds timesand and other time-streams.
     */
    public static ITimezone getTimeZone(double x, double y, double z, int dimensionId)
    {
        for(int[] timezone : timezones)
        {
            boolean sameWorld = dimensionId == timezone[3];
            if(sameWorld)
            {
                TileEntity te = DimensionManager.getWorld(dimensionId).getTileEntity(timezone[0], timezone[1], timezone[2]);
                if(te != null && te instanceof ITimezone)
                {
                    ITimezone tz = (ITimezone) te;
                    double dist = Math.sqrt(Math.pow(x - timezone[0], 2) + Math.pow(z - timezone[2], 2));
                    boolean inRange = dist <= tz.getRange();

                    if(inRange)
                    {
                        return tz;
                    }
                }
            }
        }
        return null;
    }

    public static ArrayList<int[]> timezones = new ArrayList<int[]>(10);
    public static class INTERNAL
    {
        /**
         * Checks to see if the timezone already exists or not. Avoids duplicate registrations.
         * @param tz Place to check.
         * @return Whether or not there's already an ITimezone there.
         */
        public static boolean alreadyExists(int[] tz)
        {
            for(int[] timezone : timezones)
            {
                if(tz[0] == timezone[0] && tz[1] == timezone[1] && tz[2] == timezone[2] && tz[3] == timezone[3])
                {
                    return true;
                }
            }
            return false;
        }

        /**
         * Registers a timezone at the specified world and location. This automatically checks to see if one exists in
         * the same point of origin, but it /does/ allow overlapping.
         */
        public static void registerTimezone(int x, int y, int z, World world)
        {
            int dimensionId = world.provider.dimensionId;
            int[] timezone =
                    {
                            x,
                            y,
                            z,
                            dimensionId
                    };

            if(alreadyExists(timezone))
                return;
            timezones.add(timezone);
        }

        /**
         * Called every minute or so to remove any timezones that no longer exist.
         * This should also be called whenever an ITimezone tile entity is removed.
         */
        public static void pingAndCleanTimezones()
        {
            int[] timezone;
            for(int n = 0; n < timezones.size(); n++)
            {
                timezone = timezones.get(n);
                World world = DimensionManager.getWorld(timezone[3]);
                TileEntity te = world.getTileEntity(timezone[0], timezone[1], timezone[2]);
                boolean tzExists = te != null && te instanceof ITimezone;
                if(!tzExists)
                {
                    timezones.remove(n);
                    n--;
                }
            }
        }
    }
}
