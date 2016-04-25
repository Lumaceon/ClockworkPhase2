package lumaceon.mods.clockworkphase2.client.render;

import lumaceon.mods.clockworkphase2.api.time.timezone.ITimezoneProvider;
import lumaceon.mods.clockworkphase2.api.time.timezone.TimezoneHandler;
import lumaceon.mods.clockworkphase2.client.particle.sequence.ParticleSequence;
import lumaceon.mods.clockworkphase2.client.particle.sequence.ParticleSequenceTimezone;
import lumaceon.mods.clockworkphase2.client.render.elements.overlay.OverlayRenderElement;
import lumaceon.mods.clockworkphase2.client.render.elements.world.WorldRenderElement;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RenderHandler
{
    //public static OverlayRenderElementTemporalInfluence overlayInfluence = new OverlayRenderElementTemporalInfluence();
    public static RenderItem renderItem;
    public static Minecraft mc;
    public static EntityItem item;
    public static double interpolatedPosX, interpolatedPosY, interpolatedPosZ;

    public RenderHandler()
    {
        mc = Minecraft.getMinecraft();
        renderItem = mc.getRenderItem();

        //registerWorldRenderElement(schematicRenderer);
    }

    public static ArrayList<OverlayRenderElement> overlayRenderList = new ArrayList<OverlayRenderElement>();
    @SubscribeEvent(priority = EventPriority.LOW)
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

    @SubscribeEvent(priority = EventPriority.HIGH) //TODO: fix the darn clouds. Git off my glyph...
    public void onRenderWorld(RenderWorldLastEvent event)
    {
        boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
        boolean light = GL11.glIsEnabled(GL11.GL_LIGHTING);
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
                    fx.renderParticle(Tessellator.getInstance().getWorldRenderer(), mc.getRenderViewEntity(), event.partialTicks, ActiveRenderInfo.getRotationX(), ActiveRenderInfo.getRotationXZ(), ActiveRenderInfo.getRotationZ(), ActiveRenderInfo.getRotationYZ(), ActiveRenderInfo.getRotationXY());
            }
            GL11.glPopMatrix();
        }

        if(mc.getRenderManager().renderEngine == null)
            return;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(mc.theWorld != null)
        {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            for(int[] area : TimezoneHandler.timezones)
            {
                ITimezoneProvider timezone = TimezoneHandler.getTimeZone(area[0], area[1], area[2], area[3]);
                if(timezone != null)
                {
                    TIMEZONE.renderGlyph(area, (double)area[0] - TileEntityRendererDispatcher.staticPlayerX, (double)area[1] - TileEntityRendererDispatcher.staticPlayerY, (double)area[2] - TileEntityRendererDispatcher.staticPlayerZ, timezone);
                    if(!TIMEZONE.timezoneSequences.containsKey(timezone) || TIMEZONE.timezoneSequences.get(timezone) == null)
                        TIMEZONE.timezoneSequences.put(timezone, ParticleSequence.spawnParticleSequence(new ParticleSequenceTimezone(timezone, area[0] + 0.5, area[1] + 0.5, area[2] + 0.5)));

                }
            }
            GL11.glDisable(GL11.GL_BLEND);

            Entity camera = mc.getRenderViewEntity();
            for(WorldRenderElement wre : worldRenderList)
                if(wre != null && wre.isSameWorld(mc.theWorld))
                    if(Math.sqrt(Math.pow(Math.abs(wre.xPos - camera.posX), 2) + Math.pow(Math.abs(wre.yPos - camera.posY), 2) + Math.pow(Math.abs(wre.zPos - camera.posZ), 2)) <= wre.maxRenderDistance())
                        wre.render(wre.xPos - TileEntityRendererDispatcher.staticPlayerX, wre.yPos - TileEntityRendererDispatcher.staticPlayerY, wre.zPos - TileEntityRendererDispatcher.staticPlayerZ);
        }
        if(blend)
            GL11.glEnable(GL11.GL_BLEND);
        else
            GL11.glDisable(GL11.GL_BLEND);
        if(light)
            GL11.glEnable(GL11.GL_LIGHTING);
        else
            GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
        GL11.glPopMatrix();
    }

    public static class TIMEZONE
    {
        public static Map<ITimezoneProvider, ParticleSequence> timezoneSequences = new HashMap<ITimezoneProvider, ParticleSequence>();

        public static void renderGlyph(int[] area, double x, double y, double z, ITimezoneProvider timezone)
        {
            if(mc.getRenderViewEntity() != null)
            {
                double dist = Math.sqrt(Math.pow(mc.getRenderViewEntity().posX - area[0], 2) + Math.pow(mc.getRenderViewEntity().posZ - area[2], 2));
                if(dist > timezone.getRange() * 2)
                    return;
            }
            mc.renderEngine.bindTexture(Textures.GLYPH.BASE_GLYPH);
            double low = -1 - timezone.getRange();
            double high = timezone.getRange();

            GL11.glPushMatrix();
            GL11.glTranslated(x + 1, y + 140 - area[1], z + 1);
            GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
            GL11.glRotatef((Minecraft.getSystemTime() % 115200.0F) / 320, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.5F, 0.0F, 0.5F);

            Tessellator tessy = Tessellator.getInstance();
            WorldRenderer renderer = tessy.getWorldRenderer();
            renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            renderer.pos(low, 0, low).tex(0, 0).endVertex();
            renderer.pos(low, 0, high).tex(0, 1).endVertex();
            renderer.pos(high, 0, high).tex(1, 1).endVertex();
            renderer.pos(high, 0, low).tex(1, 0).endVertex();
            tessy.draw();

            renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            renderer.pos(high, 0, low).tex(0, 0).endVertex();
            renderer.pos(high, 0, high).tex(0, 1).endVertex();
            renderer.pos(low, 0, high).tex(1, 1).endVertex();
            renderer.pos(low, 0, low).tex(1, 0).endVertex();
            tessy.draw();
            GL11.glPopMatrix();
        }
    }
}
