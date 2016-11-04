package lumaceon.mods.clockworkphase2.api.omnimachine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for storing values related to the omnimachine. By default, this is tension and time energy, but modules can
 * add additional storage classes for their own data.
 */
public class OmnimachineDataStorage
{
    public HashMap<String, OmnimachineModule.ModuleData> moduleData = new HashMap<String, OmnimachineModule.ModuleData>();

    public int maxTension;
    public int currentTension;
    public long maxTime;
    public long currentTime;

    public OmnimachineDataStorage(int maxTension, long maxTime)
    {
        this.maxTension = maxTension;
        this.currentTension = 0;
        this.maxTime = maxTime;
        this.currentTime = 0L;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setInteger("maxTension", maxTension);
        nbt.setInteger("currentTension", currentTension);
        nbt.setLong("maxTime", maxTime);
        nbt.setLong("currentTime", currentTime);

        if(moduleData != null && !moduleData.isEmpty())
        {
            NBTTagList list = new NBTTagList();
            for(Map.Entry<String, OmnimachineModule.ModuleData> entry : moduleData.entrySet())
            {
                if(entry != null)
                {
                    OmnimachineModule.ModuleData data = entry.getValue();
                    String key = entry.getKey();
                    if(data == null || key == null)
                        continue;

                    NBTTagCompound dataNBT = new NBTTagCompound();
                    data.writeToNBT(dataNBT);
                    dataNBT.setString("CP2_module_id", key);
                    list.appendTag(dataNBT);
                }
            }
            nbt.setTag("module_data", list);
        }

        return nbt;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        this.maxTension = nbt.getInteger("maxTension");
        this.currentTension = nbt.getInteger("currentTension");
        this.maxTime = nbt.getLong("maxTime");
        this.currentTime = nbt.getLong("currentTime");

        if(nbt.hasKey("module_data"))
        {
            NBTTagList list = nbt.getTagList("module_data", 10);
            if(list != null)
            {
                for(int i = 0; i < list.tagCount(); i++)
                {
                    NBTTagCompound dataTag = (NBTTagCompound) list.get(i);
                    if(dataTag == null) continue;

                    String id = dataTag.getString("CP2_module_id");
                    if(id == null) continue;

                    OmnimachineModule module = OmnimachineModuleRegistry.getModule(id);
                    if(module == null) continue;

                    Class dataClass = module.getDataStorageClass();
                    try {
                        OmnimachineModule.ModuleData data = (OmnimachineModule.ModuleData) dataClass.newInstance();
                        data.readFromNBT(dataTag);
                        this.moduleData.put(id, data);
                    } catch (InstantiationException e) {
                        System.out.println("[ClockworkPhase2] Trouble instantiating ModuleData class for omnimachine module '" + id + "'.");
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        System.out.println("[ClockworkPhase2] Trouble instantiating ModuleData class for omnimachine module '" + id + "'; is the default constructor public?");
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
