package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblyContainer;
import lumaceon.mods.clockworkphase2.container.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiAssemblyTable extends GuiContainer
{
    public GuiAssemblyTable(InventoryPlayer ip, World world)
    {
        super(new ContainerAssemblyTable(ip, world));
        this.xSize = 256;
        this.ySize = 256;
    }

    @Override
    public void initGui()
    {
        super.initGui();

        this.guiLeft = (this.width - this.xSize) / 2;
        this.guiTop = (this.height - this.ySize) / 2;
        this.buttonList.clear();
        if(this.inventorySlots instanceof IAssemblyContainer)
        {
            IAssemblyContainer container = (IAssemblyContainer) this.inventorySlots;
            container.onGUIInitialize(this.buttonList, this.guiLeft, this.guiTop);
        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        if(this.inventorySlots != null && this.inventorySlots instanceof IAssemblyContainer)
        {
            IAssemblyContainer container = (IAssemblyContainer) this.inventorySlots;
            ItemStack mainItem = container.getMainInventory().getStackInSlot(0);
            if(mainItem != null && mainItem.getItem() instanceof IAssemblable)
            {
                ((IAssemblable) mainItem.getItem()).onButtonActivated(button.id, buttonList);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        if(inventorySlots instanceof IAssemblyContainer)
        {
            IAssemblyContainer container = (IAssemblyContainer)inventorySlots;
            ItemStack item = container.getMainInventory().getStackInSlot(0);
            if(item != null && item.getItem() instanceof IAssemblable)
            {
                IAssemblable constructGUI = (IAssemblable) container.getMainInventory().getStackInSlot(0).getItem();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                Minecraft.getMinecraft().renderEngine.bindTexture(constructGUI.getBackgroundTexture(container));
                this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
                return;
            }
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.DEFAULT_ASSEMBLY_TABLE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }
}
