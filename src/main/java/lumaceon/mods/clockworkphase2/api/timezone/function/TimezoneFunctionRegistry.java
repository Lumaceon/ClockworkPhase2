package lumaceon.mods.clockworkphase2.api.timezone.function;

import lumaceon.mods.clockworkphase2.util.LogHelper;

import java.util.Collection;
import java.util.HashMap;

public class TimezoneFunctionRegistry
{
    private static final HashMap<String, TimezoneFunctionType> FUNCTION_TYPES = new HashMap<>(20);

    /**
     * Automatically called from the constructor of function types; you will probably never need to use this.
     */
    public static void registerFunctionType(TimezoneFunctionType type)
    {
        if(FUNCTION_TYPES.containsKey(type.getUniqueID()))
        {
            LogHelper.warn("Registered a duplicate timezone function with the following ID: \"" + type.getUniqueID() + "\"");
        }
        FUNCTION_TYPES.put(type.getUniqueID(), type);
    }

    public static TimezoneFunctionType getFunctionFromID(String functionID) {
        return FUNCTION_TYPES.get(functionID);
    }

    public static Collection<TimezoneFunctionType> getFunctionTypes() {
        return FUNCTION_TYPES.values();
    }
}
