package lumaceon.mods.clockworkphase2.client.gui.pane;

import net.minecraft.client.Minecraft;

public class PaneFadeChange extends Pane
{
    public PaneFadeChange(Minecraft mc) {
        super(mc);
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
                    component.alpha = Math.min(component.alpha + 0.1F, 1F);
                    alpha = component.alpha;
                }
                else if(component.alpha < 0.1F)
                {
                    components.remove(component);
                    return;
                }
                else if(alpha >= 1)
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
