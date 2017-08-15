package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.temporal.TileArmillaryRing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import org.lwjgl.opengl.GL11;

public class TESRArmillaryRing extends TileEntitySpecialRenderer
{
    private RenderItem itemRenderer;

    private IModel model;
    private IBakedModel bakedModel;

    private IBakedModel getBakedModel()
    {
        if (bakedModel == null) {

            try {
                model = ModelLoaderRegistry.getModel(new ResourceLocation(Reference.MOD_ID, "obj/armillary.obj"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            bakedModel = model.bake(TRSRTransformation.identity(), DefaultVertexFormats.BLOCK,
                    location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString()));
        }
        return bakedModel;
    }

    @Override
    public boolean isGlobalRenderer(TileEntity te) {
        return true;
    }

    @Override
    public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        /*if(itemRenderer == null)
        {
            itemRenderer = Minecraft.getMinecraft().getRenderItem();
        }

        TileArmillaryRing armillaryRing = (TileArmillaryRing) te;

        GlStateManager.pushMatrix();

        GL11.glTranslated(x, y, z);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        World world = Minecraft.getMinecraft().world;
        setLightmapDisabled(true);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        GlStateManager.disableLighting();

        Tessellator tessellator = Tessellator.getInstance();

        IBakedModel model = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(ModBlocks.bugSwatter.getDefaultState());
        ItemStack input = armillaryRing.input;
        NonNullList<ItemStack> chanceItems = armillaryRing.chanceEnhancementItems;

        if(((TileArmillaryRing) te).isCrafting)
        {

        }
        else
        {
            float floatingHeight = (float) Math.sin((System.currentTimeMillis() % 4000 / 2000.0) * Math.PI);

            GlStateManager.pushMatrix();

            GlStateManager.translate(0, floatingHeight * 0.25, 0);

            if(input != null)
            {
                GlStateManager.pushMatrix();
                float rot = System.currentTimeMillis() % 7200 / 20.0F;
                GlStateManager.translate(0.5F, 0.5F, 0.5F);
                GlStateManager.rotate(rot, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(0.95F, 0.95F, 0.95F);
                itemRenderer.renderItem(input, ItemCameraTransforms.TransformType.FIXED);
                GlStateManager.popMatrix();
            }

            GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
            tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
            Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(
                    world,
                    model,
                    world.getBlockState(te.getPos()),
                    te.getPos(),
                    Tessellator.getInstance().getBuffer(),
                    false);
            tessellator.draw();

            GlStateManager.popMatrix();
        }

        for(int i = 0; i < 8; i++)
        {
            if(((TileArmillaryRing) te).isCrafting)
            {
                float rotationAngle = (System.currentTimeMillis() % 3600 / 10.0F) + (360.0F / 8.0F) * i;
                long remainder = System.currentTimeMillis() % 7200;
                float rotationAngle2;
                if(remainder >= 3600)
                {
                    rotationAngle2 = -3 + ((remainder - 3600) / 3600.0F) * 6.0F;
                }
                else
                {
                    rotationAngle2 = 3 - (remainder / 3600.0F) * 6.0F;
                }
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.5, 0.5, 0.5);
                GlStateManager.rotate(rotationAngle, 1.0F, 0.0F, rotationAngle2);
                //GlStateManager.rotate(rotationAngle2, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(-0.5, -0.5, -0.5);

                GlStateManager.translate(0, -4.5, 0);
                GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
                //GlStateManager.translate(1.0, 0.0, 0.0);
                tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
                Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(
                        world,
                        model,
                        world.getBlockState(te.getPos()),
                        te.getPos(),
                        Tessellator.getInstance().getBuffer(),
                        false);
                tessellator.draw();
                GlStateManager.popMatrix();
            }
            else
            {
                float rotationAngle = 360.0F / 8.0F * i;
                float floatingHeight = (float) Math.sin((((System.currentTimeMillis() + 500*i) % 4000) / 2000.0) * Math.PI);
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.5, 0.5, 0.5);
                GlStateManager.rotate(rotationAngle, 0.0F, 1.0F, 0.0F);
                GlStateManager.translate(-0.5, -0.5, -0.5);

                GlStateManager.translate(4, floatingHeight * 0.25, 0);

                if(chanceItems.size() > i && !chanceItems.get(i).isEmpty())
                {
                    GlStateManager.pushMatrix();
                    float rot = System.currentTimeMillis() % 7200 / 20.0F;
                    GlStateManager.translate(0.5F, 0.5F, 0.5F);
                    GlStateManager.rotate(rot, 0.0F, 1.0F, 0.0F);
                    GlStateManager.scale(0.95F, 0.95F, 0.95F);
                    itemRenderer.renderItem(chanceItems.get(i), ItemCameraTransforms.TransformType.FIXED);
                    GlStateManager.popMatrix();
                }

                GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());
                tessellator.getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);
                Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelRenderer().renderModel(
                        world,
                        model,
                        world.getBlockState(te.getPos()),
                        te.getPos(),
                        Tessellator.getInstance().getBuffer(),
                        false);
                tessellator.draw();
                GlStateManager.popMatrix();
            }
        }

        GlStateManager.popMatrix();*/
    }
}
