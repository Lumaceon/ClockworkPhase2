package lumaceon.mods.clockworkphase2.recipe.timestream;

import lumaceon.mods.clockworkphase2.api.crafting.timestream.TimestreamCraftingRecipe;
import lumaceon.mods.clockworkphase2.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class TimestreamRecipeLightning extends TimestreamCraftingRecipe
{
    public TimestreamRecipeLightning(String unlocalizedName, ResourceLocation icon, ResourceLocation background, ItemStack result) {
        super(unlocalizedName, icon, background, result);
    }

    @Override
    public boolean updateRecipe(World world, int x, int y, int z)
    {
        if(world.getBlock(x, y + 1, z).equals(ModBlocks.lightningRod))
        {
            boolean chainBroken = false;
            int highestLightningRod = y + 1;
            int lightningRods = 1;
            for(int n = y + 2; n < 255; n++)
            {
                Block block = world.getBlock(x, n, z);
                if(!chainBroken && block != null && block.equals(ModBlocks.lightningRod))
                {
                    highestLightningRod = n;
                    lightningRods++;
                }
                else if(block == null || !block.isAir(world, x, y, z))
                    return false;
                else
                    chainBroken = true;
            }
            if(world.getWorldTime() % 5 == 0 && world.rand.nextInt(10000 / lightningRods) == 0)
                world.spawnEntityInWorld(new EntityLightningBolt(world, x, highestLightningRod + 1, z));
            return true;
        }
        return false;
    }

    @Override
    public boolean finalize(World world, int x, int y, int z) {
        return false;
    }
}
