package lumaceon.mods.clockworkphase2.client.tesr.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

/**
 * TelescopeFocus - Lumaceon
 * Created using Tabula 4.1.1
 */
public class ModelTelescope1 extends ModelBase
{
    public ModelRenderer Top;
    public ModelRenderer UpperBarrel;
    public ModelRenderer MidSupport;
    public ModelRenderer LowerBarrel;
    public ModelRenderer Bottom;

    public ModelTelescope1()
    {
        this.textureWidth = 72;
        this.textureHeight = 96;
        this.Bottom = new ModelRenderer(this, 0, 80);
        this.Bottom.setRotationPoint(0.0F, 23.0F, 0.0F);
        this.Bottom.addBox(-6.0F, -2.0F, -6.0F, 12, 4, 12, 0.0F);
        this.UpperBarrel = new ModelRenderer(this, 0, 18);
        this.UpperBarrel.setRotationPoint(0.0F, 12.5F, 0.0F);
        this.UpperBarrel.addBox(-7.0F, -7.0F, -7.0F, 14, 14, 14, 0.0F);
        this.Top = new ModelRenderer(this, 0, 0);
        this.Top.setRotationPoint(0.0F, 8.5F, 0.0F);
        this.Top.addBox(-8.0F, -1.0F, -8.0F, 16, 2, 16, 0.0F);
        this.LowerBarrel = new ModelRenderer(this, 0, 60);
        this.LowerBarrel.setRotationPoint(0.0F, 19.5F, 0.0F);
        this.LowerBarrel.addBox(-5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F);
        this.MidSupport = new ModelRenderer(this, 0, 46);
        this.MidSupport.setRotationPoint(0.0F, 16.5F, 0.0F);
        this.MidSupport.addBox(-6.0F, -1.0F, -6.0F, 12, 2, 12, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Bottom.offsetX, this.Bottom.offsetY, this.Bottom.offsetZ);
        GL11.glTranslatef(this.Bottom.rotationPointX * f5, this.Bottom.rotationPointY * f5, this.Bottom.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.Bottom.offsetX, -this.Bottom.offsetY, -this.Bottom.offsetZ);
        GL11.glTranslatef(-this.Bottom.rotationPointX * f5, -this.Bottom.rotationPointY * f5, -this.Bottom.rotationPointZ * f5);
        this.Bottom.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.UpperBarrel.offsetX, this.UpperBarrel.offsetY, this.UpperBarrel.offsetZ);
        GL11.glTranslatef(this.UpperBarrel.rotationPointX * f5, this.UpperBarrel.rotationPointY * f5, this.UpperBarrel.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.UpperBarrel.offsetX, -this.UpperBarrel.offsetY, -this.UpperBarrel.offsetZ);
        GL11.glTranslatef(-this.UpperBarrel.rotationPointX * f5, -this.UpperBarrel.rotationPointY * f5, -this.UpperBarrel.rotationPointZ * f5);
        this.UpperBarrel.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Top.offsetX, this.Top.offsetY, this.Top.offsetZ);
        GL11.glTranslatef(this.Top.rotationPointX * f5, this.Top.rotationPointY * f5, this.Top.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.Top.offsetX, -this.Top.offsetY, -this.Top.offsetZ);
        GL11.glTranslatef(-this.Top.rotationPointX * f5, -this.Top.rotationPointY * f5, -this.Top.rotationPointZ * f5);
        this.Top.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.LowerBarrel.offsetX, this.LowerBarrel.offsetY, this.LowerBarrel.offsetZ);
        GL11.glTranslatef(this.LowerBarrel.rotationPointX * f5, this.LowerBarrel.rotationPointY * f5, this.LowerBarrel.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.LowerBarrel.offsetX, -this.LowerBarrel.offsetY, -this.LowerBarrel.offsetZ);
        GL11.glTranslatef(-this.LowerBarrel.rotationPointX * f5, -this.LowerBarrel.rotationPointY * f5, -this.LowerBarrel.rotationPointZ * f5);
        this.LowerBarrel.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.MidSupport.offsetX, this.MidSupport.offsetY, this.MidSupport.offsetZ);
        GL11.glTranslatef(this.MidSupport.rotationPointX * f5, this.MidSupport.rotationPointY * f5, this.MidSupport.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.MidSupport.offsetX, -this.MidSupport.offsetY, -this.MidSupport.offsetZ);
        GL11.glTranslatef(-this.MidSupport.rotationPointX * f5, -this.MidSupport.rotationPointY * f5, -this.MidSupport.rotationPointZ * f5);
        this.MidSupport.render(f5);
        GL11.glPopMatrix();
    }

    /**
     * This is a helper function from Tabula to set the rotation of model parts
     */
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
