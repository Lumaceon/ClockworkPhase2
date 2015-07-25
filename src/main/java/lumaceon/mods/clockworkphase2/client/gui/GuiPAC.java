package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.container.ContainerPAC;
import lumaceon.mods.clockworkphase2.entity.EntityPAC;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;

public class GuiPAC extends GuiContainer
{
    public GuiPAC(EntityPlayer player, EntityPAC pac)
    {
        super(new ContainerPAC(player.inventory, player.worldObj, pac));
        this.xSize = 256;
        this.ySize = 256;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {

    }
}
