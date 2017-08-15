package lumaceon.mods.clockworkphase2.api.temporal.timezone;

import java.util.List;

/**
 * To be implemented on tiles which can provide access to a timezone. By convention, timezone relays shouldn't transfer
 * time, instead leaving that up to ITimezoneSink tiles to drain directly from the timezone. Anything else (such as
 * fluid or items) is up to the relay to try and insert into nearby tiles.
 */
public interface ITemporalRelay
{
    public List<ITimezone> getTimezones();
}
