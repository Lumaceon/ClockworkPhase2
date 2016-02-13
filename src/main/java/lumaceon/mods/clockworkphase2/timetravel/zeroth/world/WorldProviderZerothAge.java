package lumaceon.mods.clockworkphase2.timetravel.zeroth.world;

import lumaceon.mods.clockworkphase2.lib.Defaults;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderZerothAge extends WorldProviderSurface
{
    @Override
    public void registerWorldChunkManager()
    {
        this.dimensionId = Defaults.DIM_ID.ZEROTH_AGE;
        this.worldChunkMgr = new WorldChunkManager(getSeed(), WorldType.DEFAULT, "OriginWorld");
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderZerothAge(this.worldObj, getSeed(), false, worldObj.getWorldInfo().getGeneratorOptions());
    }

    @Override
    public String getDimensionName() {
        return "OriginWorld";
    }

    @Override
    public String getWelcomeMessage() {
        return "Escaping reality...";
    }
}
