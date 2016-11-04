package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.api.capabilities.achievementscore.AchievementScoreHandler;
import lumaceon.mods.clockworkphase2.api.capabilities.achievementscore.CapabilityAchievementScore;
import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

public class CapabilityAttachmentHandler
{
    @SubscribeEvent
    public void onAttachCapabilityEntity(AttachCapabilitiesEvent.Entity event)
    {
        if(event.getEntity() instanceof EntityPlayer)
        {
            class Provider implements ICapabilityProvider
            {
                AchievementScoreHandler implementation = new AchievementScoreHandler();
                EntityPlayer player;

                Provider(EntityPlayer player)
                {
                    this.player = player;
                }

                @Override
                public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
                    return CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY != null && capability == CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY;
                }

                @Override
                public <T> T getCapability(Capability<T> capability, EnumFacing facing)
                {
                    if(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY != null && capability == CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY)
                    {
                        return CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY.cast(implementation);
                    }
                    return null;
                }
            }

            event.addCapability(new ResourceLocation(Reference.MOD_ID + ":achievement_score"), new Provider((EntityPlayer) event.getEntity()));
        }
    }
}
