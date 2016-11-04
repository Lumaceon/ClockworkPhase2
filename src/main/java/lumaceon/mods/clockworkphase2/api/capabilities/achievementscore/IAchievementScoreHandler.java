package lumaceon.mods.clockworkphase2.api.capabilities.achievementscore;

import lumaceon.mods.clockworkphase2.api.EnumAchievementTier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;

public interface IAchievementScoreHandler
{
    /**
     * Used as a consistent way to get the achievement tier the player has earned.
     * @return The achievement tier calculated from the internal achievement points.
     */
    public EnumAchievementTier getAchievementTier();

    /**
     * A more internal representation of achievement. This should be usually be ignored in favor of getAchievementTier.
     * This is usually compared with the values in the AchievementTiering class to come up with the tier.
     */
    public int getAchievementPoints();

    public void calculateTier(int achievementPoints);

    /**
     * Called each time the player gains an achievement to calculate their new tier. May also be called during load to
     * recalculate the player's tier if additional achievements were added to the game later.
     * @param player The player to calculate the tier of, based on their achievements.
     */
    public void calculateTier(EntityPlayer player);

    /**
     * Overload used during the achievement event to pretend hasAchievement returns true in the case of the new achievement.
     * @param player The player to calculate the tier of, based on their achievements.
     * @param achievement The achievement to forcefully consider achieved.
     */
    public void calculateTier(EntityPlayer player, Achievement achievement);
}
