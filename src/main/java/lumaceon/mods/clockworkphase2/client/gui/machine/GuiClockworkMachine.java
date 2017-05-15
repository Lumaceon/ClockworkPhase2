package lumaceon.mods.clockworkphase2.client.gui.machine;

import lumaceon.mods.clockworkphase2.client.gui.GuiHelper;
import lumaceon.mods.clockworkphase2.client.gui.components.GuiButtonItem;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.inventory.ContainerClockworkMachine;
import lumaceon.mods.clockworkphase2.lib.Names;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageTileMachineConfiguration;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import lumaceon.mods.clockworkphase2.util.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.IEnergyStorage;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiClockworkMachine extends GuiContainer
{
    static final int tweenTicks = 10;
    protected static ResourceLocation POWER_PEG = new ResourceLocation(Reference.MOD_ID, "textures/gui/power_peg.png");
    public static ResourceLocation ICONS = new ResourceLocation(Reference.MOD_ID, "textures/gui/icons.png");
    protected static ItemStack GEAR_STACK = new ItemStack(ModItems.gearCreative);

    protected RenderItem itemRenders;

    private boolean isInConfigState = false;
    protected EntityPlayer player;
    protected TileClockworkMachine tileEntity;
    protected int powerPegX, powerPegY, configButtonX, configButtonY;
    protected ResourceLocation background;
    protected HoverableLocation[] hoverables;
    protected IOConfiguration[] configs;
    protected IOConfiguration lastClicked = null;
    protected int timeSinceLastConfigClick = 0;

    public GuiClockworkMachine(EntityPlayer player,
                               Container inventorySlotsIn,
                               int xSize, int ySize, int powerPegX, int powerPegY, int configButtonX, int configButtonY,
                               ResourceLocation background,
                               TileClockworkMachine te,
                               HoverableLocation[] hoverables,
                               IOConfiguration[] configs)
    {
        super(inventorySlotsIn);
        itemRenders = Minecraft.getMinecraft().getRenderItem();
        this.xSize = xSize;
        this.ySize = ySize;
        this.player = player;
        this.powerPegX = powerPegX;
        this.powerPegY = powerPegY;
        this.configButtonX = configButtonX;
        this.configButtonY = configButtonY;
        this.background = background;
        this.tileEntity = te;
        this.hoverables = hoverables;
        this.configs = configs;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButtonItem(GEAR_STACK, 0, this.guiLeft + configButtonX, this.guiTop + configButtonY, "", itemRenders, fontRendererObj, true));
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        if(button.id == 0)
            changeConfigState();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        float color = 1.0F;
        if(lastClicked != null)
        {
            timeSinceLastConfigClick = Math.min(timeSinceLastConfigClick+1, tweenTicks);
            color = 1 - ((float) timeSinceLastConfigClick / (float) tweenTicks) * 0.4F;
        }
        else if(timeSinceLastConfigClick > 0)
        {
            --timeSinceLastConfigClick;
            color = 1 - ((float) timeSinceLastConfigClick / (float) tweenTicks) * 0.4F;
        }

        if(isInConfigState())
            color = color * 0.7F;
        GlStateManager.color(color, color, color, 1.0F);

        Minecraft.getMinecraft().renderEngine.bindTexture(background);
        GuiHelper.drawTexturedModalRectStretched(this.guiLeft, this.guiTop, this.zLevel, this.xSize, this.ySize);

        float degrees = 0;
        IEnergyStorage energyStorage = tileEntity.energyStorage;
        if(energyStorage != null && energyStorage.getMaxEnergyStored() > 0)
        {
            degrees = ((float) energyStorage.getEnergyStored() / (float) energyStorage.getMaxEnergyStored()) * 180.0F;
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(POWER_PEG);
        GlStateManager.pushMatrix();
        GlStateManager.translate(this.guiLeft + powerPegX + 15, this.guiTop + powerPegY + 2, 0.0F);
        GlStateManager.rotate(degrees, 0.0F, 0.0F, 1.0F);
        GlStateManager.translate(-15.0F, -2.0F, 0.0F);
        GuiHelper.drawTexturedModalRectStretched(0, 0, this.zLevel, 17, 4);
        GlStateManager.popMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        if(!isInConfigState() && player.inventory.getItemStack() == null)
        {
            for(HoverableLocation h : hoverables)
            {
                if(h != null && mouseX >= this.guiLeft + h.getMinX() && mouseX <= this.guiLeft + h.getMaxX() && mouseY >= this.guiTop + h.getMinY() && mouseY <= this.guiTop + h.getMaxY())
                {
                    this.renderToolTip(h.getTooltip(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)), mouseX - this.guiLeft, mouseY - this.guiTop);
                    break;
                }
            }
        }

        if(isInConfigState())
        {
            if(lastClicked != null)
            {
                int x, y;

                //CENTER
                x = lastClicked.left;
                y = lastClicked.top;
                if(isPointInRegion(x, y, lastClicked.width, lastClicked.height, mouseX, mouseY))
                    drawRect(x, y, x + lastClicked.width, y + lastClicked.height, 0x80ffffff);
                else
                    drawRect(x, y, x + lastClicked.width, y + lastClicked.height, 0x80aaaaaa);

                //UP
                x = lastClicked.left;
                y = (int) (lastClicked.top - (lastClicked.height + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
                if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, lastClicked.width, lastClicked.height, mouseX, mouseY))
                    drawRect(x, y, x + lastClicked.width, y + lastClicked.height, 0x80ffffff);
                else
                    drawRect(x, y, x + lastClicked.width, y + lastClicked.height, 0x80aaaaaa);

                //DOWN
                x = lastClicked.left;
                y = (int) (lastClicked.top + (lastClicked.height + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
                if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, lastClicked.width, lastClicked.height, mouseX, mouseY))
                    drawRect(x, y, x + lastClicked.width, y + lastClicked.height, 0x80ffffff);
                else
                    drawRect(x, y, x + lastClicked.width, y + lastClicked.height, 0x80aaaaaa);

                //RIGHT
                x = (int) (lastClicked.left + (lastClicked.width + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
                y = lastClicked.top;
                if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, lastClicked.width, lastClicked.height, mouseX, mouseY))
                    drawRect(x, y, x + lastClicked.width, y + lastClicked.height, 0x80ffffff);
                else
                    drawRect(x, y, x + lastClicked.width, y + lastClicked.height, 0x80aaaaaa);

                //LEFT
                x = (int) (lastClicked.left - (lastClicked.width + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
                y = lastClicked.top;
                if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, lastClicked.width, lastClicked.height, mouseX, mouseY))
                    drawRect(x, y, x + lastClicked.width, y + lastClicked.height, 0x80ffffff);
                else
                    drawRect(x, y, x + lastClicked.width, y + lastClicked.height, 0x80aaaaaa);

                //BACK
                x = (int) (lastClicked.left - (lastClicked.width + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
                y = (int) (lastClicked.top - (lastClicked.height + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
                if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, lastClicked.width, lastClicked.height, mouseX, mouseY))
                    drawRect(x, y, x + lastClicked.width, y + lastClicked.height, 0x80ffffff);
                else
                    drawRect(x, y, x + lastClicked.width, y + lastClicked.height, 0x80aaaaaa);
            }
            else if(configs != null)
            {
                for(IOConfiguration config : configs)
                {
                    int x = config.left;
                    int y = config.top;
                    if(isPointInRegion(x, y, config.width, config.height, mouseX, mouseY))
                    {
                        drawRect(x, y, x + config.width, y + config.height, 0x80ffffff);
                        this.renderToolTip(config.getTooltip(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)), mouseX - this.guiLeft, mouseY - this.guiTop);
                    }
                }
            }
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if(isInConfigState())
        {
            if(configs != null)
            {
                for(IOConfiguration config : configs)
                {
                    int x = config.left;
                    int y = config.top;
                    if(isPointInRegion(x, y, config.width, config.height, mouseX, mouseY))
                    {
                        lastClicked = config;
                        timeSinceLastConfigClick = 0;
                    }
                }
            }
        }
    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
    }

    /**
     * Called when a mouse button is released.
     */
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        super.mouseReleased(mouseX, mouseY, state);
        if(lastClicked != null)
        {
            int x, y;
            EnumFacing direction;

            //CENTER
            x = lastClicked.left;
            y = lastClicked.top;
            if(isPointInRegion(x, y, lastClicked.width, lastClicked.height, mouseX, mouseY))
            {
                for(int i = 0; i < 6; i++)
                {
                    lastClicked.onIOChange(EnumFacing.getFront(i), false); //Deactivate all sides.
                }
            }

            //UP
            x = lastClicked.left;
            y = (int) (lastClicked.top - (lastClicked.height + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
            if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, lastClicked.width, lastClicked.height, mouseX, mouseY))
            {
                direction = EnumFacing.UP;
                lastClicked.onIOChange(direction, !lastClicked.isActiveOnSide(direction));
            }

            //DOWN
            x = lastClicked.left;
            y = (int) (lastClicked.top + (lastClicked.height + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
            if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, lastClicked.width, lastClicked.height, mouseX, mouseY))
            {
                direction = EnumFacing.DOWN;
                lastClicked.onIOChange(direction, !lastClicked.isActiveOnSide(direction));
            }

            //RIGHT
            x = (int) (lastClicked.left + (lastClicked.width + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
            y = lastClicked.top;
            if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, lastClicked.width, lastClicked.height, mouseX, mouseY))
            {
                direction = EnumFacing.EAST;
                lastClicked.onIOChange(direction, !lastClicked.isActiveOnSide(direction));
            }

            //LEFT
            x = (int) (lastClicked.left - (lastClicked.width + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
            y = lastClicked.top;
            if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, lastClicked.width, lastClicked.height, mouseX, mouseY))
            {
                direction = EnumFacing.WEST;
                lastClicked.onIOChange(direction, !lastClicked.isActiveOnSide(direction));
            }

            //BACK
            x = (int) (lastClicked.left - (lastClicked.width + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
            y = (int) (lastClicked.top - (lastClicked.height + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
            if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, lastClicked.width, lastClicked.height, mouseX, mouseY))
            {
                direction = EnumFacing.SOUTH;
                lastClicked.onIOChange(direction, !lastClicked.isActiveOnSide(direction));
            }

            lastClicked = null;
        }
    }

    protected void renderToolTip(List<String> list, int x, int y)
    {
        this.drawHoveringText(list, x, y, fontRendererObj);
    }

    public void changeConfigState()
    {
        if(isInConfigState)
        {
            Container c = this.inventorySlots;
            if(c != null && c instanceof ContainerClockworkMachine)
            {
                ((ContainerClockworkMachine) c).initializeSlots(player.inventory, false);
            }
            lastClicked = null;
        }
        else
        {
            Container c = this.inventorySlots;
            if(c != null && c instanceof ContainerClockworkMachine)
            {
                ((ContainerClockworkMachine) c).initializeSlots(player.inventory, true);
            }
        }
        isInConfigState = !isInConfigState;
    }

    protected boolean isInConfigState() {
        return isInConfigState;
    }

    public static class HoverableLocation
    {
        private int minX, maxX, minY, maxY;

        public HoverableLocation(int minX, int maxX, int minY, int maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;
        }

        public int getMinX() {
            return minX;
        }

        public int getMaxX() {
            return maxX;
        }

        public int getMinY() {
            return minY;
        }

        public int getMaxY() {
            return maxY;
        }

        public List<String> getTooltip(boolean isShiftDown) {
            return null;
        }
    }

    public static class HoverableLocationEnergy extends HoverableLocation
    {
        TileClockworkMachine te;

        public HoverableLocationEnergy(int minX, int maxX, int minY, int maxY, TileClockworkMachine te) {
            super(minX, maxX, minY, maxY);
            this.te = te;
        }

        @Override
        public List<String> getTooltip(boolean isShiftDown)
        {
            if(te.energyStorage != null)
            {
                ArrayList<String> ret = new ArrayList<String>();
                ret.add(Colors.RED + "Energy");
                ret.add(Colors.GREY + te.energyStorage.getEnergyStored() + " / " + te.energyStorage.getMaxEnergyStored() + Names.ENERGY_ACRONYM);
                if(isShiftDown)
                {
                    ret.add("");
                    ret.add(Colors.GREY + "Cost: " + te.getEnergyCostPerTick() + Names.ENERGY_ACRONYM + " per tick");
                    ret.add(Colors.GREY + "CW Quality: " + te.quality);
                    ret.add(Colors.GREY + "CW Speed: " + te.speed);
                }
                return ret;
            }
            else
            {
                ArrayList<String> ret = new ArrayList<String>();
                ret.add(Colors.RED + "Missing Mainspring....");
                return ret;
            }
        }
    }

    public static abstract class IOConfiguration
    {
        protected TileClockworkMachine te;
        public int left, top, width, height;

        public IOConfiguration(int left, int top, int width, int height, TileClockworkMachine te)
        {
            this.te = te;
            this.left = left;
            this.top = top;
            this.width = width;
            this.height = height;
        }

        public abstract List<String> getTooltip(boolean isShiftDown);

        public abstract void onIOChange(EnumFacing direction, boolean activate);

        public abstract boolean isActiveOnSide(EnumFacing direction);
    }

    public static class IOConfigurationSlot extends IOConfiguration
    {
        public int slotID;
        private List<String> toolTip;
        private List<String> retTip;

        public IOConfigurationSlot(int left, int top, int width, int height, int slotID, TileClockworkMachine te, List<String> toolTip) {
            super(left, top, width, height, te);
            this.slotID = slotID;
            this.toolTip = new ArrayList<String>(toolTip.size());
            this.retTip = new ArrayList<String>(toolTip.size());
            for(String s : toolTip)
            {
                this.toolTip.add(s);
                this.retTip.add(s);
            }
        }

        public IOConfigurationSlot(Slot slot, TileClockworkMachine te, List<String> toolTip) {
            this(slot.xDisplayPosition, slot.yDisplayPosition, 16, 16, slot.getSlotIndex(), te, toolTip);
        }

        @Override
        public List<String> getTooltip(boolean isShiftDown)
        {
            retTip.clear();
            for(String s : toolTip)
            {
                retTip.add(s);
            }

            String activeSides = null;
            String temp = "ERROR";
            EnumFacing d;
            for(int i = 0; i < 6; i++)
            {
                d = EnumFacing.getFront(i);
                if(isActiveOnSide(d))
                {
                    if(d.equals(EnumFacing.UP))
                        temp = "UP";
                    if(d.equals(EnumFacing.DOWN))
                        temp = "DOWN";
                    if(d.equals(EnumFacing.WEST))
                        temp = "LEFT";
                    if(d.equals(EnumFacing.EAST))
                        temp = "RIGHT";
                    if(d.equals(EnumFacing.SOUTH))
                        temp = "BACK";

                    if(activeSides == null)
                        activeSides = Colors.GREY + "Sides: " + temp;
                    else
                        activeSides = activeSides + ", " + temp;
                }
            }
            if(activeSides != null)
                retTip.add(activeSides);

            return retTip;
        }

        @Override
        public void onIOChange(EnumFacing direction, boolean activate) {
            te.changeIO(direction, slotID, activate);
            PacketHandler.INSTANCE.sendToServer(new MessageTileMachineConfiguration(te.getPos(), te.getWorld().provider.getDimension(), slotID, direction, activate));
        }

        @Override
        public boolean isActiveOnSide(EnumFacing direction)
        {
            int[] slots;
            if(direction.equals(EnumFacing.UP))
                slots = te.slotsUP;
            else if(direction.equals(EnumFacing.DOWN))
                slots = te.slotsDOWN;
            else if(direction.equals(EnumFacing.NORTH))
                slots = te.slotsFRONT;
            else if(direction.equals(EnumFacing.SOUTH))
                slots = te.slotsBACK;
            else if(direction.equals(EnumFacing.EAST))
                slots = te.slotsRIGHT;
            else if(direction.equals(EnumFacing.WEST))
                slots = te.slotsLEFT;
            else
                slots = new int[0];

            for(int slot : slots)
                if(slot == slotID)
                    return true;

            return false;
        }
    }
}
