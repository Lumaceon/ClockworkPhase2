package lumaceon.mods.clockworkphase2.tile;

import lumaceon.mods.clockworkphase2.api.crafting.timestream.ITimestreamCraftingRecipe;
import lumaceon.mods.clockworkphase2.api.crafting.timestream.TimestreamCraftingRegistry;
import lumaceon.mods.clockworkphase2.tile.generic.TileClockworkPhase;
import lumaceon.mods.clockworkphase2.util.Logger;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileTimestreamExtractionChamber extends TileClockworkPhase
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
        if(currentRecipe != null)
        {
            if(currentRecipe.finalize(worldObj, xCoord, yCoord, zCoord))
            {
                worldObj.spawnEntityInWorld(new EntityItem(worldObj, xCoord, yCoord + 1, zCoord, currentRecipe.getCraftingResult(worldObj, xCoord, yCoord, zCoord)));
                currentRecipe = null;
            }
            else if(currentRecipe.updateRecipe(worldObj, xCoord, yCoord, zCoord))
            {
                Logger.info("Recipe update");
                //TODO Fancy stuff.
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
