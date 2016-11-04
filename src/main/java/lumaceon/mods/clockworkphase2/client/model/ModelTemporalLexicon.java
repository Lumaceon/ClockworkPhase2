package lumaceon.mods.clockworkphase2.client.model;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ModelTemporalLexicon implements IModel
{
    public static final ModelResourceLocation MODEL_CORE = new ModelResourceLocation("clockworkphase2:clockwork_pickaxe");

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return null;
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return null;
    }

    //TODO This seems to be ignored completely and goes straight to the baked model.
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        IModel subComponent;
        try {
            subComponent = ModelLoaderRegistry.getModel(MODEL_CORE);
            IBakedModel bakedModelCore = subComponent.bake(state, format, bakedTextureGetter);
            return new Baked(bakedModelCore);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IModelState getDefaultState() {
        return null;
    }

    public static class Baked implements IBakedModel
    {
        //private final ItemStack APPLE = new ItemStack(Items.APPLE);
        //private final ItemStack GLASS = new ItemStack(Blocks.GLASS);
        IBakedModel subModel;
        //IBakedModel subModel2;
        //IBakedModel subModel3;
        List<BakedQuad> quadList;

        public Baked(IBakedModel model) {
            subModel = model;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            //subModel2 = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(APPLE);
            //subModel3 = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(GLASS);
            List<BakedQuad> list = new LinkedList<BakedQuad>();
            list.addAll(subModel.getQuads(state, side, rand));
            //list.addAll(subModel2.getQuads(state, side, rand));
            //list.addAll(subModel3.getQuads(state, side, rand));
            quadList = list;
            return quadList;
        }

        @Override
        public boolean isAmbientOcclusion() {
            return subModel.isAmbientOcclusion();
        }

        @Override
        public boolean isGui3d() {
            return subModel.isGui3d();
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            return subModel.getParticleTexture();
        }

        @Override
        public ItemCameraTransforms getItemCameraTransforms() {
            return subModel.getItemCameraTransforms();
        }

        private final LexiconOverride INSTANCE = new LexiconOverride(ImmutableList.<ItemOverride>of());
        @Override
        public ItemOverrideList getOverrides() {
            return INSTANCE;
        }
    }

    public static class LexiconOverride extends ItemOverrideList
    {
        public LexiconOverride(List<ItemOverride> overridesIn) {
            super(overridesIn);
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity)
        {
            net.minecraft.item.Item item = stack.getItem();
            if (item != null && item.hasCustomProperties())
            {
                ResourceLocation location = applyOverride(stack, world, entity);
                if (location != null)
                {
                    return net.minecraft.client.Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(net.minecraftforge.client.model.ModelLoader.getInventoryVariant(location.toString()));
                }
            }
            return originalModel;
        }
    }
}