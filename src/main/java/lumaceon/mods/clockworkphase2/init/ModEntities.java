package lumaceon.mods.clockworkphase2.init;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.entity.EntityDissonantSpecter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.apache.commons.lang3.ArrayUtils;

public class ModEntities
{
    public static final String DISSONANT_SPECTER = "dissonant_specter";
    public static final String PHASIC_SENTINEL = "phasic_sentinel";
    public static final String ETERNAL_GUARDIAN = "eternal_guardian";

    public static void init()
    {
        //These do repeat somewhat; Taiga Hills, for example, shows up at least twice.
        //However, unless I'm mistaken, memory strain is probably minimal, as these should all be references.
        Biome[] biomes0 = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.MESA);
        Biome[] biomes1 = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.FOREST);
        Biome[] biomes2 = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.PLAINS);
        Biome[] biomes3 = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.MOUNTAIN);
        Biome[] biomes4 = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.HILLS);
        Biome[] biomes5 = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.SWAMP);
        Biome[] biomes6 = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.SANDY);
        Biome[] biomes7 = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.SNOWY);
        Biome[] biomes8 = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.WASTELAND);
        Biome[] biomes9 = BiomeDictionary.getBiomesForType(BiomeDictionary.Type.BEACH);
        biomes0 = ArrayUtils.addAll(biomes0, biomes1);
        biomes0 = ArrayUtils.addAll(biomes0, biomes2);
        biomes0 = ArrayUtils.addAll(biomes0, biomes3);
        biomes0 = ArrayUtils.addAll(biomes0, biomes4);
        biomes0 = ArrayUtils.addAll(biomes0, biomes5);
        biomes0 = ArrayUtils.addAll(biomes0, biomes6);
        biomes0 = ArrayUtils.addAll(biomes0, biomes7);
        biomes0 = ArrayUtils.addAll(biomes0, biomes8);
        biomes0 = ArrayUtils.addAll(biomes0, biomes9);

        registerEntity(EntityDissonantSpecter.class, DISSONANT_SPECTER, 0x000000, 0x3333FF);
        //registerEntity(EntityPhasicGuardian.class, PHASIC_SENTINEL, 0xFFFFFF, 0xFFFF00);
        //registerEntity(EntityEternalGuardian.class, ETERNAL_GUARDIAN, 0x000000, 0x33FF33);
        EntityRegistry.addSpawn(EntityDissonantSpecter.class, 100, 1, 3, EnumCreatureType.MONSTER, biomes0);
        //EntityRegistry.addSpawn(EntityPhasicGuardian.class, 100, 1, 1, EnumCreatureType.MONSTER, biomes0);
        //EntityRegistry.addSpawn(EntityEternalGuardian.class, 100, 1, 1, EnumCreatureType.MONSTER, biomes0);
    }

    static int entityID = 0;
    public static void registerEntity(Class<? extends Entity> entityClass, String name, int mainColor, int secondColor)
    {
        EntityRegistry.registerModEntity(entityClass, name, entityID, ClockworkPhase2.instance, 64, 1, true, mainColor, secondColor);
        ++entityID;
    }

    public static void registerEntity(Class<? extends Entity> entityClass, String name)
    {
        int entityID = 0;
        EntityRegistry.registerModEntity(entityClass, name, entityID, ClockworkPhase2.instance, 64, 1, true);
        ++entityID;
    }
}