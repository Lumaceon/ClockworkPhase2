package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.api.util.TimeConverter;
import lumaceon.mods.clockworkphase2.container.ContainerTemporalFurnace;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.tile.machine.TileTemporalFurnace;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiTemporalFurnace extends GuiContainer
{
    public TileTemporalFurnace te;

    public GuiTemporalFurnace(InventoryPlayer ip, TileTemporalFurnace te, World world)
    {
        super(new ContainerTemporalFurnace(ip, te, world));
        this.te = te;
        this.xSize = 300;
        this.ySize = 230;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.clear();
        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.drawCenteredString(fontRendererObj, TimeConverter.parseNumber(te.timeStored, 2), 150, 23 - fontRendererObj.FONT_HEIGHT / 2, 0x00D0FF);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.BASE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        /**GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft + xSize / 2.0F, guiTop + ySize / 2.0F, 0);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.CLOCK);
        drawTexturedModalRect(-100, -100, 0, 0, 200, 200);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft + xSize / 2.0F, guiTop + ySize / 2.0F, 0);
        GL11.glRotatef((360F / 12F) * (((float) te.timeStored / (float) TimeConverter.HOUR) % 12F), 0, 0, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.CLOCK_HOUR);
        drawTexturedModalRect(-100, -100, 0, 0, 200, 200);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft + xSize / 2.0F, guiTop + ySize / 2.0F, 0);
        GL11.glRotatef((360F / 60F) * (((float) te.timeStored / (float) TimeConverter.MINUTE) % 60F), 0, 0, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.CLOCK_MINUTE);
        drawTexturedModalRect(-100, -100, 0, 0, 200, 200);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft + xSize / 2.0F, guiTop + ySize / 2.0F, 0);
        GL11.glRotatef((360F / 60F) * ((te.timeStored / TimeConverter.SECOND) % 60F), 0, 0, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.CLOCK_SECOND);
        drawTexturedModalRect(-100, -100, 0, 0, 200, 200);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslatef(guiLeft + xSize / 2.0F, guiTop + ySize / 2.0F, 0);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.CLOCK_CENTER_PEG);
        drawTexturedModalRect(-100, -100, 0, 0, 200, 200);
        GL11.glPopMatrix();*/
    }

    @Override
    public void drawTexturedModalRect(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + p_73729_6_), (double)this.zLevel, 0, 1);
        tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + p_73729_6_), (double)this.zLevel, 1, 1);
        tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + 0), (double)this.zLevel, 1, 0);
        tessellator.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + 0), (double)this.zLevel, 0, 0);
        tessellator.draw();
    }
}
