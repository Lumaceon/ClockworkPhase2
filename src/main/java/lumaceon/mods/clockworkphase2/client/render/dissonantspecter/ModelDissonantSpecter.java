package lumaceon.mods.clockworkphase2.client.render.dissonantspecter;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class ModelDissonantSpecter extends ModelBiped
{
    private final boolean smallArms;
    private Random random = new Random();

    public ModelRenderer bipedLeftArmwear;
    public ModelRenderer bipedRightArmwear;
    public ModelRenderer bipedLeftLegwear;
    public ModelRenderer bipedRightLegwear;
    public ModelRenderer bipedBodyWear;

    public ModelRenderer ghostHead;
    /** The Biped's Headwear. Used for the outer layer of player skins. */
    public ModelRenderer ghostHeadwear;
    public ModelRenderer ghostBody;
    public ModelRenderer ghostRightArm;
    public ModelRenderer ghostLeftArm;
    public ModelRenderer ghostRightLeg;
    public ModelRenderer ghostLeftLeg;

    public ModelDissonantSpecter(float modelSize, boolean slimModel)
    {
        super(modelSize, 0.0F, 64, 64);
        this.smallArms = slimModel;

        //Super Stuff
        this.leftArmPose = ModelBiped.ArmPose.EMPTY;
        this.rightArmPose = ModelBiped.ArmPose.EMPTY;
        this.textureWidth = 64;
        this.textureHeight = 64;
        float p_i1149_2_ = 0.0F;
        this.ghostHead = new ModelRenderer(this, 0, 0);
        this.ghostHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
        this.ghostHead.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.ghostHeadwear = new ModelRenderer(this, 32, 0);
        this.ghostHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize + 0.5F);
        this.ghostHeadwear.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.ghostBody = new ModelRenderer(this, 16, 16);
        this.ghostBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
        this.ghostBody.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
        this.ghostRightArm = new ModelRenderer(this, 40, 16);
        this.ghostRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
        this.ghostRightArm.setRotationPoint(-5.0F, 2.0F + p_i1149_2_, 0.0F);
        this.ghostLeftArm = new ModelRenderer(this, 40, 16);
        this.ghostLeftArm.mirror = true;
        this.ghostLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
        this.ghostLeftArm.setRotationPoint(5.0F, 2.0F + p_i1149_2_, 0.0F);
        this.ghostRightLeg = new ModelRenderer(this, 0, 16);
        this.ghostRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.ghostRightLeg.setRotationPoint(-1.9F, 12.0F + p_i1149_2_, 0.0F);
        this.ghostLeftLeg = new ModelRenderer(this, 0, 16);
        this.ghostLeftLeg.mirror = true;
        this.ghostLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.ghostLeftLeg.setRotationPoint(1.9F, 12.0F + p_i1149_2_, 0.0F);
        //Super Stuff

        if(slimModel)
        {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.bipedRightArm = new ModelRenderer(this, 40, 16);
            this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
            this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
            this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
            this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);

            this.ghostLeftArm = new ModelRenderer(this, 32, 48);
            this.ghostLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
            this.ghostLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
            this.ghostRightArm = new ModelRenderer(this, 40, 16);
            this.ghostRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
            this.ghostRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
        }
        else
        {
            this.bipedLeftArm = new ModelRenderer(this, 32, 48);
            this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
            this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
            this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
            this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
            this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
            this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
            this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
            this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);

            this.ghostLeftArm = new ModelRenderer(this, 32, 48);
            this.ghostLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
            this.ghostLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        }

        this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
        this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
        this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
        this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
        this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
        this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
        this.bipedBodyWear = new ModelRenderer(this, 16, 32);
        this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize + 0.25F);
        this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);

        this.ghostLeftLeg = new ModelRenderer(this, 16, 48);
        this.ghostLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
        this.ghostLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
    }

    /**
     * Sets the models various rotation angles then renders the model.
     */
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

        if(this.isChild)
        {
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
            this.bipedLeftLegwear.render(scale);
            this.bipedRightLegwear.render(scale);
            this.bipedLeftArmwear.render(scale);
            this.bipedRightArmwear.render(scale);
            this.bipedBodyWear.render(scale);
        }
        else
        {
            if(entityIn.isSneaking())
                GlStateManager.translate(0.0F, 0.2F, 0.0F);

            this.bipedLeftLegwear.render(scale);
            this.bipedRightLegwear.render(scale);
            this.bipedLeftArmwear.render(scale);
            this.bipedRightArmwear.render(scale);
            this.bipedBodyWear.render(scale);
        }

        GlStateManager.translate((random.nextFloat()-0.5F) *0.5F, (random.nextFloat()-0.5F) *0.5F, (random.nextFloat()-0.5F) *0.5F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.25F);
        this.ghostHead.render(scale);
        this.ghostBody.render(scale);
        this.ghostRightArm.render(scale);
        this.ghostLeftArm.render(scale);
        this.ghostRightLeg.render(scale);
        this.ghostLeftLeg.render(scale);
        this.ghostHeadwear.render(scale);

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
        copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
        copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
        copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
        copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
        copyModelAngles(this.bipedBody, this.bipedBodyWear);
    }
}
