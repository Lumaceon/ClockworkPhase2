package lumaceon.mods.clockworkphase2.api;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AchievementTiering
{
    public static Map<Achievement, Integer> achievementValues = new HashMap<Achievement, Integer>(AchievementList.ACHIEVEMENTS.size());
    public static ArrayList<String> blacklistedAchievements = new ArrayList<String>();

    public static boolean isAchievementListed(Achievement achievement) {
        return achievementValues.containsKey(achievement) && achievementValues.get(achievement) > 0 && !blacklistedAchievements.contains(achievement.statId);
    }

    public static class INTERNAL
    {
        public static int maxPoints = 0;

        /**
         * Registers this achievement, calculating it's weight based on how many parent achievements it has. This is
         * automatically called for every achievement in AchievementList.achievementList during Post-initialization.
         *
         * @param achievement The achievement to calculate.
         * @return The weight of this achievement.
         */
        public static int registerAchievement(Achievement achievement)
        {
            if(!achievement.isAchievement() || blacklistedAchievements.contains(achievement.statId))
                return 0;
            int calculatedValue = 100;
            Achievement temp = achievement.parentAchievement;

            while(temp != null && calculatedValue < 200)
            {
                calculatedValue += 10;
                temp = temp.parentAchievement;
            }

            if(achievement.getSpecial())
                calculatedValue *= 4;

            maxPoints += calculatedValue;
            achievementValues.put(achievement, calculatedValue);
            return calculatedValue;
        }
    }
}
