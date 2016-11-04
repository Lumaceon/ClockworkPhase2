package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.api.capabilities.achievementscore.CapabilityAchievementScore;
import lumaceon.mods.clockworkphase2.api.capabilities.achievementscore.IAchievementScoreHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageAchievementScore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class HandlerAchivementScore implements IMessageHandler<MessageAchievementScore, IMessage>
{
    @Override
    public IMessage onMessage(final MessageAchievementScore message, final MessageContext ctx)
    {
        if(ctx.side != Side.CLIENT)
        {
            System.err.println("MessageAchievementScore received on wrong side:" + ctx.side);
            return null;
        }

        Minecraft minecraft = Minecraft.getMinecraft();
        final WorldClient worldClient = minecraft.theWorld;
        minecraft.addScheduledTask(new Runnable()
        {
            public void run()
            {
                processMessage(message, ctx, worldClient);
            }
        });
        return null;
    }

    private void processMessage(MessageAchievementScore message, MessageContext ctx, WorldClient worldClient)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if(player != null && player.hasCapability(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY, EnumFacing.DOWN))
        {
            IAchievementScoreHandler achievementScoreHandler = player.getCapability(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY, EnumFacing.DOWN);
            achievementScoreHandler.calculateTier(message.newAchievementScore);
            //TODO - Some manner of display for the achievement score.
        }
    }
}
