package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.capabilities.toolbelt.CapabilityTemporalToolbelt;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.ITemporalToolbeltHandler;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessagePlayerDataOnWorldJoin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityHandler
{
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        Entity entity = event.getEntity();
        if(entity instanceof EntityPlayer)
        {
            if(!event.getWorld().isRemote)
            {
                ITemporalToolbeltHandler toolbelt = entity.getCapability(CapabilityTemporalToolbelt.TEMPORAL_TOOLBELT, EnumFacing.DOWN);
                if(toolbelt != null)
                {
                    PacketHandler.INSTANCE.sendTo(new MessagePlayerDataOnWorldJoin(toolbelt.getAllRows()), (EntityPlayerMP) entity);
                }
            }
        }
    }
}
