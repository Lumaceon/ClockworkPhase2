package lumaceon.mods.clockworkphase2.api;

public enum EnumExpTier
{
    TEMPORAL(0, 0, "Temporal"), //Essentially means "not phased at all." Tier 0.
    ETHEREAL(1, 200, "Ethereal"),
    PHASIC(2, 400, "Phasic"),
    ETERNAL(3, 600, "Eternal"),
    ASCENDANT(4, 800, "Ascendant"); //Final tier should be used sparingly; usually requires S achievement tier as well.
    public final int tierIndex;
    public final int minimumXP;
    public final String displayName;

    private EnumExpTier(int tierIndex, int minimumXP, String displayName)
    {
        this.tierIndex = tierIndex;
        this.minimumXP = minimumXP;
        this.displayName = displayName;
    }
}