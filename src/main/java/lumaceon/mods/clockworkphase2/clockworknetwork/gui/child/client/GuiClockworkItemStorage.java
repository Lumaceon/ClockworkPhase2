package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child.client;

import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkItemStorage;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiClockworkItemStorage extends GuiCN
{
    private static Minecraft mc;
    private TileClockworkItemStorage itemStorage;

    public GuiClockworkItemStorage(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        mc = Minecraft.getMinecraft();
        if(te != null && te instanceof TileClockworkItemStorage)
        {
            TileClockworkItemStorage itemStorage = (TileClockworkItemStorage) te;
            this.itemStorage = itemStorage;
            this.slots = new Slot[itemStorage.xSlots * itemStorage.ySlots];

            for(int y = 0; y < itemStorage.ySlots; y++)
            {
                for(int x = 0; x < itemStorage.xSlots; x++)
                    this.slots[x + (y * itemStorage.xSlots)] = new Slot(itemStorage, x + (y * itemStorage.xSlots), 1+x*18, 1+y*18);
            }
        }
    }

    @Override
    public void drawBackground(int left, int top, float zLevel) {
        mc.renderEngine.bindTexture(Textures.GUI.CHEST);
        this.drawTiledTexturedModalRect(left, top, xSize, ySize, itemStorage.xSlots, itemStorage.ySlots, zLevel);
    }

    public void drawTiledTexturedModalRect(int x, int y, int width, int height, int tilesX, int tilesY, float zLevel)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer renderer = tessellator.getWorldRenderer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos((double)(x + 0), (double)(y + height), (double)zLevel).tex(0, tilesY).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), (double)zLevel).tex(tilesX, tilesY).endVertex();
        renderer.pos((double)(x + width), (double)(y + 0), (double)zLevel).tex(tilesX, 0).endVertex();
        renderer.pos((double)(x + 0), (double)(y + 0), (double)zLevel).tex(0, 0).endVertex();
        tessellator.draw();
    }

    //All of these are overridden to NOOP
    @Override
    public void drawForeground(int left, int top, float zLevel) {}
    @Override
    public int getUpdateCount() { return 0; }
    @Override
    public void initialCraftingUpdate(ICrafting crafting, int startingIndex, Container container) {}
    @Override
    public void detectAndSendChanges(ICrafting crafting, int startingIndex, Container container) {}
    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {}
}
