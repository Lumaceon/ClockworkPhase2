package lumaceon.mods.clockworkphase2.tile.temporal;

import lumaceon.mods.clockworkphase2.api.block.IMultiblockTemplate;
import lumaceon.mods.clockworkphase2.block.multiblocktemplate.MultiblockTemplateArmillaryRing;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModFluids;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TileArmillaryRing extends TileMod implements ITickable
{
    /**
     * Contains one quarter of where all the fluid blocks should be placed. Does not include the blocks directly below
     * this tile. Rotating this 3 additional times 90, 180, and 270 degrees will give you the full area.
     */
    public BlockPos[] fluidPositionQuarter =
            {
                    new BlockPos(1, 0, 0),
                    new BlockPos(2, 0, 0),
                    new BlockPos(3, 0, 0),
                    new BlockPos(4, 0, 0),
                    new BlockPos(5, 0, 0),
                    new BlockPos(1, -1, 0),
                    new BlockPos(2, -1, 0),
                    new BlockPos(3, -1, 0),
                    new BlockPos(4, -1, 0),
                    new BlockPos(5, -1, 0),
                    new BlockPos(1, -2, 0),
                    new BlockPos(2, -2, 0),
                    new BlockPos(3, -2, 0),
                    new BlockPos(4, -2, 0),
                    new BlockPos(5, -2, 0),
                    new BlockPos(1, -3, 0),
                    new BlockPos(2, -3, 0),
                    new BlockPos(3, -3, 0),
                    new BlockPos(4, -3, 0),
                    new BlockPos(1, -4, 0),
                    new BlockPos(2, -4, 0),
                    new BlockPos(3, -4, 0),
                    new BlockPos(1, -5, 0),
                    new BlockPos(2, -5, 0),

                    new BlockPos(1, 0, 1),
                    new BlockPos(2, 0, 1),
                    new BlockPos(3, 0, 1),
                    new BlockPos(4, 0, 1),
                    new BlockPos(5, 0, 1),
                    new BlockPos(1, -1, 1),
                    new BlockPos(2, -1, 1),
                    new BlockPos(3, -1, 1),
                    new BlockPos(4, -1, 1),
                    new BlockPos(5, -1, 1),
                    new BlockPos(1, -2, 1),
                    new BlockPos(2, -2, 1),
                    new BlockPos(3, -2, 1),
                    new BlockPos(4, -2, 1),
                    new BlockPos(5, -2, 1),
                    new BlockPos(1, -3, 1),
                    new BlockPos(2, -3, 1),
                    new BlockPos(3, -3, 1),
                    new BlockPos(4, -3, 1),
                    new BlockPos(1, -4, 1),
                    new BlockPos(2, -4, 1),
                    new BlockPos(3, -4, 1),
                    new BlockPos(1, -5, 1),
                    new BlockPos(2, -5, 1),
                    new BlockPos(2, -5, 0),

                    new BlockPos(1, 0, 2),
                    new BlockPos(2, 0, 2),
                    new BlockPos(3, 0, 2),
                    new BlockPos(4, 0, 2),
                    new BlockPos(5, 0, 2),
                    new BlockPos(1, -1, 2),
                    new BlockPos(2, -1, 2),
                    new BlockPos(3, -1, 2),
                    new BlockPos(4, -1, 2),
                    new BlockPos(5, -1, 2),
                    new BlockPos(1, -2, 2),
                    new BlockPos(2, -2, 2),
                    new BlockPos(3, -2, 2),
                    new BlockPos(4, -2, 2),
                    new BlockPos(1, -3, 2),
                    new BlockPos(2, -3, 2),
                    new BlockPos(3, -3, 2),
                    new BlockPos(1, -4, 2),
                    new BlockPos(2, -4, 2),
                    new BlockPos(1, -5, 2),

                    new BlockPos(1, 0, 3),
                    new BlockPos(2, 0, 3),
                    new BlockPos(3, 0, 3),
                    new BlockPos(4, 0, 3),
                    new BlockPos(1, -1, 3),
                    new BlockPos(2, -1, 3),
                    new BlockPos(3, -1, 3),
                    new BlockPos(4, -1, 3),
                    new BlockPos(1, -2, 3),
                    new BlockPos(2, -2, 3),
                    new BlockPos(3, -2, 3),
                    new BlockPos(1, -3, 3),
                    new BlockPos(2, -3, 3),
                    new BlockPos(1, -4, 3),

                    new BlockPos(1, 0, 4),
                    new BlockPos(2, 0, 4),
                    new BlockPos(3, 0, 4),
                    new BlockPos(1, -1, 4),
                    new BlockPos(2, -1, 4),
                    new BlockPos(3, -1, 4),
                    new BlockPos(1, -2, 4),
                    new BlockPos(2, -2, 4),
                    new BlockPos(1, -3, 4),

                    new BlockPos(1, 0, 5),
                    new BlockPos(2, 0, 5),
                    new BlockPos(1, -1, 5),
                    new BlockPos(2, -1, 5),
                    new BlockPos(1, -2, 5)
            };

    public boolean isBeingDestroyed = false;

    public long internalTickCount = 0;
    public boolean isCrafting = false;

    @Override
    public void update()
    {
        /*if(internalTickCount % 3 == 0)
        {
            List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.add(-4, -4, -4), pos.add(5, 5, 5)));
            for(EntityItem itemEntity : items)
            {
                Vec3d blockMiddle = new Vec3d(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                Vec3d entityPos = itemEntity.getPositionVector();
                if(blockMiddle.distanceTo(entityPos) <= 5)
                {
                    ItemStack item = itemEntity.getItem();
                    if(!item.isEmpty())
                    {
                        //If there's no crafting recipe already set up, see if the item is the main item for crafting.
                        if(input.isEmpty())
                        {
                            ArmillaryRecipes.ArmillaryRecipe recipe = ArmillaryRecipes.instance.getRecipe(item);
                            if(recipe != null)
                            {
                                input = item;
                                itemEntity.setDead();
                            }
                        }

                        //If there's an input already, we're checking for components.
                        if(!input.isEmpty() && !itemEntity.isDead) //Check for isDead, in case we just killed it.
                        {
                            //Find the first free slot (or -1 if all are full).
                            int firstFreeSlot = -1;
                            for(int index = 0; index < chanceEnhancementItems.size(); index++)
                            {
                                ItemStack is = chanceEnhancementItems.get(index);
                                if(is.isEmpty())
                                {
                                    firstFreeSlot = index;
                                    break; //We found the first free slot, so stop.
                                }
                            }

                            if(firstFreeSlot != -1)
                            {
                                ArmillaryRecipes.ArmillaryRecipe recipe = ArmillaryRecipes.instance.getRecipe(input);
                                if(recipe != null)
                                {
                                    int numberOfItemThisRecipeCanAccept = recipe.getMaxNumberOfItem(item);
                                    if(numberOfItemThisRecipeCanAccept > 0)
                                    {
                                        //Find number of the item we're trying to add already inside.
                                        int numberOfItemsAlreadyInInventory = 0;
                                        for(ItemStack is : chanceEnhancementItems)
                                        {
                                            if(!is.isEmpty() && OreDictionary.itemMatches(is, item, false))
                                            {
                                                ++numberOfItemsAlreadyInInventory;
                                            }
                                        }

                                        //If we can fit the item in the recipe.
                                        if(numberOfItemsAlreadyInInventory < numberOfItemThisRecipeCanAccept)
                                        {
                                            chanceEnhancementItems.set(firstFreeSlot, item);
                                            itemEntity.setDead();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }*/

        if(internalTickCount % 200 == 0)
        {
            IBlockState state;
            BlockPos translatedPosition;
            for(int i = 0; i < fluidPositionQuarter.length; i++)
            {
                translatedPosition = getPos().add(fluidPositionQuarter[i]);
                state = world.getBlockState(translatedPosition);
                if(state == null || !state.getBlock().equals(ModFluids.TIMESTREAM.getBlock()) && state.getBlock().isReplaceable(world, translatedPosition))
                {
                    world.setBlockState(translatedPosition, ModFluids.TIMESTREAM.getBlock().getDefaultState());
                }

                translatedPosition = getPos().add(fluidPositionQuarter[i].rotate(Rotation.CLOCKWISE_90));
                state = world.getBlockState(translatedPosition);
                if(state == null || !state.getBlock().equals(ModFluids.TIMESTREAM.getBlock()) && state.getBlock().isReplaceable(world, translatedPosition))
                {
                    world.setBlockState(translatedPosition, ModFluids.TIMESTREAM.getBlock().getDefaultState());
                }

                translatedPosition = getPos().add(fluidPositionQuarter[i].rotate(Rotation.CLOCKWISE_180));
                state = world.getBlockState(translatedPosition);
                if(state == null || !state.getBlock().equals(ModFluids.TIMESTREAM.getBlock()) && state.getBlock().isReplaceable(world, translatedPosition))
                {
                    world.setBlockState(translatedPosition, ModFluids.TIMESTREAM.getBlock().getDefaultState());
                }

                translatedPosition = getPos().add(fluidPositionQuarter[i].rotate(Rotation.COUNTERCLOCKWISE_90));
                state = world.getBlockState(translatedPosition);
                if(state == null || !state.getBlock().equals(ModFluids.TIMESTREAM.getBlock()) && state.getBlock().isReplaceable(world, translatedPosition))
                {
                    world.setBlockState(translatedPosition, ModFluids.TIMESTREAM.getBlock().getDefaultState());
                }
            }

            for(int yDown = 1; yDown < 6; yDown++)
            {
                translatedPosition = getPos().down(yDown);
                state = world.getBlockState(translatedPosition);
                if(state == null || !state.getBlock().equals(ModFluids.TIMESTREAM.getBlock()) && state.getBlock().isReplaceable(world, translatedPosition))
                {
                    world.setBlockState(translatedPosition, ModFluids.TIMESTREAM.getBlock().getDefaultState());
                }
            }
        }

        ++internalTickCount;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
    }

    public static void destroyMultiblock(TileArmillaryRing te, World world, BlockPos pos)
    {
        IMultiblockTemplate template = MultiblockTemplateArmillaryRing.INSTANCE;
        te.isBeingDestroyed = true;

        int consBlocksToDrop = template.getMaxIndex();
        while(consBlocksToDrop > 0)
        {
            int numberDropped = Math.min(64, consBlocksToDrop);
            ItemStack consBlocks1 = new ItemStack(ModBlocks.constructionBlock);
            consBlocks1.setCount(numberDropped);
            consBlocksToDrop -= numberDropped;
            world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), consBlocks1));
        }

        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.multiblockArmillaryRing)));

        BlockPos currentPosition;
        IBlockState state;
        for(int i = 0; i < te.fluidPositionQuarter.length; i++)
        {
            currentPosition = pos.add(te.fluidPositionQuarter[i]);
            state = world.getBlockState(currentPosition);
            if(state != null && state.getBlock().equals(ModFluids.TIMESTREAM.getBlock()))
                world.setBlockToAir(currentPosition);

            currentPosition = pos.add(te.fluidPositionQuarter[i].rotate(Rotation.CLOCKWISE_90));
            state = world.getBlockState(currentPosition);
            if(state != null && state.getBlock().equals(ModFluids.TIMESTREAM.getBlock()))
                world.setBlockToAir(currentPosition);

            currentPosition = pos.add(te.fluidPositionQuarter[i].rotate(Rotation.CLOCKWISE_180));
            state = world.getBlockState(currentPosition);
            if(state != null && state.getBlock().equals(ModFluids.TIMESTREAM.getBlock()))
                world.setBlockToAir(currentPosition);

            currentPosition = pos.add(te.fluidPositionQuarter[i].rotate(Rotation.COUNTERCLOCKWISE_90));
            state = world.getBlockState(currentPosition);
            if(state != null && state.getBlock().equals(ModFluids.TIMESTREAM.getBlock()))
                world.setBlockToAir(currentPosition);

            for(int n = 1; n < 6; n++)
            {
                currentPosition = pos.down(n);
                state = world.getBlockState(currentPosition);
                if(state != null && state.getBlock().equals(ModFluids.TIMESTREAM.getBlock()))
                    world.setBlockToAir(currentPosition);
            }
        }

        for(int i = 0; i <= template.getMaxIndex(); i++)
        {
            IMultiblockTemplate.BlockData data = template.getBlockForIndex(i);
            currentPosition = pos.add(data.getPosition());
            state = world.getBlockState(currentPosition);
            if(state != null && state.getBlock() != null && state.getBlock().equals(data.getBlock()))
                world.setBlockToAir(currentPosition);
        }

        world.setBlockToAir(te.getPos());
    }

    /*@SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }*/
}
