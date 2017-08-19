package lumaceon.mods.clockworkphase2.handler;

import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.init.ModBiomes;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import lumaceon.mods.clockworkphase2.world.deadzone.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.structure.*;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class WorldGenHandler
{
    public int deathCircleRadius = 350;
    public Block deathCircleFloorBlock = ModBlocks.ruinedLand;
    public Block deathCircleWaterReplacement = Blocks.OBSIDIAN;

    /**
     * Replaces old map generators with gens that do the exact thing, but only if outside our borders.
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onMapGenInitialization(InitMapGenEvent event)
    {
        if(event.getType().equals(InitMapGenEvent.EventType.NETHER_BRIDGE) || event.getType().equals(InitMapGenEvent.EventType.NETHER_CAVE))
            return;

        if(event.getType().equals(InitMapGenEvent.EventType.CAVE))
        {
            event.setNewGen(new MapGenDeadzone(event.getOriginalGen()));
        }
        else if(event.getType().equals(InitMapGenEvent.EventType.STRONGHOLD))
        {
            event.setNewGen(new MapGenStrongholdDeadzone((MapGenStronghold) event.getOriginalGen()));
        }
        else if(event.getType().equals(InitMapGenEvent.EventType.VILLAGE))
        {
            event.setNewGen(new MapGenVillageDeadzone((MapGenVillage) event.getOriginalGen()));
        }
        else if(event.getType().equals(InitMapGenEvent.EventType.MINESHAFT))
        {
            event.setNewGen(new MapGenMineshaftDeadzone((MapGenMineshaft) event.getOriginalGen()));
        }
        else if(event.getType().equals(InitMapGenEvent.EventType.SCATTERED_FEATURE))
        {
            event.setNewGen(new MapGenScatteredFeatureDeadzone((MapGenScatteredFeature) event.getOriginalGen()));
        }
        else if(event.getType().equals(InitMapGenEvent.EventType.RAVINE))
        {
            event.setNewGen(new MapGenDeadzone(event.getOriginalGen()));
        }
        else if(event.getType().equals(InitMapGenEvent.EventType.OCEAN_MONUMENT))
        {
            event.setNewGen(new MapGenOceanMonumentDeadzone((StructureOceanMonument) event.getOriginalGen()));
        }
        else
        {
            event.setNewGen(new MapGenDeadzone(event.getOriginalGen()));
        }
    }

    @SubscribeEvent
    public void onOreGeneration(OreGenEvent.GenerateMinable event)
    {
        if(event.getWorld().provider.getDimension() == 0)
        {
            int x = event.getPos().getX();
            int z = event.getPos().getZ();
            if(Math.sqrt((x*x) + (z*z)) < 378)
            {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onBiomeDecoration(DecorateBiomeEvent.Decorate event)
    {
        if(event.getWorld().provider.getDimension() == 0)
        {
            int x = event.getPos().getX();
            int z = event.getPos().getZ();
            if(Math.sqrt((x*x) + (z*z)) < 378)
            {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onBiomePopulate(PopulateChunkEvent.Populate event)
    {
        if(event.getWorld().provider.getDimension() == 0)
        {
            int chunkX = event.getChunkX();
            int chunkZ = event.getChunkZ();
            if(Math.sqrt(((chunkX*16)*(chunkX*16)) + ((chunkZ*16)*(chunkZ*16))) < 378)
            {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void afterBiomePopulate(PopulateChunkEvent.Post event)
    {
        if(event.getWorld().provider.getDimension() == 0)
        {
            if(ConfigValues.RAGNAROK_MODE)
            {
                Chunk c = event.getWorld().getChunkFromChunkCoords(event.getChunkX(), event.getChunkZ());
                ChunkPos cPos = c.getPos();
                /*if(cPos.x >= 0 && cPos.x < 20 && cPos.z >= 0 && cPos.z < 20)
                {
                    SchematicChunk.ModSchematicChunk chunk = SchematicHandler.INSTANCE.loadModSchematic("Ragnarok_castle", false, cPos.x, cPos.z);
                    ExtendedBlockStorage[] storages = c.getBlockStorageArray();
                    for(int stack = 0; stack < 16; stack++)
                    {
                        ExtendedBlockStorage storage = storages[stack];
                        if(storage == null)
                        {
                            storage = new ExtendedBlockStorage(stack * 16, event.getWorld().provider.hasSkyLight());
                            storages[stack] = storage;
                        }
                        for(int x = 0; x < 16; x++)
                        {
                            for(int z = 0; z < 16; z++)
                            {
                                for(int y = 0; y < 16; y++)
                                {
                                    if(chunk.height < stack*16 + y)
                                        break;

                                    Block blok = chunk.getBlock(x, stack*16 + y, z);
                                    if(blok == null)
                                    {
                                        blok = Blocks.AIR;
                                    }

                                    storage.set(x, y, z, blok.getStateFromMeta(chunk.getMetadata(x, stack*16 + y, z)));
                                }
                            }
                        }
                    }
                    c.resetRelightChecks();
                    c.generateSkylightMap();
                    c.setModified(true);
                }*/

                if((cPos.getXStart() <= deathCircleRadius && cPos.getXEnd() >= -deathCircleRadius) && (cPos.getZStart() <= deathCircleRadius && cPos.getZEnd() >= -deathCircleRadius))
                {
                    ExtendedBlockStorage[] storages = c.getBlockStorageArray();
                    int worldX;
                    int worldZ;
                    int height;

                    int[][] heightMap = new int[16][16];

                    for(int x = 0; x < 16; x++)
                    {
                        for(int z = 0; z < 16; z++)
                        {
                            worldX = cPos.getXStart() + x;
                            worldZ = cPos.getZStart() + z;
                            height = getHeightForWorldCoordinate(worldX, worldZ);
                            heightMap[x][z] = height;
                        }
                    }

                    //Actually make the changes.
                    for(int stack = 0; stack < 16; stack++)
                    {
                        ExtendedBlockStorage storage = storages[stack];
                        if(storage == null)
                        {
                            storage = new ExtendedBlockStorage(stack * 16, event.getWorld().provider.hasSkyLight());
                            storages[stack] = storage;
                        }
                        for(int x = 0; x < 16; x++)
                        {
                            for(int z = 0; z < 16; z++)
                            {
                                if(heightMap[x][z] == -1)
                                    continue; //If the height is -1, completely ignore the column

                                setBiome(x, z, ModBiomes.temporalFallout, c);

                                for(int y = 0; y < 16; y++)
                                {
                                    int actualHeight = stack*16 + y;
                                    if(actualHeight < heightMap[x][z])
                                    {
                                        if(actualHeight < 6) //Set the ground below height 20 to floor material.
                                        {
                                            if(actualHeight < 2) //Set the ground below height 2 to bedrock.
                                            {
                                                storage.set(x, y, z, Blocks.BEDROCK.getDefaultState());
                                            }
                                            else
                                            {
                                                storage.set(x, y, z, deathCircleFloorBlock.getDefaultState());
                                            }
                                        }

                                        if(heightMap[x][z] < 68)
                                        {
                                            //Kill the liquid blocks near the perimeter, if any.
                                            IBlockState blockState = storage.get(x, y, z);
                                            if(blockState != null && blockState.getBlock() instanceof BlockLiquid)
                                            {
                                                storage.set(x, y, z, deathCircleWaterReplacement.getDefaultState());
                                            }
                                        }
                                    }
                                    else
                                    {
                                        storage.set(x, y, z, Blocks.AIR.getDefaultState());
                                    }
                                }

                                setBiome(x, z, ModBiomes.temporalFallout, c);
                            }
                        }
                    }

                    c.resetRelightChecks();
                    c.generateSkylightMap();
                    c.setModified(true);
                    List<Entity> entities = event.getWorld().getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(cPos.getXStart(), 0, cPos.getZStart(), cPos.getXEnd(), 256, cPos.getZEnd()));
                    for(Entity e : entities)
                    {
                        if(e instanceof EntityPlayer)
                            continue;
                        e.setDead();
                    }
                }
            }
        }
    }

    void setBiome(int x, int z, Biome biome, Chunk chunk)
    {
        byte[] biomes = chunk.getBiomeArray();
        biomes[z*16 + x] = (byte) Biome.getIdForBiome(biome);
    }

    int getHeightForWorldCoordinate(int x, int z)
    {
        if(x > deathCircleRadius || x < -deathCircleRadius || z > deathCircleRadius || z < -deathCircleRadius)
        {
            //Coordinates are outside of our intended range, so return -1.
            return -1;
        }

        double distance = Math.sqrt(x*x + z*z);
        if(distance < 250)
        {
            return 5;
        }
        else if(distance > 350)
        {
            return -1;
        }
        else
        {
            return (int)((distance - 250) + 5);
        }
    }
}
