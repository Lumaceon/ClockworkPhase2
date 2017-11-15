package lumaceon.mods.clockworkphase2.timezonefunction.type;

import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import lumaceon.mods.clockworkphase2.timezonefunction.construction.TimezoneFunctionConstructorCapacitor;
import net.minecraft.util.ResourceLocation;

public class TimezoneFunctionTypeCapacitor extends TimezoneFunctionType<TimezoneFunctionConstructorCapacitor>
{
    public TimezoneFunctionTypeCapacitor(String uniqueID, ResourceLocation iconLocation) {
        super(uniqueID, iconLocation);
    }

    @Override
    public String getDisplayName() {
        return "Capacitor";
    }

    @Override
    public String getDescription(boolean detailed) {
        return !detailed ? "Massive Energy Bank" : "Isolate a section of AT-space to store energy.";
    }

    @Override
    public TimezoneFunctionConstructorCapacitor cteateTimezoneFunctionConstructorInstance() {
        return new TimezoneFunctionConstructorCapacitor(this);
    }
}
