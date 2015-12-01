package lumaceon.mods.clockworkphase2.client.render.elements.overlay;

import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.client.render.RenderHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.ArrayList;

public class OverlayRenderElementTemporalInfluence extends OverlayRenderElement
{
    public ArrayList<InfluenceIncrease> additionList = new ArrayList<InfluenceIncrease>(5);

    public int displayInfluence;
    public int sleepTimer = 0;

    public int yTranslation = -100;

    @Override
    public boolean render(RenderGameOverlayEvent.Post event)
    {
        if(event.type.equals(RenderGameOverlayEvent.ElementType.HOTBAR))
        {
            if(!additionList.isEmpty())
                yTranslation = Math.min(yTranslation + 1, 0);
            else
                yTranslation = Math.max(yTranslation - 1, -100);
            if(yTranslation == -100)
                return false;

            Minecraft.getMinecraft().fontRenderer.drawString(TimeConverter.parseNumber(displayInfluence, 2), event.resolution.getScaledWidth() / 2 - Minecraft.getMinecraft().fontRenderer.getStringWidth(TimeConverter.parseNumber(displayInfluence, 2)) / 2, 30 + yTranslation, 125576);
            for(int n = 1; n < additionList.size(); n++) //Draw additional strings.
            {
                InfluenceIncrease ii = additionList.get(n);
                Minecraft.getMinecraft().fontRenderer.drawString(TimeConverter.parseNumber(ii.newInfluence - ii.previousInfluence, 2), event.resolution.getScaledWidth() / 2 - Minecraft.getMinecraft().fontRenderer.getStringWidth(TimeConverter.parseNumber(ii.newInfluence - ii.previousInfluence, 2)) / 2, (40 - ii.tweening) + yTranslation + n * 30, 125576);
            }
            if(yTranslation == 0)
            {
                if(sleepTimer > 120)
                {
                    if(!additionList.isEmpty() && additionList.get(0).tweening == 10)
                    {
                        InfluenceIncrease ii = additionList.get(0);
                        displayInfluence = Math.min((int) (ii.previousInfluence + ((ii.newInfluence - ii.previousInfluence) * (ii.ticksIncreased / 200.0))), ii.newInfluence);
                        ii.ticksIncreased++;
                        additionList.get(0).show = false;

                        if(displayInfluence >= ii.newInfluence)
                        {
                            sleepTimer = 0;
                            additionList.remove(0);
                        }
                    }
                    else
                        additionList.get(0).tweening = Math.min(additionList.get(0).tweening + 1, 10);
                }
                else
                    sleepTimer++;
            }
        }
        return true;
    }

    @Override
    public void update() {

    }

    public void displayInfluenceIncrease(int previousInfluence, int newInfluence)
    {
        additionList.add(new InfluenceIncrease(previousInfluence, newInfluence));
        if(!RenderHandler.overlayRenderList.contains(this))
            RenderHandler.overlayRenderList.add(this);
    }

    public class InfluenceIncrease
    {
        public int previousInfluence, newInfluence;
        public int tweening = 0;
        public int ticksIncreased = 0;
        public boolean show = true;

        public InfluenceIncrease(int previousInfluence, int newInfluence)
        {
            this.previousInfluence = previousInfluence;
            this.newInfluence = newInfluence;
        }
    }
}
