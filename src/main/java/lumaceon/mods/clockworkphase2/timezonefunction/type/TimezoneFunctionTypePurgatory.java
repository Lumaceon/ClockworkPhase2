package lumaceon.mods.clockworkphase2.timezonefunction.type;

import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import lumaceon.mods.clockworkphase2.timezonefunction.construction.TimezoneFunctionConstructorPurgatory;
import net.minecraft.util.ResourceLocation;

public class TimezoneFunctionTypePurgatory extends TimezoneFunctionType<TimezoneFunctionConstructorPurgatory>
{
    public TimezoneFunctionTypePurgatory(String uniqueID, ResourceLocation iconLocation) {
        super(uniqueID, iconLocation);
    }

    @Override
    public String getDisplayName() {
        return "Purgatory";
    }

    @Override
    public String getDescription(boolean detailed) {
        return !detailed ? "Mob Hell" : "Lock down the area in AT-space and banish unwanted mob spawns there to be \"processed.\"";
    }

    @Override
    public TimezoneFunctionConstructorPurgatory cteateTimezoneFunctionConstructorInstance() {
        return new TimezoneFunctionConstructorPurgatory(this);
    }
}
