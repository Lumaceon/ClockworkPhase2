package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.api.block.IMultiblockTemplate;
import lumaceon.mods.clockworkphase2.api.item.IMultiblockTemplateItem;
import lumaceon.mods.clockworkphase2.block.BlockMultiblockAssembler;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentString;

public class TileMultiblockAssembler extends TileMod
{
    public ItemStack templateStack = ItemStack.EMPTY;
    public IMultiblockTemplate template;
    public boolean isAssemblingMultiblock = false; //Used in the breakBlock to keep the template from dropping when this block is replaced.

    /**
     * @return True if the item was removed, false if not.
     */
    public boolean onRightClickWithEmptyHand(EntityPlayer player)
    {
        if(!templateStack.isEmpty())
        {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, templateStack.copy());
            templateStack = ItemStack.EMPTY;
            template = null;
            markDirty();
        }
        return false;
    }

    /**
     * @return True if the item was accepted into here.
     */
    public boolean onRightClickWithMultiblockTemplate(ItemStack stack)
    {
        if(templateStack.isEmpty() && !stack.isEmpty() && stack.getItem() instanceof IMultiblockTemplateItem)
        {
            templateStack = stack.copy();
            templateStack.setCount(1);
            template = ((IMultiblockTemplateItem) stack.getItem()).getTemplate();
            markDirty();
            return true;
        }
        return false;
    }

    /**
     * @return True if a block was placed, false if it failed.
     */
    public boolean onRightClickWithConstructionBlock(EntityPlayer player)
    {
        if(template == null)
        {
            if(templateStack.isEmpty())
                return false;
            Item item = templateStack.getItem();
            if(item instanceof IMultiblockTemplateItem)
                template = ((IMultiblockTemplateItem) item).getTemplate();
        }

        if(template != null)
        {
            int maxIndex = template.getMaxIndex();
            for(int i = 0; i <= maxIndex; i++)
            {
                IMultiblockTemplate.BlockData data = template.getBlockForIndex(i);
                IBlockState state = world.getBlockState(pos.add(data.getPosition()));
                if(state != null)
                {
                    Block block = state.getBlock();
                    if(block != null)
                    {
                        if((block.equals(ModBlocks.constructionBlock) || block.equals(ModBlocks.multiblockAssembler)))
                            continue; //The block is either a construction block, or the assembler itself, so we keep going.
                        if(block.isReplaceable(world, pos.add(data.getPosition())))
                        {
                            setNewBlock(data);
                            return true;
                        }

                        if(world != null && !world.isRemote)
                        {
                            player.sendMessage(new TextComponentString(Colors.RED + "Block in the way: " + data.getPosition().toString()));
                        }
                        return false; //Here, a block exists, but it's in the way, so we can't put anything down.
                    }
                    setNewBlock(data); //At this point, we know the block is null, so we know we can modify it.
                    return true;
                }
                setNewBlock(data); //Similarly, if the state is null, it should be fine to change the block.
                return true;
            }
        }
        return false;
    }

    private void setNewBlock(IMultiblockTemplate.BlockData data)
    {
        world.setBlockToAir(pos.add(data.getPosition()));
        world.setBlockState(pos.add(data.getPosition()), ModBlocks.constructionBlock.getDefaultState().withProperty(BlockMultiblockAssembler.METADATA, data.meta));

        //Check to see if all of the blocks are ready for the full multiblock to form.
        for(int i = 0; i <= template.getMaxIndex(); i++)
        {
            data = template.getBlockForIndex(i);
            IBlockState state = world.getBlockState(pos.add(data.getPosition()));
            if(state != null)
            {
                Block block = state.getBlock();
                if(block != null)
                {
                    if( (block.equals(ModBlocks.constructionBlock) && state.getValue(BlockMultiblockAssembler.METADATA) == data.meta)
                            || (block.equals(ModBlocks.multiblockAssembler)) )
                        continue;
                }
            }
            return;
        }

        //If we make it to here, we know all of the blocks are valid, so we can begin converting them.
        IMultiblockTemplate.BlockData assemblerReplacement = null;
        for(int i = 0; i <= template.getMaxIndex(); i++)
        {
            data = template.getBlockForIndex(i);
            if(data.getPosition().getX() == 0 && data.getPosition().getY() == 0 && data.getPosition().getZ() == 0)
            {
                assemblerReplacement = data;
                continue; //We replace the assembler last...it probably wouldn't break, but best to make sure.
            }
            world.setBlockState(pos.add(data.getPosition()), data.getBlock().getStateFromMeta(data.meta));
        }

        if(assemblerReplacement != null)
        {
            isAssemblingMultiblock = true;
            world.setBlockState(pos, assemblerReplacement.getBlock().getStateFromMeta(assemblerReplacement.meta));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if(!templateStack.isEmpty())
        {
            NBTTagCompound stackTag = new NBTTagCompound();
            templateStack.writeToNBT(stackTag);
            nbt.setTag("template_stack", stackTag);
        }
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if(nbt.hasKey("template_stack"))
        {
            templateStack = new ItemStack((NBTTagCompound) nbt.getTag("template_stack"));
            if(templateStack.getItem() instanceof IMultiblockTemplateItem)
                template = ((IMultiblockTemplateItem) templateStack.getItem()).getTemplate();
        }
    }
}
