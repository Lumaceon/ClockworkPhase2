package lumaceon.mods.clockworkphase2.api;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

public class TemporalHarvestRegistry
{
    public static class ORE
    {
        private static ArrayList<Block> validOres = new ArrayList<Block>();

        public static void registerValidOre(Block block)
        {
            validOres.add(block);
        }

        public static boolean isValid(Block block)
        {
            for(Block blk : validOres)
            {
                if(blk.equals(block))
                    return true;
            }
            return false;
        }
    }

    public static class WOOD
    {
        private static ArrayList<Block> validLogs = new ArrayList<Block>();

        public static void registerValidLog(Block block)
        {
            validLogs.add(block);
        }

        public static boolean isValid(Block block)
        {
            for(Block blk : validLogs)
            {
                if(blk.equals(block))
                    return true;
            }
            return false;
        }
    }

    public static class SHOVELERS
    {
        private static ArrayList<Block> validShovelers = new ArrayList<Block>();

        public static void registerValidShoveler(Block block)
        {
            validShovelers.add(block);
        }

        public static boolean isValid(Block block)
        {
            for(Block blk : validShovelers)
            {
                if(blk.equals(block))
                    return true;
            }
            return false;
        }
    }

    public static void init()
    {
        String[] oreNames = OreDictionary.getOreNames();
        ArrayList<ItemStack> ores;

        for(String name : oreNames)
        {
            if(name.contains("ore"))
            {
                ores = OreDictionary.getOres(name);
                for(ItemStack ore : ores)
                {
                    if(Block.getBlockFromItem(ore.getItem()) != null)
                    {
                        ORE.registerValidOre(Block.getBlockFromItem(ore.getItem()));
                    }
                }
            }

            if(name.contains("logWood"))
            {
                ores = OreDictionary.getOres(name);
                for(ItemStack ore : ores)
                {
                    if(Block.getBlockFromItem(ore.getItem()) != null)
                    {
                        WOOD.registerValidLog(Block.getBlockFromItem(ore.getItem()));
                    }
                }
            }

            if(name.contains("gravel") || name.contains("sand") || name.contains("grass") || name.contains("dirt"))
            {
                ores = OreDictionary.getOres(name);
                for(ItemStack ore : ores)
                {
                    if(Block.getBlockFromItem(ore.getItem()) != null)
                    {
                        SHOVELERS.registerValidShoveler(Block.getBlockFromItem(ore.getItem()));
                    }
                }
            }
        }
    }
}
