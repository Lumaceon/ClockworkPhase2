package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.api.block.IMultiblockTemplate;
import lumaceon.mods.clockworkphase2.api.item.IMultiblockTemplateItem;
import lumaceon.mods.clockworkphase2.block.BlockMultiblockAssembler;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileMultiblockAssembler extends TileClockworkPhase
{
    public ItemStack templateStack;
    public IMultiblockTemplate template;

    /**
     * @return True if the item was removed, false if not.
     */
    public boolean onRightClickWithEmptyHand(EntityPlayer player)
    {
        if(templateStack != null)
        {
            player.inventory.setInventorySlotContents(player.inventory.currentItem, templateStack.copy());
            templateStack = null;
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
        if(templateStack == null && stack != null && stack.getItem() instanceof IMultiblockTemplateItem)
        {
            templateStack = stack.copy();
            templateStack.stackSize = 1;
            template = ((IMultiblockTemplateItem) stack.getItem()).getTemplate();
            markDirty();
            return true;
        }
        return false;
    }

    /**
     * @return True if a block was placed, false if it failed.
     */
    public boolean onRightClickWithConstructionBlock()
    {
        if(template == null)
        {
            if(templateStack == null)
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
                IBlockState state = worldObj.getBlockState(pos.add(data.getPosition()));
                if(state != null)
                {
                    Block block = state.getBlock();
                    if(block != null)
                    {
                        if((block.equals(ModBlocks.constructionBlock.getBlock()) || block.equals(ModBlocks.multiblockAssembler.getBlock())))
                            continue; //The block is either a construction block, or the assembler itself, so we keep going.
                        if(block.isReplaceable(worldObj, pos.add(data.getPosition())))
                        {
                            setNewBlock(data);
                            return true;
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
        worldObj.setBlockToAir(pos.add(data.getPosition()));
        worldObj.setBlockState(pos.add(data.getPosition()), ModBlocks.constructionBlock.getBlock().getDefaultState().withProperty(BlockMultiblockAssembler.METADATA, data.meta));

        //Check to see if all of the blocks are ready for the full multiblock to form.
        for(int i = 0; i <= template.getMaxIndex(); i++)
        {
            data = template.getBlockForIndex(i);
            IBlockState state = worldObj.getBlockState(pos.add(data.getPosition()));
            if(state != null)
            {
                Block block = state.getBlock();
                if(block != null)
                {
                    if( (block.equals(ModBlocks.constructionBlock.getBlock()) && state.getValue(BlockMultiblockAssembler.METADATA) == data.meta)
                            || (block.equals(ModBlocks.multiblockAssembler.getBlock())) )
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
            worldObj.setBlockState(pos.add(data.getPosition()), data.getBlock().getStateFromMeta(data.meta));
        }

        if(assemblerReplacement != null)
            worldObj.setBlockState(pos, assemblerReplacement.getBlock().getStateFromMeta(assemblerReplacement.meta));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if(templateStack != null)
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
            templateStack = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbt.getTag("template_stack"));
            if(templateStack != null && templateStack.getItem() instanceof IMultiblockTemplateItem)
                template = ((IMultiblockTemplateItem) templateStack.getItem()).getTemplate();
        }
    }
}
