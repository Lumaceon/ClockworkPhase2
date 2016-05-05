package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.api.time.timezone.TileTimezoneModulator;
import lumaceon.mods.clockworkphase2.api.time.timezone.TimezoneModulation;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.tile.TileTimezoneController;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemBugSwatter extends ItemClockworkPhase
{
    //public static SchematicUtility.Schematic schematic;
    public static boolean go = false;
    public static int x;
    public static int y;
    public static int z;
    public static int index = 0;

    public ItemBugSwatter(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    /*@Override
    public void onUpdate(ItemStack is, World world, Entity owner, int p_77663_4_, boolean p_77663_5_)
    {
        if(!world.isRemote && schematic != null && go)
        {
            if(index >= schematic.areaBlockCount)
            {
                index = 0;
                go = false;
                return;
            }

            int blocksPerSecond = 300;
            int blocksDone = 0;
            int innerIndex = 0;
            boolean exit = false;
            for(short schematicY = 0; schematicY < schematic.height && !exit; schematicY++)
                for(short schematicZ = 0; schematicZ < schematic.length && !exit; schematicZ++)
                    for(short schematicX = 0; schematicX < schematic.width && !exit; schematicX++)
                    {
                        if(innerIndex == index)
                        {
                            Block block = schematic.getBlock(schematicX, schematicY, schematicZ);
                            if(block != null)
                            {
                                if (block.isAir(world, x + schematicX, y + schematicY, z + schematicZ))
                                    world.setBlockToAir(x + schematicX, y + schematicY, z + schematicZ);
                                else {
                                    world.setBlockToAir(x + schematicX, y + schematicY, z + schematicZ);
                                    world.setBlock(x + schematicX, y + schematicY, z + schematicZ, block, schematic.data[index], 3);
                                }
                            }
                            ++blocksDone;
                            ++innerIndex;
                            if(blocksDone < blocksPerSecond)
                                ++index;
                            else
                                exit = true;
                        }
                        else
                            ++innerIndex;
                    }
            ++index;
        }

    }*/

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        if(playerIn.isSneaking() == worldIn.isRemote)
        {
            if(worldIn.getBlockState(pos).getBlock().equals(ModBlocks.timezoneModulator.getBlock()))
            {
                TileEntity te = worldIn.getTileEntity(pos);
                if(te != null && te instanceof TileTimezoneModulator && ((TileTimezoneModulator) te).timezoneModulatorStack != null)
                {
                    playerIn.addChatComponentMessage(new ChatComponentText("CURRENT MODULATION ITEM: " + ((TileTimezoneModulator) te).timezoneModulatorStack.getDisplayName()));
                }
            }
            if(worldIn.getBlockState(pos).getBlock().equals(ModBlocks.timezoneController.getBlock()))
            {
                TileEntity te = worldIn.getTileEntity(pos);
                if(te != null && te instanceof TileTimezoneController)
                {
                    TileTimezoneController tzc = (TileTimezoneController) te;
                    playerIn.addChatComponentMessage(new ChatComponentText("TIMEZONE ACTIVE: " + Boolean.toString(tzc.getTimezone() != null)));
                    playerIn.addChatComponentMessage(new ChatComponentText("MODULATION LIST"));
                    playerIn.addChatComponentMessage(new ChatComponentText("---------------"));
                    if(tzc.getTimezone() != null)
                    {
                        for(TimezoneModulation mod : tzc.getTimezone().getTimezoneModulations())
                            if(mod != null)
                                playerIn.addChatComponentMessage(new ChatComponentText(mod.toString()));
                    }
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack bugSwatter, World world, EntityPlayer player)
    {
        //if(!world.isRemote)
        //    SchematicUtility.INSTANCE.createModSchematic(world, new Area((int) player.posX - 50, (int) player.posY - 50, (int) player.posZ - 50, (int) player.posX + 50, (int) player.posY + 50, (int) player.posZ + 50), (short) 64, "NewModSchematic");
        return bugSwatter;
    }
}
