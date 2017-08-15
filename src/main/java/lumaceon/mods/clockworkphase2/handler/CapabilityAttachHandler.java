package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.capabilities.stasis.CapabilityStasis;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.CapabilityTemporalToolbelt;
import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityAttachHandler
{
    @SubscribeEvent
    public void onEntityCapabilityAttach(AttachCapabilitiesEvent<Entity> event)
    {
        Entity entity = event.getObject();
        if(entity instanceof EntityPlayer && !entity.hasCapability(CapabilityTemporalToolbelt.TEMPORAL_TOOLBELT, EnumFacing.DOWN))
        {
            event.addCapability(new ResourceLocation(Reference.MOD_ID + ":temporal_toolbelt"), new CapabilityTemporalToolbelt.TemporalToolbeltGenericProvider());
            event.addCapability(new ResourceLocation(Reference.MOD_ID + ":stasis"), new CapabilityStasis.StasisGenericProvider());
        }
    }
}
