package lumaceon.mods.clockworkphase2.item.mob;

import lumaceon.mods.clockworkphase2.api.EntityStack;
import lumaceon.mods.clockworkphase2.capabilities.entitycontainer.EntityContainer;
import lumaceon.mods.clockworkphase2.capabilities.entitycontainer.IEntityContainer;
import lumaceon.mods.clockworkphase2.item.ItemClockworkPhase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMobCapsule extends ItemClockworkPhase
{
    @CapabilityInject(IEntityContainer.class)
    public static final Capability<IEntityContainer> ENTITY_CONTAINER = null;

    public ItemMobCapsule(int maxStack, int maxDamage, String name) {
        super(maxStack, maxDamage, name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        IEntityContainer cap = stack.getCapability(ENTITY_CONTAINER, EnumFacing.DOWN);
        if(cap != null)
        {
            tooltip.add("Capacity: " + cap.getCapacity());
            EntityStack[] ents = cap.getEntities();
            for(EntityStack e : ents)
                tooltip.add(e.getEntityTypeDisplayName());
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            ItemStack stack = player.getHeldItem(hand);
            IEntityContainer cap = stack.getCapability(ENTITY_CONTAINER, EnumFacing.DOWN);
            if(cap != null)
            {
                if(cap.getEntityCount() > 0)
                {
                    EntityStack entityStack = cap.extractEntity(0);
                    if(entityStack != null)
                    {
                        Entity e = entityStack.createEntityForWorld(worldIn);
                        e.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                        worldIn.spawnEntity(e);
                        return EnumActionResult.SUCCESS;
                    }
                }
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public net.minecraftforge.common.capabilities.ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new Provider();
    }

    private static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {
        EntityContainer container;

        public Provider() {
            container = new EntityContainer();
        }

        public Provider(int capacity) {
            container = new EntityContainer(capacity);
        }

        @Override
        public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
            return ENTITY_CONTAINER != null && ENTITY_CONTAINER == capability;
        }

        @Override
        public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
            if(hasCapability(capability, facing))
                return ENTITY_CONTAINER.cast(container);
            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return container.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            container.deserializeNBT(nbt);
        }
    }
}
