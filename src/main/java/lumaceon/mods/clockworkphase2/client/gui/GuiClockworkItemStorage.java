package lumaceon.mods.clockworkphase2.client.gui;

import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkItemStorage;
import lumaceon.mods.clockworkphase2.container.ContainerClockworkItemStorage;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageItemStorageResize;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public class GuiClockworkItemStorage extends GuiContainer
{
    public TileClockworkItemStorage te;

    public GuiClockworkItemStorage(InventoryPlayer ip, TileClockworkItemStorage te, World world) {
        super(new ContainerClockworkItemStorage(ip, te, world));
        this.te = te;
        this.xSize = 300;
        this.ySize = 230;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft.getMinecraft().renderEngine.bindTexture(Textures.GUI.BASE);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.clear();

        buttonList.add(new GuiButton(0, 0, 20, 20, 20, "-x"));
        buttonList.add(new GuiButton(1, 40, 20, 20, 20, "+x"));
        buttonList.add(new GuiButton(2, 20, 40, 20, 20, "-y"));
        buttonList.add(new GuiButton(3, 20, 0, 20, 20, "+y"));
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        switch(button.id)
        {
            case 0: //- X
                if(te.canResizeInventory(te.xSlots - 1, te.ySlots))
                {
                    te.resizeInventory(te.xSlots - 1, te.ySlots);
                    if(inventorySlots != null && inventorySlots instanceof ContainerClockworkItemStorage)
                        ((ContainerClockworkItemStorage) inventorySlots).resizeSlotsInContainer(te.xSlots, te.ySlots);
                    PacketHandler.INSTANCE.sendToServer(new MessageItemStorageResize(te.xSlots, te.ySlots, te.getPos()));
                }
                break;
            case 1: //+ X
                if(te.canResizeInventory(te.xSlots + 1, te.ySlots))
                {
                    te.resizeInventory(te.xSlots + 1, te.ySlots);
                    if(inventorySlots != null && inventorySlots instanceof ContainerClockworkItemStorage)
                        ((ContainerClockworkItemStorage) inventorySlots).resizeSlotsInContainer(te.xSlots, te.ySlots);
                    PacketHandler.INSTANCE.sendToServer(new MessageItemStorageResize(te.xSlots, te.ySlots, te.getPos()));
                }
                break;
            case 2: //- Y
                if(te.canResizeInventory(te.xSlots, te.ySlots - 1))
                {
                    te.resizeInventory(te.xSlots, te.ySlots - 1);
                    if(inventorySlots != null && inventorySlots instanceof ContainerClockworkItemStorage)
                        ((ContainerClockworkItemStorage) inventorySlots).resizeSlotsInContainer(te.xSlots, te.ySlots);
                    PacketHandler.INSTANCE.sendToServer(new MessageItemStorageResize(te.xSlots, te.ySlots, te.getPos()));
                }
                break;
            case 3: //+ Y
                if(te.canResizeInventory(te.xSlots, te.ySlots + 1))
                {
                    te.resizeInventory(te.xSlots, te.ySlots + 1);
                    if(inventorySlots != null && inventorySlots instanceof ContainerClockworkItemStorage)
                        ((ContainerClockworkItemStorage) inventorySlots).resizeSlotsInContainer(te.xSlots, te.ySlots);
                    PacketHandler.INSTANCE.sendToServer(new MessageItemStorageResize(te.xSlots, te.ySlots, te.getPos()));
                }
                break;
        }
    }
}
