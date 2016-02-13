package lumaceon.mods.clockworkphase2.client.render;

import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ModelRegistry
{
    public static void registerItemBlockModel(Block block, String blockUnlocalizedName) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Reference.MOD_ID + ":" + blockUnlocalizedName, "inventory"));
    }

    public static void registerItemModel(Item item, String unlocalizedName) {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MOD_ID + ":" + unlocalizedName, "inventory"));
    }
}
