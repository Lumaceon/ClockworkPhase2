package lumaceon.mods.clockworkphase2.timezonefunction.type;

import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import lumaceon.mods.clockworkphase2.timezonefunction.construction.TimezoneFunctionConstructorColony;
import net.minecraft.util.ResourceLocation;

public class TimezoneFunctionTypeColony extends TimezoneFunctionType<TimezoneFunctionConstructorColony>
{
    public TimezoneFunctionTypeColony(String uniqueID, ResourceLocation iconLocation) {
        super(uniqueID, iconLocation);
    }

    @Override
    public String getDisplayName() {
        return "Colony";
    }

    @Override
    public String getDescription(boolean detailed) {
        return !detailed ? "Trading Settlement" : "Sustain an AT-space trading colony of villagers, allowing you to conduct automated and consistent trades.";
    }

    @Override
    public TimezoneFunctionConstructorColony cteateTimezoneFunctionConstructorInstance() {
        return new TimezoneFunctionConstructorColony(this);
    }
}
