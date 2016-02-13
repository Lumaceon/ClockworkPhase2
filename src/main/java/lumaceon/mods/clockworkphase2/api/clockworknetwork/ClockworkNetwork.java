package lumaceon.mods.clockworkphase2.api.clockworknetwork;

import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IClockworkNetworkTile;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.IMainspringTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

public class ClockworkNetwork
{
    public boolean hasLoaded = false;
    protected int currentTension = 0;
    protected int maxTension = 0;

    protected ArrayList<IMainspringTile> mainsprings = new ArrayList<IMainspringTile>(1);
    protected ArrayList<IClockworkNetworkMachine> machines = new ArrayList<IClockworkNetworkMachine>(1);
    protected ArrayList<IClockworkNetworkTile> additionalTiles = new ArrayList<IClockworkNetworkTile>(1);


    public int getCurrentTension() {
        return currentTension;
    }

    /**
     * @return The amount of tension consumed from this network.
     */
    public int consumeTension(int tensionToConsume) {
        int initialTensionToConsume = tensionToConsume;
        for(IMainspringTile mainspring : mainsprings)
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
        for(IMainspringTile mainspring : mainsprings)
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
    public boolean addMainspring(IMainspringTile mainspring) {
        for(IMainspringTile m : mainsprings)
            if(m.equals(mainspring))
                return false;
        mainsprings.add(mainspring);
        currentTension += mainspring.getTension();
        maxTension += mainspring.getMaxTension();
        mainspring.setClockworkNetwork(this);
        return true;
    }

    /**
     * @return True if added, false if not (usually because it already is a part of this network).
     */
    public boolean addMachine(IClockworkNetworkMachine clockworkMachine) {
        for(IClockworkNetworkMachine m : machines)
            if(m.equals(clockworkMachine))
                return false;
        machines.add(clockworkMachine);
        clockworkMachine.setClockworkNetwork(this);
        return true;
    }

    /**
     * @return True if added, false if not (usually because it already is a part of this network).
     */
    public boolean addNetworkTile(IClockworkNetworkTile clockworkNetworkTile) {
        for(IClockworkNetworkTile tile : additionalTiles)
            if(tile.equals(clockworkNetworkTile))
                return false;
        additionalTiles.add(clockworkNetworkTile);
        clockworkNetworkTile.setClockworkNetwork(this);
        return true;
    }

    public ArrayList<IMainspringTile> getMainsprings() { return mainsprings; }
    public ArrayList<IClockworkNetworkMachine> getMachines() { return machines; }
    public ArrayList<IClockworkNetworkTile> getAdditionalTiles() { return additionalTiles; }

    /**
     * Joins the tiles network passed in with this one. The parameter network will thus cease to exist in favor of
     * this network, as all of the machines and mainsprings will be reassigned to it.
     * @param clockworkNetwork The tiles network to join this with one.
     */
    public void joinNetworks(ClockworkNetwork clockworkNetwork)
    {
        ArrayList<IMainspringTile> newNetworkMainsprings = clockworkNetwork.getMainsprings();
        ArrayList<IClockworkNetworkMachine> newNetworkMachines = clockworkNetwork.getMachines();
        for(IMainspringTile m : newNetworkMainsprings)
            addMainspring(m);

        for(IClockworkNetworkMachine m : newNetworkMachines)
            addMachine(m);

        for(IClockworkNetworkTile tile : additionalTiles)
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

        boolean addedSomething = false;
        BlockPos pos;
        for(int n = 0 ; n < additionalTiles.size(); n++)
        {
            IClockworkNetworkTile t = additionalTiles.get(n);
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
        for(int n = 0 ; n < mainsprings.size(); n++)
        {
            IMainspringTile t = mainsprings.get(n);
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

        for(int n = 0 ; n < machines.size(); n++)
        {
            IClockworkNetworkMachine t = machines.get(n);
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

        hasLoaded = true;

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
        boolean isMachineOrMainspring = false;
        boolean addedSomething = false;

        tile = world.getTileEntity(pos);
        if(tile != null && tile instanceof IClockworkNetworkTile)
        {
            IClockworkNetworkTile networkTile = (IClockworkNetworkTile) tile;

            if(networkTile.getClockworkNetwork() != null && !networkTile.getClockworkNetwork().equals(this))
            {
                joinNetworks(networkTile.getClockworkNetwork()); //Resistance is futile.
                addedSomething = true;
            }

            if(tile instanceof IClockworkNetworkMachine)
            {
                if(addMachine((IClockworkNetworkMachine) tile))
                    addedSomething = true;
                isMachineOrMainspring = true;
            }
            if(tile instanceof IMainspringTile)
            {
                if(addMainspring((IMainspringTile) tile))
                    addedSomething = true;
                isMachineOrMainspring = true;
            }
            if(!isMachineOrMainspring)
                if(addNetworkTile((IClockworkNetworkTile) tile))
                    addedSomething = true;
            return addedSomething;
        }
        return false;
    }
}
