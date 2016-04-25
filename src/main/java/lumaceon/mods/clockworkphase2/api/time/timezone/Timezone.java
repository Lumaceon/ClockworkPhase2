package lumaceon.mods.clockworkphase2.api.time.timezone;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a timezone, which is an area of space where time is manipulated by a timezone controller.
 * Functionally, time is stored within an ITimezoneProvider and effects are determined by the timezone modulators.
 */
public class Timezone
{
    protected ITimezoneProvider timezoneProvider;
    protected ArrayList<TimezoneModulation> timezoneModulations = new ArrayList<TimezoneModulation>();

    public void onUpdate()
    {
        for(TimezoneModulation timezoneModulation : timezoneModulations)
            if(timezoneModulation != null)
                timezoneModulation.onUpdate(timezoneModulation.getTile().timezoneModulatorStack, timezoneProvider);
    }

    public ITimezoneProvider getTimezoneProvider() {
        return timezoneProvider;
    }

    public void addTimezoneModulation(TimezoneModulation modulation) {
        timezoneModulations.add(modulation);
    }

    /**
     * @param mod The class of the modulation to search for.
     * @return The first instance of the given modulation type, or null if none are found.
     */
    public TimezoneModulation getTimezoneModulation(Class<? extends TimezoneModulation> mod)
    {
        for(TimezoneModulation modulation : timezoneModulations)
            if(modulation.getClass().equals(mod))
                return modulation;
        return null;
    }

    /**
     * @param mod The class of the modulations to search for.
     * @return A list containing all instances of the given modulation type, or null if none are found.
     */
    public List<TimezoneModulation> getTimezoneModulations(Class<? extends TimezoneModulation> mod)
    {
        ArrayList<TimezoneModulation> modulations = new ArrayList<TimezoneModulation>();

        for(TimezoneModulation modulation : timezoneModulations)
            if(modulation.getClass().equals(mod))
                modulations.add(modulation);

        if(!modulations.isEmpty())
            return modulations;

        return null;
    }

    /**
     * @return A list containing all of the modulations effecting this timezone.
     */
    public List<TimezoneModulation> getTimezoneModulations() {
        return timezoneModulations;
    }
}
