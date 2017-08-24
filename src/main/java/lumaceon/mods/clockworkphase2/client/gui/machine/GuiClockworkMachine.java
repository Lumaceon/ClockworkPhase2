package lumaceon.mods.clockworkphase2.client.gui.machine;

import lumaceon.mods.clockworkphase2.client.gui.GuiHelper;
import lumaceon.mods.clockworkphase2.client.gui.components.GuiButtonItem;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.inventory.ContainerClockworkMachine;
import lumaceon.mods.clockworkphase2.lib.Names;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.network.PacketHandler;
import lumaceon.mods.clockworkphase2.network.message.MessageMachineModeActivate;
import lumaceon.mods.clockworkphase2.network.message.MessageTileMachineConfiguration;
import lumaceon.mods.clockworkphase2.network.message.MessageTileMachineConfigurationTank;
import lumaceon.mods.clockworkphase2.tile.machine.TileClockworkMachine;
import lumaceon.mods.clockworkphase2.util.Colors;
import lumaceon.mods.clockworkphase2.util.FluidTankSided;
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
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiClockworkMachine extends GuiContainer
{
    static final int tweenTicks = 10;
    protected static ResourceLocation PROGRESS_CLOCK = new ResourceLocation(Reference.MOD_ID, "textures/gui/progress_clock.png");
    protected static ResourceLocation PROGRESS_CLOCK_TEMPORAL = new ResourceLocation(Reference.MOD_ID, "textures/gui/progress_clock_temporal.png");
    protected static ResourceLocation PROGRESS_HAND = new ResourceLocation(Reference.MOD_ID, "textures/gui/progress_hand.png");
    protected static ResourceLocation POWER_BAR_RED = new ResourceLocation(Reference.MOD_ID, "textures/gui/red_power_bar.png");
    protected static ResourceLocation SIDE_CONFIG_ARROWS = new ResourceLocation(Reference.MOD_ID, "textures/gui/side_config_arrows.png");
    public static ResourceLocation ICONS = new ResourceLocation(Reference.MOD_ID, "textures/gui/icons.png");
    public static ResourceLocation OVERLAY = new ResourceLocation(Reference.MOD_ID, "textures/gui/overlayeffect.png");

    protected static ItemStack GEAR_STACK_FOR_DISPLAY = new ItemStack(ModItems.gearCreative);
    protected static ItemStack HOURGLASS_STACK_FOR_DISPLAY = new ItemStack(ModItems.temporalHourglass);

    protected RenderItem itemRenders;

    private boolean isInConfigState = false;
    protected EntityPlayer player;
    protected TileClockworkMachine tileEntity;

    protected int powerBarX, powerBarY,
            progressClockX, progressClockY,
            configButtonX, configButtonY,
            temporalButtonX, temporalButtonY;

    protected ResourceLocation background;
    protected HoverableLocation[] hoverables;
    protected IOConfiguration[] configs;
    protected IOConfiguration lastClicked = null;
    protected int timeSinceLastConfigClick = 0;
    private int stateChangeTween = 0;

    public GuiClockworkMachine(EntityPlayer player,
                               Container inventorySlotsIn,
                               int xSize, int ySize,
                               int powerBarX, int powerBarY,
                               int progressClockX, int progressClockY,
                               int configButtonX, int configButtonY,
                               int temporalButtonX, int temporalButtonY,
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
        this.powerBarX = powerBarX;
        this.powerBarY = powerBarY;
        this.progressClockX = progressClockX;
        this.progressClockY = progressClockY;
        this.configButtonX = configButtonX;
        this.configButtonY = configButtonY;
        this.temporalButtonX = temporalButtonX;
        this.temporalButtonY = temporalButtonY;
        this.background = background;
        this.tileEntity = te;
        this.hoverables = hoverables;
        this.configs = configs;

        stateChangeTween = te.isInTemporalMode() ? 10 : 0;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButtonItem(GEAR_STACK_FOR_DISPLAY, 0, this.guiLeft + configButtonX, this.guiTop + configButtonY, "", itemRenders, fontRenderer, true));
        if(tileEntity.hasReceivedTemporalUpgrade)
        {
            this.buttonList.add(new GuiButtonItem(HOURGLASS_STACK_FOR_DISPLAY, 1, this.guiLeft + temporalButtonX, this.guiTop + temporalButtonY, "", itemRenders, fontRenderer, true));
        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        if(button.id == 0)
            changeConfigState();
        else if(button.id == 1)
            changeTemporalState();
    }

    private void changeTemporalState()
    {
        PacketHandler.INSTANCE.sendToServer(new MessageMachineModeActivate(tileEntity.getPos(), tileEntity.getWorld().provider.getDimension()));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        boolean dev = true;

        GlStateManager.pushMatrix();

        //Apply tweening between temporal and normal modes.
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

        //Configuration mode should look darker.
        if(isInConfigState())
            color = color * 0.7F;


        //Render progress clock
        if(stateChangeTween < 10)
        {
            Minecraft.getMinecraft().renderEngine.bindTexture(PROGRESS_CLOCK);
            GlStateManager.color(color, color, color, 1.0F);
            GuiHelper.drawTexturedModalRectStretched(this.guiLeft + this.progressClockX, this.guiTop + this.progressClockY, zLevel, 32, 32);
        }


        //Only is temporal mode is on.
        if(tileEntity.isInTemporalMode() || stateChangeTween > 0)
        {
            if(stateChangeTween < 10 && tileEntity.isInTemporalMode())
                stateChangeTween++;
            else if(stateChangeTween > 0 && !tileEntity.isInTemporalMode())
                stateChangeTween--;

            Minecraft.getMinecraft().renderEngine.bindTexture(PROGRESS_CLOCK_TEMPORAL);
            GuiHelper.drawTexturedModalRectStretched(this.guiLeft + this.progressClockX, this.guiTop + this.progressClockY, zLevel, 32, 32);

            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();

            if(stateChangeTween == 10)
                GlStateManager.color(color, color, color, 0.3F);
            else
                GlStateManager.color(color, color, color, 0.3F * (stateChangeTween * 0.1F));

            Minecraft.getMinecraft().renderEngine.bindTexture(OVERLAY);
            float millisF = (System.currentTimeMillis() % 10000) / 10000.0F;
            GlStateManager.blendFunc(GL11.GL_DST_COLOR, GL11.GL_ZERO);
            GuiHelper.drawTexturedModalRectStretchedWithUVOffset(this.guiLeft + progressClockX, this.guiTop + progressClockY, this.zLevel, 32, 32, millisF, millisF * 2.0F);
            GlStateManager.blendFunc(GL11.GL_ZERO, GL11.GL_DST_COLOR);
            GuiHelper.drawTexturedModalRectStretchedWithUVOffset(this.guiLeft + progressClockX, this.guiTop + progressClockY, this.zLevel, 32, 32, -millisF * 2.0F, millisF);
            GlStateManager.blendFunc(GL11.GL_DST_COLOR, GL11.GL_SRC_COLOR);
            GuiHelper.drawTexturedModalRectStretchedWithUVOffset(this.guiLeft + progressClockX, this.guiTop + progressClockY, this.zLevel, 32, 32, -millisF * 2.0F, -millisF * 2.0F);

            Minecraft.getMinecraft().renderEngine.bindTexture(PROGRESS_CLOCK_TEMPORAL);
            GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_DST_COLOR);
            GlStateManager.color(color, color, color, stateChangeTween * 0.1F);
            GuiHelper.drawTexturedModalRectStretched(this.guiLeft + this.progressClockX, this.guiTop + this.progressClockY, zLevel, 32, 32);
        }


        //Render main background
        GlStateManager.disableBlend();
        GlStateManager.color(color, color, color, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(background);
        GuiHelper.drawTexturedModalRectStretched(this.guiLeft, this.guiTop, this.zLevel, this.xSize, this.ySize);


        //Render power bar.
        float powerPercentage = 0; //Technically not a percentage. Ranges from 0 to 1 (empty to full).
        IEnergyStorage energyStorage = tileEntity.energyStorage;
        if(energyStorage != null && energyStorage.getMaxEnergyStored() > 0)
        {
            powerPercentage = (float) energyStorage.getEnergyStored() / (float) energyStorage.getMaxEnergyStored();
        }

        Minecraft.getMinecraft().renderEngine.bindTexture(POWER_BAR_RED);
        GlStateManager.color(color, color, color, 1.0F);

        GuiHelper.drawTexturedModalRectCutTop(this.guiLeft + powerBarX, this.guiTop + powerBarY, this.zLevel, 13, 68, (int) (powerPercentage * 68.0F));
        GlStateManager.disableBlend();


        //Render progress hand
        float rotationAngle = tileEntity.getProgressScaled(360);
        Minecraft.getMinecraft().renderEngine.bindTexture(PROGRESS_HAND);
        GlStateManager.translate(this.guiLeft + this.progressClockX + 16, this.guiTop + this.progressClockY + 16, 0);
        GlStateManager.rotate(rotationAngle, 0, 0, 1);
        GlStateManager.translate(-(this.guiLeft + this.progressClockX + 16), -(this.guiTop + this.progressClockY + 16), 0);
        GuiHelper.drawTexturedModalRectStretched(this.guiLeft + this.progressClockX, this.guiTop + this.progressClockY, this.zLevel, 32, 32);
        GlStateManager.popMatrix();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        if(!isInConfigState() && player.inventory.getItemStack().isEmpty())
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
                float offColor = 0.7F;
                mc.renderEngine.bindTexture(SIDE_CONFIG_ARROWS);
                GlStateManager.color(offColor, offColor, offColor);

                int x, y;

                //CENTER
                x = lastClicked.left;
                y = lastClicked.top + (lastClicked.height / 2) - 8;
                if(isPointInRegion(x, y, 16, 16, mouseX, mouseY))
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    GuiHelper.drawTexturedModalRect(x, y, 16, 0, 16, 16, 64, zLevel);
                    GlStateManager.color(offColor, offColor, offColor);
                }
                else
                {
                    GuiHelper.drawTexturedModalRect(x, y, 16, 0, 16, 16, 64, zLevel);
                }


                //UP
                x = lastClicked.left;
                y = (int) ((lastClicked.top + (lastClicked.height / 2) - 8) - 18 * ((float) timeSinceLastConfigClick / (float) tweenTicks));
                if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, 16, 16, mouseX, mouseY))
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    GuiHelper.drawTexturedModalRect(x, y, 16, 32, 16, 16, 64, zLevel);
                    GlStateManager.color(offColor, offColor, offColor);
                }
                else
                {
                    GuiHelper.drawTexturedModalRect(x, y, 16, 32, 16, 16, 64, zLevel);
                }


                //DOWN
                x = lastClicked.left;
                y = (int) ((lastClicked.top + (lastClicked.height / 2) - 8) + 18 * ((float) timeSinceLastConfigClick / (float) tweenTicks));
                if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, 16, 16, mouseX, mouseY))
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    GuiHelper.drawTexturedModalRect(x, y, 0, 16, 16, 16, 64, zLevel);
                    GlStateManager.color(offColor, offColor, offColor);
                }
                else
                {
                    GuiHelper.drawTexturedModalRect(x, y, 0, 16, 16, 16, 64, zLevel);
                }


                //RIGHT
                x = (int) (lastClicked.left + (lastClicked.width + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
                y = lastClicked.top + (lastClicked.height / 2) - 8;
                if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, 16, 16, mouseX, mouseY))
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    GuiHelper.drawTexturedModalRect(x, y, 0, 32, 16, 16, 64, zLevel);
                    GlStateManager.color(offColor, offColor, offColor);
                }
                else
                {
                    GuiHelper.drawTexturedModalRect(x, y, 0, 32, 16, 16, 64, zLevel);
                }


                //LEFT
                x = (int) (lastClicked.left - (lastClicked.width + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
                y = lastClicked.top + (lastClicked.height / 2) - 8;
                if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, 16, 16, mouseX, mouseY))
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    GuiHelper.drawTexturedModalRect(x, y, 16, 16, 16, 16, 64, zLevel);
                    GlStateManager.color(offColor, offColor, offColor);
                }
                else
                {
                    GuiHelper.drawTexturedModalRect(x, y, 16, 16, 16, 16, 64, zLevel);
                }


                //BACK
                x = (int) (lastClicked.left - (lastClicked.width + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
                y = (int) ((lastClicked.top + (lastClicked.height / 2) - 8) - 18 * ((float) timeSinceLastConfigClick / (float) tweenTicks));
                if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, 16, 16, mouseX, mouseY))
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    GuiHelper.drawTexturedModalRect(x, y, 0, 0, 16, 16, 64, zLevel);
                    GlStateManager.color(offColor, offColor, offColor);
                }
                else
                {
                    GuiHelper.drawTexturedModalRect(x, y, 0, 0, 16, 16, 64, zLevel);
                }
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
            y = lastClicked.top + (lastClicked.height / 2) - 8;
            if(isPointInRegion(x, y, 16, 16, mouseX, mouseY))
            {
                for(int i = 0; i < 6; i++)
                {
                    lastClicked.onIOChange(EnumFacing.getFront(i), false); //Deactivate all sides.
                }
            }

            //UP
            x = lastClicked.left;
            y = (int) ((lastClicked.top + (lastClicked.height / 2) - 8) - 18 * ((float) timeSinceLastConfigClick / (float) tweenTicks));
            if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, 16, 16, mouseX, mouseY))
            {
                direction = EnumFacing.UP;
                lastClicked.onIOChange(direction, !lastClicked.isActiveOnSide(direction));
            }

            //DOWN
            x = lastClicked.left;
            y = (int) ((lastClicked.top + (lastClicked.height / 2) - 8) + 18 * ((float) timeSinceLastConfigClick / (float) tweenTicks));
            if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, 16, 16, mouseX, mouseY))
            {
                direction = EnumFacing.DOWN;
                lastClicked.onIOChange(direction, !lastClicked.isActiveOnSide(direction));
            }

            //RIGHT
            x = (int) (lastClicked.left + (lastClicked.width + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
            y = lastClicked.top + (lastClicked.height / 2) - 8;
            if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, 16, 16, mouseX, mouseY))
            {
                direction = EnumFacing.EAST;
                lastClicked.onIOChange(direction, !lastClicked.isActiveOnSide(direction));
            }

            //LEFT
            x = (int) (lastClicked.left - (lastClicked.width + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
            y = lastClicked.top + (lastClicked.height / 2) - 8;
            if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, 16, 16, mouseX, mouseY))
            {
                direction = EnumFacing.WEST;
                lastClicked.onIOChange(direction, !lastClicked.isActiveOnSide(direction));
            }

            //BACK
            x = (int) (lastClicked.left - (lastClicked.width + 2) * ((float) timeSinceLastConfigClick / (float) tweenTicks));
            y = (int) ((lastClicked.top + (lastClicked.height / 2) - 8) - 18 * ((float) timeSinceLastConfigClick / (float) tweenTicks));
            if(timeSinceLastConfigClick >= tweenTicks && isPointInRegion(x, y, 16, 16, mouseX, mouseY))
            {
                direction = EnumFacing.SOUTH;
                lastClicked.onIOChange(direction, !lastClicked.isActiveOnSide(direction));
            }

            lastClicked = null;
        }
    }

    protected void renderToolTip(List<String> list, int x, int y)
    {
        this.drawHoveringText(list, x, y, fontRenderer);
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
                ArrayList<String> ret = new ArrayList<>();
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

    public static class HoverableLocationTank extends HoverableLocation
    {
        TileClockworkMachine te;
        int tankID;

        public HoverableLocationTank(int minX, int maxX, int minY, int maxY, TileClockworkMachine te, int tankID) {
            super(minX, maxX, minY, maxY);
            this.te = te;
            this.tankID = tankID;
        }

        @Override
        public List<String> getTooltip(boolean isShiftDown)
        {
            ArrayList<String> ret = new ArrayList<>();
            if(te.fluidTanks != null && te.fluidTanks.length > tankID)
            {
                FluidTankSided tank = te.fluidTanks[tankID];
                FluidStack fluid = tank.getFluid();

                if(fluid != null && fluid.amount > 0)
                {
                    ret.add(fluid.getLocalizedName());
                    ret.add(Colors.GREY + fluid.amount + " mB");
                }
            }
            return ret;
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
            this(slot.xPos, slot.yPos, 16, 16, slot.getSlotIndex(), te, toolTip);
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

    public static class IOConfigurationTank extends IOConfiguration
    {
        public int tankID;
        private List<String> toolTip;
        private List<String> retTip;

        public IOConfigurationTank(int left, int top, int width, int height, int tankID, TileClockworkMachine te, List<String> toolTip) {
            super(left, top, width, height, te);
            this.tankID = tankID;
            this.toolTip = new ArrayList<String>(toolTip.size());
            this.retTip = new ArrayList<String>(toolTip.size());
            for(String s : toolTip)
            {
                this.toolTip.add(s);
                this.retTip.add(s);
            }
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
            te.changeTankIO(direction, tankID, activate);
            PacketHandler.INSTANCE.sendToServer(new MessageTileMachineConfigurationTank(te.getPos(), te.getWorld().provider.getDimension(), tankID, direction, activate));
        }

        @Override
        public boolean isActiveOnSide(EnumFacing direction) {
            return te.fluidTanks[tankID].isAvailableForSide(direction);
        }
    }
}
