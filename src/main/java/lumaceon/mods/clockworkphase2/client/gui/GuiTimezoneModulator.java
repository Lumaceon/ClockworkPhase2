package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionRegistry;
import lumaceon.mods.clockworkphase2.api.timezone.function.TimezoneFunctionType;
import lumaceon.mods.clockworkphase2.inventory.ContainerTimezoneModulatorConstruction;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTimezoneFunctionStart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.Collection;

public class GuiTimezoneModulator extends GuiContainer
{
    public static final ResourceLocation BG = new ResourceLocation(Reference.MOD_ID, "textures/gui/timezone_modulator_construct.png");

    float slideWheelXCoordinate = 0.0F; //Ranges 0.0 to x.0, where x is the number of available selections minus one.
    float targetSlideWheel = 0.0F; //Where the slide wheel is tweening towards.

    TimezoneFunctionType[] types;

    TileEntity te;

    public GuiTimezoneModulator(TileEntity te) {
        super(new ContainerTimezoneModulatorConstruction(te));
        this.te = te;
        this.xSize = 236;
        this.ySize = 225;
        Collection<TimezoneFunctionType> c = TimezoneFunctionRegistry.getFunctionTypes();
        this.types = c.toArray(new TimezoneFunctionType[c.size()]);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        buttonList.clear();
        buttonList.add(new GuiButton(0, guiLeft + xSize/2, guiTop + 120, 50, 20,  "Start Upgrade"));
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        if(button.id == 0) //Start Upgrade...
        {
            int activeIndex = getActiveIndex();
            PacketHandler.INSTANCE.sendToServer(new MessageTimezoneFunctionStart(te.getPos(), te.getWorld().provider.getDimension(), types[activeIndex].getUniqueID()));
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);

        if(targetSlideWheel != slideWheelXCoordinate)
        {
            float dist = Math.abs(targetSlideWheel - slideWheelXCoordinate);
            if(dist < 0.01F)
            {
                slideWheelXCoordinate = targetSlideWheel;
            }

            if(slideWheelXCoordinate > targetSlideWheel)
            {
                slideWheelXCoordinate -= dist * 0.25F;
            }
            else
            {
                slideWheelXCoordinate += dist * 0.25F;
            }
        }

        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(BG);
        GuiHelper.drawTexturedModalRectStretched(this.guiLeft, this.guiTop, this.zLevel, this.xSize, this.ySize);

        int index = 0;
        for(TimezoneFunctionType type : types)
        {
            float size;
            float dist = Math.abs(slideWheelXCoordinate - index);
            if(dist > 2.5)
            {
                index++;
                continue; //"Out of bounds"
            }
            if(dist < 0.1F)
                size = 25 + 50*(0.1F-dist); //A quick increase from 25 to 30 within the last 10th of a distance.
            else
                size = 20 - dist*2;

            GlStateManager.color(1.0F, 1.0F, 1.0F, dist <= 1.1F ? 1.0F : 1.0F - dist/2.5F );

            float xPos = 118 + index*35 - slideWheelXCoordinate*35.0F;
            float yPos = 112;
            Minecraft.getMinecraft().renderEngine.bindTexture(type.getIconLocation());
            GuiHelper.drawTexturedModalRectStretched(this.guiLeft+xPos - size*0.5F, this.guiTop+yPos - size*0.5F, this.zLevel, size, size);
            index++;
        }

        int activeIndex = getActiveIndex();

        if(activeIndex >= 0 && activeIndex < types.length)
        {
            int xCenter = (int) (this.xSize * 0.5F) + this.guiLeft;
            int yCenter = 10 + this.guiTop;
            TimezoneFunctionType active = types[activeIndex];
            this.drawCenteredString(fontRenderer, active.getDescription(false), xCenter, yCenter + 21, 0xFFAAAAAA);
            fontRenderer.drawSplitString(active.getDescription(true), this.guiLeft + 8, yCenter + 38, xSize - 8, 0xFF9999FF);
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            this.drawCenteredString(fontRenderer, active.getDisplayName(), xCenter/2, yCenter/2, 0xFFFFFFFF);
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        int activeIndex = getActiveIndex();

        if(isPointInRegion(8, 96, 50, 33, mouseX, mouseY)) //Two left
        {
            activeIndex -= 2;
            if(activeIndex < 0)
                activeIndex = 0;
            targetSlideWheel = activeIndex;
        }

        if(isPointInRegion(118 - 60, 96, 50, 33, mouseX, mouseY)) //Two left
        {
            activeIndex -= 1;
            if(activeIndex < 0)
                activeIndex = 0;
            targetSlideWheel = activeIndex;
        }

        if(isPointInRegion(128, 96, 50, 33, mouseX, mouseY)) //One right
        {
            activeIndex += 1;
            if(activeIndex >= types.length)
                activeIndex = types.length - 1;
            targetSlideWheel = activeIndex;
        }

        if(isPointInRegion(178, 96, 50, 33, mouseX, mouseY)) //Two right
        {
            activeIndex += 2;
            if(activeIndex >= types.length)
                activeIndex = types.length - 1;
            targetSlideWheel = activeIndex;
        }
    }

    private int getActiveIndex()
    {
        return (int) Math.floor(slideWheelXCoordinate + 0.5F);
    }
}
