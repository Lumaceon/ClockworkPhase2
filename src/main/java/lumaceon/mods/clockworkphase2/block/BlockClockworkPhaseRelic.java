package lumaceon.mods.clockworkphase2.block;

import lumaceon.mods.clockworkphase2.api.block.ITrowelBlock;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.api.util.WeightedChance;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class BlockClockworkPhaseRelic extends BlockClockworkPhase implements ITrowelBlock
{
    WeightedChance<ItemStack>[] possibleDrops;
    int harvestLevel;

    public BlockClockworkPhaseRelic(Material blockMaterial, int harvestLevel, String unlocalizedName, ArrayList<ModItems.ItemReference> possibleDrops)
    {
        super(blockMaterial, unlocalizedName);
        this.setHardness(3F);
        this.harvestLevel = harvestLevel;
        this.possibleDrops = possibleDrops.toArray(new WeightedChance[possibleDrops.size()]);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(Blocks.DIRT);
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public void onTrowelRightClick(ItemStack trowelStack, World world, BlockPos pos, int trowelLevel)
    {
        if(harvestLevel > trowelLevel)
            return;
        world.setBlockToAir(pos);
        if(possibleDrops.length <= 0)
            return;
        if(!world.isRemote)
            world.spawnEntityInWorld(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), possibleDrops[0].getObjectFromWeightedChance(possibleDrops).copy()));
    }

    @Override
    public ItemStack getCleaningResult(World world, BlockPos pos) {
        return possibleDrops[0].getObjectFromWeightedChance(possibleDrops).copy();
    }
}
