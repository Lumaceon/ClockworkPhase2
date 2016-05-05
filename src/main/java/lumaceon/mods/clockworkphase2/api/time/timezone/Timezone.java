package lumaceon.mods.clockworkphase2.api.time.timezone;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a timezone, which is an area of space-time manipulated by a timezone provider. Functionally,
 * time is stored within an ITimezoneProvider and effects are determined by the timezone modulators.
 */
public class Timezone
{
    public World world;

    protected ITimezoneProvider timezoneProvider;
    protected ArrayList<TimezoneModulation> timezoneModulations = new ArrayList<TimezoneModulation>();

    public Timezone(World world, ITimezoneProvider provider) {
        this.world = world;
        this.timezoneProvider = provider;
    }

    public void writeToNBT(NBTTagCompound nbt)
    {
        if(timezoneModulations != null && !timezoneModulations.isEmpty())
        {
            NBTTagList list = new NBTTagList();
            for(TimezoneModulation modulation : timezoneModulations)
            {
                if(modulation != null && modulation.getTile() != null && modulation.getTile().timezoneModulatorStack != null)
                {
                    NBTTagCompound modTag = new NBTTagCompound();
                    NBTTagCompound innerModulationTag = new NBTTagCompound();
                    NBTTagCompound innerItemTag = new NBTTagCompound();
                    BlockPos pos = modulation.getTile().getPos();

                    modulation.writeToNBT(innerModulationTag);
                    modulation.getTile().timezoneModulatorStack.writeToNBT(innerItemTag);

                    modTag.setTag("custom_data", innerModulationTag);
                    modTag.setTag("item", innerItemTag);
                    modTag.setInteger("x", pos.getX());
                    modTag.setInteger("y", pos.getY());
                    modTag.setInteger("z", pos.getZ());

                    list.appendTag(modTag);
                }
            }
            nbt.setTag("modulations", list);
        }
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
        if(nbt.hasKey("modulations"))
        {
            NBTTagList list = (NBTTagList) nbt.getTag("modulations");
            for(int n = 0; n < list.tagCount(); n++) //For every tag in the list.
            {
                NBTTagCompound tag = list.getCompoundTagAt(n);
                if(tag != null && tag.hasKey("custom_data") && tag.hasKey("item") && tag.hasKey("x"))
                {
                    ItemStack item = ItemStack.loadItemStackFromNBT((NBTTagCompound) tag.getTag("item"));
                    if(item != null && world != null && item.getItem() instanceof ITimezoneModulationItem)
                    {
                        ITimezoneModulationItem modItem = (ITimezoneModulationItem) item.getItem();
                        TileEntity te = world.getTileEntity(new BlockPos(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z")));
                        if(te != null && te instanceof TileTimezoneModulator)
                        {
                            TimezoneModulation mod = modItem.createTimezoneModulation(item, world, (TileTimezoneModulator) te);
                            mod.readFromNBT((NBTTagCompound) tag.getTag("custom_data"));
                            timezoneModulations.add(mod);
                        }
                    }
                }
            }
        }
    }

    public void onUpdate()
    {
        for(TimezoneModulation timezoneModulation : timezoneModulations)
            if(timezoneModulation != null)
                timezoneModulation.onUpdate(timezoneModulation.getTile().timezoneModulatorStack, timezoneProvider);
    }

    public ITimezoneProvider getTimezoneProvider() {
        return timezoneProvider;
    }

    public void addTimezoneModulation(TimezoneModulation modulation) {
        timezoneModulations.add(modulation);
    }

    public void removeTimezoneModulation(TimezoneModulation modulation) {
        timezoneModulations.remove(modulation);
    }

    /**
     * @param mod The class of the modulation to search for.
     * @return The first instance of the given modulation type, or null if none are found.
     */
    public TimezoneModulation getTimezoneModulation(Class<? extends TimezoneModulation> mod)
    {
        for(TimezoneModulation modulation : timezoneModulations)
            if(modulation.getClass().equals(mod))
                return modulation;
        return null;
    }

    /**
     * @param mod The class of the modulations to search for.
     * @return A list containing all instances of the given modulation type, or null if none are found.
     */
    public List<TimezoneModulation> getTimezoneModulations(Class<? extends TimezoneModulation> mod)
    {
        ArrayList<TimezoneModulation> modulations = new ArrayList<TimezoneModulation>();

        for(TimezoneModulation modulation : timezoneModulations)
            if(modulation.getClass().equals(mod))
                modulations.add(modulation);

        if(!modulations.isEmpty())
            return modulations;

        return null;
    }

    /**
     * @return A list containing all of the modulations effecting this timezone.
     */
    public List<TimezoneModulation> getTimezoneModulations() {
        return timezoneModulations;
    }

    public int getMaxTime() {
        if(this.timezoneProvider == null)
            return 0;
        return this.timezoneProvider.getMaxTime();
    }

    public int getTime() {
        if(this.timezoneProvider == null)
            return 0;
        return this.timezoneProvider.getTime();
    }

    /**
     * Adds time to this timezone.
     * @param time Amount of time to add to this timezone.
     * @return The amount of time successfully added.
     */
    public int addTime(int time) {
        if(this.timezoneProvider == null)
            return 0;
        return this.timezoneProvider.addTime(time);
    }

    /**
     * Consumes time from this timezone.
     * @param time Amount of time to remove from this timezone.
     * @return The amount of time successfully consumed.
     */
    public int consumeTime(int time) {
        if(this.timezoneProvider == null)
            return 0;
        return this.timezoneProvider.consumeTime(time);
    }
}
