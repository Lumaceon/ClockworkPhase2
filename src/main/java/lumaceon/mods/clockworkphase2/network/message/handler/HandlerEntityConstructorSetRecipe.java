package lumaceon.mods.clockworkphase2.network.message.handler;

import lumaceon.mods.clockworkphase2.network.message.MessageEntityConstructorSetRecipe;
import lumaceon.mods.clockworkphase2.recipe.EntityConstructionRecipes;
import lumaceon.mods.clockworkphase2.tile.machine.lifeform.TileLifeformConstructor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.relauncher.Side;

public class HandlerEntityConstructorSetRecipe implements IMessageHandler<MessageEntityConstructorSetRecipe, IMessage>
{
    @Override
    public IMessage onMessage(final MessageEntityConstructorSetRecipe message, final net.minecraftforge.fml.common.network.simpleimpl.MessageContext ctx)
    {
        if(ctx.side != Side.SERVER)
        {
            System.err.println("MessageEntityConstructorSetRecipe received on wrong side:" + ctx.side);
            return null;
        }

        final WorldServer world = DimensionManager.getWorld(message.dimension);
        world.addScheduledTask(() -> processMessage(message, ctx, world));
        return null;
    }

    private void processMessage(MessageEntityConstructorSetRecipe message, net.minecraftforge.fml.common.network.simpleimpl.MessageContext ctx, WorldServer world)
    {
        TileEntity te = world.getTileEntity(message.pos);
        if(te != null)
        {
            if(te instanceof TileLifeformConstructor)
            {
                TileLifeformConstructor lifeformConstructor = (TileLifeformConstructor) te;
                EntityConstructionRecipes.EntityConstructionRecipe recipe = EntityConstructionRecipes.INSTANCE.getRecipe(message.recipeID);
                if(recipe != null)
                {
                    lifeformConstructor.activeRecipe = recipe;
                }
            }
        }
    }
}
