package lumaceon.mods.clockworkphase2.util;

import cpw.mods.fml.common.FMLLog;
import lumaceon.mods.clockworkphase2.lib.Reference;
import org.apache.logging.log4j.Level;

public class Logger
{
    public static void log(Level logLevel, Object object)
    {
        FMLLog.log(Reference.MOD_NAME, logLevel, String.valueOf(object));
    }

    /**
     * No events will be logged.
     */
    public static void off(Object object)
    {
        log(Level.OFF, object);
    }

    /**
     * A severe error that will prevent the application from continuing.
     */
    public static void fatal(Object object)
    {
        log(Level.FATAL, object);
    }

    /**
     * An error in the application, possibly recoverable.
     */
    public static void error(Object object)
    {
        log(Level.ERROR, object);
    }

    /**
     * An event that might possibly lead to an error.
     */
    public static void warn(Object object)
    {
        log(Level.WARN, object);
    }

    /**
     * An event for informational purposes.
     */
    public static void info(Object object)
    {
        log(Level.INFO, object);
    }

    /**
     * A general debugging event.
     */
    public static void debug(Object object)
    {
        log(Level.DEBUG, object);
    }

    /**
     * A fine-grained debug message, typically capturing the flow through the application.
     */
    public static void trace(Object object)
    {
        log(Level.TRACE, object);
    }

    /**
     * All events should be logged.
     */
    public static void all(Object object)
    {
        log(Level.ALL, object);
    }
}