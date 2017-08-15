package lumaceon.mods.clockworkphase2.client.render;

import lumaceon.mods.clockworkphase2.init.ModFluids;
import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;

public class ModelRegistry
{
    public static void registerItemBlockModel(Block block, String blockUnlocalizedName) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + blockUnlocalizedName, "inventory"));
    }

    public static void registerItemBlockCustomModel(Block block, String blockUnlocalizedName) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID.toLowerCase() + ":" + blockUnlocalizedName, "inventory"));
    }

    public static void registerItemModel(Item item, String unlocalizedName) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + unlocalizedName, "inventory"));
    }

    public static void registerFluidModels() {
        ModFluids.MOD_FLUID_BLOCKS.forEach(ModelRegistry::registerFluidModel);
    }

    private static void registerFluidModel(IFluidBlock fluidBlock)
    {
        final Item item = Item.getItemFromBlock((Block) fluidBlock);
        assert item != null;

        ModelBakery.registerItemVariants(item);

        final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(Reference.MOD_ID + ":fluid", fluidBlock.getFluid().getName());

        ModelLoader.setCustomMeshDefinition(item, MeshDefinitionFix.create(stack -> modelResourceLocation));

        ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
                return modelResourceLocation;
            }
        });
    }

    /*public static void registerFluidModel(Block liquidBlock, String name)
    {
        Item fluid = Item.getItemFromBlock(liquidBlock);
        ModelBakery.registerItemVariants(fluid);

        final ModelResourceLocation modelLocation = new ModelResourceLocation(Reference.MOD_ID + ":fluids", name);

        ModelLoader.setCustomMeshDefinition(fluid, stack -> modelLocation);
        ModelLoader.setCustomStateMapper(liquidBlock, new StateMapperBase() {
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return modelLocation;
            }
        });
    }*/
}
