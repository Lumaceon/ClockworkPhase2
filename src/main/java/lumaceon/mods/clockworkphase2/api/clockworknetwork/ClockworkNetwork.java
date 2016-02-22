package lumaceon.mods.clockworkphase2.api.clockworknetwork;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkTile;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IMainspringTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.HashMap;

public class ClockworkNetwork
{
    public boolean hasLoaded = false;
    protected int currentTension = 0;
    protected int maxTension = 0;

    protected HashMap<Long, IMainspringTile> mainsprings = new HashMap<Long, IMainspringTile>(1);
    protected HashMap<Long, IClockworkNetworkMachine> machines = new HashMap<Long, IClockworkNetworkMachine>(1);
    protected HashMap<Long, IClockworkNetworkTile> additionalTiles = new HashMap<Long, IClockworkNetworkTile>(1);


    public int getCurrentTension() {
        return currentTension;
    }

    /**
     * @return The amount of tension consumed from this network.
     */
    public int consumeTension(int tensionToConsume) {
        int initialTensionToConsume = tensionToConsume;
        Collection<IMainspringTile> mainspringTiles = mainsprings.values();
        for(IMainspringTile mainspring : mainspringTiles)
            if(mainspring != null && tensionToConsume > 0)
                tensionToConsume -= mainspring.consumeTension(tensionToConsume);
        currentTension -= (initialTensionToConsume - tensionToConsume);
        return initialTensionToConsume - tensionToConsume;
    }

    /**
     * @return The amount of tension added to this network.
     */
    public int addTension(int tensionToAdd) {
        int initialTensionToAdd = tensionToAdd;
        Collection<IMainspringTile> mainspringTiles = mainsprings.values();
        for(IMainspringTile mainspring : mainspringTiles)
            if(mainspring != null && tensionToAdd > 0)
                tensionToAdd -= mainspring.addTension(tensionToAdd);
        currentTension += (initialTensionToAdd - tensionToAdd);
        return initialTensionToAdd - tensionToAdd;
    }

    public int getMaxTension() {
        return maxTension;
    }

    /**
     * @return True if added, false if not (usually because it already is a part of this network).
     */
    public boolean addNetworkTile(IClockworkNetworkTile clockworkNetworkTile)
    {
        IClockworkNetworkTile dupe = getTile(clockworkNetworkTile.getUniqueID());
        if(dupe != null) //A tile already exists with that ID but...
        {
            if(dupe.equals(clockworkNetworkTile)) //...is it the same tile we're trying to add now?
                return false; //If it is, just return false and don't add it; no problem here.
            else //...or is it a completely separate block?
            {
                //Keep increasing uniqueID until it actually is unique.
                long uniqueID = System.currentTimeMillis();
                while(getTile(uniqueID) != null)
                    uniqueID++;

                clockworkNetworkTile.setUniqueID(uniqueID); //Give the new tile the unique ID.
            }
        }

        if(clockworkNetworkTile instanceof IClockworkNetworkMachine)
            machines.put(clockworkNetworkTile.getUniqueID(), (IClockworkNetworkMachine) clockworkNetworkTile);
        else if(clockworkNetworkTile instanceof IMainspringTile)
        {
            mainsprings.put(clockworkNetworkTile.getUniqueID(), (IMainspringTile) clockworkNetworkTile);
            currentTension += ((IMainspringTile) clockworkNetworkTile).getTension();
            maxTension += ((IMainspringTile) clockworkNetworkTile).getMaxTension();
        }
        else
            additionalTiles.put(clockworkNetworkTile.getUniqueID(), clockworkNetworkTile);
        clockworkNetworkTile.setClockworkNetwork(this);
        return true;
    }

    public HashMap<Long, IMainspringTile> getMainsprings() { return mainsprings; }
    public HashMap<Long, IClockworkNetworkMachine> getMachines() { return machines; }
    public HashMap<Long, IClockworkNetworkTile> getAdditionalTiles() { return additionalTiles; }

