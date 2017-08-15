package lumaceon.mods.clockworkphase2.util;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Credits to the developers of EnderIO for the original code.
 * @link https://github.com/SleepyTrousers/EnderIO/blob/master/src/main/java/crazypants/enderio/xp/XpUtil.java
 */
public class ExperienceHelper
{
    public static int getExperienceForLevel(int level)
    {
        if(level == 0)
            return 0;

        if(level > 0 && level <= 15)
        {
            return level * (level + 1) + 7 * level;
        }
        else if(level > 15 && level <= 30)
        {
            int valueForLevel15 = 345;
            float lvl = level - 15.0F;
            return (int) (((lvl * (lvl + 1.0F)) / 2.0F) * 5.0F + 37.0F * lvl) + valueForLevel15;
        }
        else
        {
            int valueForLevel30 = 1500;
            float lvl = level - 30.0F;
            return (int) (((lvl * (lvl + 1.0F)) / 2.0F) * 9.0F + 112.0F * lvl) + valueForLevel30;
        }
    }

    public static int getLevelForExperience(int experience)
    {
        int i = 0;
        while (getExperienceForLevel(i) <= experience) {
            i++;
        }
        return i - 1;
    }

    public static int getPlayerXP(EntityPlayer player) {
        return (int) (getExperienceForLevel(player.experienceLevel) + (player.experience * player.xpBarCap()));
    }

    public static void addPlayerXP(EntityPlayer player, int amount)
    {
        int experience = getPlayerXP(player) + amount;
        player.experienceTotal = experience;
        player.experienceLevel = getLevelForExperience(experience);
        int expForLevel = getExperienceForLevel(player.experienceLevel);
        player.experience = (float)(experience - expForLevel) / (float)player.xpBarCap();
    }
}
