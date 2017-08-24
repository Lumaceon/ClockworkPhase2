package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TESRTimezoneController extends TileEntitySpecialRenderer
{
    private RenderItem itemRenderer;
    private EntityItem item;

    public TESRTimezoneController() {
        itemRenderer = Minecraft.getMinecraft().getRenderItem();
    }

    @Override
    public boolean isGlobalRenderer(TileEntity te) {
        return true;
    }

    @Override
    public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GlStateManager.disableLighting();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder renderer = tessellator.getBuffer();
        bindTexture(Textures.MISC.CELESTIAL_COMPASS_SIDE);
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        addSideVerticies(renderer, te, partialTicks);
        tessellator.draw();

        GL11.glTranslatef(-5.0F, 0.0F, -5.0F);
        GL11.glScalef(11.0F, 1.0F, 11.0F);

        bindTexture(Textures.MISC.CELESTIAL_COMPASS_MAIN);
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0.98, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0.98, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0.98, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0.98, 0).tex(1, 0).endVertex();

        /*renderer.pos(1, 0.98, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0.98, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0.98, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0.98, 0).tex(1, 0).endVertex();*/

        //bindTexture(Textures.MISC.TIMEZONE_CONTROLLER_BOTTOM);
        renderer.pos(0, 0.01, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0.01, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0.01, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0.01, 0).tex(1, 0).endVertex();

        renderer.pos(1, 0.01, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0.01, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0.01, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0.01, 0).tex(1, 0).endVertex();
        tessellator.draw();

        /*bindTexture(Textures.MISC.TIMEZONE_CONTROLLER_METAL);
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0.8, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0.8, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0.8, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0.8, 0).tex(1, 0).endVertex();

        renderer.pos(1, 0.8, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0.8, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0.8, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0.8, 0).tex(1, 0).endVertex();
        tessellator.draw();

        GL11.glScalef(1F/11.0F, 1.0F, 1F/11.0F);
        GL11.glTranslatef(5.5F, 0.0F, 5.5F);
        GL11.glRotatef((System.currentTimeMillis() / 10) % 360, 0.0F, -1.0F, 0.0F);
        GL11.glTranslatef(-5.5F, 0.0F, -5.5F);
        GL11.glScalef(11.0F, 1.0F, 11.0F);
        bindTexture(Textures.MISC.TIMEZONE_CONTROLLER_LHAND);
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0.999, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0.999, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0.999, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0.999, 0).tex(1, 0).endVertex();

        renderer.pos(1, 0.999, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0.999, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0.999, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0.999, 0).tex(1, 0).endVertex();
        tessellator.draw();

        GL11.glScalef(1F/11.0F, 1.0F, 1F/11.0F);
        GL11.glTranslatef(5.5F, 0.0F, 5.5F);
        GL11.glRotatef(-((System.currentTimeMillis() / 10) % 360), 0.0F, -1.0F, 0.0F); //Negate old translation.
        GL11.glRotatef(((System.currentTimeMillis() / 600) % 360), 0.0F, -1.0F, 0.0F);
        GL11.glTranslatef(-5.5F, 0.0F, -5.5F);
        GL11.glScalef(11.0F, 1.0F, 11.0F);
        bindTexture(Textures.MISC.TIMEZONE_CONTROLLER_SHAND);
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos(0, 0.99, 0).tex(0, 0).endVertex();
        renderer.pos(0, 0.99, 1).tex(0, 1).endVertex();
        renderer.pos(1, 0.99, 1).tex(1, 1).endVertex();
        renderer.pos(1, 0.99, 0).tex(1, 0).endVertex();

        renderer.pos(1, 0.99, 0).tex(0, 0).endVertex();
        renderer.pos(1, 0.99, 1).tex(0, 1).endVertex();
        renderer.pos(0, 0.99, 1).tex(1, 1).endVertex();
        renderer.pos(0, 0.99, 0).tex(1, 0).endVertex();
        tessellator.draw();*/

        /*GL11.glScalef(1/11.0F, 1.0F, 1/11.0F);
        GL11.glTranslatef(5.0F, 0.0F, 5.0F);
        if(te != null && te instanceof TileCelestialCompass)
        {
            //double floatingHeight = (System.currentTimeMillis()/100.0) % 10.0;
            int rotationSpeed = 15; //This should never be 0. Slower numbers are faster.
            GL11.glPushMatrix();
            GL11.glTranslated(0.5, 3.0, 0.5);
            GL11.glRotatef(((System.currentTimeMillis()) % (360*rotationSpeed)) / (float)rotationSpeed, 0.0F, 1.0F, 0.0F);
            //float rotation = (Minecraft.getSystemTime() % 1440.0F) * 0.25F;
            //GL11.glRotatef(rotation, 0, 1.0F, 0);
            //GL11.glScalef(1.5F, 1.5F, 1.5F);
            TileCelestialCompass timezoneController = (TileCelestialCompass) te;
            ItemStack itemToRender = timezoneController.getCraftingItem(8);
            if(item == null)
                item = new EntityItem(te.getWorld());
            if(itemToRender != null)
            {
                item.setItem(itemToRender);
                itemRenderer.renderItem(itemToRender, ItemCameraTransforms.TransformType.FIXED);
            }
            GL11.glRotatef(-(((System.currentTimeMillis()) % (360*rotationSpeed)) / (float)rotationSpeed), 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            renderSideItems(timezoneController, rotationSpeed);
            GL11.glPopMatrix();
        }*/
        GL11.glPopMatrix();
    }

    /*private void renderSideItems(TileCelestialCompass timezoneController, int rotationSpeed) //Slower speed is faster (weird, but easier to code). Never 0.
    {
        ItemStack itemToRender;
        for(int n = 0; n < 8; n++)
        {
            itemToRender = timezoneController.getCraftingItem(n);
            if(itemToRender == null)
                continue;
            switch(n)
            {
                case 0:
                    GL11.glTranslatef(BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.LIFE_X, 0, BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.LIFE_Z);
                    GL11.glRotatef(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed, 0.0F, 1.0F, 0.0F);
                    itemRenderer.renderItem(itemToRender, ItemCameraTransforms.TransformType.FIXED);
                    GL11.glRotatef(-(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed), 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(-BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.LIFE_X, 0, -BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.LIFE_Z);
                    break;
                case 1:
                    GL11.glTranslatef(BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.LIGHT_X, 0, BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.LIGHT_Z);
                    GL11.glRotatef(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed, 0.0F, 1.0F, 0.0F);
                    itemRenderer.renderItem(itemToRender, ItemCameraTransforms.TransformType.FIXED);
                    GL11.glRotatef(-(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed), 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(-BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.LIGHT_X, 0, -BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.LIGHT_Z);
                    break;
                case 2:
                    GL11.glTranslatef(BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.WATER_X, 0, BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.WATER_Z);
                    GL11.glRotatef(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed, 0.0F, 1.0F, 0.0F);
                    itemRenderer.renderItem(itemToRender, ItemCameraTransforms.TransformType.FIXED);
                    GL11.glRotatef(-(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed), 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(-BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.WATER_X, 0, -BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.WATER_Z);
                    break;
                case 3:
                    GL11.glTranslatef(BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.EARTH_X, 0, BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.EARTH_Z);
                    GL11.glRotatef(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed, 0.0F, 1.0F, 0.0F);
                    itemRenderer.renderItem(itemToRender, ItemCameraTransforms.TransformType.FIXED);
                    GL11.glRotatef(-(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed), 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(-BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.EARTH_X, 0, -BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.EARTH_Z);
                    break;
                case 4:
                    GL11.glTranslatef(BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.AIR_X, 0, BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.AIR_Z);
                    GL11.glRotatef(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed, 0.0F, 1.0F, 0.0F);
                    itemRenderer.renderItem(itemToRender, ItemCameraTransforms.TransformType.FIXED);
                    GL11.glRotatef(-(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed), 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(-BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.AIR_X, 0, -BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.AIR_Z);
                    break;
                case 5:
                    GL11.glTranslatef(BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.FIRE_X, 0, BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.FIRE_Z);
                    GL11.glRotatef(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed, 0.0F, 1.0F, 0.0F);
                    itemRenderer.renderItem(itemToRender, ItemCameraTransforms.TransformType.FIXED);
                    GL11.glRotatef(-(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed), 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(-BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.FIRE_X, 0, -BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.FIRE_Z);
                    break;
                case 6:
                    GL11.glTranslatef(BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.CELESTIAL_X, 0, BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.CELESTIAL_Z);
                    GL11.glRotatef(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed, 0.0F, 1.0F, 0.0F);
                    itemRenderer.renderItem(itemToRender, ItemCameraTransforms.TransformType.FIXED);
                    GL11.glRotatef(-(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed), 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(-BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.CELESTIAL_X, 0, -BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.CELESTIAL_Z);
                    break;
                case 7:
                    GL11.glTranslatef(BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.DEATH_X, 0, BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.DEATH_Z);
                    GL11.glRotatef(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed, 0.0F, 1.0F, 0.0F);
                    itemRenderer.renderItem(itemToRender, ItemCameraTransforms.TransformType.FIXED);
                    GL11.glRotatef(-(((System.currentTimeMillis() + 45*n) % (360*rotationSpeed)) / (float)rotationSpeed), 0.0F, 1.0F, 0.0F);
                    GL11.glTranslatef(-BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.DEATH_X, 0, -BlockCelestialCompassSB.Ranges.CELESTIAL_COMPASS_COORDINATES.DEATH_Z);
                    break;
            }
        }
    }*/

    private void addSideVerticies(BufferBuilder renderer, TileEntity te, float partialTicks)
    {
        renderer.pos(4, 0, 4).tex(1, 1).endVertex(); //Bottom-right
        renderer.pos(4, 1, 4).tex(1, 0).endVertex(); //Top-right
        renderer.pos(4, 1, 5).tex(0, 0).endVertex(); //Top-left
        renderer.pos(4, 0, 5).tex(0, 1).endVertex(); //Bottom-left

        renderer.pos(4, 0, 5).tex(0, 1).endVertex();
        renderer.pos(4, 1, 5).tex(0, 0).endVertex();
        renderer.pos(4, 1, 4).tex(1, 0).endVertex();
        renderer.pos(4, 0, 4).tex(1, 1).endVertex();

        renderer.pos(4, 0, 5).tex(1, 1).endVertex();
        renderer.pos(4, 1, 5).tex(1, 0).endVertex();
        renderer.pos(3, 1, 5).tex(0, 0).endVertex();
        renderer.pos(3, 0, 5).tex(0, 1).endVertex();

        renderer.pos(3, 0, 5).tex(0, 1).endVertex();
        renderer.pos(3, 1, 5).tex(0, 0).endVertex();
        renderer.pos(4, 1, 5).tex(1, 0).endVertex();
        renderer.pos(4, 0, 5).tex(1, 1).endVertex();

        renderer.pos(3, 0, 5).tex(1, 1).endVertex(); //Bottom-right
        renderer.pos(3, 1, 5).tex(1, 0).endVertex(); //Top-right
        renderer.pos(3, 1, 6).tex(0, 0).endVertex(); //Top-left
        renderer.pos(3, 0, 6).tex(0, 1).endVertex(); //Bottom-left

        renderer.pos(3, 0, 6).tex(0, 1).endVertex();
        renderer.pos(3, 1, 6).tex(0, 0).endVertex();
        renderer.pos(3, 1, 5).tex(1, 0).endVertex();
        renderer.pos(3, 0, 5).tex(1, 1).endVertex();

        renderer.pos(3, 0, 6).tex(1, 1).endVertex();
        renderer.pos(3, 1, 6).tex(1, 0).endVertex();
        renderer.pos(2, 1, 6).tex(0, 0).endVertex();
        renderer.pos(2, 0, 6).tex(0, 1).endVertex();

        renderer.pos(2, 0, 6).tex(0, 1).endVertex();
        renderer.pos(2, 1, 6).tex(0, 0).endVertex();
        renderer.pos(3, 1, 6).tex(1, 0).endVertex();
        renderer.pos(3, 0, 6).tex(1, 1).endVertex();

        renderer.pos(2, 0, 6).tex(1, 1).endVertex();
        renderer.pos(2, 1, 6).tex(1, 0).endVertex();
        renderer.pos(1, 1, 6).tex(0, 0).endVertex();
        renderer.pos(1, 0, 6).tex(0, 1).endVertex();

        renderer.pos(1, 0, 6).tex(0, 1).endVertex();
        renderer.pos(1, 1, 6).tex(0, 0).endVertex();
        renderer.pos(2, 1, 6).tex(1, 0).endVertex();
        renderer.pos(2, 0, 6).tex(1, 1).endVertex();

        renderer.pos(1, 0, 6).tex(1, 1).endVertex();
        renderer.pos(1, 1, 6).tex(1, 0).endVertex();
        renderer.pos(0, 1, 6).tex(0, 0).endVertex();
        renderer.pos(0, 0, 6).tex(0, 1).endVertex();

        renderer.pos(0, 0, 6).tex(0, 1).endVertex();
        renderer.pos(0, 1, 6).tex(0, 0).endVertex();
        renderer.pos(1, 1, 6).tex(1, 0).endVertex();
        renderer.pos(1, 0, 6).tex(1, 1).endVertex();

        renderer.pos(0, 0, 6).tex(1, 1).endVertex();
        renderer.pos(0, 1, 6).tex(1, 0).endVertex();
        renderer.pos(-1, 1, 6).tex(0, 0).endVertex();
        renderer.pos(-1, 0, 6).tex(0, 1).endVertex();

        renderer.pos(-1, 0, 6).tex(0, 1).endVertex();
        renderer.pos(-1, 1, 6).tex(0, 0).endVertex();
        renderer.pos(0, 1, 6).tex(1, 0).endVertex();
        renderer.pos(0, 0, 6).tex(1, 1).endVertex();

        renderer.pos(-1, 0, 6).tex(1, 1).endVertex();
        renderer.pos(-1, 1, 6).tex(1, 0).endVertex();
        renderer.pos(-2, 1, 6).tex(0, 0).endVertex();
        renderer.pos(-2, 0, 6).tex(0, 1).endVertex();

        renderer.pos(-2, 0, 6).tex(0, 1).endVertex();
        renderer.pos(-2, 1, 6).tex(0, 0).endVertex();
        renderer.pos(-1, 1, 6).tex(1, 0).endVertex();
        renderer.pos(-1, 0, 6).tex(1, 1).endVertex();

        renderer.pos(-2, 0, 6).tex(1, 1).endVertex();
        renderer.pos(-2, 1, 6).tex(1, 0).endVertex();
        renderer.pos(-2, 1, 5).tex(0, 0).endVertex();
        renderer.pos(-2, 0, 5).tex(0, 1).endVertex();

        renderer.pos(-2, 0, 5).tex(0, 1).endVertex();
        renderer.pos(-2, 1, 5).tex(0, 0).endVertex();
        renderer.pos(-2, 1, 6).tex(1, 0).endVertex();
        renderer.pos(-2, 0, 6).tex(1, 1).endVertex();

        renderer.pos(-2, 0, 5).tex(1, 1).endVertex();
        renderer.pos(-2, 1, 5).tex(1, 0).endVertex();
        renderer.pos(-3, 1, 5).tex(0, 0).endVertex();
        renderer.pos(-3, 0, 5).tex(0, 1).endVertex();

        renderer.pos(-3, 0, 5).tex(0, 1).endVertex();
        renderer.pos(-3, 1, 5).tex(0, 0).endVertex();
        renderer.pos(-2, 1, 5).tex(1, 0).endVertex();
        renderer.pos(-2, 0, 5).tex(1, 1).endVertex();

        renderer.pos(-3, 0, 5).tex(1, 1).endVertex();
        renderer.pos(-3, 1, 5).tex(1, 0).endVertex();
        renderer.pos(-3, 1, 4).tex(0, 0).endVertex();
        renderer.pos(-3, 0, 4).tex(0, 1).endVertex();

        renderer.pos(-3, 0, 4).tex(0, 1).endVertex();
        renderer.pos(-3, 1, 4).tex(0, 0).endVertex();
        renderer.pos(-3, 1, 5).tex(1, 0).endVertex();
        renderer.pos(-3, 0, 5).tex(1, 1).endVertex();

        renderer.pos(-3, 0, 4).tex(1, 1).endVertex();
        renderer.pos(-3, 1, 4).tex(1, 0).endVertex();
        renderer.pos(-4, 1, 4).tex(0, 0).endVertex();
        renderer.pos(-4, 0, 4).tex(0, 1).endVertex();

        renderer.pos(-4, 0, 4).tex(0, 1).endVertex();
        renderer.pos(-4, 1, 4).tex(0, 0).endVertex();
        renderer.pos(-3, 1, 4).tex(1, 0).endVertex();
        renderer.pos(-3, 0, 4).tex(1, 1).endVertex();

        renderer.pos(-4, 0, 4).tex(1, 1).endVertex();
        renderer.pos(-4, 1, 4).tex(1, 0).endVertex();
        renderer.pos(-4, 1, 3).tex(0, 0).endVertex();
        renderer.pos(-4, 0, 3).tex(0, 1).endVertex();

        renderer.pos(-4, 0, 3).tex(0, 1).endVertex();
        renderer.pos(-4, 1, 3).tex(0, 0).endVertex();
        renderer.pos(-4, 1, 4).tex(1, 0).endVertex();
        renderer.pos(-4, 0, 4).tex(1, 1).endVertex();

        renderer.pos(-4, 0, 3).tex(1, 1).endVertex();
        renderer.pos(-4, 1, 3).tex(1, 0).endVertex();
        renderer.pos(-5, 1, 3).tex(0, 0).endVertex();
        renderer.pos(-5, 0, 3).tex(0, 1).endVertex();

        renderer.pos(-5, 0, 3).tex(0, 1).endVertex();
        renderer.pos(-5, 1, 3).tex(0, 0).endVertex();
        renderer.pos(-4, 1, 3).tex(1, 0).endVertex();
        renderer.pos(-4, 0, 3).tex(1, 1).endVertex();

        renderer.pos(-5, 0, 3).tex(1, 1).endVertex();
        renderer.pos(-5, 1, 3).tex(1, 0).endVertex();
        renderer.pos(-5, 1, 2).tex(0, 0).endVertex();
        renderer.pos(-5, 0, 2).tex(0, 1).endVertex();

        renderer.pos(-5, 0, 2).tex(0, 1).endVertex();
        renderer.pos(-5, 1, 2).tex(0, 0).endVertex();
        renderer.pos(-5, 1, 3).tex(1, 0).endVertex();
        renderer.pos(-5, 0, 3).tex(1, 1).endVertex();

        renderer.pos(-5, 0, 2).tex(1, 1).endVertex();
        renderer.pos(-5, 1, 2).tex(1, 0).endVertex();
        renderer.pos(-5, 1, 1).tex(0, 0).endVertex();
        renderer.pos(-5, 0, 1).tex(0, 1).endVertex();

        renderer.pos(-5, 0, 1).tex(0, 1).endVertex();
        renderer.pos(-5, 1, 1).tex(0, 0).endVertex();
        renderer.pos(-5, 1, 2).tex(1, 0).endVertex();
        renderer.pos(-5, 0, 2).tex(1, 1).endVertex();

        renderer.pos(-5, 0, 1).tex(1, 1).endVertex();
        renderer.pos(-5, 1, 1).tex(1, 0).endVertex();
        renderer.pos(-5, 1, 0).tex(0, 0).endVertex();
        renderer.pos(-5, 0, 0).tex(0, 1).endVertex();

        renderer.pos(-5, 0, 0).tex(0, 1).endVertex();
        renderer.pos(-5, 1, 0).tex(0, 0).endVertex();
        renderer.pos(-5, 1, 1).tex(1, 0).endVertex();
        renderer.pos(-5, 0, 1).tex(1, 1).endVertex();

        renderer.pos(-5, 0, 0).tex(1, 1).endVertex();
        renderer.pos(-5, 1, 0).tex(1, 0).endVertex();
        renderer.pos(-5, 1, -1).tex(0, 0).endVertex();
        renderer.pos(-5, 0, -1).tex(0, 1).endVertex();

        renderer.pos(-5, 0, -1).tex(0, 1).endVertex();
        renderer.pos(-5, 1, -1).tex(0, 0).endVertex();
        renderer.pos(-5, 1, 0).tex(1, 0).endVertex();
        renderer.pos(-5, 0, 0).tex(1, 1).endVertex();

        renderer.pos(-5, 0, -1).tex(1, 1).endVertex();
        renderer.pos(-5, 1, -1).tex(1, 0).endVertex();
        renderer.pos(-5, 1, -2).tex(0, 0).endVertex();
        renderer.pos(-5, 0, -2).tex(0, 1).endVertex();

        renderer.pos(-5, 0, -2).tex(0, 1).endVertex();
        renderer.pos(-5, 1, -2).tex(0, 0).endVertex();
        renderer.pos(-5, 1, -1).tex(1, 0).endVertex();
        renderer.pos(-5, 0, -1).tex(1, 1).endVertex();

        renderer.pos(-5, 0, -2).tex(1, 1).endVertex();
        renderer.pos(-5, 1, -2).tex(1, 0).endVertex();
        renderer.pos(-4, 1, -2).tex(0, 0).endVertex();
        renderer.pos(-4, 0, -2).tex(0, 1).endVertex();

        renderer.pos(-4, 0, -2).tex(0, 1).endVertex();
        renderer.pos(-4, 1, -2).tex(0, 0).endVertex();
        renderer.pos(-5, 1, -2).tex(1, 0).endVertex();
        renderer.pos(-5, 0, -2).tex(1, 1).endVertex();

        renderer.pos(-4, 0, -2).tex(1, 1).endVertex();
        renderer.pos(-4, 1, -2).tex(1, 0).endVertex();
        renderer.pos(-4, 1, -3).tex(0, 0).endVertex();
        renderer.pos(-4, 0, -3).tex(0, 1).endVertex();

        renderer.pos(-4, 0, -3).tex(0, 1).endVertex();
        renderer.pos(-4, 1, -3).tex(0, 0).endVertex();
        renderer.pos(-4, 1, -2).tex(1, 0).endVertex();
        renderer.pos(-4, 0, -2).tex(1, 1).endVertex();

        renderer.pos(-4, 0, -3).tex(1, 1).endVertex();
        renderer.pos(-4, 1, -3).tex(1, 0).endVertex();
        renderer.pos(-3, 1, -3).tex(0, 0).endVertex();
        renderer.pos(-3, 0, -3).tex(0, 1).endVertex();

        renderer.pos(-3, 0, -3).tex(0, 1).endVertex();
        renderer.pos(-3, 1, -3).tex(0, 0).endVertex();
        renderer.pos(-4, 1, -3).tex(1, 0).endVertex();
        renderer.pos(-4, 0, -3).tex(1, 1).endVertex();

        renderer.pos(-3, 0, -3).tex(1, 1).endVertex();
        renderer.pos(-3, 1, -3).tex(1, 0).endVertex();
        renderer.pos(-3, 1, -4).tex(0, 0).endVertex();
        renderer.pos(-3, 0, -4).tex(0, 1).endVertex();

        renderer.pos(-3, 0, -4).tex(0, 1).endVertex();
        renderer.pos(-3, 1, -4).tex(0, 0).endVertex();
        renderer.pos(-3, 1, -3).tex(1, 0).endVertex();
        renderer.pos(-3, 0, -3).tex(1, 1).endVertex();

        renderer.pos(-3, 0, -4).tex(1, 1).endVertex();
        renderer.pos(-3, 1, -4).tex(1, 0).endVertex();
        renderer.pos(-2, 1, -4).tex(0, 0).endVertex();
        renderer.pos(-2, 0, -4).tex(0, 1).endVertex();

        renderer.pos(-2, 0, -4).tex(0, 1).endVertex();
        renderer.pos(-2, 1, -4).tex(0, 0).endVertex();
        renderer.pos(-3, 1, -4).tex(1, 0).endVertex();
        renderer.pos(-3, 0, -4).tex(1, 1).endVertex();

        renderer.pos(-2, 0, -4).tex(1, 1).endVertex();
        renderer.pos(-2, 1, -4).tex(1, 0).endVertex();
        renderer.pos(-2, 1, -5).tex(0, 0).endVertex();
        renderer.pos(-2, 0, -5).tex(0, 1).endVertex();

        renderer.pos(-2, 0, -5).tex(0, 1).endVertex();
        renderer.pos(-2, 1, -5).tex(0, 0).endVertex();
        renderer.pos(-2, 1, -4).tex(1, 0).endVertex();
        renderer.pos(-2, 0, -4).tex(1, 1).endVertex();

        renderer.pos(-2, 0, -5).tex(1, 1).endVertex();
        renderer.pos(-2, 1, -5).tex(1, 0).endVertex();
        renderer.pos(-1, 1, -5).tex(0, 0).endVertex();
        renderer.pos(-1, 0, -5).tex(0, 1).endVertex();

        renderer.pos(-1, 0, -5).tex(0, 1).endVertex();
        renderer.pos(-1, 1, -5).tex(0, 0).endVertex();
        renderer.pos(-2, 1, -5).tex(1, 0).endVertex();
        renderer.pos(-2, 0, -5).tex(1, 1).endVertex();
        //

        renderer.pos(-1, 0, -5).tex(1, 1).endVertex();
        renderer.pos(-1, 1, -5).tex(1, 0).endVertex();
        renderer.pos(0, 1, -5).tex(0, 0).endVertex();
        renderer.pos(0, 0, -5).tex(0, 1).endVertex();

        renderer.pos(0, 0, -5).tex(0, 1).endVertex();
        renderer.pos(0, 1, -5).tex(0, 0).endVertex();
        renderer.pos(-1, 1, -5).tex(1, 0).endVertex();
        renderer.pos(-1, 0, -5).tex(1, 1).endVertex();

        renderer.pos(0, 0, -5).tex(1, 1).endVertex();
        renderer.pos(0, 1, -5).tex(1, 0).endVertex();
        renderer.pos(1, 1, -5).tex(0, 0).endVertex();
        renderer.pos(1, 0, -5).tex(0, 1).endVertex();

        renderer.pos(1, 0, -5).tex(0, 1).endVertex();
        renderer.pos(1, 1, -5).tex(0, 0).endVertex();
        renderer.pos(0, 1, -5).tex(1, 0).endVertex();
        renderer.pos(0, 0, -5).tex(1, 1).endVertex();

        renderer.pos(1, 0, -5).tex(1, 1).endVertex();
        renderer.pos(1, 1, -5).tex(1, 0).endVertex();
        renderer.pos(2, 1, -5).tex(0, 0).endVertex();
        renderer.pos(2, 0, -5).tex(0, 1).endVertex();

        renderer.pos(2, 0, -5).tex(0, 1).endVertex();
        renderer.pos(2, 1, -5).tex(0, 0).endVertex();
        renderer.pos(1, 1, -5).tex(1, 0).endVertex();
        renderer.pos(1, 0, -5).tex(1, 1).endVertex();

        renderer.pos(2, 0, -5).tex(1, 1).endVertex();
        renderer.pos(2, 1, -5).tex(1, 0).endVertex();
        renderer.pos(3, 1, -5).tex(0, 0).endVertex();
        renderer.pos(3, 0, -5).tex(0, 1).endVertex();

        renderer.pos(3, 0, -5).tex(0, 1).endVertex();
        renderer.pos(3, 1, -5).tex(0, 0).endVertex();
        renderer.pos(2, 1, -5).tex(1, 0).endVertex();
        renderer.pos(2, 0, -5).tex(1, 1).endVertex();

        renderer.pos(3, 0, -5).tex(1, 1).endVertex();
        renderer.pos(3, 1, -5).tex(1, 0).endVertex();
        renderer.pos(3, 1, -4).tex(0, 0).endVertex();
        renderer.pos(3, 0, -4).tex(0, 1).endVertex();

        renderer.pos(3, 0, -4).tex(0, 1).endVertex();
        renderer.pos(3, 1, -4).tex(0, 0).endVertex();
        renderer.pos(3, 1, -5).tex(1, 0).endVertex();
        renderer.pos(3, 0, -5).tex(1, 1).endVertex();

        renderer.pos(3, 0, -4).tex(1, 1).endVertex();
        renderer.pos(3, 1, -4).tex(1, 0).endVertex();
        renderer.pos(4, 1, -4).tex(0, 0).endVertex();
        renderer.pos(4, 0, -4).tex(0, 1).endVertex();

        renderer.pos(4, 0, -4).tex(0, 1).endVertex();
        renderer.pos(4, 1, -4).tex(0, 0).endVertex();
        renderer.pos(3, 1, -4).tex(1, 0).endVertex();
        renderer.pos(3, 0, -4).tex(1, 1).endVertex();

        renderer.pos(4, 0, -4).tex(1, 1).endVertex();
        renderer.pos(4, 1, -4).tex(1, 0).endVertex();
        renderer.pos(4, 1, -3).tex(0, 0).endVertex();
        renderer.pos(4, 0, -3).tex(0, 1).endVertex();

        renderer.pos(4, 0, -3).tex(0, 1).endVertex();
        renderer.pos(4, 1, -3).tex(0, 0).endVertex();
        renderer.pos(4, 1, -4).tex(1, 0).endVertex();
        renderer.pos(4, 0, -4).tex(1, 1).endVertex();

        renderer.pos(4, 0, -3).tex(1, 1).endVertex();
        renderer.pos(4, 1, -3).tex(1, 0).endVertex();
        renderer.pos(5, 1, -3).tex(0, 0).endVertex();
        renderer.pos(5, 0, -3).tex(0, 1).endVertex();

        renderer.pos(5, 0, -3).tex(0, 1).endVertex();
        renderer.pos(5, 1, -3).tex(0, 0).endVertex();
        renderer.pos(4, 1, -3).tex(1, 0).endVertex();
        renderer.pos(4, 0, -3).tex(1, 1).endVertex();

        renderer.pos(5, 0, -3).tex(1, 1).endVertex();
        renderer.pos(5, 1, -3).tex(1, 0).endVertex();
        renderer.pos(5, 1, -2).tex(0, 0).endVertex();
        renderer.pos(5, 0, -2).tex(0, 1).endVertex();

        renderer.pos(5, 0, -2).tex(0, 1).endVertex();
        renderer.pos(5, 1, -2).tex(0, 0).endVertex();
        renderer.pos(5, 1, -3).tex(1, 0).endVertex();
        renderer.pos(5, 0, -3).tex(1, 1).endVertex();

        renderer.pos(5, 0, -2).tex(1, 1).endVertex();
        renderer.pos(5, 1, -2).tex(1, 0).endVertex();
        renderer.pos(6, 1, -2).tex(0, 0).endVertex();
        renderer.pos(6, 0, -2).tex(0, 1).endVertex();

        renderer.pos(6, 0, -2).tex(0, 1).endVertex();
        renderer.pos(6, 1, -2).tex(0, 0).endVertex();
        renderer.pos(5, 1, -2).tex(1, 0).endVertex();
        renderer.pos(5, 0, -2).tex(1, 1).endVertex();

        renderer.pos(6, 0, -2).tex(1, 1).endVertex();
        renderer.pos(6, 1, -2).tex(1, 0).endVertex();
        renderer.pos(6, 1, -1).tex(0, 0).endVertex();
        renderer.pos(6, 0, -1).tex(0, 1).endVertex();

        renderer.pos(6, 0, -1).tex(0, 1).endVertex();
        renderer.pos(6, 1, -1).tex(0, 0).endVertex();
        renderer.pos(6, 1, -2).tex(1, 0).endVertex();
        renderer.pos(6, 0, -2).tex(1, 1).endVertex();
        //

        renderer.pos(6, 0, -1).tex(1, 1).endVertex();
        renderer.pos(6, 1, -1).tex(1, 0).endVertex();
        renderer.pos(6, 1, 0).tex(0, 0).endVertex();
        renderer.pos(6, 0, 0).tex(0, 1).endVertex();

        renderer.pos(6, 0, 0).tex(0, 1).endVertex();
        renderer.pos(6, 1, 0).tex(0, 0).endVertex();
        renderer.pos(6, 1, -1).tex(1, 0).endVertex();
        renderer.pos(6, 0, -1).tex(1, 1).endVertex();

        renderer.pos(6, 0, 0).tex(1, 1).endVertex();
        renderer.pos(6, 1, 0).tex(1, 0).endVertex();
        renderer.pos(6, 1, 1).tex(0, 0).endVertex();
        renderer.pos(6, 0, 1).tex(0, 1).endVertex();

        renderer.pos(6, 0, 1).tex(0, 1).endVertex();
        renderer.pos(6, 1, 1).tex(0, 0).endVertex();
        renderer.pos(6, 1, 0).tex(1, 0).endVertex();
        renderer.pos(6, 0, 0).tex(1, 1).endVertex();

        renderer.pos(6, 0, 1).tex(1, 1).endVertex();
        renderer.pos(6, 1, 1).tex(1, 0).endVertex();
        renderer.pos(6, 1, 2).tex(0, 0).endVertex();
        renderer.pos(6, 0, 2).tex(0, 1).endVertex();

        renderer.pos(6, 0, 2).tex(0, 1).endVertex();
        renderer.pos(6, 1, 2).tex(0, 0).endVertex();
        renderer.pos(6, 1, 1).tex(1, 0).endVertex();
        renderer.pos(6, 0, 1).tex(1, 1).endVertex();

        renderer.pos(6, 0, 2).tex(1, 1).endVertex();
        renderer.pos(6, 1, 2).tex(1, 0).endVertex();
        renderer.pos(6, 1, 3).tex(0, 0).endVertex();
        renderer.pos(6, 0, 3).tex(0, 1).endVertex();

        renderer.pos(6, 0, 3).tex(0, 1).endVertex();
        renderer.pos(6, 1, 3).tex(0, 0).endVertex();
        renderer.pos(6, 1, 2).tex(1, 0).endVertex();
        renderer.pos(6, 0, 2).tex(1, 1).endVertex();

        renderer.pos(6, 0, 3).tex(1, 1).endVertex();
        renderer.pos(6, 1, 3).tex(1, 0).endVertex();
        renderer.pos(5, 1, 3).tex(0, 0).endVertex();
        renderer.pos(5, 0, 3).tex(0, 1).endVertex();

        renderer.pos(5, 0, 3).tex(0, 1).endVertex();
        renderer.pos(5, 1, 3).tex(0, 0).endVertex();
        renderer.pos(6, 1, 3).tex(1, 0).endVertex();
        renderer.pos(6, 0, 3).tex(1, 1).endVertex();

        renderer.pos(5, 0, 3).tex(1, 1).endVertex();
        renderer.pos(5, 1, 3).tex(1, 0).endVertex();
        renderer.pos(5, 1, 4).tex(0, 0).endVertex();
        renderer.pos(5, 0, 4).tex(0, 1).endVertex();

        renderer.pos(5, 0, 4).tex(0, 1).endVertex();
        renderer.pos(5, 1, 4).tex(0, 0).endVertex();
        renderer.pos(5, 1, 3).tex(1, 0).endVertex();
        renderer.pos(5, 0, 3).tex(1, 1).endVertex();

        renderer.pos(5, 0, 4).tex(1, 1).endVertex();
        renderer.pos(5, 1, 4).tex(1, 0).endVertex();
        renderer.pos(4, 1, 4).tex(0, 0).endVertex();
        renderer.pos(4, 0, 4).tex(0, 1).endVertex();

        renderer.pos(4, 0, 4).tex(0, 1).endVertex();
        renderer.pos(4, 1, 4).tex(0, 0).endVertex();
        renderer.pos(5, 1, 4).tex(1, 0).endVertex();
        renderer.pos(5, 0, 4).tex(1, 1).endVertex();
    }
}
