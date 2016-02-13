package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.extendeddata.ExtendedPlayerProperties;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AchievementHandler
{
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onAchievement(AchievementEvent event)
    {
        if(     !event.isCanceled() //Event wasn't cancelled by another similar event.
                && event.achievement != null //There is an actual achievement.
                && !event.entityPlayer.worldObj.isRemote //Only server-side.
                && !((EntityPlayerMP)event.entityPlayer).getStatFile().hasAchievementUnlocked(event.achievement) //Player hasn't already earned this achievement
                && (event.achievement.parentAchievement == null || ((EntityPlayerMP)event.entityPlayer).getStatFile().hasAchievementUnlocked(event.achievement.parentAchievement)) //There is either no parent achievement, or there is one that the player has already achieved.
                )
        {
            ExtendedPlayerProperties.get(event.entityPlayer).calculateTemporalInfluence(event.achievement);
        }
    }
}