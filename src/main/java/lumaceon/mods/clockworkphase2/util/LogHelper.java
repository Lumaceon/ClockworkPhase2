package lumaceon.mods.clockworkphase2.util;

import org.apache.logging.log4j.Logger;

public class LogHelper
{
    public static Logger logger;

    public static void info(String message) {
        logger.info(message);
    }

    public static void warn(String message) {
        logger.warn(message);
    }
}
