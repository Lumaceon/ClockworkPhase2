package lumaceon.mods.clockworkphase2.clockworknetwork.gui.child.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lumaceon.mods.clockworkphase2.api.clockworknetwork.ClockworkNetworkGuiClient;
import lumaceon.mods.clockworkphase2.client.render.RenderItemTransparent;
import lumaceon.mods.clockworkphase2.inventory.slot.SlotInventoryValid;
import lumaceon.mods.clockworkphase2.lib.Textures;
import lumaceon.mods.clockworkphase2.clockworknetwork.tile.child.TileClockworkFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiClockworkFurnaceClient extends ClockworkNetworkGuiClient
{
    protected Slot[] slots;
    private TileClockworkFurnace furnace;
    private static RenderItemTransparent itemRenderer = RenderItemTransparent.getInstance();
    private static Minecraft mc;

    private int previousCookTime;

    public GuiClockworkFurnaceClient(TileEntity te, int xSize, int ySize) {
        super(te, xSize, ySize);
        itemRenderer.renderWithColor = true;
        mc = Minecraft.getMinecraft();
        if(te != null && te instanceof IInventory)
        {
            furnace = (TileClockworkFurnace) te;

            Slot input = new SlotInventoryValid((IInventory) te, 0, 3, 3);
            Slot output = new SlotInventoryValid((IInventory) te, 1, 154, 3);

            slots = new Slot[] { input, output };
        }
    }

    @Override
    public void drawBackground(int left, int top, float zLevel)
    {
        mc.renderEngine.bindTexture(Textures.GUI.FURNACE);
        this.drawTexturedModalRect(left, top, xSize, ySize, zLevel);
        ItemStack input = furnace.getStackInSlot(0);
        if(input != null)
        {
            ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(input);
            float distance = slots[1].xDisplayPosition - slots[0].xDisplayPosition;
            float scaledCookProgress = furnace.getCookProgressScaled((int) distance);
            if(result != null && itemRenderer != null)
            {
                GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                RenderHelper.enableGUIStandardItemLighting();
                if(scaledCookProgress == 0)
                    itemRenderer.renderItemIntoGUI(mc.fontRenderer, mc.getTextureManager(), input, left + slots[0].xDisplayPosition, top + slots[0].yDisplayPosition);
                else
                {
                    itemRenderer.renderItemIntoGUIAlpha(mc.fontRenderer, mc.getTextureManager(), result, left + slots[0].xDisplayPosition + (int) scaledCookProgress, top + slots[0].yDisplayPosition, false, scaledCookProgress / distance);
                    itemRenderer.renderItemIntoGUIAlpha(mc.fontRenderer, mc.getTextureManager(), input, left + slots[0].xDisplayPosition + (int) scaledCookProgress, top + slots[0].yDisplayPosition, false, 1.0F - scaledCookProgress / distance);
                }
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                RenderHelper.disableStandardItemLighting();
            }
        }
    }

    @Override
    public void drawForeground(int left, int top, float zLevel) {}

    @Override
    public Slot[] getSlots() {
        return slots;
    }

    @Override
    public int getUpdateCount() {
        return 1;
    }

    @Override
    public void initialCraftingUpdate(ICrafting crafting, int startingIndex, Container container) {
        crafting.sendProgressBarUpdate(container, startingIndex, furnace.furnaceCookTime);
    }

    @Override
    public void detectAndSendChanges(ICrafting crafting, int startingIndex, Container container) {
        if(furnace.furnaceCookTime != previousCookTime)
            crafting.sendProgressBarUpdate(container, startingIndex, furnace.furnaceCookTime);
        previousCookTime = furnace.furnaceCookTime;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int value) {
        //if(id == 0)
            furnace.furnaceCookTime = value;
    }
}
