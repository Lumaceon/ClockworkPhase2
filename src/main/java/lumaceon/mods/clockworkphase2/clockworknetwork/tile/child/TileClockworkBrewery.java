package lumaceon.mods.clockworkphase2.clockworknetwork.tile.child;

import lumaceon.mods.clockworkphase2.ClockworkPhase2;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkContainer;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.tiles.TileClockworkNetworkMachine;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.EnumFacing;

import java.util.List;

public class TileClockworkBrewery extends TileClockworkNetworkMachine
{
    public TileClockworkBrewery() {
        inventory = new ItemStack[4];
    }

    /**
     * Modified from TileEntityBrewingStand.canBrew().
     */
    @Override
    public boolean canWork()
    {
        if(inventory[0] != null && inventory[0].stackSize > 0)
        {
            ItemStack itemstack = inventory[0];

            if(!itemstack.getItem().isPotionIngredient(itemstack)) //TODO account for custom ingredients.
                return false;
            else
            {
                boolean flag = false;

                for(int i = 1; i <= 3; ++i)
                    if(inventory[i] != null && inventory[i].getItem() instanceof ItemPotion)
                    {
                        int j = inventory[i].getItemDamage();
                        int k = doSomethingVanillaish(j, itemstack);

                        if(!ItemPotion.isSplash(j) && ItemPotion.isSplash(k))
                        {
                            flag = true;
                            break;
                        }

                        List list = Items.potionitem.getEffects(j);
                        List list1 = Items.potionitem.getEffects(k);

                        if((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null) && j != k)
                        {
                            flag = true;
                            break;
                        }
                    }
                return flag;
            }
        }
        else
            return false;
    }

    /**
     * Modified from TileEntityBrewingStand.brewPotions().
     */
    @Override
    public void work()
    {
        if(net.minecraftforge.event.ForgeEventFactory.onPotionAttemptBreaw(rearrangeInventory(inventory))) return;
        if(this.canWork())
        {
            ItemStack itemstack = inventory[0];

            for(int i = 1; i <= 3; ++i)
            {
                if(inventory[i] != null && inventory[i].getItem() instanceof ItemPotion)
                {
                    int j = inventory[i].getItemDamage();
                    int k = doSomethingVanillaish(j, itemstack);
                    List list = Items.potionitem.getEffects(j);
                    List list1 = Items.potionitem.getEffects(k);

                    if((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null))
                    {
                        if(j != k)
                            inventory[i].setItemDamage(k);
                    }
                    else if(!ItemPotion.isSplash(j) && ItemPotion.isSplash(k))
                        inventory[i].setItemDamage(k);
                }
            }

            if(itemstack.getItem().hasContainerItem(itemstack))
                inventory[0] = itemstack.getItem().getContainerItem(itemstack);
            else
            {
                --inventory[0].stackSize;

                if(inventory[0].stackSize <= 0)
                    inventory[0] = null;
            }
            net.minecraftforge.event.ForgeEventFactory.onPotionBrewed(rearrangeInventory(inventory));
        }
    }

    @Override
    public ClockworkNetworkContainer getGui() {
        return ClockworkPhase2.proxy.getClockworkNetworkGui(this, 1);
    }

    @Override
    public boolean canExportToTargetInventory() {
        return false;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return new int[] { 0, 1, 2, 3 };
    }

    @Override
    public boolean canInsertItem(int slotID, ItemStack inputStack, EnumFacing side) {
        return
                inputStack != null
                        && FurnaceRecipes.instance().getSmeltingResult(inputStack) != null
                        && (inventory[0] == null || inventory[0].stackSize < inventory[0].getMaxStackSize())
                        && slotID == 0;
    }

    @Override
    public boolean canExtractItem(int slotID, ItemStack outputStack, EnumFacing side) {
        return slotID > 0 && slotID < 4;
    }

    /**
     * TileEntityBrewingStand has it's inventory set up with input being last (index 3) as opposed to my own convention
     * of putting input first. This method creates a new array to match vanilla so the forge events can still work.
     */
    private ItemStack[] rearrangeInventory(ItemStack[] items) {
        ItemStack[] newArray = new ItemStack[4];
        newArray[0] = inventory[1];
        newArray[1] = inventory[2];
        newArray[2] = inventory[3];
        newArray[3] = inventory[0];
        return newArray;
    }

    /**
     * Copied from TileEntityBrewingStand, originally named func_145936_c.
     */
    private int doSomethingVanillaish(int p_145936_1_, ItemStack p_145936_2_) {
        return p_145936_2_ == null ? p_145936_1_ : (p_145936_2_.getItem().isPotionIngredient(p_145936_2_) ? PotionHelper.applyIngredient(p_145936_1_, p_145936_2_.getItem().getPotionEffect(p_145936_2_)) : p_145936_1_);
    }
}
