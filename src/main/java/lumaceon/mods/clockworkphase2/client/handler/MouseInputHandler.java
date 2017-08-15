package lumaceon.mods.clockworkphase2.client.handler;

import lumaceon.mods.clockworkphase2.capabilities.toolbelt.CapabilityTemporalToolbelt;
import lumaceon.mods.clockworkphase2.capabilities.toolbelt.ITemporalToolbeltHandler;
import lumaceon.mods.clockworkphase2.client.handler.keybind.Keybindings;
import lumaceon.mods.clockworkphase2.proxy.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MouseInputHandler
{
    @SubscribeEvent
    public void onMouseInput(MouseEvent event)
    {
        if(Keybindings.toolbelt.isKeyDown())
        {
            int wheel = event.getDwheel();
            if(wheel > 0) //To the left.
            {
                if(process(true))
                {
                    event.setCanceled(true);
                }
            }
            else if(wheel < 0) //To the right.
                if(process(false))
                {
                    event.setCanceled(true);
                }
        }
    }

    /**
     * @param left True for left, false for right (mouse wheel up is usually left).
     */
    private boolean process(boolean left)
    {
        EntityPlayerSP player = Minecraft.getMinecraft().player;

        if(player != null)
        {
            ITemporalToolbeltHandler toolbelt = player.getCapability(CapabilityTemporalToolbelt.TEMPORAL_TOOLBELT, EnumFacing.DOWN);
            if(toolbelt != null && toolbelt.getRowCount() > 0 && Keybindings.toolbelt.isKeyDown())
            {
                if(left)
                {
                    ++ClientProxy.toolbeltRowSelectIndex;
                    if(ClientProxy.toolbeltRowSelectIndex > toolbelt.getRowCount())
                    {
                        ClientProxy.toolbeltRowSelectIndex = 0;
                    }
                }
                else
                {
                    --ClientProxy.toolbeltRowSelectIndex;
                    if(ClientProxy.toolbeltRowSelectIndex < 0)
                    {
                        ClientProxy.toolbeltRowSelectIndex = toolbelt.getRowCount(); //Not off-by-one: 0 = none selected
                    }
                }
                return true;
            }
        }
        return false;
    }
}
