package lumaceon.mods.clockworkphase2.client.model;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import lumaceon.mods.clockworkphase2.util.NBTHelper;
import lumaceon.mods.clockworkphase2.util.NBTTags;
import lumaceon.mods.clockworkphase2.proxy.ClientProxy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.IModelState;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ModelTemporalMultitool implements IModel
{
    @Override
    public Collection<ResourceLocation> getDependencies() {
        return null;
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return null;
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        IModel subComponent;
        try {
            subComponent = ModelLoaderRegistry.getModel(ClientProxy.MULTITOOL_MODEL);
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

    public static class Baked implements IPerspectiveAwareModel
    {
        public IBakedModel model;
        IBakedModel subModel;
        List<BakedQuad> quadList;

        public Baked(IBakedModel model) {
            this.model = model;
        }

        public Baked(IBakedModel model, IBakedModel subModel) {
            this.model = model;
            this.subModel = subModel;
        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
        {
            List<BakedQuad> list = new LinkedList<BakedQuad>();
            if(subModel != null)
                list.addAll(subModel.getQuads(state, side, rand));
            quadList = list;
            return quadList;
        }

        @Override
        public boolean isAmbientOcclusion() {
            if(subModel != null)
                return subModel.isAmbientOcclusion();
            return model.isAmbientOcclusion();
        }

        @Override
        public boolean isGui3d() {
            if(subModel != null)
                return subModel.isGui3d();
            return model.isGui3d();
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
            if(subModel != null)
                return subModel.getParticleTexture();
            return model.getParticleTexture();
        }

        @Override
        public ItemCameraTransforms getItemCameraTransforms() {
            if(subModel != null)
                return subModel.getItemCameraTransforms();
            return model.getItemCameraTransforms();
        }

        private final Overrides INSTANCE = new Overrides(ImmutableList.<ItemOverride>of());
        @Override
        public ItemOverrideList getOverrides() {
            return INSTANCE;
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
            if(subModel != null && subModel instanceof IPerspectiveAwareModel)
                return ((IPerspectiveAwareModel) subModel).handlePerspective(cameraTransformType);
            return ((IPerspectiveAwareModel) model).handlePerspective(cameraTransformType);
        }
    }

    public static class Overrides extends ItemOverrideList
    {
        IBakedModel baseModel;
        IBakedModel overrideModel;
        Item overrideItem;

        public Overrides(List<ItemOverride> overridesIn) {
            super(overridesIn);
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity)
        {
            if(baseModel == null)
                baseModel = originalModel;

            if(NBTHelper.hasTag(stack, NBTTags.COMPONENT_INVENTORY))
            {
                byte index = NBTHelper.BYTE.get(stack, "MT_index");
                ItemStack[] items = NBTHelper.INVENTORY.get(stack, NBTTags.COMPONENT_INVENTORY);
                if(items.length > index)
                {
                    ItemStack current = items[index];
                    if(current != null)
                    {
                        if(!current.getItem().equals(overrideItem))
                        {
                            overrideModel = new Baked(baseModel, Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(current));
                            overrideItem = current.getItem();
                        }
                    }
                    else if(overrideItem != null)
                    {
                        overrideModel = null;
                        overrideItem = null;
                    }
                }
            }
            else
            {
                overrideModel = null;
                overrideItem = null;
            }

            if(overrideModel != null)
                return overrideModel;
            return baseModel;
            /*

            byte index = NBTHelper.BYTE.get(stack, "MT_index");
            if(index == previousIndex)
                return originalModel;
            else
                previousIndex = index;

            if(NBTHelper.hasTag(stack, NBTTags.COMPONENT_INVENTORY))
            {
                ItemStack[] items = NBTHelper.INVENTORY.get(stack, NBTTags.COMPONENT_INVENTORY);
                if(items.length > index)
                {
                    ItemStack current = items[index];
                    if(current != null)
                    {
                        if(!current.getItem().equals(previousItem))
                        {
                            overrideModel = new Baked(baseModel, Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(current));
                            previousItem = current.getItem();
                        }
                    }
                    else if(previousItem != null)
                    {
                        overrideModel = baseModel;
                        previousItem = null;
                    }
                }
            }
            if(overrideModel != null)
                return overrideModel;
            return baseModel;*/
        }
    }
}
