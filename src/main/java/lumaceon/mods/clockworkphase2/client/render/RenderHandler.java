package lumaceon.mods.clockworkphase2.client.render;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import lumaceon.mods.clockworkphase2.api.item.ITimezoneModule;
import lumaceon.mods.clockworkphase2.api.time.ITimezone;
import lumaceon.mods.clockworkphase2.api.time.TimezoneHandler;
import lumaceon.mods.clockworkphase2.block.BlockCelestialCompassSB;
import lumaceon.mods.clockworkphase2.client.particle.sequence.ParticleSequence;
import lumaceon.mods.clockworkphase2.client.particle.sequence.ParticleSequenceTimezone;
import lumaceon.mods.clockworkphase2.client.render.elements.overlay.OverlayRenderElement;
import lumaceon.mods.clockworkphase2.client.render.elements.overlay.OverlayRenderElementTemporalInfluence;
import lumaceon.mods.clockworkphase2.client.render.elements.world.WorldRenderElement;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.tile.TileCelestialCompass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RenderHandler
{
    public static OverlayRenderElementTemporalInfluence overlayInfluence = new OverlayRenderElementTemporalInfluence();
    public static RenderItem renderItem;
    public static Minecraft mc;
    public static EntityItem item;
    public static double interpolatedPosX, interpolatedPosY, interpolatedPosZ;

    public RenderHandler()
    {
        renderItem = new RenderItem();
        renderItem.renderWithColor = false;
        renderItem.setRenderManager(RenderManager.instance);

        mc = Minecraft.getMinecraft();

        //registerWorldRenderElement(schematicRenderer);
    }

    public static ArrayList<OverlayRenderElement> overlayRenderList = new ArrayList<OverlayRenderElement>();
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onRenderOverlay(RenderGameOverlayEvent.Post event)
    {
        for(int n = 0; n < overlayRenderList.size(); n++)
        {
            OverlayRenderElement element = overlayRenderList.get(n);
            if(!element.render(event))
            {
                overlayRenderList.remove(n);
                n--;
            }
        }
    }

    public static ArrayList<WorldRenderElement> worldRenderList = new ArrayList<WorldRenderElement>();

    /**
     * Registers a worldRenderElement to be rendered each RenderWorldLastEvent tick.
     * @param worldRenderElement The element to add.
     * @return False if this position is already registered (and wasn't registered).
     */
    public static boolean registerWorldRenderElement(WorldRenderElement worldRenderElement)
    {
        for(WorldRenderElement WRE : worldRenderList)
            if(WRE.alreadyExists(worldRenderElement))
                return false;

        worldRenderList.add(worldRenderElement);
        return true;
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event)
    {
        interpolatedPosX = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * (double)event.partialTicks;
        interpolatedPosY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double)event.partialTicks;
        interpolatedPosZ = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * (double)event.partialTicks;
        if(!ParticleSequence.particles.isEmpty())
        {
            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_LIGHTING);
            Minecraft.getMinecraft().renderEngine.bindTexture(Textures.PARTICLE.TEST);
            for(int n = 0; n < ParticleSequence.particles.size(); n++)
            {
                EntityFX fx = ParticleSequence.particles.get(n);
                if(fx == null || fx.isDead)
                {
                    ParticleSequence.particles.remove(n);
                    --n;
                }
                else
                    fx.renderParticle(Tessellator.instance, event.partialTicks, ActiveRenderInfo.rotationX, ActiveRenderInfo.rotationXZ, ActiveRenderInfo.rotationZ, ActiveRenderInfo.rotationYZ, ActiveRenderInfo.rotationXY);
            }
            GL11.glPopMatrix();
        }

        if(RenderManager.instance.renderEngine == null)
            return;

        if(mc.theWorld != null)
        {
            for(int[] area : TimezoneHandler.timezones)
            {
                ITimezone timezone = TimezoneHandler.getTimeZone(area[0], area[1], area[2], area[3]);
                if(timezone != null)
                {
                    ItemStack coreStack = timezone.getTimezoneModule(8);
                    //if(coreStack != null && coreStack.getItem() instanceof ITemporalCore)
                    //    TIMEZONE.renderGlyph(area, (double)area[0] - TileEntityRendererDispatcher.staticPlayerX, (double)area[1] - TileEntityRendererDispatcher.staticPlayerY, (double)area[2] - TileEntityRendererDispatcher.staticPlayerZ, timezone);
                    TIMEZONE.renderCelestialCompassItems(area, (double)area[0] - TileEntityRendererDispatcher.staticPlayerX, (double)area[1] - TileEntityRendererDispatcher.staticPlayerY, (double)area[2] - TileEntityRendererDispatcher.staticPlayerZ);
                    if(!TIMEZONE.timezoneSequences.containsKey(timezone) || TIMEZONE.timezoneSequences.get(timezone) == null)
                        TIMEZONE.timezoneSequences.put(timezone, ParticleSequence.spawnParticleSequence(new ParticleSequenceTimezone(timezone, area[0] + 0.5, area[1] + 0.5, area[2] + 0.5)));
                }
            }

            EntityLivingBase camera = mc.renderViewEntity;
            for(WorldRenderElement wre : worldRenderList)
                if(wre != null && wre.isSameWorld(mc.theWorld))
                    if(Math.sqrt(Math.pow(Math.abs(wre.xPos - camera.posX), 2) + Math.pow(Math.abs(wre.yPos - camera.posY), 2) + Math.pow(Math.abs(wre.zPos - camera.posZ), 2)) <= wre.maxRenderDistance())
                        wre.render(wre.xPos - TileEntityRendererDispatcher.staticPlayerX, wre.yPos - TileEntityRendererDispatcher.staticPlayerY, wre.zPos - TileEntityRendererDispatcher.staticPlayerZ);
        }
    }

    public static class TIMEZONE
    {
        public static Map<ITimezone, ParticleSequence> timezoneSequences = new HashMap<ITimezone, ParticleSequence>();

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
            GL11.glTranslated(x + 1, y + 257 - area[1], z + 1);
            //GL11.glRotatef((Minecraft.getSystemTime() % 115200.0F) / 320, 0.0F, 1.0F, 0.0F);
            tessy.startDrawingQuads();
            tessy.addVertexWithUV(low, 0, low, 0, 0);
            tessy.addVertexWithUV(low, 0, high, 0, 1);
            tessy.addVertexWithUV(high, 0, high, 1, 1);
            tessy.addVertexWithUV(high, 0, low, 1, 0);

            tessy.addVertexWithUV(high, 0, low, 0, 0);
            tessy.addVertexWithUV(high, 0, high, 0, 1);
            tessy.addVertexWithUV(low, 0, high, 1, 1);
            tessy.addVertexWithUV(low, 0, low, 1, 0);
            tessy.draw();

            ItemStack stack;
            for(int n = 0; n < 8; n++)
            {
                stack = timezone.getTimezoneModule(n);
                if(stack != null && stack.getItem() instanceof ITimezoneModule)
                {
                    ITimezoneModule ts = (ITimezoneModule) stack.getItem();
                    mc.renderEngine.bindTexture(Textures.GLYPH.BASE_GLYPH_GEMS[n]);
                    GL11.glColor3f(ts.getColorRed(stack) / 255.0F, ts.getColorGreen(stack) / 255.0F, ts.getColorBlue(stack) / 255.0F);
                    tessy.startDrawingQuads();
                    tessy.addVertexWithUV(low, 0, low, 0, 0);
                    tessy.addVertexWithUV(low, 0, high, 0, 1);
                    tessy.addVertexWithUV(high, 0, high, 1, 1);
                    tessy.addVertexWithUV(high, 0, low, 1, 0);

                    tessy.addVertexWithUV(high, 0, low, 0, 0);
                    tessy.addVertexWithUV(high, 0, high, 0, 1);
                    tessy.addVertexWithUV(low, 0, high, 1, 1);
                    tessy.addVertexWithUV(low, 0, low, 1, 0);
                    tessy.draw();
                }
            }
            GL11.glPopMatrix();
        }

        public static void renderCelestialCompassItems(int[] area, double x, double y, double z)
        {
            TileEntity te = mc.theWorld.getTileEntity(area[0], area[1], area[2]);
            if(te != null && te instanceof TileCelestialCompass)
            {
                TileCelestialCompass celestialCompass = (TileCelestialCompass) te;
                ItemStack itemToRender;
                //float scale;

                if(item == null)
                    item = new EntityItem(mc.theWorld);
                /*if(itemToRender != null)
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
                }*/

                ResourceLocation loc = null;
                for(int n = 0; n < 9; n++)
                {
                    itemToRender = celestialCompass.getTimezoneModule(n);
                    if(itemToRender == null)
                        continue;
                    if(itemToRender.getItem() instanceof ITimezoneModule)
                        loc = ((ITimezoneModule) itemToRender.getItem()).getGlyphTexture(itemToRender);
                    //else if(itemToRender.getItem() instanceof ITemporalCore)
                    //    loc = ((ITemporalCore) itemToRender.getItem()).getGlyphTexture(itemToRender);
                    GL11.glPushMatrix();
                    GL11.glColor3f(1, 1, 1);
                    mc.renderEngine.bindTexture(loc);
                    Tessellator tessy = Tessellator.instance;
                    float high = 0.5F;
                    float low = -high;

                    switch(n)
                    {
                        case 0:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.TOP_X, y + 1.1, z + BlockCelestialCompassSB.Ranges.TOP_Z);
                            tessy.startDrawingQuads();
                            tessy.addVertexWithUV(low, 0, low, 0, 0);
                            tessy.addVertexWithUV(low, 0, high, 0, 1);
                            tessy.addVertexWithUV(high, 0, high, 1, 1);
                            tessy.addVertexWithUV(high, 0, low, 1, 0);

                            tessy.addVertexWithUV(high, 0, low, 0, 0);
                            tessy.addVertexWithUV(high, 0, high, 0, 1);
                            tessy.addVertexWithUV(low, 0, high, 1, 1);
                            tessy.addVertexWithUV(low, 0, low, 1, 0);
                            tessy.draw();
                            break;
                        case 1:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.TOP_RIGHT_X, y + 1.1, z + BlockCelestialCompassSB.Ranges.TOP_RIGHT_Z);
                            tessy.startDrawingQuads();
                            tessy.addVertexWithUV(low, 0, low, 0, 0);
                            tessy.addVertexWithUV(low, 0, high, 0, 1);
                            tessy.addVertexWithUV(high, 0, high, 1, 1);
                            tessy.addVertexWithUV(high, 0, low, 1, 0);

                            tessy.addVertexWithUV(high, 0, low, 0, 0);
                            tessy.addVertexWithUV(high, 0, high, 0, 1);
                            tessy.addVertexWithUV(low, 0, high, 1, 1);
                            tessy.addVertexWithUV(low, 0, low, 1, 0);
                            tessy.draw();
                            break;
                        case 2:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.RIGHT_X, y + 1.1, z + BlockCelestialCompassSB.Ranges.RIGHT_Z);
                            tessy.startDrawingQuads();
                            tessy.addVertexWithUV(low, 0, low, 0, 0);
                            tessy.addVertexWithUV(low, 0, high, 0, 1);
                            tessy.addVertexWithUV(high, 0, high, 1, 1);
                            tessy.addVertexWithUV(high, 0, low, 1, 0);

                            tessy.addVertexWithUV(high, 0, low, 0, 0);
                            tessy.addVertexWithUV(high, 0, high, 0, 1);
                            tessy.addVertexWithUV(low, 0, high, 1, 1);
                            tessy.addVertexWithUV(low, 0, low, 1, 0);
                            tessy.draw();
                            break;
                        case 3:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.BOTTOM_RIGHT_X, y + 1.1, z + BlockCelestialCompassSB.Ranges.BOTTOM_RIGHT_Z);
                            tessy.startDrawingQuads();
                            tessy.addVertexWithUV(low, 0, low, 0, 0);
                            tessy.addVertexWithUV(low, 0, high, 0, 1);
                            tessy.addVertexWithUV(high, 0, high, 1, 1);
                            tessy.addVertexWithUV(high, 0, low, 1, 0);

                            tessy.addVertexWithUV(high, 0, low, 0, 0);
                            tessy.addVertexWithUV(high, 0, high, 0, 1);
                            tessy.addVertexWithUV(low, 0, high, 1, 1);
                            tessy.addVertexWithUV(low, 0, low, 1, 0);
                            tessy.draw();
                            break;
                        case 4:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.BOTTOM_X, y + 1.1, z + BlockCelestialCompassSB.Ranges.BOTTOM_Z);
                            tessy.startDrawingQuads();
                            tessy.addVertexWithUV(low, 0, low, 0, 0);
                            tessy.addVertexWithUV(low, 0, high, 0, 1);
                            tessy.addVertexWithUV(high, 0, high, 1, 1);
                            tessy.addVertexWithUV(high, 0, low, 1, 0);

                            tessy.addVertexWithUV(high, 0, low, 0, 0);
                            tessy.addVertexWithUV(high, 0, high, 0, 1);
                            tessy.addVertexWithUV(low, 0, high, 1, 1);
                            tessy.addVertexWithUV(low, 0, low, 1, 0);
                            tessy.draw();
                            break;
                        case 5:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.BOTTOM_LEFT_X, y + 1.1, z + BlockCelestialCompassSB.Ranges.BOTTOM_LEFT_Z);
                            tessy.startDrawingQuads();
                            tessy.addVertexWithUV(low, 0, low, 0, 0);
                            tessy.addVertexWithUV(low, 0, high, 0, 1);
                            tessy.addVertexWithUV(high, 0, high, 1, 1);
                            tessy.addVertexWithUV(high, 0, low, 1, 0);

                            tessy.addVertexWithUV(high, 0, low, 0, 0);
                            tessy.addVertexWithUV(high, 0, high, 0, 1);
                            tessy.addVertexWithUV(low, 0, high, 1, 1);
                            tessy.addVertexWithUV(low, 0, low, 1, 0);
                            tessy.draw();
                            break;
                        case 6:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.LEFT_X, y + 1.1, z + BlockCelestialCompassSB.Ranges.LEFT_Z);
                            tessy.startDrawingQuads();
                            tessy.addVertexWithUV(low, 0, low, 0, 0);
                            tessy.addVertexWithUV(low, 0, high, 0, 1);
                            tessy.addVertexWithUV(high, 0, high, 1, 1);
                            tessy.addVertexWithUV(high, 0, low, 1, 0);

                            tessy.addVertexWithUV(high, 0, low, 0, 0);
                            tessy.addVertexWithUV(high, 0, high, 0, 1);
                            tessy.addVertexWithUV(low, 0, high, 1, 1);
                            tessy.addVertexWithUV(low, 0, low, 1, 0);
                            tessy.draw();
                            break;
                        case 7:
                            GL11.glTranslated(x + BlockCelestialCompassSB.Ranges.TOP_LEFT_X, y + 1.1, z + BlockCelestialCompassSB.Ranges.TOP_LEFT_Z);
                            tessy.startDrawingQuads();
                            tessy.addVertexWithUV(low, 0, low, 0, 0);
                            tessy.addVertexWithUV(low, 0, high, 0, 1);
                            tessy.addVertexWithUV(high, 0, high, 1, 1);
                            tessy.addVertexWithUV(high, 0, low, 1, 0);

                            tessy.addVertexWithUV(high, 0, low, 0, 0);
                            tessy.addVertexWithUV(high, 0, high, 0, 1);
                            tessy.addVertexWithUV(low, 0, high, 1, 1);
                            tessy.addVertexWithUV(low, 0, low, 1, 0);
                            tessy.draw();
                            break;
                        case 8:
                            GL11.glTranslated(x + 0.5, y + 1.1, z + 0.5);
                            tessy.startDrawingQuads();
                            tessy.addVertexWithUV(low, 0, low, 0, 0);
                            tessy.addVertexWithUV(low, 0, high, 0, 1);
                            tessy.addVertexWithUV(high, 0, high, 1, 1);
                            tessy.addVertexWithUV(high, 0, low, 1, 0);

                            tessy.addVertexWithUV(high, 0, low, 0, 0);
                            tessy.addVertexWithUV(high, 0, high, 0, 1);
                            tessy.addVertexWithUV(low, 0, high, 1, 1);
                            tessy.addVertexWithUV(low, 0, low, 1, 0);
                            tessy.draw();
                            break;
                    }
                    GL11.glPopMatrix();
                }
            }
        }
    }
}
