package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.container.ContainerTemporalizer;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.tile.timezone.TileTemporalizer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiTemporalizer extends GuiContainer
{
    TileTemporalizer te;

    public GuiTemporalizer(InventoryPlayer ip, TileTemporalizer te, World world)
    {
        super(new ContainerTemporalizer(ip, te, world));
        this.xSize = 256;
        this.ySize = 200;
        this.te = te;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.DEFAULT_ASSEMBLY_TABLE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }
}
