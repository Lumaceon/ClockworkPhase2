package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.client.tesr.model.ModelAssemblyTable;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.TileAssemblyTable;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TESRAssemblyTable extends TileEntitySpecialRenderer
{
    private static final ResourceLocation mainTexture = new ResourceLocation(Reference.MOD_ID, "textures/blocks/model_textures/assembly_table.png");
    public static final ModelBase MODEL = new ModelAssemblyTable();

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float p_147500_8_)
    {
        if(te != null && te instanceof TileAssemblyTable)
        {
            GL11.glPushMatrix();
            GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
            GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
            GL11.glTranslatef(0F, -1F, 0F);

            this.bindTexture(mainTexture);
            MODEL.render(null, 0F, 0F, 0F, 0F, 0F, 0.0625F);

            GL11.glPopMatrix();
        }
    }
}
