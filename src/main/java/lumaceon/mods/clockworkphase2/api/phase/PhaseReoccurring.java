package lumaceon.mods.clockworkphase2.api.phase;

public class PhaseReoccurring extends Phase
{
    public int ticksBetweenOccurrences = 24000;

    public PhaseReoccurring(int duration, int ticksBetweenOccurrences) {
        super(duration);
        this.ticksBetweenOccurrences = ticksBetweenOccurrences;
    }
}
