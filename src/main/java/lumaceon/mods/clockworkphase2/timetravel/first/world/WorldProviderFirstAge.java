package lumaceon.mods.clockworkphase2.timetravel.first.world;

import lumaceon.mods.clockworkphase2.lib.Defaults;
import lumaceon.mods.clockworkphase2.timetravel.third.world.ChunkProviderThirdAge;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderFirstAge extends WorldProviderSurface
{
    @Override
    public void registerWorldChunkManager() {
        this.dimensionId = Defaults.DIM_ID.FIRST_AGE;
        this.worldChunkMgr = new WorldChunkManager(getSeed(), WorldType.DEFAULT);
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderThirdAge(this.worldObj, getSeed(), false);
    }

    @Override
    public String getDimensionName() {
        return "The 1st Age";
    }

    @Override
    public String getWelcomeMessage() {
        return "Turning time back to the 1st Age...";
    }
}
