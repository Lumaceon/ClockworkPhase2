package lumaceon.mods.clockworkphase2.api.clockworknetwork;

import lumaceon.mods.clockworkphase2.api.block.clockwork.IClockworkNetworkMachine;
import lumaceon.mods.clockworkphase2.api.block.clockwork.IMainspringTile;

import java.util.ArrayList;

public class ClockworkNetwork
{
    private ArrayList<IMainspringTile> mainsprings = new ArrayList<IMainspringTile>(2);
    private ArrayList<IClockworkNetworkMachine> machines = new ArrayList<IClockworkNetworkMachine>(5);

    private int currentTension = 0;
    private int maxTension = 0;

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

    public int getMaxTension() {
        return maxTension;
    }

    public void addMainspring(IMainspringTile mainspring) {
        for(IMainspringTile m : mainsprings)
            if(m.equals(mainspring))
                return;
        mainsprings.add(mainspring);
        currentTension += mainspring.getTension();
        maxTension += mainspring.getMaxTension();
        mainspring.setClockworkNetwork(this);
    }

    public void addMachine(IClockworkNetworkMachine clockworkMachine) {
        for(IClockworkNetworkMachine m : machines)
            if(m.equals(clockworkMachine))
                return;
        machines.add(clockworkMachine);
        clockworkMachine.setClockworkNetwork(this);
    }

    public ArrayList<IMainspringTile> getMainsprings() { return mainsprings; }
    public ArrayList<IClockworkNetworkMachine> getMachines() { return machines; }

    public void joinNetworks(ClockworkNetwork clockworkNetwork)
    {
        ArrayList<IMainspringTile> newNetworkMainsprings = clockworkNetwork.getMainsprings();
        ArrayList<IClockworkNetworkMachine> newNetworkMachines = clockworkNetwork.getMachines();
        for(IMainspringTile m : newNetworkMainsprings)
            addMainspring(m);

        for(IClockworkNetworkMachine m : newNetworkMachines)
            addMachine(m);
    }
}
