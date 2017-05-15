package lumaceon.mods.clockworkphase2.api.temporal.timezone;

/**
 * An empty interface to be implemented by TileEntity subclasses that will request a timezone from time relays. This is
 * mostly for visual purposes.
 *
 * Most time sinks don't actually hold time. By convention, they query adjacent tiles for ITimezoneRelays, request an
 * ITimezone, and attempt to use time energy directly from it.
 */
public interface ITimeSink {}
