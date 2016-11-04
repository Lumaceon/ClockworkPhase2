package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.api.assembly.IAssemblable;
import lumaceon.mods.clockworkphase2.api.assembly.ContainerAssemblyTable;
import lumaceon.mods.clockworkphase2.api.assembly.IAssemblableButtons;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiAssemblyTable extends GuiContainer
{
    public World world;
    public int x, y, z;

    public GuiAssemblyTable(InventoryPlayer ip, World world, int x, int y, int z) {
        super(new ContainerAssemblyTable(ip, world));
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xSize = 300;
        this.ySize = 230;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.clear();
        if(this.inventorySlots instanceof ContainerAssemblyTable)
        {
            ContainerAssemblyTable container = (ContainerAssemblyTable) this.inventorySlots;
            container.buttonList = this.buttonList;
            container.guiLeft = this.guiLeft;
            container.guiTop = this.guiTop;
            container.onGUIResize();
        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        if(this.inventorySlots != null && this.inventorySlots instanceof ContainerAssemblyTable)
        {
            ContainerAssemblyTable container = (ContainerAssemblyTable) this.inventorySlots;
            ItemStack mainItem = container.mainInventory.getStackInSlot(0);
            if(mainItem != null && mainItem.getItem() instanceof IAssemblableButtons)
                ((IAssemblableButtons) mainItem.getItem()).onButtonClicked(button.id, buttonList);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        if(inventorySlots instanceof ContainerAssemblyTable)
        {
            ContainerAssemblyTable container = (ContainerAssemblyTable)inventorySlots;
            ItemStack item = container.mainInventory.getStackInSlot(0);
            if(item != null && item.getItem() instanceof IAssemblable)
            {
                IAssemblable assemblyGUI = (IAssemblable)container.mainInventory.getStackInSlot(0).getItem();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                Minecraft.getMinecraft().renderEngine.bindTexture(assemblyGUI.getGUIBackground(container));
                this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
                return;
            }
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.ASSEMBLY_TABLE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    /**
     * Overridden to fix UV errors.
     */
    @Override
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
    {
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer renderer = tessellator.getBuffer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex(0, 1).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex(1, 1).endVertex();
        renderer.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex(1, 0).endVertex();
        renderer.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex(0, 0).endVertex();
        tessellator.draw();
    }
}
