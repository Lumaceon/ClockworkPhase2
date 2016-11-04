package lumaceon.mods.clockworkphase2.handler;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class BucketHandler
{
    public static BucketHandler INSTANCE = new BucketHandler();
    public Map<Block, Item> buckets = new HashMap<Block, Item>();

    private BucketHandler() {}

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event)
    {
        ItemStack stack = fillCustomBucket(event.getWorld(), event.getTarget());

        if(stack == null)
            return;

        event.setFilledBucket(stack);
        event.setResult(Event.Result.ALLOW);
    }

    private ItemStack fillCustomBucket(World world, RayTraceResult pos)
    {
        Block block = world.getBlockState(pos.getBlockPos()).getBlock();
        Item bucket = buckets.get(block);

        if(bucket != null)// && world.getBlockMetadata(pos.getBlockPos()) == 0)
        {
            world.setBlockToAir(pos.getBlockPos());
            return new ItemStack(bucket);
        }
        else
            return null;
    }
}
