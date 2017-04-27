package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.api.block.IMultiblockTemplate;
import lumaceon.mods.clockworkphase2.block.multiblocktemplate.MultiblockTemplateCelestialCompass;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageCelestialCompassItemGet;
import lumaceon.mods.clockworkphase2.tile.generic.TileMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileCelestialCompass extends TileMod implements ITickable
{
    private ItemStack[] craftingItems = new ItemStack[9]; //Index 8 for center.

    /**
     * Called during destruction of the multiblock, mostly so that destroyed blocks don't try to destroy the multiblock
     * themselves, and only the first will ever call it.
     */
    public boolean isBeingDestroyed = false;

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);

        for(int i = 0; i < 9; i++)
        {
            ItemStack item = craftingItems[i];
            if(item != null)
            {
                NBTTagCompound tag = new NBTTagCompound();
                item.writeToNBT(tag);
                nbt.setTag("craftingitem_" + i, tag);
            }
        }
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);

        for(int i = 0; i < 9; i++)
            if(nbt.hasKey("craftingitem_" + i))
                craftingItems[i] = ItemStack.loadItemStackFromNBT((NBTTagCompound) nbt.getTag("craftingitem_" + i));
    }

    @Override
    public void update()
    {

    }

    public ItemStack getCraftingItem(int index) {
        return craftingItems[index];
    }

    public void setCraftingItem(ItemStack is, int index) {
        craftingItems[index] = is;
    }

    public boolean onSubBlockClicked(EntityPlayer player, int circleClicked)
    {
        int currentItem = player.inventory.currentItem;
        ItemStack item = player.inventory.getCurrentItem();
        if(circleClicked == -1 || player.isSneaking())
            return false;
        else
        {
            if(item != null && craftingItems[circleClicked] == null)
            {
                ItemStack newItem = item.copy();
                newItem.stackSize = 1;
                setCraftingItem(newItem, circleClicked);
                player.inventory.decrStackSize(currentItem, 1);
                PacketHandler.INSTANCE.sendToAllAround(new MessageCelestialCompassItemGet(newItem, getPos(), (byte) circleClicked), new NetworkRegistry.TargetPoint(worldObj.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 256));
                markDirty();
                return true;
            }
            else if(item == null && craftingItems[circleClicked] != null)
            {
                player.inventory.setInventorySlotContents(currentItem, craftingItems[circleClicked]);
                craftingItems[circleClicked] = null;
                PacketHandler.INSTANCE.sendToAllAround(new MessageCelestialCompassItemGet(null, getPos(), (byte) circleClicked), new NetworkRegistry.TargetPoint(worldObj.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 256));
                markDirty();
                return true;
            }
        }
        return false;
    }

    public static void destroyMultiblock(TileCelestialCompass te, World world, BlockPos pos)
    {
        te.isBeingDestroyed = true;
        world.spawnEntityInWorld(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.multiblockAssembler)));
        world.spawnEntityInWorld(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.multiblockCelestialCompass)));
        ItemStack consBlocks1 = new ItemStack(ModBlocks.constructionBlock);
        consBlocks1.stackSize = 64;
        ItemStack consBlocks2 = new ItemStack(ModBlocks.constructionBlock);
        consBlocks2.stackSize = 32;
        world.spawnEntityInWorld(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), consBlocks1));
        world.spawnEntityInWorld(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), consBlocks2));
        for(int i = 0; i < 9; i++)
        {
            ItemStack stack = te.getCraftingItem(i);
            if(stack != null)
                world.spawnEntityInWorld(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack));
            te.setCraftingItem(null, i);
        }

        IMultiblockTemplate template = MultiblockTemplateCelestialCompass.INSTANCE;
        BlockPos currentPosition;
        for(int i = 0; i <= template.getMaxIndex(); i++)
        {
            IMultiblockTemplate.BlockData data = template.getBlockForIndex(i);
            currentPosition = pos.add(data.getPosition());
            IBlockState state = world.getBlockState(currentPosition);
            if(state != null && state.getBlock() != null && state.getBlock().equals(data.getBlock()))
                world.setBlockToAir(currentPosition);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public net.minecraft.util.math.AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 256D * 256D;
    }
}
