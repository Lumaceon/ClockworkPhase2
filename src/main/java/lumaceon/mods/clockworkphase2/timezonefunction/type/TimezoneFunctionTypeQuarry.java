package lumaceon.mods.clockworkphase2.timezonefunction.type;

import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import lumaceon.mods.clockworkphase2.timezonefunction.construction.TimezoneFunctionConstructorQuarry;
import net.minecraft.util.ResourceLocation;

public class TimezoneFunctionTypeQuarry extends TimezoneFunctionType<TimezoneFunctionConstructorQuarry>
{
    public TimezoneFunctionTypeQuarry(String uniqueID, ResourceLocation iconLocation) {
        super(uniqueID, iconLocation);
    }

    @Override
    public String getDisplayName() {
        return "Quarry";
    }

    @Override
    public String getDescription(boolean detailed) {
        return !detailed ? "AT-space Mining Device" : "Completely strip the AT-space area of it's resources.";
    }

    @Override
    public TimezoneFunctionConstructorQuarry cteateTimezoneFunctionConstructorInstance() {
        return new TimezoneFunctionConstructorQuarry(this);
    }
}
