package lumaceon.mods.clockworkphase2.client.tesr.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

/**
 * TelescopeFocus - Lumaceon
 * Created using Tabula 4.1.1
 */
public class ModelTelescope3 extends ModelBase {
    public ModelRenderer Top;
    public ModelRenderer UpperBarrel;
    public ModelRenderer MidSupport;
    public ModelRenderer BottomBarrel;

    public ModelTelescope3() {
        this.textureWidth = 128;
        this.textureHeight = 148;
        this.Top = new ModelRenderer(this, 0, 0);
        this.Top.setRotationPoint(0.0F, 9.0F, 0.0F);
        this.Top.addBox(-16.0F, -2.0F, -16.0F, 32, 4, 32, 0.0F);
        this.BottomBarrel = new ModelRenderer(this, 0, 110);
        this.BottomBarrel.setRotationPoint(0.0F, 21.0F, 0.0F);
        this.BottomBarrel.addBox(-13.0F, -6.0F, -13.0F, 26, 12, 26, 0.0F);
        this.UpperBarrel = new ModelRenderer(this, 0, 36);
        this.UpperBarrel.setRotationPoint(0.0F, 13.0F, 0.0F);
        this.UpperBarrel.addBox(-15.0F, -6.0F, -15.0F, 30, 12, 30, 0.0F);
        this.MidSupport = new ModelRenderer(this, 0, 78);
        this.MidSupport.setRotationPoint(0.0F, 17.0F, 0.0F);
        this.MidSupport.addBox(-14.0F, -2.0F, -14.0F, 28, 4, 28, 0.0F);
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
