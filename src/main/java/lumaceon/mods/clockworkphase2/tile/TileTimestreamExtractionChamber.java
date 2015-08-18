package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.api.crafting.timestream.ITimestreamCraftingRecipe;
import lumaceon.mods.clockworkphase2.api.crafting.timestream.TimestreamCraftingRegistry;
import lumaceon.mods.clockworkphase2.api.time.ITimezone;
import lumaceon.mods.clockworkphase2.tile.generic.TileTemporal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;

public class TileTimestreamExtractionChamber extends TileTemporal
{
    public ITimestreamCraftingRecipe currentRecipe;

    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        if(currentRecipe != null)
            nbt.setString("recipe", currentRecipe.getUnlocalizedName());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        if(nbt.hasKey("recipe"))
            this.currentRecipe = TimestreamCraftingRegistry.getRecipe(nbt.getString("recipe"));
    }

    @Override
    public void setState(int state) {}
    @Override
    public void setStateAndUpdate(int state) {}

    @Override
    public void updateEntity()
    {
        ITimezone timezone = getTimezone();
        if(currentRecipe != null && timezone != null)
        {
            if(currentRecipe.getTimeSandRequirement() <= timezone.getTimeSand())
            {
                if(currentRecipe.finalize(worldObj, xCoord, yCoord, zCoord))
                {
                    if(!worldObj.isRemote)
                        worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord + 1, zCoord, currentRecipe.getCraftingResult(worldObj, xCoord, yCoord, zCoord)));
                    currentRecipe = null;
                }
                else if(currentRecipe.updateRecipe(worldObj, xCoord, yCoord, zCoord))
                {
                    //TODO Fancies.
                }
            }
        }
    }

    public void setRecipe(ITimestreamCraftingRecipe recipe) {
        currentRecipe = recipe;
    }

    public boolean isReady() {
        return true;
    }
}
