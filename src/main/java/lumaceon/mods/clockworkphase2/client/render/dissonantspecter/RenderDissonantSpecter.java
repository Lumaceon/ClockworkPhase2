package lumaceon.mods.clockworkphase2.client.render.dissonantspecter;

import lumaceon.mods.clockworkphase2.entity.EntityDissonantSpecter;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderDissonantSpecter<T extends EntityDissonantSpecter> extends RenderLiving<T>
{
    public RenderDissonantSpecter(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
    }

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return Textures.ENTITY.ETHEREAL_SPECTER;
    }
}
