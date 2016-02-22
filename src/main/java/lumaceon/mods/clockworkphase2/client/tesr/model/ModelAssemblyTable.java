package lumaceon.mods.clockworkphase2.client.tesr.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

/**
 * Credits to Grudge25 for the assembly table model and texture.
 */
public class ModelAssemblyTable extends ModelBase
{
    ModelRenderer leftStand;
    ModelRenderer rightStand;
    ModelRenderer leftFrontLeg;
    ModelRenderer leftBackLeg;
    ModelRenderer rightFrontLeg;
    ModelRenderer rightBackLeg;
    ModelRenderer surfaceTable;
    ModelRenderer footRest;

    public ModelAssemblyTable()
    {
        textureWidth = 128;
        textureHeight = 64;

        leftStand = new ModelRenderer(this, 0, 19);
        leftStand.addBox(0F, 0F, 0F, 3, 3, 12);
        leftStand.setRotationPoint(-7F, 21F, -6F);
        leftStand.setTextureSize(128, 64);
        leftStand.mirror = true;
        setRotation(leftStand, 0F, 0F, 0F);
        rightStand = new ModelRenderer(this, 0, 19);
        rightStand.addBox(0F, 0F, 0F, 3, 3, 12);
        rightStand.setRotationPoint(20F, 21F, -6F);
        rightStand.setTextureSize(128, 64);
        rightStand.mirror = true;
        setRotation(rightStand, 0F, 0F, 0F);
        leftFrontLeg = new ModelRenderer(this, 0, 34);
        leftFrontLeg.addBox(0F, -12F, -2F, 1, 12, 2);
        leftFrontLeg.setRotationPoint(-6F, 21F, 3F);
        leftFrontLeg.setTextureSize(128, 64);
        leftFrontLeg.mirror = true;
        setRotation(leftFrontLeg, 0.6320364F, 0F, 0F);
        leftBackLeg = new ModelRenderer(this, 6, 34);
        leftBackLeg.addBox(0F, 0F, 0F, 1, 8, 2);
        leftBackLeg.setRotationPoint(-6F, 13F, 3F);
        leftBackLeg.setTextureSize(128, 64);
        leftBackLeg.mirror = true;
        setRotation(leftBackLeg, 0F, 0F, 0F);
        rightFrontLeg = new ModelRenderer(this, 0, 34);
        rightFrontLeg.addBox(0F, -12F, -2F, 1, 12, 2);
        rightFrontLeg.setRotationPoint(21F, 21F, 3F);
        rightFrontLeg.setTextureSize(128, 64);
        rightFrontLeg.mirror = true;
        setRotation(rightFrontLeg, 0.6320364F, 0F, 0F);
        rightBackLeg = new ModelRenderer(this, 6, 34);
        rightBackLeg.mirror = true;
        rightBackLeg.addBox(0F, 0F, 0F, 1, 8, 2);
        rightBackLeg.setRotationPoint(21F, 13F, 3F);
        rightBackLeg.setTextureSize(128, 64);
        rightBackLeg.mirror = true;
        setRotation(rightBackLeg, 0F, 0F, 0F);
        rightBackLeg.mirror = false;
        surfaceTable = new ModelRenderer(this, 0, 0);
        surfaceTable.addBox(-16F, 0F, -8F, 32, 3, 16);
        surfaceTable.setRotationPoint(8F, 10F, 0F);
        surfaceTable.setTextureSize(128, 64);
        surfaceTable.mirror = true;
        setRotation(surfaceTable, 0F, 0F, 0F);
        footRest = new ModelRenderer(this, 30, 19);
        footRest.addBox(0F, 0F, 0F, 24, 1, 2);
        footRest.setRotationPoint(-4F, 22F, -1F);
        footRest.setTextureSize(128, 64);
        footRest.mirror = true;
        setRotation(footRest, 0F, 0F, 0F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float scale) {
        super.render(entity, f, f1, f2, f3, f4, scale);
        setRotationAngles(f, f1, f2, f3, f4, scale, entity);
        leftStand.render(scale);
        rightStand.render(scale);
        leftFrontLeg.render(scale);
        leftBackLeg.render(scale);
        rightFrontLeg.render(scale);
        rightBackLeg.render(scale);
        surfaceTable.render(scale);
        footRest.render(scale);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
