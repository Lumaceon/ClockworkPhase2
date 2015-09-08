package lumaceon.mods.clockworkphase2.item;

import lumaceon.mods.clockworkphase2.util.Area;
import lumaceon.mods.clockworkphase2.util.SchematicUtility;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBugSwatter extends ItemClockworkPhase
{
    public static SchematicUtility.Schematic schematic;
    public static boolean go = false;
    public static int x;
    public static int y;
    public static int z;
    public static int index = 0;

    public ItemBugSwatter(int maxStack, int maxDamage, String unlocalizedName) {
        super(maxStack, maxDamage, unlocalizedName);
    }

    @Override
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

    }

    @Override
    public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int meta, float f1, float f2, float f3)
    {
        if(player.isSneaking() && !world.isRemote)
        {
            if(schematic != null)
                schematic = null;
            else
                schematic = SchematicUtility.INSTANCE.loadModSchematic("NewModSchematic", true);
        }
        else
        {
            ItemBugSwatter.x = x;
            ItemBugSwatter.y = y;
            ItemBugSwatter.z = z;
            go = true;
        }

        //if(world.provider.dimensionId == Defaults.DIM_ID.FIRST_AGE)
        //    player.travelToDimension(0);
        //else
        //    player.travelToDimension(Defaults.DIM_ID.FIRST_AGE);
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack bugSwatter, World world, EntityPlayer player)
    {
        if(!world.isRemote)
            SchematicUtility.INSTANCE.createModSchematic(new Area((int) player.posX - 50, (int) player.posY - 50, (int) player.posZ - 50, (int) player.posX + 50, (int) player.posY + 50, (int) player.posZ + 50), (short) 64, "NewModSchematic");
        return bugSwatter;
    }
}
