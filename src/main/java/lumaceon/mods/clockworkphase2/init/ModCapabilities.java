package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.api.capabilities.achievementscore.CapabilityAchievementScore;

public class ModCapabilities
{
    public static void init()
    {
        CapabilityAchievementScore.register();
    }
}
