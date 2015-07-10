package lumaceon.mods.clockworkphase2.handler;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lumaceon.mods.clockworkphase2.extendeddata.ExtendedPlayerProperties;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.player.AchievementEvent;

public class AchievementHandler
{
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onAchievement(AchievementEvent event)
    {
        if(     !event.isCanceled() //Event wasn't cancelled by another similar event.
                && event.achievement != null //There is an actual achievement.
                && !event.entityPlayer.worldObj.isRemote //Only server-side.
                && !((EntityPlayerMP)event.entityPlayer).func_147099_x().hasAchievementUnlocked(event.achievement) //Player hasn't already earned this achievement
                && (event.achievement.parentAchievement == null || ((EntityPlayerMP)event.entityPlayer).func_147099_x().hasAchievementUnlocked(event.achievement.parentAchievement)) //There is either no parent achievement, or there is one that the player has already achieved.
                )
        {
            ExtendedPlayerProperties.get(event.entityPlayer).calculateTemporalInfluence(event.achievement);
        }
    }
}