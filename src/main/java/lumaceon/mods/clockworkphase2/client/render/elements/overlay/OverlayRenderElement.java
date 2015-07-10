package lumaceon.mods.clockworkphase2.client.render.elements.overlay;

import net.minecraftforge.client.event.RenderGameOverlayEvent;

public abstract class OverlayRenderElement
{
    public abstract boolean render(RenderGameOverlayEvent.Post event);

    public abstract void update();
}
