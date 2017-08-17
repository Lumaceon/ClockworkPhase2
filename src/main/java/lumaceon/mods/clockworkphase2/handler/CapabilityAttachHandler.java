package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.capabilities.custombehavior.ICustomBehavior;
import lumaceon.mods.clockworkphase2.capabilities.stasis.CapabilityStasis;
import lumaceon.mods.clockworkphase2.capabilities.stasis.IStasis;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.CapabilityTemporalToolbelt;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.ITemporalToolbeltHandler;
import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityAttachHandler
{
    @CapabilityInject(ITemporalToolbeltHandler.class)
    public static final Capability<ITemporalToolbeltHandler> TEMPORAL_TOOLBELT = null;

    @CapabilityInject(IStasis.class)
    public static final Capability<IStasis> STASIS_CAPABILITY = null;

    @CapabilityInject(ICustomBehavior.class)
    public static final Capability<ICustomBehavior> CUSTOM_BEHAVIOR = null;

    @SubscribeEvent
    public void onEntityCapabilityAttach(AttachCapabilitiesEvent<Entity> event)
    {
        Entity entity = event.getObject();
        if(entity instanceof EntityPlayer)
        {
            if(!entity.hasCapability(TEMPORAL_TOOLBELT, EnumFacing.DOWN))
            {
                event.addCapability(new ResourceLocation(Reference.MOD_ID + ":temporal_toolbelt"), new CapabilityTemporalToolbelt.TemporalToolbeltGenericProvider());
            }
            else if(!entity.hasCapability(STASIS_CAPABILITY, EnumFacing.DOWN))
            {
                event.addCapability(new ResourceLocation(Reference.MOD_ID + ":stasis"), new CapabilityStasis.StasisGenericProvider());
            }
        }
        else if(entity instanceof EntityLiving)
        {
            if(!entity.hasCapability(CUSTOM_BEHAVIOR, EnumFacing.DOWN))
            {
                event.addCapability(new ResourceLocation(Reference.MOD_ID + ":customBehavior"), new CapabilityStasis.StasisGenericProvider());
            }
        }
    }
}
