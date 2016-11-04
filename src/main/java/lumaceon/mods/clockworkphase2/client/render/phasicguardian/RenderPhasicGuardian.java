package lumaceon.mods.clockworkphase2.client.render.phasicguardian;

import lumaceon.mods.clockworkphase2.entity.EntityPhasicGuardian;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderPhasicGuardian<T extends EntityPhasicGuardian> extends RenderLiving<T>
{
    public RenderPhasicGuardian(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return Textures.ENTITY.ETHEREAL_SPECTER;
    }
}
