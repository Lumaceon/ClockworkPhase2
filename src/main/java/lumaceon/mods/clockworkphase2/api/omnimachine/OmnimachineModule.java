package lumaceon.mods.clockworkphase2.api.omnimachine;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public abstract class OmnimachineModule
{
    /**
     * Called every tick the omnimachine itself is updated.
     * @param power A unit of storage for power shared by the omnimachine, allowing one to change the values from here.
     * @param moduleData Class containing data for this module; an instance of what getDataStorageClass() returns.
     * @return True if changes to this module (or the omnimachine/power) have occurred, false if nothing changed.
     */
    public abstract boolean doWork(OmnimachineDataStorage power, ModuleData moduleData);

    public abstract String getModuleID();
    public abstract ResourceLocation getBackgroundTexture();
    public abstract ResourceLocation getForegroundTexture(); //If applicable; can be null.

    /**
     * Gets the class that will be instantiated to save the data needed by the module.
     */
    public abstract Class<? extends ModuleData> getDataStorageClass();

    /**
     * This should be extended to include the data your module requires. Most commonly, an inventory and timer.
     */
    public static abstract class ModuleData
    {
        public ModuleData() {}

        public abstract NBTTagCompound writeToNBT(NBTTagCompound nbt);
        public abstract void readFromNBT(NBTTagCompound nbt);
    }
}