    public IClockworkNetworkTile getTile(long uniqueID)
    {
        IClockworkNetworkTile tile = machines.get(uniqueID);
        if(tile != null)
            return tile;

        tile = mainsprings.get(uniqueID);
        if(tile != null)
            return tile;

        tile = additionalTiles.get(uniqueID);
        return tile;
    }

    /**
     * Joins the tiles network passed in with this one. The parameter network will thus cease to exist in favor of
     * this network, as all of the machines and mainsprings will be reassigned to it.
     * @param clockworkNetwork The tiles network to join this with one.
     */
    public void joinNetworks(ClockworkNetwork clockworkNetwork)
    {
        Collection<IMainspringTile> newNetworkMainsprings = clockworkNetwork.getMainsprings().values();
        Collection<IClockworkNetworkMachine> newNetworkMachines = clockworkNetwork.getMachines().values();
        Collection<IClockworkNetworkTile> newNetworkTiles = clockworkNetwork.getAdditionalTiles().values();

        for(IMainspringTile m : newNetworkMainsprings)
            addNetworkTile(m);

        for(IClockworkNetworkMachine m : newNetworkMachines)
            addNetworkTile(m);

        for(IClockworkNetworkTile tile : newNetworkTiles)
            addNetworkTile(tile);
    }

    /**
     * Used to load a network that should already exist in the world. This is typically called in an update call for a
     * mainspring or controller tile (once per load).
     * @param forceLoad If true, forces the network to load, regardless of whether or not it already has. In general,
     *                  this should almost always be false.
     */
    public void loadNetwork(World world, boolean forceLoad)
    {
        if(!forceLoad && hasLoaded)
            return;
        hasLoaded = true;

        boolean addedSomething = false;
        BlockPos pos;
        IMainspringTile[] springs = mainsprings.values().toArray(new IMainspringTile[mainsprings.size()]);
        for(int n = 0 ; n < springs.length; n++)
        {
            IMainspringTile t = springs[n];
            if(t != null)
            {
                pos = t.getPosition();
                if(checkAndAddTileAtPosition(world, pos.north()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.south()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.east()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.west()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.up()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.down()))
                    addedSomething = true;
            }
        }
        IClockworkNetworkMachine[] machs = machines.values().toArray(new IClockworkNetworkMachine[machines.size()]);
        for(int n = 0 ; n < machs.length; n++)
        {
            IClockworkNetworkMachine t = machs[n];
            if(t != null)
            {
                pos = t.getPosition();
                if(checkAndAddTileAtPosition(world, pos.north()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.south()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.east()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.west()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.up()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.down()))
                    addedSomething = true;
            }
        }
        IClockworkNetworkTile[] tiles = additionalTiles.values().toArray(new IClockworkNetworkTile[additionalTiles.size()]);
        for(int n = 0 ; n < tiles.length; n++)
        {
            IClockworkNetworkTile t = tiles[n];
            if(t != null)
            {
                pos = t.getPosition();
                if(checkAndAddTileAtPosition(world, pos.north()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.south()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.east()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.west()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.up()))
                    addedSomething = true;
                if(checkAndAddTileAtPosition(world, pos.down()))
                    addedSomething = true;
            }
        }

        if(addedSomething)
            loadNetwork(world, true);
    }

    /**
     * Checks for a valid clockwork network tile and adds it to this network if it's valid. Also joins networks if the
     * new tile has one already.
     * @param pos The position to check for the tile entity.
     * @return True if a NEW tile was found and added, false otherwise.
     */
    protected boolean checkAndAddTileAtPosition(World world, BlockPos pos)
    {
        TileEntity tile;
        boolean addedSomething = false;

        tile = world.getTileEntity(pos);
        if(tile != null && tile instanceof IClockworkNetworkTile)
        {
            IClockworkNetworkTile networkTile = (IClockworkNetworkTile) tile;

            if(networkTile.getClockworkNetwork() != null && !(networkTile.getClockworkNetwork().equals(this)))
            {
                joinNetworks(networkTile.getClockworkNetwork()); //Resistance is futile.
                addedSomething = true;
            }

            if(addNetworkTile((IClockworkNetworkTile) tile))
                addedSomething = true;
            return addedSomething;
        }
        return false;
    }
}
