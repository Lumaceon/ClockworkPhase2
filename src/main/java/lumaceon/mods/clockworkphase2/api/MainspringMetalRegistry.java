package lumaceon.mods.clockworkphase2.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class MainspringMetalRegistry
{
    private static ArrayList<MainspringMetal> METALS = new ArrayList<MainspringMetal>();

    public static int metalTensionMultiplier = 8;

    /**
     * Registers a material, allowing it to be added to a mainspring to increase tension.
     * @param metal A stack representing the material to check for.
     * @param baseValue The base value of this material, which will be multiplied by metalTensionMultiplier.
     */
    public static void registerMetal(ItemStack metal, int baseValue) {
        MainspringMetalRegistry.METALS.add(new MainspringMetal(metal, baseValue * metalTensionMultiplier));
    }

    /**
     * Registers a material from an oreDictionary name, allowing it to be added to a mainspring to increase tension.
     * @param metalName The oreDictionary name for the material, such as ingotIron or blockDiamond.
     * @param baseValue The base value of this material, which will be multiplied by metalTensionMultiplier.
     */
    public static void registerMetal(String metalName, int baseValue) {
        MainspringMetalRegistry.METALS.add(new MainspringMetal(metalName, baseValue * metalTensionMultiplier));
    }

    /**
     * Gets the mainspring metal value of the given itemstack.
     * @param is The itemstack to check.
     * @return The value of the mainspring metal, or 0 if not valid.
     */
    public static int getValue(ItemStack is)
    {
        if(is == null)
            return 0;

        List<ItemStack> ores;
        for(MainspringMetal metal : METALS)
        {
            if(metal.metal != null)
            {
                if(is.equals(metal.metal))
                    return metal.metalValue * is.getCount();
            }

            if(metal.metalName != null)
            {
                ores = OreDictionary.getOres(metal.metalName);
                for(ItemStack item : ores)
                {
                    if(OreDictionary.itemMatches(item, is, false))
                        return metal.metalValue * is.getCount();
                }
            }
        }
        return 0;
    }

    public static class INTERNAL
    {
        //These default values are registered during the FMLPostInitializationEvent load phase.
        //If you wish to change these, do so during your mod's FMLInitializationEvent phase.
        public static int tin = 80;
        public static int aluminium = 90;
        public static int gold = 80;
        public static int iron = 120;
        public static int copper = 120;
        public static int zinc = 80;
        public static int nickel = 130; //Commonly known as "Ferrous" (Thermal Expansion).
        public static int lead = 150;
        public static int silver = 160;
        public static int aluminiumBrass = 180;
        public static int thaumium = 200;
        public static int bronze = 250;
        public static int brass = 260;
        public static int cobalt = 250;
        public static int ardite = 250;
        public static int alumite = 310;
        public static int invar = 320;
        public static int electrum = 340;
        public static int steel = 400;
        public static int temporal = 500;
        public static int manyullyn = 650;
        public static void initDefaults()
        {
            MainspringMetalRegistry.registerMetal("ingotTin", tin);
            MainspringMetalRegistry.registerMetal("blockTin", tin * 9);
            MainspringMetalRegistry.registerMetal("ingotAluminum", aluminium);
            MainspringMetalRegistry.registerMetal("blockAluminum", aluminium * 9);
            MainspringMetalRegistry.registerMetal("ingotGold", gold);
            MainspringMetalRegistry.registerMetal("blockGold", gold * 9);
            MainspringMetalRegistry.registerMetal("ingotIron", iron);
            MainspringMetalRegistry.registerMetal("blockIron", iron * 9);
            MainspringMetalRegistry.registerMetal("ingotCopper", copper);
            MainspringMetalRegistry.registerMetal("blockCopper", copper * 9);
            MainspringMetalRegistry.registerMetal("ingotZinc", zinc);
            MainspringMetalRegistry.registerMetal("blockZinc", zinc * 9);
            MainspringMetalRegistry.registerMetal("ingotNickel", nickel);
            MainspringMetalRegistry.registerMetal("blockNickel", nickel * 9);
            MainspringMetalRegistry.registerMetal("ingotLead", lead);
            MainspringMetalRegistry.registerMetal("blockLead", lead * 9);
            MainspringMetalRegistry.registerMetal("ingotSilver", silver);
            MainspringMetalRegistry.registerMetal("blockSilver", silver * 9);
            MainspringMetalRegistry.registerMetal("ingotAluminiumBrass", aluminiumBrass);
            MainspringMetalRegistry.registerMetal("blockAluminiumBrass", aluminiumBrass * 9);
            MainspringMetalRegistry.registerMetal("ingotThaumium", thaumium);
            MainspringMetalRegistry.registerMetal("ingotBronze", bronze);
            MainspringMetalRegistry.registerMetal("blockBronze", bronze * 9);
            MainspringMetalRegistry.registerMetal("ingotBrass", brass);
            MainspringMetalRegistry.registerMetal("blockBrass", brass * 9);
            MainspringMetalRegistry.registerMetal("ingotCobalt", cobalt);
            MainspringMetalRegistry.registerMetal("blockCobalt", cobalt * 9);
            MainspringMetalRegistry.registerMetal("ingotArdite", ardite);
            MainspringMetalRegistry.registerMetal("blockArdite", ardite * 9);
            MainspringMetalRegistry.registerMetal("ingotAlumite", alumite);
            MainspringMetalRegistry.registerMetal("blockAlumite", alumite * 9);
            MainspringMetalRegistry.registerMetal("ingotInvar", invar);
            MainspringMetalRegistry.registerMetal("blockInvar", invar * 9);
            MainspringMetalRegistry.registerMetal("ingotElectrum", electrum);
            MainspringMetalRegistry.registerMetal("blockElectrum", electrum * 9);
            MainspringMetalRegistry.registerMetal("ingotSteel", steel);
            MainspringMetalRegistry.registerMetal("blockSteel", steel * 9);
            MainspringMetalRegistry.registerMetal("ingotTemporal", temporal);
            MainspringMetalRegistry.registerMetal("blockTemporal", temporal * 9);
            MainspringMetalRegistry.registerMetal("ingotManyullyn", manyullyn);
            MainspringMetalRegistry.registerMetal("blockManyullyn", manyullyn * 9);
        }
    }

    public static class MainspringMetal
    {
        public ItemStack metal = null;
        public String metalName = null;
        public int metalValue;

        public MainspringMetal(String metalName, int metalValue)
        {
            this.metalName = metalName;
            this.metalValue = metalValue;
        }

        public MainspringMetal(ItemStack metal, int metalValue)
        {
            this.metal = metal;
            this.metalValue = metalValue;
        }
    }
}
