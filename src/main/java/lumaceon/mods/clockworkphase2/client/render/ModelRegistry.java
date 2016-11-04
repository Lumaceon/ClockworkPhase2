package lumaceon.mods.clockworkphase2.client.render;

import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

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

    public static void registerFluidModel(Block liquidBlock, String name)
    {
        Item fluid = Item.getItemFromBlock(liquidBlock);
        ModelBakery.registerItemVariants(fluid);

        final ModelResourceLocation modelLocation = new ModelResourceLocation(Reference.MOD_ID + ":fluids", name);

        ModelLoader.setCustomMeshDefinition(fluid, new ItemMeshDefinition() {
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return modelLocation;
            }
        });
        ModelLoader.setCustomStateMapper(liquidBlock, new StateMapperBase() {
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return modelLocation;
            }
        });
    }
}
