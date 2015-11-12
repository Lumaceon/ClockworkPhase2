package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.client.tesr.model.ModelTemporalFurnace;
import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TESRTemporalFurnace extends TileEntitySpecialRenderer
{
    public static final ResourceLocation TEMPORAL_FURNACE = new ResourceLocation(Reference.MOD_ID, "textures/blocks/temporal_furnace.png");
    public static final ModelBase MODEL = new ModelTemporalFurnace();

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float p_147500_8_)
    {
        boolean lightAlreadyActive = GL11.glIsEnabled(GL11.GL_LIGHTING);

        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(0F, -1F, 0F);
        if(!lightAlreadyActive)
            GL11.glEnable(GL11.GL_LIGHTING);

        this.bindTexture(TEMPORAL_FURNACE);
        MODEL.render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F);

        if(!lightAlreadyActive)
            GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }
}
