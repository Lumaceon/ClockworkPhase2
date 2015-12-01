package lumaceon.mods.clockworkphase2.api;

import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;

import java.util.HashMap;
import java.util.Map;

public class TemporalAchievementList
{
    public static Map<Achievement, Integer> achievementValues = new HashMap<Achievement, Integer>(AchievementList.achievementList.size());

    public static boolean isAchievementListed(Achievement achievement) {
        return achievementValues.containsKey(achievement) && achievementValues.get(achievement) > 0;
    }

    public static int getPageBonusMultiplier(AchievementPage page)
    {
        int output = 0;
        for(Achievement achievement : page.getAchievements())
            if(isAchievementListed(achievement))
                output++;
        return output * 100;
    }

    public static class INTERNAL
    {
        public static int totalWeight = 0;
        public static int specialAchievementCount = 0;
        public static int maxPageMultiplier = 1;
        public static double specialAchievementMultiplierExponent = 1;

        /**
         * Registers this achievement, calculating it's weight based on how many parent achievements it has. This is
         * automatically called for every achievement in AchievementList.achievementList during Post-initialization.
         *
         * @param achievement The achievement to calculate.
         * @return The weight of this achievement.
         */
        public static int registerAchievement(Achievement achievement)
        {
            if(achievement.isIndependent || !achievement.isAchievement())
                return 0;
            int calculatedWeight = 60;
            Achievement temp = achievement.parentAchievement;

            while(temp != null)
            {
                calculatedWeight += 60;
                temp = temp.parentAchievement;
            }

            if(achievement.getSpecial())
                specialAchievementCount += 1;

            totalWeight += calculatedWeight;
            achievementValues.put(achievement, calculatedWeight);
            return calculatedWeight;
        }

        /**
         * Registers a page of achievements (usually associated with a specific mod). This is automatically called for
         * every AchievementPage in AchievementPage.achievmentPages during Post-initialization.
         *
         * @param page The page to register.
         * @return The page multiplier value of this page.
         */
        public static int registerPage(AchievementPage page)
        {
            int multiplier = getPageBonusMultiplier(page);
            if(multiplier > 0)
                maxPageMultiplier += multiplier;
            return multiplier;
        }

        public static void setupSpecialMultiplier() {
            int maxValueWithoutSpecialMultipliers = totalWeight * maxPageMultiplier;
            specialAchievementMultiplierExponent = Math.log(TimeConverter.INFINITE / maxValueWithoutSpecialMultipliers) / Math.log(specialAchievementCount + 1);
        }
    }
}
