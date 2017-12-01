package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import org.lwjgl.opengl.GL11;

public class TESRTimezoneModulator extends TileEntitySpecialRenderer
{
    @CapabilityInject(ITimezone.class)
    static final Capability<ITimezone> TIMEZONE = null;

    private static final IProperty<EnumFacing> FACING = BlockHorizontal.FACING;

    Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        GL11.glPushMatrix();

        GlStateManager.disableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, ((float) Math.sin((System.currentTimeMillis() % 2000 / 2000.0) * (2*Math.PI)) + 1.0F) / 2.0F);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        setLightmapDisabled(true);

        GlStateManager.translate(x, y, z);

        @SuppressWarnings("deprecation")
        IBlockState state = te.getBlockType().getStateFromMeta(te.getBlockMetadata());
        EnumFacing direction = state.getValue(FACING);

        float rotationAngle = 0.0F;
        if(direction.equals(EnumFacing.WEST))
            rotationAngle = 90;
        else if(direction.equals(EnumFacing.NORTH))
            rotationAngle = 180;
        else if(direction.equals(EnumFacing.EAST))
            rotationAngle = 270;

        GlStateManager.translate(0.5F, 0.5F, 0.5F);
        GlStateManager.rotate(rotationAngle, 0.0F, -1.0F, 0.0F);
        GlStateManager.translate(-0.5F, -0.5F, -0.5F);
        GlStateManager.translate(0.0F, 1.0F/16.0F, 0.0F);

        ITimezone timezone = te.getCapability(TIMEZONE, null);

        double low = 4.0/16.0;
        double high = 1.0 - 4.0/16.0;

        this.bindTexture(Textures.GUI.TZF_COLONY);

        Tessellator tessy = Tessellator.getInstance();
        BufferBuilder renderer = tessy.getBuffer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(high, low, 9.01/16.0).tex(0, 0).endVertex();
        renderer.pos(high, high, 9.01/16.0).tex(0, 1).endVertex();
        renderer.pos(low, high, 9.01/16.0).tex(1, 1).endVertex();
        renderer.pos(low, low, 9.01/16.0).tex(1, 0).endVertex();
        tessy.draw();

        GL11.glPopMatrix();
    }
}
