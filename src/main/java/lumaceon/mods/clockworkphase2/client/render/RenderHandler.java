package lumaceon.mods.clockworkphase2.client.render;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lumaceon.mods.clockworkphase2.api.timezone.ITimezone;
import lumaceon.mods.clockworkphase2.api.timezone.TimezoneHandler;
import lumaceon.mods.clockworkphase2.block.BlockCelestialCompassSB;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.tile.TileCelestialCompass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public class RenderHandler
{
    public static RenderItem renderItem;
    public static Minecraft mc;
    public static EntityItem item;

    public RenderHandler()
    {
        renderItem = new RenderItem();
        renderItem.renderWithColor = false;
        renderItem.setRenderManager(RenderManager.instance);

        mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event)
    {
        if(RenderManager.instance.renderEngine == null)
            return;
        if(mc.theWorld != null)
        {
            for(int[] area : TimezoneHandler.timezones)
            {
                ITimezone timezone = TimezoneHandler.getTimeZone(area[0], area[1], area[2], area[3]);
                if(timezone != null)
                {
                    TIMEZONE.renderGlyph(area, (double)area[0] - TileEntityRendererDispatcher.staticPlayerX, (double)area[1] - TileEntityRendererDispatcher.staticPlayerY, (double)area[2] - TileEntityRendererDispatcher.staticPlayerZ, timezone);
                    TIMEZONE.renderCelestialCompassItems(area, (double)area[0] - TileEntityRendererDispatcher.staticPlayerX, (double)area[1] - TileEntityRendererDispatcher.staticPlayerY, (double)area[2] - TileEntityRendererDispatcher.staticPlayerZ);
                }
            }
        }
    }

    public static class TIMEZONE
    {
        public static void renderGlyph(int[] area, double x, double y, double z, ITimezone timezone)
        {
            if(mc.renderViewEntity != null)
            {
                double dist = Math.sqrt(Math.pow(mc.renderViewEntity.posX - area[0], 2) + Math.pow(mc.renderViewEntity.posZ - area[2], 2));
                if(dist > timezone.getRange() * 2)
                {
                    return;
                }
            }
            mc.renderEngine.bindTexture(Textures.GLYPH.BASE_GLYPH);
            double low = -1 - timezone.getRange();
            double high = timezone.getRange();

            Tessellator tessy = Tessellator.instance;
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glTranslated(x, y + 257 - area[1], z);
            tessy.startDrawingQuads();
            tessy.setColorRGBA_F(1, 1, 1, 1);
            tessy.addVertexWithUV(low, 0, low, 0, 0);
            tessy.addVertexWithUV(low, 0, high, 0, 1);
            tessy.addVertexWithUV(high, 0, high, 1, 1);
            tessy.addVertexWithUV(high, 0, low, 1, 0);

            tessy.addVertexWithUV(high, 0, low, 0, 0);
            tessy.addVertexWithUV(high, 0, high, 0, 1);
            tessy.addVertexWithUV(low, 0, high, 1, 1);
            tessy.addVertexWithUV(low, 0, low, 1, 0);
            tessy.draw();
            GL11.glPopMatrix();
        }

        public static void renderCelestialCompassItems(int[] area, double x, double y, double z)
        {
            TileEntity te = mc.theWorld.getTileEntity(area[0], area[1], area[2]);
            if(te != null && te instanceof TileCelestialCompass)
            {
                TileCelestialCompass celestialCompass = (TileCelestialCompass) te;
                ItemStack itemToRender = celestialCompass.getCenterItem();
                float scale;

                if(item == null)
                    item = new EntityItem(mc.theWorld);
                if(itemToRender != null)
                {
                    GL11.glPushMatrix();
                    if(itemToRender.getItem() instanceof ItemBlock)
                        scale = 1.5F;
                    else
                        scale = 1.0F;
                    GL11.glTranslated(x + 0.5F, y + 3, z + 0.5F);
                    item.setEntityItemStack(itemToRender);
                    GL11.glScalef(scale, scale, scale);
                    GL11.glRotatef((Minecraft.getSystemTime() % 5760.0F) / 16, 0, 1, 0);
                    renderItem.doRender(item, 0, 0, 0, 0, 0);
                    GL11.glPopMatrix();
                }

                for(int n = 0; n < 8; n++)
                {
                    itemToRender = celestialCompass.getCraftingItem(n);
                    if(itemToRender == null)
                        continue;
                    GL11.glPushMatrix();
                    if(itemToRender.getItem() instanceof ItemBlock)
                        scale = 1.5F;
                    else
                        scale = 1.0F;
                    switch(n)
                    {
                        case 0:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.TOP_X, y + 2, z + BlockCelestialCompassSB.Ranges.TOP_Z);
                            GL11.glScalef(scale, scale, scale);
                            GL11.glRotatef((Minecraft.getSystemTime() % 5760.0F) / 16, 0, 1, 0);
                            item.setEntityItemStack(itemToRender);
                            renderItem.doRender(item, 0, 0, 0, 0, 0);
                            break;
                        case 1:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.TOP_RIGHT_X, y + 2, z + BlockCelestialCompassSB.Ranges.TOP_RIGHT_Z);
                            GL11.glScalef(scale, scale, scale);
                            GL11.glRotatef((Minecraft.getSystemTime() % 5760.0F) / 16, 0, 1, 0);
                            item.setEntityItemStack(itemToRender);
                            renderItem.doRender(item, 0, 0, 0, 0, 0);
                            break;
                        case 2:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.RIGHT_X, y + 2, z + BlockCelestialCompassSB.Ranges.RIGHT_Z);
                            GL11.glScalef(scale, scale, scale);
                            GL11.glRotatef((Minecraft.getSystemTime() % 5760.0F) / 16, 0, 1, 0);
                            item.setEntityItemStack(itemToRender);
                            renderItem.doRender(item, 0, 0, 0, 0, 0);
                            break;
                        case 3:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.BOTTOM_RIGHT_X, y + 2, z + BlockCelestialCompassSB.Ranges.BOTTOM_RIGHT_Z);
                            GL11.glScalef(scale, scale, scale);
                            GL11.glRotatef((Minecraft.getSystemTime() % 5760.0F) / 16, 0, 1, 0);
                            item.setEntityItemStack(itemToRender);
                            renderItem.doRender(item, 0, 0, 0, 0, 0);
                            break;
                        case 4:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.BOTTOM_X, y + 2, z + BlockCelestialCompassSB.Ranges.BOTTOM_Z);
                            GL11.glScalef(scale, scale, scale);
                            GL11.glRotatef((Minecraft.getSystemTime() % 5760.0F) / 16, 0, 1, 0);
                            item.setEntityItemStack(itemToRender);
                            renderItem.doRender(item, 0, 0, 0, 0, 0);
                            break;
                        case 5:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.BOTTOM_LEFT_X, y + 2, z + BlockCelestialCompassSB.Ranges.BOTTOM_LEFT_Z);
                            GL11.glScalef(scale, scale, scale);
                            GL11.glRotatef((Minecraft.getSystemTime() % 5760.0F) / 16, 0, 1, 0);
                            item.setEntityItemStack(itemToRender);
                            renderItem.doRender(item, 0, 0, 0, 0, 0);
                            break;
                        case 6:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.LEFT_X, y + 2, z + BlockCelestialCompassSB.Ranges.LEFT_Z);
                            GL11.glScalef(scale, scale, scale);
                            GL11.glRotatef((Minecraft.getSystemTime() % 5760.0F) / 16, 0, 1, 0);
                            item.setEntityItemStack(itemToRender);
                            renderItem.doRender(item, 0, 0, 0, 0, 0);
                            break;
                        case 7:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.TOP_LEFT_X, y + 2, z + BlockCelestialCompassSB.Ranges.TOP_LEFT_Z);
                            GL11.glScalef(scale, scale, scale);
                            GL11.glRotatef((Minecraft.getSystemTime() % 5760.0F) / 16, 0, 1, 0);
                            item.setEntityItemStack(itemToRender);
                            renderItem.doRender(item, 0, 0, 0, 0, 0);
                            break;
                    }
                    GL11.glPopMatrix();
                }
            }
        }
    }
}
