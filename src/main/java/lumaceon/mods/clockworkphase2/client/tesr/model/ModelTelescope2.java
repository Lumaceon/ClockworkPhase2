package lumaceon.mods.clockworkphase2.client.tesr.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

/**
 * TelescopeFocus - Lumaceon
 * Created using Tabula 4.1.1
 */
public class ModelTelescope2 extends ModelBase {
    public ModelRenderer Top;
    public ModelRenderer UpperBarrel;
    public ModelRenderer MidSupport;
    public ModelRenderer BottomBarrel;

    public ModelTelescope2() {
        this.textureWidth = 96;
        this.textureHeight = 116;
        this.Top = new ModelRenderer(this, 0, 0);
        this.Top.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.Top.addBox(-12.0F, -2.0F, -12.0F, 24, 4, 24, 0.0F);
        this.BottomBarrel = new ModelRenderer(this, 0, 84);
        this.BottomBarrel.setRotationPoint(0.0F, 20.5F, 0.0F);
        this.BottomBarrel.addBox(-9.0F, -7.0F, -9.0F, 18, 14, 18, 0.0F);
        this.UpperBarrel = new ModelRenderer(this, 0, 28);
        this.UpperBarrel.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.UpperBarrel.addBox(-11.0F, -6.0F, -11.0F, 22, 12, 22, 0.0F);
        this.MidSupport = new ModelRenderer(this, 0, 62);
        this.MidSupport.setRotationPoint(0.0F, 16.5F, 0.0F);
        this.MidSupport.addBox(-10.0F, -1.0F, -10.0F, 20, 2, 20, 0.0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) { 
        GL11.glPushMatrix();
        GL11.glTranslatef(this.Top.offsetX, this.Top.offsetY, this.Top.offsetZ);
        GL11.glTranslatef(this.Top.rotationPointX * f5, this.Top.rotationPointY * f5, this.Top.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.Top.offsetX, -this.Top.offsetY, -this.Top.offsetZ);
        GL11.glTranslatef(-this.Top.rotationPointX * f5, -this.Top.rotationPointY * f5, -this.Top.rotationPointZ * f5);
        this.Top.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(this.BottomBarrel.offsetX, this.BottomBarrel.offsetY, this.BottomBarrel.offsetZ);
        GL11.glTranslatef(this.BottomBarrel.rotationPointX * f5, this.BottomBarrel.rotationPointY * f5, this.BottomBarrel.rotationPointZ * f5);
        GL11.glScaled(0.5D, 0.5D, 0.5D);
        GL11.glTranslatef(-this.BottomBarrel.offsetX, -this.BottomBarrel.offsetY, -this.BottomBarrel.offsetZ);
        GL11.glTranslatef(-this.BottomBarrel.rotationPointX * f5, -this.BottomBarrel.rotationPointY * f5, -this.BottomBarrel.rotationPointZ * f5);
        this.BottomBarrel.render(f5);
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
    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
