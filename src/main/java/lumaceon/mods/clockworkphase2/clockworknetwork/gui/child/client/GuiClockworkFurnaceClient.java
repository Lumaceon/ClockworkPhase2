package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child.client;

import lumaceon.mods.clockworkphase2.inventory.slot.SlotInventoryValid;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiClockworkFurnaceClient extends GuiCN
{
    private static RenderItem itemRenderer;
    private static Minecraft mc;

    public GuiClockworkFurnaceClient(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        mc = Minecraft.getMinecraft();
        itemRenderer = mc.getRenderItem();
        if(te != null && te instanceof IInventory)
        {
            Slot input = new SlotInventoryValid((IInventory) te, 0, 1, 1);
            Slot output = new SlotInventoryValid((IInventory) te, 1, 151, 1);

            slots = new Slot[] { input, output };
        }
    }

    @Override
    public void drawBackground(int left, int top, float zLevel)
    {
        mc.renderEngine.bindTexture(Textures.GUI.FURNACE);
        this.drawTexturedModalRect(left, top, xSize, ySize, zLevel);
        ItemStack input = machine.getStackInSlot(0);
        if(input != null)
        {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(input);
            float distance = slots[1].xDisplayPosition - slots[0].xDisplayPosition;
            float scaledCookProgress = machine.getProgressScaled((int) distance);
            if(result != null && itemRenderer != null)
            {
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                RenderHelper.enableGUIStandardItemLighting();
                if(scaledCookProgress == 0)
                    itemRenderer.renderItemIntoGUI(input, left + slots[0].xDisplayPosition, top + slots[0].yDisplayPosition);
                else
                {
                    //itemRenderer.renderItemIntoGUIAlpha(mc.fontRendererObj, mc.getTextureManager(), result, left + slots[0].xDisplayPosition + (int) scaledCookProgress, top + slots[0].yDisplayPosition, false, scaledCookProgress / distance);
                    //itemRenderer.renderItemIntoGUIAlpha(mc.fontRendererObj, mc.getTextureManager(), input, left + slots[0].xDisplayPosition + (int) scaledCookProgress, top + slots[0].yDisplayPosition, false, 1.0F - scaledCookProgress / distance);
                }
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                RenderHelper.disableStandardItemLighting();
            }
        }
    }

    @Override
    public void drawForeground(int left, int top, float zLevel) {
        mc.renderEngine.bindTexture(Textures.GUI.FURNACE_FORE);
        this.drawTexturedModalRect(left, top, xSize, ySize, zLevel);
    }
}
