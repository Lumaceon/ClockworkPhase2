package lumaceon.mods.clockworkphase2.client.handler;

import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TooltipModificationHandler
{
    @SubscribeEvent
    public void onTooltipGet(ItemTooltipEvent event) {
        if(!event.isCanceled() && event.getItemStack().getItem() instanceof IAssemblable)
            event.getToolTip().add(Colors.AQUA + "~Assembly Item~");
    }
}
