package lumaceon.mods.clockworkphase2.api.phase;

import cpw.mods.fml.relauncher.Side;

/**
 * Base class for all "phases." Phases usually occur either periodically, or when something else causes one. In most
 * cases, a phase should have a set amount of time to exist, after which it will automatically be removed if registered
 * with the PhaseHandler class.
 */
public abstract class Phase
{
    public int duration = 24000;

    public Phase(int duration) {
        this.duration = duration;
    }

    public void update(Side side) {};
}
