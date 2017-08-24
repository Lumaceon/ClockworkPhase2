package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.inventory.ContainerTemporalFishingRod;
import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiTemporalFishingRod extends GuiContainer
{
    public static final ResourceLocation BG = new ResourceLocation(Reference.MOD_ID, "textures/gui/temporal_fishing_rod.png");

    public GuiTemporalFishingRod(EntityPlayer player, ItemStack fishingRodStack)
    {
        super(new ContainerTemporalFishingRod(player, fishingRodStack));
        this.xSize = 176;
        this.ySize = 133;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(BG);
        GuiHelper.drawTexturedModalRectStretched(this.guiLeft, this.guiTop, this.zLevel, this.xSize, this.ySize);
    }
}
