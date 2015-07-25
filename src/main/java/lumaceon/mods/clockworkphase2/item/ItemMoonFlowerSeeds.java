package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemMoonFlowerSeeds extends ItemClockworkPhase implements IPlantable
{
    public ItemMoonFlowerSeeds(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    public boolean onItemUse(ItemStack item, EntityPlayer player, World world, int x, int y, int z, int metadata, float p_77648_8_, float p_77648_9_, float p_77648_10_)
    {
        if(player.canPlayerEdit(x, y, z, metadata, item) && player.canPlayerEdit(x, y + 1, z, metadata, item))
        {
            if(world.getBlock(x, y, z).canSustainPlant(world, x, y, z, ForgeDirection.UP, this) && world.isAirBlock(x, y + 1, z))
            {
                world.setBlock(x, y + 1, z, ModBlocks.moonFlower);
                --item.stackSize;
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return EnumPlantType.Crop;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return ModBlocks.moonFlower;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return 0;
    }
}
