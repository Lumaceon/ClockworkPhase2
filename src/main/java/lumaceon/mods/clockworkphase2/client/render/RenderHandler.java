package lumaceon.mods.clockworkphase2.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class RenderHandler
{
    protected static final ResourceLocation WIDGETS_TEX_PATH = new ResourceLocation("textures/gui/widgets.png");

    private static RenderItem itemRenderer;
    public static Minecraft mc;
    public static EntityItem item;
    public static double interpolatedPosX, interpolatedPosY, interpolatedPosZ;
    FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

    public RenderHandler()
    {
        mc = Minecraft.getMinecraft();
        itemRenderer = mc.getRenderItem();
    }

    /*@SubscribeEvent
    public void onPreRenderOverlay(RenderGameOverlayEvent.Pre event)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if(player == null)
            return;
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.EXPERIENCE) && !player.isCreative())
        {
            event.setCanceled(true);
            EnumAchievementTier tier = EnumAchievementTier.START;
            if(player.hasCapability(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY, EnumFacing.DOWN))
            {
                IAchievementScoreHandler achievementScoreHandler = player.getCapability(CapabilityAchievementScore.ACHIEVEMENT_HANDLER_CAPABILITY, EnumFacing.DOWN);
                tier = achievementScoreHandler.getAchievementTier();
            }
            int xLoc = event.getResolution().getScaledWidth() / 2 - 91;
            renderExpBar(event.getResolution(), xLoc, tier);
        }
    }

    int tweentimer = 0;
    @SubscribeEvent
    public void onPostRenderOverlay(RenderGameOverlayEvent.Post event)
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if(player == null)
            return;
        if(event.getType().equals(RenderGameOverlayEvent.ElementType.HOTBAR))
        {
            ItemStack item = player.inventory.getCurrentItem();
            if(item != null && item.getItem() instanceof ItemTemporalMultitool && NBTHelper.hasTag(item, NBTTags.COMPONENT_INVENTORY))
            {
                if(tweentimer > 0 || Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
                {
                    boolean tweenIncrease = false;
                    if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL))
                        tweenIncrease = true;

                    ItemStack[] items = NBTHelper.INVENTORY.get(item, NBTTags.COMPONENT_INVENTORY);
                    if(items != null)
                    {
                        if(tweenIncrease)
                            tweentimer++;
                        else
                            tweentimer--;
                        if(tweentimer > 10)
                            tweentimer = 10;
                        GlStateManager.enableBlend();
                        GlStateManager.enableRescaleNormal();
                        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        RenderHelper.enableGUIStandardItemLighting();
                        GlStateManager.color(1.0F, 1.0F, 1.0F, (float) tweentimer * 0.1F);
                        renderHotbar(event.getResolution(), event.getPartialTicks(), items, NBTHelper.BYTE.get(item, "MT_index"));
                    }
                }
            }
        }
    }

    protected void renderHotbar(ScaledResolution sr, float partialTicks, ItemStack[] multitoolItems, byte index)
    {
        int pixelsUp = 24;
        if(mc.getRenderViewEntity() instanceof EntityPlayer)
        {
            mc.getTextureManager().bindTexture(WIDGETS_TEX_PATH);
            EntityPlayer entityplayer = (EntityPlayer) mc.getRenderViewEntity();
            EnumHandSide enumhandside = entityplayer.getPrimaryHand().opposite();
            int i = sr.getScaledWidth() / 2;
            this.drawTexturedModalRectVanilla(i - 91, sr.getScaledHeight() - 22 - pixelsUp, 0, 0, 182, 22, 0.0F);
            this.drawTexturedModalRectVanilla(i - 91 - 1 + index * 20, sr.getScaledHeight() - 22 - 1 - pixelsUp, 0, 22, 24, 22, 0.0F);

            for(int l = 0; l < 9; ++l)
            {
                int i1 = i - 90 + l * 20 + 2;
                int j1 = sr.getScaledHeight() - 16 - 3;
                this.renderHotbarItem(i1, j1 - pixelsUp, partialTicks, entityplayer, multitoolItems[l]);
            }

            if(mc.gameSettings.attackIndicator == 2)
            {
                float f1 = mc.thePlayer.getCooledAttackStrength(0.0F);

                if(f1 < 1.0F)
                {
                    int i2 = sr.getScaledHeight() - 20;
                    int j2 = i + 91 + 6;

                    if(enumhandside == EnumHandSide.RIGHT)
                    {
                        j2 = i - 91 - 22;
                    }

                    mc.getTextureManager().bindTexture(Gui.ICONS);
                    int k1 = (int)(f1 * 19.0F);
                    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                    this.drawTexturedModalRectVanilla(j2, i2 - pixelsUp, 0, 94, 18, 18, 0.0F);
                    this.drawTexturedModalRectVanilla(j2, i2 + 18 - k1 - pixelsUp, 18, 112 - k1, 18, k1, 0.0F);
                }
            }

            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
        }
    }

    protected void renderHotbarItem(int p_184044_1_, int p_184044_2_, float p_184044_3_, EntityPlayer player, @Nullable ItemStack stack)
    {
        if(stack != null)
        {
            float f = (float)stack.animationsToGo - p_184044_3_;

            if(f > 0.0F)
            {
                GlStateManager.pushMatrix();
                float f1 = 1.0F + f / 5.0F;
                GlStateManager.translate((float)(p_184044_1_ + 8), (float)(p_184044_2_ + 12), 0.0F);
                GlStateManager.scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
                GlStateManager.translate((float)(-(p_184044_1_ + 8)), (float)(-(p_184044_2_ + 12)), 0.0F);
            }

            itemRenderer.renderItemAndEffectIntoGUI(player, stack, p_184044_1_, p_184044_2_);

            if(f > 0.0F)
                GlStateManager.popMatrix();

            itemRenderer.renderItemOverlays(mc.fontRendererObj, stack, p_184044_1_, p_184044_2_);
        }
    }

    private void renderExpBar(ScaledResolution scaledRes, int x, EnumAchievementTier tier)
    {
        mc.getTextureManager().bindTexture(Gui.ICONS);
        int i = mc.thePlayer.xpBarCap();

        if(i > 0)
        {
            int k = (int)(mc.thePlayer.experience * 183.0F);
            int l = scaledRes.getScaledHeight() - 32 + 3;
            this.drawTexturedModalRectVanilla(x, l, 0, 64, 182, 5, 0.0F);

            if(k > 0)
                this.drawTexturedModalRectVanilla(x, l, 0, 69, k, 5, 0.0F);
        }

        if(mc.thePlayer.experienceLevel > 0)
        {
            if(fontRenderer == null)
                fontRenderer = Minecraft.getMinecraft().fontRendererObj;

            String s = "" + mc.thePlayer.experienceLevel;
            if(tier != EnumAchievementTier.START) //Don't display 'start', only the letter tiers.
                s = "" + mc.thePlayer.experienceLevel + tier.displayName;
            int i1 = (scaledRes.getScaledWidth() - fontRenderer.getStringWidth(s)) / 2;
            int j1 = scaledRes.getScaledHeight() - 31 - 4;
            fontRenderer.drawString(s, i1 + 1, j1, 0);
            fontRenderer.drawString(s, i1 - 1, j1, 0);
            fontRenderer.drawString(s, i1, j1 + 1, 0);
            fontRenderer.drawString(s, i1, j1 - 1, 0);
            fontRenderer.drawString(s, i1, j1, 8453920);
        }
    }

    private void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        float zLevel = 0;
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x + 0), (double)(y + height), zLevel).tex((double)((float)(textureX + 0) * (1F/182F)), (double)((float)(textureY + height)*0.2)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + height), zLevel).tex((double)((float)(textureX + width) * (1F/182F)), (double)((float)(textureY + height)*0.2)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + 0), zLevel).tex((double)((float)(textureX + width) * (1F/182F)), (double)((float)(textureY + 0)*0.2)).endVertex();
        vertexbuffer.pos((double)(x + 0), (double)(y + 0), zLevel).tex((double)((float)(textureX + 0) * (1F/182F)), (double)((float)(textureY + 0)*0.2)).endVertex();
        tessellator.draw();
    }

    private void drawTexturedModalRectVanilla(int x, int y, int textureX, int textureY, int width, int height, float zLevel)
    {
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x + 0), (double)(y + height), zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + height), zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + 0), zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + 0), (double)(y + 0), zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();
    }*/

    @SubscribeEvent(priority = EventPriority.HIGH) //TODO: fix the darn clouds. Git off my glyph...
    public void onRenderWorld(RenderWorldLastEvent event)
    {
        boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
        boolean light = GL11.glIsEnabled(GL11.GL_LIGHTING);
        interpolatedPosX = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * (double)event.getPartialTicks();
        interpolatedPosY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * (double)event.getPartialTicks();
        interpolatedPosZ = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * (double)event.getPartialTicks();

        if(mc.getRenderManager().renderEngine == null)
            return;

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if(mc.theWorld != null)
        {
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            /*for(int[] area : TimezoneHandler.timezones)
            {
                ITimezoneProvider timezone = TimezoneHandler.getTimeZoneFromWorldPosition(area[0], area[1], area[2], area[3]);
                if(timezone != null)
                {
                    TIMEZONE.renderGlyph(area, (double)area[0] - TileEntityRendererDispatcher.staticPlayerX, (double)area[1] - TileEntityRendererDispatcher.staticPlayerY, (double)area[2] - TileEntityRendererDispatcher.staticPlayerZ, timezone);
                    if(!TIMEZONE.timezoneSequences.containsKey(timezone) || TIMEZONE.timezoneSequences.get(timezone) == null)
                        TIMEZONE.timezoneSequences.put(timezone, ParticleSequence.spawnParticleSequence(new ParticleSequenceTimezone(timezone, area[0] + 0.5, area[1] + 0.5, area[2] + 0.5)));

                }
            }*/
            /*GL11.glDisable(GL11.GL_BLEND);

            Entity camera = mc.getRenderViewEntity();
            for(WorldRenderElement wre : worldRenderList)
                if(wre != null && wre.isSameWorld(mc.theWorld))
                    if(Math.sqrt(Math.pow(Math.abs(wre.xPos - camera.posX), 2) + Math.pow(Math.abs(wre.yPos - camera.posY), 2) + Math.pow(Math.abs(wre.zPos - camera.posZ), 2)) <= wre.maxRenderDistance())
                        wre.render(wre.xPos - TileEntityRendererDispatcher.staticPlayerX, wre.yPos - TileEntityRendererDispatcher.staticPlayerY, wre.zPos - TileEntityRendererDispatcher.staticPlayerZ);*/
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
        /*public static Map<ITimezoneProvider, ParticleSequence> timezoneSequences = new HashMap<ITimezoneProvider, ParticleSequence>();

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
            GL11.glTranslated(x + 1, y + 254.99 - area[1], z + 1);
            GL11.glTranslatef(-0.5F, 0.0F, -0.5F);
            GL11.glRotatef((Minecraft.getSystemTime() % 115200.0F) / 320, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(0.5F, 0.0F, 0.5F);

            Tessellator tessy = Tessellator.getInstance();
            VertexBuffer renderer = tessy.getBuffer();
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
        }*/
    }
}
