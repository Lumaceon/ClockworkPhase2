package lumaceon.mods.clockworkphase2.timezonefunction.type;

import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import lumaceon.mods.clockworkphase2.timezonefunction.construction.TimezoneFunctionConstructorReservoir;
import net.minecraft.util.ResourceLocation;

public class TimezoneFunctionTypeReservoir extends TimezoneFunctionType<TimezoneFunctionConstructorReservoir>
{
    public TimezoneFunctionTypeReservoir(String uniqueID, ResourceLocation iconLocation) {
        super(uniqueID, iconLocation);
    }

    @Override
    public String getDisplayName() {
        return "Reservoir";
    }

    @Override
    public String getDescription(boolean detailed) {
        return !detailed ? "Segmented Multi-tank" : "Networks a complex series of glass tank segments in AT-space to store several types of fluid.";
    }

    @Override
    public TimezoneFunctionConstructorReservoir cteateTimezoneFunctionConstructorInstance() {
        return new TimezoneFunctionConstructorReservoir(this);
    }
}
