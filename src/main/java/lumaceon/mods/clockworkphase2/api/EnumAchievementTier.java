package lumaceon.mods.clockworkphase2.api;

public enum EnumAchievementTier
{
    START(0, "Start"),
    C(1, "C"),
    B(2, "B"),
    A(3, "A"),
    S(4, "S");

    public int tierIndex;
    public String displayName;

    private EnumAchievementTier(int tierIndex, String displayName) {
        this.tierIndex = tierIndex;
        this.displayName = displayName;
    }
}
