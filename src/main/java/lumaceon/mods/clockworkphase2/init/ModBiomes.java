package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.lib.Reference;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBiomes
{
    public static BiomeTemporalFallout temporalFallout;

    public static void init()
    {
        temporalFallout = new BiomeTemporalFallout(new Biome.BiomeProperties("Temporal Fallout").setRainfall(0.0F).setTemperature(0.5F).setWaterColor(0x008855).setRainDisabled());
        temporalFallout.setRegistryName(Reference.MOD_ID, "temporal_fallout");
        ForgeRegistries.BIOMES.register(temporalFallout);
    }

    public static class BiomeTemporalFallout extends Biome
    {
        public BiomeTemporalFallout(BiomeProperties properties) {
            super(properties);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public int getSkyColorByTemp(float currentTemperature)
        {
            return 0x002222;
        }
    }
}
