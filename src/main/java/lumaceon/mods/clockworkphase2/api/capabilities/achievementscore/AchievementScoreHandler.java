package lumaceon.mods.clockworkphase2.api.capabilities.achievementscore;

import lumaceon.mods.clockworkphase2.api.AchievementTiering;
import lumaceon.mods.clockworkphase2.api.EnumAchievementTier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;

import java.util.Set;

public class AchievementScoreHandler implements IAchievementScoreHandler
{
    protected EnumAchievementTier tier = EnumAchievementTier.START;
    protected int points = 0;

    @Override
    public EnumAchievementTier getAchievementTier() {
        return tier;
    }

    @Override
    public int getAchievementPoints() {
        return points;
    }

    @Override
    public void calculateTier(int achievementPoints)
    {
        int maxPoints = AchievementTiering.INTERNAL.maxPoints;
        if(achievementPoints >= maxPoints)             //All achievements (or MORE than all, just in case).
            tier = EnumAchievementTier.S;
        else if(achievementPoints < maxPoints / 4)     //Zero to 1/4th.
            tier = EnumAchievementTier.START;
        else if(achievementPoints < maxPoints / 2)     //1/4th to 2/4ths.
            tier = EnumAchievementTier.C;
        else if(achievementPoints < (maxPoints*3) / 4) //2/4ths to 3/4ths.
            tier = EnumAchievementTier.B;
        else                                           //3/4ths to all.
            tier = EnumAchievementTier.A;
        points = achievementPoints;
    }

    @Override
    public void calculateTier(EntityPlayer player)
    {
        int points = 0;
        Set<Achievement> achievements = AchievementTiering.achievementValues.keySet();
        for(Achievement achievement : achievements)
            if(achievement != null && player.hasAchievement(achievement))
                points += AchievementTiering.achievementValues.get(achievement);
        this.points = points;
        calculateTier(points);
    }

    @Override
    public void calculateTier(EntityPlayer player, Achievement achievement)
    {
        int points = 0;
        Set<Achievement> achievements = AchievementTiering.achievementValues.keySet();
        for(Achievement ach : achievements)
            if(ach != null && (player.hasAchievement(ach) || ach.statId.equals(achievement.statId)))
                points += AchievementTiering.achievementValues.get(ach);
        this.points = points;
        calculateTier(points);
    }
}
