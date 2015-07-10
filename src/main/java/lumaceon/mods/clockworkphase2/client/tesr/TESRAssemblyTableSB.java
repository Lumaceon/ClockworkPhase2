package lumaceon.mods.clockworkphase2.client.tesr;

import lumaceon.mods.clockworkphase2.api.assembly.AssemblySlot;
import lumaceon.mods.clockworkphase2.api.item.IAssemblable;
import lumaceon.mods.clockworkphase2.item.ItemBugSwatter;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.tile.TileAssemblyTableSB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;

public class TESRAssemblyTableSB extends TileEntitySpecialRenderer
{
    private RenderItem itemRenderer = new RenderItem();
    private EntityItem itemEntity;
    private ResourceLocation top = new ResourceLocation(Reference.MOD_ID, "textures/blocks/assembly_table_sb/top.png");
    private ResourceLocation side = new ResourceLocation(Reference.MOD_ID, "textures/blocks/assembly_table_sb/side.png");
    private ResourceLocation back = new ResourceLocation(Reference.MOD_ID, "textures/blocks/assembly_table_sb/back.png");
    private ResourceLocation front = new ResourceLocation(Reference.MOD_ID, "textures/blocks/assembly_table_sb/front.png");

    public TESRAssemblyTableSB() {
        itemRenderer.setRenderManager(RenderManager.instance);
        itemRenderer.renderWithColor = true;
    }

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float p_147500_8_)
    {
        if(te != null && te instanceof TileAssemblyTableSB)
        {
            TileAssemblyTableSB table = (TileAssemblyTableSB) te;
            float pixel = 1.0F/16.0F;
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            ForgeDirection meta = ForgeDirection.getOrientation(te.blockMetadata);

            switch(meta)
            {
                case NORTH:
                    GL11.glRotatef(90, 0, 1, 0);
                    GL11.glTranslatef(-1F, 0, 0);
                    break;
                case WEST:
                    GL11.glRotatef(180, 0, 1, 0);
                    GL11.glTranslatef(-1F, 0, -1F);
                    break;
                case SOUTH:
                    GL11.glRotatef(270, 0, 1, 0);
                    GL11.glTranslatef(0, 0, -1);
                    break;
            }
            Tessellator t = Tessellator.instance;

            this.bindTexture(top);
            t.startDrawingQuads();
            t.addVertexWithUV(0, 1, 1, 0, 0);
            t.addVertexWithUV(pixel * 2, 1, 1, 0, 1);
            t.addVertexWithUV(pixel * 2, 1, 0, 1, 1);
            t.addVertexWithUV(0, 1, 0, 1, 0);
            t.draw();
            this.bindTexture(side);
            t.startDrawingQuads();
            t.addVertexWithUV(0, 0, 0, 1, 0);
            t.addVertexWithUV(0, 1, 0, 0, 0);
            t.addVertexWithUV(pixel * 2, 1, 0, 0, 1);
            t.addVertexWithUV(pixel * 2, 0, 0, 1, 1);
            t.draw();
            this.bindTexture(side);
            t.startDrawingQuads();
            t.addVertexWithUV(0, 1, 1, 1, 0);
            t.addVertexWithUV(0, 0, 1, 0, 0);
            t.addVertexWithUV(pixel * 2, 0, 1, 0, 1);
            t.addVertexWithUV(pixel * 2, 1, 1, 1, 1);
            t.draw();
            this.bindTexture(front);
            t.startDrawingQuads();
            t.addVertexWithUV(pixel * 2, 0, 0, 1, 1);
            t.addVertexWithUV(pixel * 2, 1, 0, 1, 0);
            t.addVertexWithUV(pixel * 2, 1, 1, 0, 0);
            t.addVertexWithUV(pixel * 2, 0, 1, 0, 1);
            t.draw();
            this.bindTexture(back);
            t.startDrawingQuads();
            t.addVertexWithUV(0, 1, 0, 1, 0);
            t.addVertexWithUV(0, 0, 0, 1, 1);
            t.addVertexWithUV(0, 0, 1, 0, 1);
            t.addVertexWithUV(0, 1, 1, 0, 0);
            t.draw();
            ItemStack workItem = table.getWorkItem();
            if(workItem != null)
            {
                if(itemEntity == null)
                    itemEntity = new EntityItem(Minecraft.getMinecraft().theWorld);
                if(itemEntity.getEntityItem() == null || !itemEntity.getEntityItem().equals(workItem))
                    itemEntity.setEntityItemStack(workItem);
                itemEntity.hoverStart = 0;
                GL11.glTranslatef(pixel * 2.5F, pixel * 1.75F, 0.5F);
                GL11.glRotatef(90, 0, 1, 0);
                GL11.glScalef(1.9F, 1.9F, 1.9F);
                RenderItem.renderInFrame = true;
                itemRenderer.doRender(itemEntity, 0, 0, 0, 0, 0);
                RenderItem.renderInFrame = false;
                AssemblySlot[] slots = table.getAssemblySlots();
                if(workItem.getItem() instanceof IAssemblable && slots != null)
                {
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glTranslated(x, y, z);
                    switch(meta)
                    {
                        case EAST:
                            GL11.glTranslated(pixel * 3.0, 0, 0);
                            break;
                        case NORTH:
                            GL11.glTranslated(0, 0, 1 - pixel * 3.0);
                            GL11.glRotatef(90, 0, 1, 0);
                            break;
                        case WEST:
                            GL11.glTranslated(1 - (pixel * 3.0), 0, 1);
                            GL11.glRotatef(180, 0, 1, 0);
                            break;
                        case SOUTH:
                            GL11.glTranslated(1, 0, pixel * 3.0);
                            GL11.glRotatef(270, 0, 1, 0);
                            break;
                    }
                    MovingObjectPosition MOB = Minecraft.getMinecraft().objectMouseOver;
                    double xCoord = -10;
                    double yCoord = -10;
                    double zCoord = -10;
                    if(MOB != null && MOB.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK) && te.getWorldObj().getBlock(MOB.blockX, MOB.blockY, MOB.blockZ).equals(te.blockType) && MOB.hitVec != null)
                    {
                        xCoord = MOB.hitVec.xCoord - te.xCoord;
                        yCoord = MOB.hitVec.yCoord - te.yCoord;
                        zCoord = MOB.hitVec.zCoord - te.zCoord;
                    }
                    ItemStack slotItem;
                    ResourceLocation defaultIcon;
                    for(AssemblySlot slot : slots)
                    {
                        if(slot != null && slot.isEnabled)
                        {
                            boolean isMouseOver = isIntersecting(xCoord, yCoord, zCoord, meta, slot);
                            if(isMouseOver)
                                slot.ticksMousedOver = Math.min(30, slot.ticksMousedOver + 1);
                            else
                                slot.ticksMousedOver = Math.max(0, slot.ticksMousedOver - 1);
                            slotItem = slot.getItemStack();
                            defaultIcon = slot.getRenderIcon();
                            if(slotItem != null) //Item is in slot, render that.
                            {
                                float lowX = ((1 - slot.centerX) - (slot.sizeX * 0.5F * pixel) * (1F + slot.ticksMousedOver / 30F));
                                float highX = ((1 - slot.centerX) + (slot.sizeX * 0.5F * pixel) * (1F + slot.ticksMousedOver / 30F));
                                float lowY = ((1 - slot.centerY) - (slot.sizeY * 0.5F * pixel) * (1F + slot.ticksMousedOver / 30F));
                                float highY = ((1 - slot.centerY) + (slot.sizeY * 0.5F * pixel) * (1F + slot.ticksMousedOver / 30F));
                                IIcon icon = slotItem.getItem().getIconIndex(slotItem);
                                if(icon != null)
                                {
                                    ResourceLocation itemLocation = Minecraft.getMinecraft().renderEngine.getResourceLocation(slotItem.getItemSpriteNumber());
                                    this.bindTexture(itemLocation);
                                    t.startDrawingQuads();
                                    t.addVertexWithUV(0, lowY, lowX, icon.getMaxU(), icon.getMaxV());
                                    t.addVertexWithUV(0, highY, lowX, icon.getMaxU(), icon.getMinV());
                                    t.addVertexWithUV(0, highY, highX, icon.getMinU(), icon.getMinV());
                                    t.addVertexWithUV(0, lowY, highX, icon.getMinU(), icon.getMaxV());
                                    t.draw();
                                }
                            }
                            else if(defaultIcon != null) //No item is in slot, render default icon.
                            {
                                float lowX = ((1 - slot.centerX) - (slot.sizeX * 0.5F * pixel) * (1F + slot.ticksMousedOver / 30F));
                                float highX = ((1 - slot.centerX) + (slot.sizeX * 0.5F * pixel) * (1F + slot.ticksMousedOver / 30F));
                                float lowY = ((1 - slot.centerY) - (slot.sizeY * 0.5F * pixel) * (1F + slot.ticksMousedOver / 30F));
                                float highY = ((1 - slot.centerY) + (slot.sizeY * 0.5F * pixel) * (1F + slot.ticksMousedOver / 30F));
                                this.bindTexture(defaultIcon);
                                t.startDrawingQuads();
                                t.setColorRGBA_F(0.5F, 0.5F, 0.5F, 1.0F);
                                t.addVertexWithUV(0, lowY, lowX, 1, 1);
                                t.addVertexWithUV(0, highY, lowX, 1, 0);
                                t.addVertexWithUV(0, highY, highX, 0, 0);
                                t.addVertexWithUV(0, lowY, highX, 0, 1);
                                t.draw();

                                if(slot.ticksMousedOver > 0)
                                {
                                    EntityPlayer player = Minecraft.getMinecraft().thePlayer;
                                    if(player != null)
                                    {
                                        ItemStack itemInHand = player.inventory.getCurrentItem();
                                        ResourceLocation location = slot.getMouseOverIcon(player, workItem, itemInHand);
                                        if(location != null)
                                        {
                                            lowX = ((1 - slot.centerX) - (slot.sizeX * 0.5F * pixel) * (Math.min(slot.ticksMousedOver, 20) / 20F));
                                            highX = ((1 - slot.centerX) + (slot.sizeX * 0.5F * pixel) * (Math.min(slot.ticksMousedOver, 20) / 20F));
                                            lowY = ((1 - slot.centerY) - (slot.sizeY * 0.5F * pixel) * (Math.min(slot.ticksMousedOver, 20) / 20F));
                                            highY = ((1 - slot.centerY) + (slot.sizeY * 0.5F * pixel) * (Math.min(slot.ticksMousedOver, 20) / 20F));
                                            float upwardTranslation = ((slot.ticksMousedOver * slot.sizeY * pixel) + 1) / 30;
                                            this.bindTexture(location);
                                            t.startDrawingQuads();
                                            t.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
                                            t.addVertexWithUV(0.01, lowY + upwardTranslation, lowX, 1, 1);
                                            t.addVertexWithUV(0.01, highY + upwardTranslation, lowX, 1, 0);
                                            t.addVertexWithUV(0.01, highY + upwardTranslation, highX, 0, 0);
                                            t.addVertexWithUV(0.01, lowY + upwardTranslation, highX, 0, 1);
                                            t.draw();
                                        }
                                        /*else
                                        {
                                            lowX = ((1 - slot.centerX) - (slot.sizeX * 0.5F * pixel) * (Math.min(slot.ticksMousedOver, 20) / 20F));
                                            highX = ((1 - slot.centerX) + (slot.sizeX * 0.5F * pixel) * (Math.min(slot.ticksMousedOver, 20) / 20F));
                                            lowY = ((1 - slot.centerY) - (slot.sizeY * 0.5F * pixel) * (Math.min(slot.ticksMousedOver, 20) / 20F));
                                            highY = ((1 - slot.centerY) + (slot.sizeY * 0.5F * pixel) * (Math.min(slot.ticksMousedOver, 20) / 20F));
                                            float upwardTranslation = ((slot.ticksMousedOver * slot.sizeY * pixel) + 1) / 30;
                                            this.bindTexture(invalid);
                                            t.startDrawingQuads();
                                            t.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
                                            t.addVertexWithUV(0.01, lowY + upwardTranslation, lowX, 1, 1);
                                            t.addVertexWithUV(0.01, highY + upwardTranslation, lowX, 1, 0);
                                            t.addVertexWithUV(0.01, highY + upwardTranslation, highX, 0, 0);
                                            t.addVertexWithUV(0.01, lowY + upwardTranslation, highX, 0, 1);
                                            t.draw();
                                        }*/
                                    }
                                }
                            }
                        }
                    }
                }
            }
            GL11.glPopMatrix();
        }
    }

    private boolean isIntersecting(double x, double y, double z, ForgeDirection meta, AssemblySlot slot)
    {
        if(x < 0 || z < 0)
            return false;
        switch(meta)
        {
            case EAST:
                if(z > (1 - slot.centerX) + slot.sizeX * 0.5 * 1F/16F || z < (1 - slot.centerX) - slot.sizeX * 0.5 * 1F/16F)
                    return false;
                break;
            case WEST:
                z = Math.abs(1 - z);
                if(z > (1 - slot.centerX) + slot.sizeX * 0.5 * 1F/16F || z < (1 - slot.centerX) - slot.sizeX * 0.5 * 1F/16F)
                    return false;
                break;
            case NORTH:
                if(x > (1 - slot.centerX) + slot.sizeX * 0.5 * 1F/16F || x < (1 - slot.centerX) - slot.sizeX * 0.5 * 1F/16F)
                    return false;
                break;
            case SOUTH:
                x = Math.abs(1 - x);
                if(x > (1 - slot.centerX) + slot.sizeX * 0.5 * 1F/16F || x < (1 - slot.centerX) - slot.sizeX * 0.5 * 1F/16F)
                    return false;
                break;
        }
        if(y > (1 - slot.centerY) + slot.sizeY * 0.5 * 1F/16F || y < (1 - slot.centerY) - slot.sizeY * 0.5 * 1F/16F)
            return false;
        return true;
    }
}