package lumaceon.mods.clockworkphase2.item.construct.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageLightningSwordActivate;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ItemLightningSword extends ItemSword
{
    public ItemLightningSword(ToolMaterial material, String unlocalizedName)
    {
        super(material);
        this.setMaxStackSize(1);
        this.setMaxDamage(100);
        this.setNoRepair();
        this.setCreativeTab(ClockworkPhase2.instance.CREATIVE_TAB);
        this.setUnlocalizedName(unlocalizedName);
    }

    public void lightningTeleport(ItemStack is, World world, EntityPlayer player, int charge, Vec3 pos, Vec3 look)
    {
        int j = this.getMaxItemUseDuration(is) - charge;
        float f = (float)j / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;

        if((double)f < 0.1D)
            return;
        if(f > 1.0F)
            f = 1.0F;
        double x = look.xCoord;
        double y = look.yCoord;
        double z = look.zCoord;

        double newPlayerX = player.posX + x * (f * 100F);
        double newPlayerY = player.posY + y * (f * 100F);
        double newPlayerZ = player.posZ + z * (f * 100F);
        MovingObjectPosition MOB = getRayTrace(pos, look, world, f * 100F);
        if(MOB != null)
        {
            if(MOB.typeOfHit.equals(MovingObjectPosition.MovingObjectType.BLOCK))
            {
                int xCoord = MOB.blockX;
                int yCoord = MOB.blockY + 1;
                int zCoord = MOB.blockZ;
                Block block = world.getBlock(xCoord, yCoord, zCoord);
                Block block2 = world.getBlock(xCoord, yCoord + 1, zCoord);
                if(block != null && block2 != null && block.isAir(world, xCoord, yCoord, zCoord) && block2.isAir(world, xCoord, yCoord + 1, zCoord))
                {
                    newPlayerX = xCoord + 0.5;
                    newPlayerY = yCoord;
                    newPlayerZ = zCoord + 0.5;
                }
                else
                {
                    boolean exitFlag = false;
                    while(!exitFlag && yCoord < 256)
                    {
                        yCoord++;
                        block = world.getBlock(xCoord, yCoord, zCoord);
                        block2 = world.getBlock(xCoord, yCoord + 1, zCoord);
                        if(block != null && block.isAir(world, xCoord, yCoord, zCoord) && block2 != null && block2.isAir(world, xCoord, yCoord + 1, zCoord))
                            exitFlag = true;
                    }
                    newPlayerX = xCoord + 0.5;
                    newPlayerY = yCoord;
                    newPlayerZ = zCoord + 0.5;
                }
            }
            else if(MOB.typeOfHit.equals(MovingObjectPosition.MovingObjectType.ENTITY))
            {
                newPlayerX = MOB.entityHit.posX;
                newPlayerY = MOB.entityHit.posY;
                newPlayerZ = MOB.entityHit.posZ;
                MOB.entityHit.attackEntityFrom(DamageSource.inFire, this.getLightningDamage(is));
            }
        }
        world.spawnEntityInWorld(new EntityLightningBolt(world, player.posX, player.posY, player.posZ));
        world.spawnEntityInWorld(new EntityLightningBolt(world, newPlayerX, newPlayerY, newPlayerZ));
        player.setPositionAndUpdate(newPlayerX, newPlayerY, newPlayerZ);
    }

    public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int charge)
    {
        if(world.isRemote)
        {
            PacketHandler.INSTANCE.sendToServer(new MessageLightningSwordActivate(player, charge));
            lightningTeleport(is, world, player, charge, player.getPosition(0), player.getLook(0));
        }
    }

    public float getLightningDamage(ItemStack item) {
        return 10.0F;
    }

    public ItemStack onEaten(ItemStack is, World world, EntityPlayer player) {
        return is;
    }

    public EnumAction getItemUseAction(ItemStack is) {
        return EnumAction.bow;
    }

    public int getMaxItemUseDuration(ItemStack is) {
        return 72000;
    }

    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player) {
        player.setItemInUse(is, this.getMaxItemUseDuration(is));
        return is;
    }

    public MovingObjectPosition getRayTrace(Vec3 pos, Vec3 look, World world, float range) {
        Vec3 vec32 = pos.addVector(look.xCoord * range, look.yCoord * range, look.zCoord * range);
        return world.func_147447_a(pos, vec32, false, false, true);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }

    @Override
    public String getUnlocalizedName(ItemStack is) {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, super.getUnlocalizedName().substring(super.getUnlocalizedName().indexOf('.') + 1));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister registry) {
        this.itemIcon = registry.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }
}
