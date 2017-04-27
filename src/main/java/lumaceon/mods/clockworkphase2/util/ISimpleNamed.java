package lumaceon.mods.clockworkphase2.util;

/**
 * Used for items and blocks to get the initial name passed in without all that "MOD_ID:" stuff added on. Probably a
 * better way to do this, but this works and I understand it, so I'm sticking with it for now.
 */
public interface ISimpleNamed
{
    public String getSimpleName();
}
