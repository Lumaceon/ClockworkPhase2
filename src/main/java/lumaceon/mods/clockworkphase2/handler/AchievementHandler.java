package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.AchievementTiering;
import lumaceon.mods.clockworkphase2.api.capabilities.achievementscore.CapabilityAchievementScore;
import lumaceon.mods.clockworkphase2.api.capabilities.achievementscore.IAchievementScoreHandler;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageAchievementScore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AchievementHandler
{
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onAchievement(AchievementEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
        Achievement achievement = event.getAchievement();
        if(     !event.isCanceled() //Event wasn't cancelled by another similar event.
                && !player.worldObj.isRemote //Only server-side.
                && achievement != null //There is an actual achievement.
                && AchievementTiering.isAchievementListed(achievement) //The achievement has value and considered valid.
                && !player.hasAchievement(achievement) //Player hasn't already earned this achievement
                && (achievement.isIndependent || player.hasAchievement(achievement.parentAchievement)) //Achievement is independent, or there is one that the player has already achieved.
          )
        {
            if(player.hasCapability(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY, EnumFacing.DOWN))
            {
                IAchievementScoreHandler achievementScoreHandler = player.getCapability(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY, EnumFacing.DOWN);
                achievementScoreHandler.calculateTier(player, achievement);
                PacketHandler.INSTANCE.sendTo(new MessageAchievementScore(achievementScoreHandler.getAchievementPoints()), (EntityPlayerMP) player);
            }
        }
    }
}