package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.container.ContainerClockworkFurnace;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiClockworkFurnace extends GuiContainer
{
    public TileClockworkFurnace te;

    public GuiClockworkFurnace(InventoryPlayer ip, TileClockworkFurnace te, World world)
    {
        super(new ContainerClockworkFurnace(ip, te, world));
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
        this.drawCenteredString(fontRendererObj, "Cooking Progress: " + String.valueOf(te.getProgressScaled(100)) + "%", 150, 23 - fontRendererObj.FONT_HEIGHT / 2, 0x00D0FF);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.BASE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    /*@Override
    public void drawTexturedModalRect(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + p_73729_6_), (double)this.zLevel, 0, 1);
        tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + p_73729_6_), (double)this.zLevel, 1, 1);
        tessellator.addVertexWithUV((double)(p_73729_1_ + p_73729_5_), (double)(p_73729_2_ + 0), (double)this.zLevel, 1, 0);
        tessellator.addVertexWithUV((double)(p_73729_1_ + 0), (double)(p_73729_2_ + 0), (double)this.zLevel, 0, 0);
        tessellator.draw();
    }*/
}
