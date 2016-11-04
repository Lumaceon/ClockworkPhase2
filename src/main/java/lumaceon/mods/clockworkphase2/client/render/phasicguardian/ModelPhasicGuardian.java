package lumaceon.mods.clockworkphase2.client.render.phasicguardian;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

import java.util.Random;

public class ModelPhasicGuardian extends ModelBiped
{
    Random random = new Random();

    public ModelRenderer ghostHead;
    /** The Biped's Headwear. Used for the outer layer of player skins. */
    public ModelRenderer ghostHeadwear;
    public ModelRenderer ghostBody;
    public ModelRenderer ghostRightArm;
    public ModelRenderer ghostLeftArm;
    public ModelRenderer ghostRightLeg;
    public ModelRenderer ghostLeftLeg;

    public ModelPhasicGuardian() {
        float modelSize = 1.0F;
        float iDontEvenKnow = 0.0F;
        this.leftArmPose = ArmPose.EMPTY;
        this.rightArmPose = ArmPose.EMPTY;
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.bipedHead = new ModelRenderer(this, 0, 0);
        this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + iDontEvenKnow, 0.0F);
        this.bipedHeadwear = new ModelRenderer(this, 32, 0);
        this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize + 0.5F);
        this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + iDontEvenKnow, 0.0F);
        this.bipedBody = new ModelRenderer(this, 16, 16);
        this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + iDontEvenKnow, 0.0F);
        this.bipedRightArm = new ModelRenderer(this, 40, 16);
        this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + iDontEvenKnow, 0.0F);
        this.bipedLeftArm = new ModelRenderer(this, 40, 16);
        this.bipedLeftArm.mirror = true;
        this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + iDontEvenKnow, 0.0F);
        this.bipedRightLeg = new ModelRenderer(this, 0, 16);
        this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + iDontEvenKnow, 0.0F);
        this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
        this.bipedLeftLeg.mirror = true;
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + iDontEvenKnow, 0.0F);


        this.ghostHead = new ModelRenderer(this, 0, 0);
        this.ghostHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
        this.ghostHead.setRotationPoint(0.0F, 0.0F + iDontEvenKnow, 0.0F);
        this.ghostHeadwear = new ModelRenderer(this, 32, 0);
        this.ghostHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize + 0.5F);
        this.ghostHeadwear.setRotationPoint(0.0F, 0.0F + iDontEvenKnow, 0.0F);
        this.ghostBody = new ModelRenderer(this, 16, 16);
        this.ghostBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
        this.ghostBody.setRotationPoint(0.0F, 0.0F + iDontEvenKnow, 0.0F);
        this.ghostRightArm = new ModelRenderer(this, 40, 16);
        this.ghostRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
        this.ghostRightArm.setRotationPoint(-5.0F, 2.0F + iDontEvenKnow, 0.0F);
        this.ghostLeftArm = new ModelRenderer(this, 40, 16);
        this.ghostLeftArm.mirror = true;
        this.ghostLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
        this.ghostLeftArm.setRotationPoint(5.0F, 2.0F + iDontEvenKnow, 0.0F);
        this.ghostRightLeg = new ModelRenderer(this, 0, 16);
        this.ghostRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.ghostRightLeg.setRotationPoint(-1.9F, 12.0F + iDontEvenKnow, 0.0F);
        this.ghostLeftLeg = new ModelRenderer(this, 0, 16);
        this.ghostLeftLeg.mirror = true;
        this.ghostLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.ghostLeftLeg.setRotationPoint(1.9F, 12.0F + iDontEvenKnow, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
        GlStateManager.pushMatrix();
        if(entityIn.isSneaking())
            GlStateManager.translate(0.0F, 0.2F, 0.0F);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        this.bipedHead.render(scale);
        this.bipedBody.render(scale);
        this.bipedRightArm.render(scale);
        this.bipedLeftArm.render(scale);
        this.bipedRightLeg.render(scale);
        this.bipedLeftLeg.render(scale);
        this.bipedHeadwear.render(scale);

        GlStateManager.translate((random.nextFloat()-0.5F) *2.0F, (random.nextFloat()-0.5F) *2.0F, (random.nextFloat()-0.5F) *2.0F);
        //GlStateManager.translate(0, -2, 2);
        GlStateManager.color(0.0F, 0.0F, 1.0F, 1.0F);
        this.ghostHead.render(scale);
        this.ghostBody.render(scale);
        this.ghostRightArm.render(scale);
        this.ghostLeftArm.render(scale);
        this.ghostRightLeg.render(scale);
        this.ghostLeftLeg.render(scale);
        this.ghostHeadwear.render(scale);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
