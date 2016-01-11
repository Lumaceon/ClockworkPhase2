package lumaceon.mods.clockworkphase2.timetravel.second.world;

import lumaceon.mods.clockworkphase2.lib.Defaults;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;

public class WorldProviderSecondAge extends WorldProviderSurface
{
    @Override
    public void registerWorldChunkManager() {
        this.dimensionId = Defaults.DIM_ID.SECOND_AGE;
        this.worldChunkMgr = new WorldChunkManager(getSeed(), WorldType.DEFAULT);
    }

    @Override
    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderSecondAge(this.worldObj, getSeed(), false);
    }

    @Override
    public String getDimensionName() {
        return "The 2nd Age";
    }

    @Override
    public String getWelcomeMessage() {
        return "Turning time back to the 2nd Age...";
    }
}
