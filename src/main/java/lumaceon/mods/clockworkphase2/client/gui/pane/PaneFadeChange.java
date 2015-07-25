package lumaceon.mods.clockworkphase2.client.gui.pane;

import lumaceon.mods.clockworkphase2.client.gui.GuiPane;
import net.minecraft.client.Minecraft;

/**
 * This pane will only attempt to show the latest pane added, fading out all the other pane components.
 */
public class PaneFadeChange extends Pane
{
    public PaneFadeChange(Minecraft mc, GuiPane guiPane) {
        super(mc, guiPane);
    }

    public void addComponent(PaneComponent component, int screenWidth, int screenHeight)
    {
        component.alpha = 0F;
        components.add(0, component);
        update(screenWidth, screenHeight);
    }

    @Override
    public void update(int screenWidth, int screenHeight)
    {
        super.update(screenWidth, screenHeight);
        int run = 0;
        float alpha = 0;
        for(PaneComponent component : components)
        {
            if(component != null)
            {
                if(run == 0)
                {
                    component.alpha = Math.min(component.alpha + 0.1F, this.alpha);
                    alpha = component.alpha;
                }
                else if(component.alpha < 0.1F)
                {
                    components.remove(component);
                    return;
                }
                else
                    component.alpha -= 0.1F;
                component.xCenter = this.xCenter + (spacingLeft / 2) - (spacingRight / 2);
                component.yCenter = this.yCenter + (spacingTop / 2) - (spacingBottom / 2);
                component.xSize = this.xSize - spacingLeft - spacingRight;
                component.ySize = this.ySize - spacingTop - spacingBottom;
                component.update(screenWidth, screenHeight);
                run++;
            }
        }
    }
}
