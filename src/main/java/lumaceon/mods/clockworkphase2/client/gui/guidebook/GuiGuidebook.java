package lumaceon.mods.clockworkphase2.client.gui.guidebook;

import com.mojang.realmsclient.gui.ChatFormatting;
import lumaceon.mods.clockworkphase2.api.guidebook.*;
import lumaceon.mods.clockworkphase2.api.guidebook.renderers.GuidebookCustomRender;
import lumaceon.mods.clockworkphase2.config.ConfigValues;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiGuidebook extends GuiScreen
{
    private final boolean DEVELOPING;

    protected State guiState = State.DEFAULT;
    protected Category selectedCategory;

    protected EntityPlayer player;
    private int bookImageWidth = 144;
    private int bookImageHeight = 178;
    protected int guiTop = 0;
    protected int guiLeft = 0;
    /** holds the slot currently hovered */
    private ItemStack tooltipStack;
    protected ContainerGuidebook inventorySlots;
    private NextPageButton buttonNextPage;
    private NextPageButton buttonPreviousPage;

    private Article currentArticle;
    private int currPage; //Also shows currPage + 1.
    private int bookTotalPages = 2;
    private List<ITextComponent> field_175386_A;
    private int updateCount;
    private boolean rightsideSelected = false;
    private boolean enteringTitle = false;
    private boolean enteringCrafting = false;
    private boolean enteringImage = false;
    private boolean enteringCustom = false;
    private String craftingText = "";
    private String imageText = "";
    private String customText = "";

    //Fixes a bug where actionPerformed is called twice if the state changes and adds a button at the clicked location
    private boolean stateChangedThisTick = false;
    private boolean hoveringOverSlotThisTick = false;

    public GuiGuidebook(EntityPlayer player)
    {
        super();
        DEVELOPING = ConfigValues.DEVELOPING;
        inventorySlots = Article.guidebookContainer;
        int numberOfCategories = 0;
        ArrayList<Category> categories = Categories.CATEGORIES;
        for(Category c : categories)
            if(c != null && c.isAvailableForPlayer(player))
                ++numberOfCategories;
        this.bookTotalPages = numberOfCategories / 7 + 1;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        ++this.updateCount;
    }

    @Override
    public void initGui()
    {
        super.initGui();
        buttonList.clear();
        Keyboard.enableRepeatEvents(true);

        int i = (this.width - this.bookImageWidth) / 2;
        guiLeft = i;
        guiTop = 2;
        this.buttonList.add(this.buttonNextPage = new NextPageButton(0, i + bookImageWidth - 23, bookImageHeight + 7, true));
        this.buttonList.add(this.buttonPreviousPage = new NextPageButton(1, i, bookImageHeight + 7, false));
        this.buttonNextPage.visible = false;
        this.buttonNextPage.enabled = false;
        if(currPage == 0)
        {
            this.buttonPreviousPage.visible = false;
            this.buttonPreviousPage.enabled = false;
        }
        if(guiState == State.DEFAULT)
        {
            ArrayList<Category> categories = Categories.CATEGORIES;
            int index = 2;

            for(Category c : categories)
            {
                if(c != null && c.isAvailableForPlayer(player))
                {
                    if(index >= 2 + currPage*7 && index < 2 + (currPage+1) * 7)
                        this.buttonList.add(new CategoryButton(index, i + 9, 7 + 23*(index - 2 - currPage*7), true, c.title, 1.0F));
                    else if(index >= 2 + (currPage+1) * 7)
                    {
                        buttonNextPage.visible = true;
                        buttonNextPage.enabled = true;
                    }
                    ++index;
                }
            }
        }
        else if(guiState == State.ARTICLE_SELECT)
        {
            int index = 3;
            ArrayList<Article> articles = selectedCategory.articles;
            for(Article a : articles)
            {
                if(index >= 3 + currPage*7 && index < 3 + (currPage+1) * 7)
                    this.buttonList.add(new ArticleButton(index, i + 9, 7 + 10*(index - 3 - currPage*7), true, a.displayName, 0.5F, a, this));
                ++index;
            }
            if(DEVELOPING && index >= 3 + currPage*7 && index < 3 + (currPage+1) * 7)
                this.buttonList.add(new ArticleButton(index, i + 9, 7 + 10*(index - 3 - currPage*7), true, "Create New Article...", 0.5F, null, this));
            this.buttonList.add(new GuiButton(2, i + bookImageWidth / 2 - 25, bookImageHeight + 5, 50, 10, "Back"));
        }
        else if(guiState == State.ARTICLE)
        {
            if(currPage == 0 && currentArticle != null && currentArticle.pages.size() > 2 || DEVELOPING)
            {
                buttonNextPage.enabled = true;
                buttonNextPage.visible = true;
            }
            this.buttonList.add(new GuiButton(2, i + bookImageWidth / 2 - 25, bookImageHeight + 5, 50, 10, "Back"));

            if(DEVELOPING)
            {
                if(enteringTitle)
                    this.buttonList.add(new GuiButton(3, i - 50, 5, 50, 20, "Entering Title..."));
                else
                    this.buttonList.add(new GuiButton(3, i - 50, 5, 50, 20, "Title"));

                if(enteringCrafting)
                    this.buttonList.add(new GuiButton(4, i - 50, 30, 50, 20, craftingText));
                else
                    this.buttonList.add(new GuiButton(4, i - 50, 30, 50, 20, "Recipe"));

                if(enteringImage)
                    this.buttonList.add(new GuiButton(5, i - 50, 55, 50, 20, imageText));
                else
                    this.buttonList.add(new GuiButton(5, i - 50, 55, 50, 20, "Image"));

                if(enteringCustom)
                    this.buttonList.add(new GuiButton(6, i - 50, 80, 50, 20, customText));
                else
                    this.buttonList.add(new GuiButton(6, i - 50, 80, 50, 20, "Custom"));

                this.buttonList.add(new GuiButton(7, i - 50, 105, 50, 20, "Clear Page"));
                this.buttonList.add(new GuiButton(8, i - 50, 130, 50, 20, "Delete Last Page"));
                this.buttonList.add(new GuiButton(9, i - 50, 155, 50, 20, "Save+Exit"));
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        if(stateChangedThisTick)
            return;

        if(button.enabled)
        {
            if(guiState == State.DEFAULT)
            {
                switch(button.id)
                {
                    case 0:
                        if(this.currPage < this.bookTotalPages - 1)
                        {
                            this.currPage += 2;
                            initGui();
                        }
                        break;
                    case 1:
                        if(this.currPage > 0)
                        {
                            this.currPage -= 2;
                            initGui();
                        }
                        break;
                    default:
                        setGuiState(State.ARTICLE_SELECT);
                        selectedCategory = Categories.CATEGORIES.get(button.id - 2 + (7*currPage));
                        initGui();
                        break;
                }
            }
            else if(guiState == State.ARTICLE_SELECT)
            {
                switch(button.id)
                {
                    case 0:
                        if(this.currPage < this.bookTotalPages - 2)
                        {
                            this.currPage += 2;
                            initGui();
                        }
                        break;
                    case 1:
                        if(this.currPage > 0)
                        {
                            this.currPage -= 2;
                            initGui();
                        }
                        break;
                    case 2:
                        setGuiState(State.DEFAULT);
                        initGui();
                        break;
                    default:
                        setGuiState(State.ARTICLE);
                        if(this.selectedCategory.articles.size() > button.id - 3 + (7*currPage))
                            this.currentArticle = this.selectedCategory.articles.get(button.id - 3 + (7*currPage));
                        else if(DEVELOPING)
                        {
                            this.currentArticle = new Article(new NBTTagCompound(), "NEWARTICLE", "NEWARTICLE", ModItems.bugSwatter.getItem());
                            this.currentArticle.pages.add(new ArticlePage("", inventorySlots));
                            this.currentArticle.pages.add(new ArticlePage("", inventorySlots));
                            this.bookTotalPages = 2;
                        }
                        initGui();
                        break;
                }
            }
            else if(guiState == State.ARTICLE)
            {
                switch(button.id)
                {
                    case 0:
                        if(this.currPage < this.currentArticle.pages.size() - 2)
                        {
                            this.currPage += 2;
                            initGui();
                        }
                        else if(DEVELOPING)
                        {
                            this.addNewPage();
                            if(this.currPage < this.currentArticle.pages.size() - 2)
                            {
                                this.currPage += 2;
                                initGui();
                            }
                        }
                        break;
                    case 1:
                        if(this.currPage > 0)
                        {
                            this.currPage -= 2;
                            initGui();
                        }
                        break;
                    case 2:
                        if(DEVELOPING && currentArticle != null && currentArticle.displayName.equals("NEWARTICLE"))
                            GuidebookFileHelper.saveArticleToFile(currentArticle);
                        setGuiState(State.ARTICLE_SELECT);
                        initGui();
                        break;
                    case 3: //TITLE
                        enteringCrafting = false;
                        enteringImage = false;
                        enteringCustom = false;
                        enteringTitle = !enteringTitle;
                        initGui();
                        break;
                    case 4: //CRAFT
                        enteringTitle = false;
                        enteringImage = false;
                        enteringCustom = false;
                        enteringCrafting = !enteringCrafting;
                        initGui();
                        break;
                    case 5: //IMAGE
                        enteringTitle = false;
                        enteringCrafting = false;
                        enteringCustom = false;
                        enteringImage = !enteringImage;
                        initGui();
                        break;
                    case 6: //CUSTOM
                        enteringTitle = false;
                        enteringCrafting = false;
                        enteringImage = false;
                        enteringCustom = !enteringCustom;
                        initGui();
                        break;
                    case 7: //CLEAR
                        enteringTitle = false;
                        enteringCrafting = false;
                        enteringImage = false;
                        enteringCustom = false;
                        ArticlePage page = pageGetCurrent();
                        if(page != null)
                        {
                            page.images = null;
                            page.titleText = "";
                            page.craftingRecipes = null;
                            page.customRenderers = null;
                        }
                        initGui();
                        break;
                    case 8: //DELETE LAST PAGE
                        enteringTitle = false;
                        enteringCrafting = false;
                        enteringImage = false;
                        enteringCustom = false;
                        currentArticle.pages.remove(currentArticle.pages.size() - 1);
                        currentArticle.pages.remove(currentArticle.pages.size() - 1);
                        currPage = 0;
                        break;
                    case 9: //SAVE+EXIT
                        if(DEVELOPING && currentArticle != null)
                            GuidebookFileHelper.saveArticleToFile(currentArticle);
                        setGuiState(State.ARTICLE_SELECT);
                        initGui();
                        break;
                }
            }
        }
    }

    private void addNewPage()
    {
        if(this.currentArticle != null && this.currentArticle.pages != null && this.currentArticle.pages.size() < 50)
        {
            this.currentArticle.pages.add(new ArticlePage("", inventorySlots));
            this.currentArticle.pages.add(new ArticlePage("", inventorySlots));
            ++this.bookTotalPages;
            ++this.bookTotalPages;
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        hoveringOverSlotThisTick = false;
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if(guiState == State.DEFAULT)
            this.mc.getTextureManager().bindTexture(Textures.GUI.BOOK_COVER);
        else if(guiState == State.ARTICLE_SELECT || guiState == State.ARTICLE)
            this.mc.getTextureManager().bindTexture(Textures.GUI.BOOK_INSIDE);

        int i = (this.width - this.bookImageWidth) / 2;
        int j = 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.bookImageWidth, this.bookImageHeight);
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawText(true, mouseX, mouseY);
        drawText(false, mouseX, mouseY);

        InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
        if(hoveringOverSlotThisTick && inventoryplayer.getItemStack() == null && tooltipStack != null)
            this.renderToolTip(tooltipStack, mouseX, mouseY);

        if(stateChangedThisTick)
            stateChangedThisTick = false;
    }

    private void drawText(boolean rightSide, int mouseX, int mouseY)
    {
        int i = (this.width - this.bookImageWidth) / 2;
        int j = 2;
        if(guiState == State.ARTICLE)
        {
            String s4;
            String s5;

            if(this.currentArticle == null || this.currentArticle.pages == null || this.currPage < 0 || this.currPage >= this.currentArticle.pages.size() - 1)
                return;

            ArticlePage page = rightSide ? this.currentArticle.pages.get(currPage + 1) : this.currentArticle.pages.get(currPage);
            if(page == null)
                return;

            s5 = page.pageText;

            if(DEVELOPING && rightSide == rightsideSelected)
            {
                if(this.fontRendererObj.getBidiFlag())
                    s5 = s5 + "_";
                else if(this.updateCount / 6 % 2 == 0)
                    s5 = s5 + "" + ChatFormatting.BLACK + "_";
                else
                    s5 = s5 + "" + ChatFormatting.GRAY + "_";
            }

            //PAGE NUMBERES
            int j1;
            s4 = I18n.format("book.pageIndicator", this.currPage / 2 + 1, this.currentArticle.pages.size() / 2);
            j1 = this.fontRendererObj.getStringWidth(s4);
            this.fontRendererObj.drawString(s4, i + (this.bookImageWidth / 2) - j1/2, j + bookImageHeight + 16, 0xFFFFFF);
            //PAGE NUMBERS

            int yPadding = 5;
            if(page.titleText != null && page.titleText.length() > 0)
            {
                String string = page.titleText;
                if(rightSide)
                    this.drawCenteredStringWithoutShadow(fontRendererObj, string, (i + 150) + 134/2, (j + 9 + yPadding), 0x000000);
                else
                    this.drawCenteredStringWithoutShadow(fontRendererObj, string, (i + 9) + 124/2, (j + 9 + yPadding), 0x000000);

                yPadding += fontRendererObj.FONT_HEIGHT + 8;
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F);
            if(page.craftingRecipes != null)
            {
                for(InventoryGuidebook ig : page.craftingRecipes)
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    GlStateManager.disableLighting();
                    if(ig != null)
                    {
                        this.mc.getTextureManager().bindTexture(Textures.GUI.GUIDEBOOK_CRAFTING);
                        int x;
                        int y;
                        if(rightSide)
                        {
                            x = (i + 150) + 132/2 - 125/2;
                            y = j + 9 + yPadding;
                            this.drawTexturedModalRect(x, y, 0, 0, 125, 58);
                            if(drawCraftingItems(x, y, mouseX, mouseY, ig))
                                hoveringOverSlotThisTick = true;
                        }
                        else
                        {
                            x = (i + 6) + 132/2 - 125/2;
                            y = j + 9 + yPadding;
                            this.drawTexturedModalRect(x, y, 0, 0, 125, 58);
                            if(drawCraftingItems(x, y, mouseX, mouseY, ig))
                                hoveringOverSlotThisTick = true;
                        }
                        yPadding += 58 + 8;
                    }
                }
            }

            GlStateManager.disableLighting();
            if(page.images != null)
            {
                for(String img : page.images)
                {
                    if(img == null)
                        continue;
                    GuidebookRegistry.GuidebookImage image = GuidebookRegistry.getImageForString(img);
                    if(image != null)
                    {
                        this.mc.getTextureManager().bindTexture(image.texture);
                        if(rightSide)
                            this.drawTexturedModalRect((i + 150) + 132/2 - image.xSize/2, j+9+yPadding, 0, 0, image.xSize, image.ySize);
                        else
                            this.drawTexturedModalRect((i + 6) + 132/2 - image.xSize/2, j+9+yPadding, 0, 0, image.xSize, image.ySize);
                        yPadding += image.ySize + 8;
                    }
                }
            }

            if(page.customRenderers != null)
            {
                ItemStack stack = null;
                for(GuidebookCustomRender render : page.customRenderers)
                {
                    if(render == null)
                        continue;
                    GL11.glPushMatrix();

                    int x;
                    int y;
                    if(rightSide)
                        x = i+150;
                    else
                        x = i+6;
                    y = j+9+yPadding;

                    GL11.glTranslatef(x, y, 0.0F);
                    ItemStack temp = render.draw(mouseX - x, mouseY - y, yPadding);
                    if(temp != null)
                        stack = temp;
                    GL11.glPopMatrix();
                }
                if(stack != null)
                {
                    this.tooltipStack = stack;
                    this.hoveringOverSlotThisTick = true;
                }
            }

            float textScale = 0.75F;
            GL11.glScalef(textScale, textScale, 1.0F);
            if(rightSide)
                this.fontRendererObj.drawSplitString(s5, (int) ((i + 10 + bookImageWidth / 2) / textScale), (int) ((j + 9 + yPadding) / textScale), (int) ((bookImageWidth/2 - 15) / textScale), 0);
            else
                this.fontRendererObj.drawSplitString(s5, (int) ((i + 9) / textScale), (int) ((j + 9 + yPadding) / textScale), (int) ((bookImageWidth/2 - 15) / textScale), 0);
            GL11.glScalef(1/textScale, 1/textScale, 1.0F);

            if(this.field_175386_A != null)
            {
                int k1 = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());

                for(int l1 = 0; l1 < k1; ++l1)
                {
                    ITextComponent ichatcomponent2 = this.field_175386_A.get(l1);
                    this.fontRendererObj.drawString(ichatcomponent2.getUnformattedText(), i + 36, j + 16 + 16 + l1 * this.fontRendererObj.FONT_HEIGHT, 0);
                }

                ITextComponent ichatcomponent1 = this.iDunnoWhatThisDoes(mouseX, mouseY);

                if(ichatcomponent1 != null)
                    this.handleComponentHover(ichatcomponent1, mouseX, mouseY);
            }
        }
    }

    /**
     * @return True if mouse hovered over an itemstack, false if not.
     */
    public boolean drawCraftingItems(int x, int y, int mouseX, int mouseY, InventoryGuidebook ig)
    {
        boolean ret = false;
        int i = this.guiLeft;
        int j = this.guiTop;
        GlStateManager.disableRescaleNormal();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        int k = 240;
        int l = 240;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) k / 1.0F, (float) l / 1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        for(int i1 = 0; i1 < ig.getSizeInventory(); ++i1)
        {
            int x1 = i1 == ig.getSizeInventory() - 1 ? 100 : ((i1 % 3) * 18) + 6;
            int y1 = i1 == ig.getSizeInventory() - 1 ? 21 : (((int) Math.floor(i1 / 3.0F)) * 18) + 3;
            ItemStack stack = ig.getStackInSlot(i1);
            this.drawSlot(x1+x, y1+y, stack);

            if(this.isMouseOverSlot(x1+x-i, y1+y-1-j, mouseX, mouseY))
            {
                this.tooltipStack = stack;
                if(stack != null)
                    ret = true;
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                GlStateManager.colorMask(true, true, true, false);
                this.drawGradientRect(x1+x, y1+y, x1+x + 16, y1+y + 16, -2130706433, -2130706433);
                GlStateManager.colorMask(true, true, true, true);
                GlStateManager.enableLighting();
                GlStateManager.enableDepth();
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.enableDepth();
        RenderHelper.enableStandardItemLighting();
        return ret;
    }

    private void drawSlot(int x, int y, ItemStack itemstack)
    {
        this.zLevel = 100.0F;
        this.itemRender.zLevel = 100.0F;

        GlStateManager.enableDepth();
        this.itemRender.renderItemAndEffectIntoGUI(itemstack, x, y);
        this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, itemstack, x, y, null);

        this.itemRender.zLevel = 0.0F;
        this.zLevel = 0.0F;
    }

    //Originally func_176385_b
    private ITextComponent iDunnoWhatThisDoes(int mouseX, int mouseY)
    {
        if(this.field_175386_A == null)
            return null;
        else
        {
            int i = mouseX - (this.width - this.bookImageWidth) / 2 - 36;
            int j = mouseY - 2 - 16 - 16;

            if (i >= 0 && j >= 0)
            {
                int k = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());

                if (i <= 116 && j < this.mc.fontRendererObj.FONT_HEIGHT * k + k)
                {
                    int l = j / this.mc.fontRendererObj.FONT_HEIGHT;

                    if (l >= 0 && l < this.field_175386_A.size())
                    {
                        ITextComponent ichatcomponent = this.field_175386_A.get(l);
                        int i1 = 0;

                        for (ITextComponent ichatcomponent1 : ichatcomponent)
                        {
                            if(ichatcomponent1 != null)
                            {
                                i1 += this.mc.fontRendererObj.getStringWidth(ichatcomponent1.getFormattedText());

                                if (i1 > i)
                                {
                                    return ichatcomponent1;
                                }
                            }
                        }
                    }

                    return null;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
    }

    public void drawCenteredStringWithoutShadow(FontRenderer fontRendererIn, String text, int x, int y, int color) {
        fontRendererIn.drawString(text, (float)(x - fontRendererIn.getStringWidth(text) / 2), (float)y, color, false);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        if(DEVELOPING)
            this.keyTypedInBook(typedChar, keyCode);
    }

    private void keyTypedInBook(char typedChar, int keyCode)
    {
        if(enteringTitle)
        {
            String string = this.pageGetCurrent().titleText;
            switch(keyCode)
            {
                case 14: //Backspace.
                    if(string.length() > 0)
                        this.pageGetCurrent().titleText = string.substring(0, string.length() - 1);
                    return;
                case 28: //Enter
                case 156: //Probably the enter that is sometimes on another place on the keyboard.
                    this.pageGetCurrent().titleText = string.concat("\n");
                    return;
                default: //Any other character.
                    if(ChatAllowedCharacters.isAllowedCharacter(typedChar))
                        this.pageGetCurrent().titleText = string.concat(Character.toString(typedChar));
                    return;
            }
        }
        if(enteringCrafting)
        {
            String string = this.craftingText;
            switch(keyCode)
            {
                case 14: //Backspace.
                    if(string.length() > 0)
                        this.craftingText = string.substring(0, string.length() - 1);
                    initGui();
                    return;
                case 28: //Enter
                case 156: //Probably the enter that is sometimes on another place on the keyboard.
                    if(this.pageGetCurrent() != null)
                        this.pageGetCurrent().addCraftingRecipeFromKey(this.craftingText);
                    this.craftingText = "";
                    this.enteringCrafting = false;
                    initGui();
                    return;
                default: //Any other character.
                    if(ChatAllowedCharacters.isAllowedCharacter(typedChar))
                        this.craftingText = string.concat(Character.toString(typedChar));
                    initGui();
                    return;
            }
        }
        if(enteringImage)
        {
            String string = this.imageText;
            switch(keyCode)
            {
                case 14: //Backspace.
                    if(string.length() > 0)
                        this.imageText = string.substring(0, string.length() - 1);
                    initGui();
                    return;
                case 28: //Enter
                case 156: //Probably the enter that is sometimes on another place on the keyboard.
                    this.pageGetCurrent().addImageFromKey(this.imageText);
                    this.imageText = "";
                    this.enteringImage = false;
                    initGui();
                    return;
                default: //Any other character.
                    if(ChatAllowedCharacters.isAllowedCharacter(typedChar))
                        this.imageText = string.concat(Character.toString(typedChar));
                    initGui();
                    return;
            }
        }

        if(enteringCustom)
        {
            String string = this.customText;
            switch(keyCode)
            {
                case 14: //Backspace.
                    if(string.length() > 0)
                        this.customText = string.substring(0, string.length() - 1);
                    initGui();
                    return;
                case 28: //Enter
                case 156: //Probably the enter that is sometimes on another place on the keyboard.
                    this.pageGetCurrent().addCustomFromKey(this.customText);
                    this.customText = "";
                    this.enteringImage = false;
                    initGui();
                    return;
                default: //Any other character.
                    if(ChatAllowedCharacters.isAllowedCharacter(typedChar))
                        this.customText = string.concat(Character.toString(typedChar));
                    initGui();
                    return;
            }
        }


        if(GuiScreen.isKeyComboCtrlV(keyCode))
            this.pageInsertIntoCurrent(GuiScreen.getClipboardString());
        else
        {
            switch(keyCode)
            {
                case 14:
                    String s = this.pageGetCurrent().pageText;
                    if(s.length() > 0)
                        this.pageSetCurrent(s.substring(0, s.length() - 1));
                    return;
                case 28:
                case 156:
                    this.pageInsertIntoCurrent("\n");
                    return;
                default:
                    if(ChatAllowedCharacters.isAllowedCharacter(typedChar))
                        this.pageInsertIntoCurrent(Character.toString(typedChar));
            }
        }
    }

    private ArticlePage pageGetCurrent() {
        if(rightsideSelected)
            return this.currentArticle != null && this.currentArticle.pages != null && this.currPage+1 >= 0 && this.currPage+1 < this.currentArticle.pages.size() ? this.currentArticle.pages.get(this.currPage+1) : null;
        else
            return this.currentArticle != null && this.currentArticle.pages != null && this.currPage >= 0 && this.currPage < this.currentArticle.pages.size() ? this.currentArticle.pages.get(this.currPage) : null;
    }

    /**
     * Sets the text of the current page as determined by currPage
     */
    private void pageSetCurrent(String p_146457_1_) {
        if(this.currentArticle != null && this.currentArticle.pages != null && this.currPage >= 0 && this.currPage < this.currentArticle.pages.size())
        {
            if(rightsideSelected)
                this.currentArticle.pages.get(this.currPage+1).pageText =  p_146457_1_;
            else
                this.currentArticle.pages.get(this.currPage).pageText =  p_146457_1_;
        }
    }

    private void pageInsertIntoCurrent(String p_146459_1_)
    {
        String s = this.pageGetCurrent().pageText;
        String s1 = s + p_146459_1_;
        int i = this.fontRendererObj.splitStringWidth(s1 + "" + ChatFormatting.BLACK + "_", 118);

        if (i <= 512 && s1.length() < 1000)
        {
            this.pageSetCurrent(s1);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if(mouseButton == 0)
        {
            int i = (this.width - this.bookImageWidth) / 2;
            int j = 2;
            if(mouseX >= i && mouseY >= j && mouseX < i + this.bookImageWidth / 2 && mouseY < j + this.bookImageHeight)
                rightsideSelected = false;
            else if(mouseX >= i && mouseY >= j && mouseX < i + this.bookImageWidth && mouseY < j + this.bookImageHeight)
                rightsideSelected = true;

            ITextComponent ichatcomponent = this.iDunnoWhatThisDoes(mouseX, mouseY);
            if(this.handleComponentClick(ichatcomponent))
                return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    public void setGuiState(State state)
    {
        if(state == guiState)
            return;
        if(state == State.DEFAULT)
        {
            bookImageWidth = 144;
            bookImageHeight = 178;
            int numberOfCategories = 0;
            ArrayList<Category> categories = Categories.CATEGORIES;
            for(Category c : categories)
                if(c != null && c.isAvailableForPlayer(player))
                    ++numberOfCategories;
            this.bookTotalPages = numberOfCategories / 7 + 1;
        }
        else if(state == State.ARTICLE || state == State.ARTICLE_SELECT)
        {
            bookImageWidth = 288;
            bookImageHeight = 178;
        }
        currPage = 0;
        this.stateChangedThisTick = true;
        this.guiState = state;
    }

    @Override
    public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        Tessellator tessellator = Tessellator.getInstance();
        VertexBuffer renderer = tessellator.getBuffer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex(0, 1).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex(1, 1).endVertex();
        renderer.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex(1, 0).endVertex();
        renderer.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex(0, 0).endVertex();
        tessellator.draw();
    }

    /**
     * Returns if the passed mouse position is over the specified slot.
     */
    private boolean isMouseOverSlot(int x, int y, int mouseX, int mouseY) {
        return this.isPointInRegion(x, y, 16, 16, mouseX, mouseY);
    }

    /**
     * Test if the 2D point is in a rectangle (relative to the GUI). Args : rectX, rectY, rectWidth, rectHeight, pointX,
     * pointY
     */
    protected boolean isPointInRegion(int left, int top, int right, int bottom, int pointX, int pointY)
    {
        int i = this.guiLeft;
        int j = this.guiTop;
        pointX = pointX - i;
        pointY = pointY - j;
        return pointX >= left - 1 && pointX < left + right + 1 && pointY >= top - 1 && pointY < top + bottom + 1;
    }

    private static final ResourceLocation bookGuiTextures = new ResourceLocation("textures/gui/book.png");
    @SideOnly(Side.CLIENT)
    static class NextPageButton extends GuiButton
    {
        private final boolean field_146151_o;

        public NextPageButton(int p_i46316_1_, int p_i46316_2_, int p_i46316_3_, boolean p_i46316_4_)
        {
            super(p_i46316_1_, p_i46316_2_, p_i46316_3_, 23, 13, "");
            this.field_146151_o = p_i46316_4_;
        }

        /**
         * Draws this button to the screen.
         */
        public void drawButton(Minecraft mc, int mouseX, int mouseY)
        {
            if(this.visible)
            {
                boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(GuiGuidebook.bookGuiTextures);
                int i = 0;
                int j = 192;

                if(flag)
                    i += 23;

                if(!this.field_146151_o)
                    j += 13;

                super.drawTexturedModalRect(this.xPosition, this.yPosition, i, j, 23, 13);
            }
        }
    }

    private static final ResourceLocation category = new ResourceLocation(Reference.MOD_ID, "textures/gui/guidebook_category.png");
    @SideOnly(Side.CLIENT)
    static class CategoryButton extends GuiButton
    {
        private final boolean field_146151_o;
        private String displayString;
        private float textScale;

        public CategoryButton(int p_i46316_1_, int p_i46316_2_, int p_i46316_3_, boolean p_i46316_4_, String displayString, float textScale)
        {
            super(p_i46316_1_, p_i46316_2_, p_i46316_3_, 128, 25, "");
            this.field_146151_o = p_i46316_4_;
            this.displayString = displayString;
            this.textScale = textScale;
        }

        /**
         * Draws this button to the screen.
         */
        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY)
        {
            if(this.visible)
            {
                boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                mc.getTextureManager().bindTexture(GuiGuidebook.category);

                drawTexturedModalRect(this.xPosition, this.yPosition, 0, 0, this.width, this.height);
                if(textScale != 1.0F)
                {
                    GL11.glScalef(textScale, textScale, textScale);
                    drawCenteredString(mc.fontRendererObj, displayString, (int) ((xPosition + this.width / 2) * (1 / textScale)), (int) ((yPosition + (this.height / 2)) * (1 / textScale)) - (mc.fontRendererObj.FONT_HEIGHT / 2) + 1, 0xFFFFFF);
                    GL11.glScalef(1.0F / textScale, 1.0F / textScale, 1.0F / textScale);
                }
                else
                    drawCenteredString(mc.fontRendererObj, displayString, xPosition + this.width / 2, yPosition + (this.height / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2) + 1, 0xFFFFFF);
            }
        }

        @Override
        public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
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

    @SideOnly(Side.CLIENT)
    static class ArticleButton extends GuiButton
    {
        private final boolean field_146151_o;
        private String displayString;
        private float scale;
        private Article article;
        private GuiGuidebook gui;

        public ArticleButton(int p_i46316_1_, int p_i46316_2_, int p_i46316_3_, boolean p_i46316_4_, String displayString, float scale, Article article, GuiGuidebook gui)
        {
            super(p_i46316_1_, p_i46316_2_, p_i46316_3_, 128, 10, "");
            this.field_146151_o = p_i46316_4_;
            this.displayString = displayString;
            this.scale = scale;
            this.article = article;
            this.gui = gui;
        }

        /**
         * Draws this button to the screen.
         */
        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY)
        {
            if(this.visible)
            {
                boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                //mc.getTextureManager().bindTexture(GuiGuidebook.category);
                //drawTexturedModalRect(this.xPosition, this.yPosition, 0, 0, this.width, this.height);

                if(article != null && article.texture != null)
                {
                    mc.getTextureManager().bindTexture(article.texture);
                    drawTexturedModalRect(this.xPosition, this.yPosition, 0, 0, (int) (16 * scale), (int) (16 * scale));
                }
                else if(article != null && article.textureItem != null)
                {
                    GL11.glScalef(scale, scale, 1.0F);
                    drawItemStack(new ItemStack(article.textureItem), (int) (this.xPosition * (1/scale)), (int) (this.yPosition * (1/scale)), "missingTex");
                    GL11.glScalef(1.0F / scale, 1.0F / scale, 1.0F);
                }

                if(scale != 1.0F)
                {
                    GL11.glScalef(scale, scale, 1.0F);
                    drawCenteredString(mc.fontRendererObj, displayString, (int) ((xPosition + this.width / 2) * (1 / scale)), (int) ((yPosition + (this.height / 2)) * (1 / scale)) - (mc.fontRendererObj.FONT_HEIGHT / 2) + 1, 0xFFFFFF);
                    GL11.glScalef(1.0F / scale, 1.0F / scale, 1.0F);
                }
                else
                    drawCenteredString(mc.fontRendererObj, displayString, xPosition + this.width / 2, yPosition + (this.height / 2) - (mc.fontRendererObj.FONT_HEIGHT / 2) + 1, 0xFFFFFF);
            }
        }

        @Override
        public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
            Tessellator tessellator = Tessellator.getInstance();
            VertexBuffer renderer = tessellator.getBuffer();
            renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
            renderer.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex(0, 1).endVertex();
            renderer.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex(1, 1).endVertex();
            renderer.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex(1, 0).endVertex();
            renderer.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex(0, 0).endVertex();
            tessellator.draw();
        }

        private void drawItemStack(ItemStack stack, int x, int y, String altText)
        {
            if(gui.itemRender == null)
                gui.itemRender = gui.mc.getRenderItem();
            GlStateManager.translate(0.0F, 0.0F, 32.0F);
            this.gui.zLevel = 200.0F;
            this.gui.itemRender.zLevel = 200.0F;
            this.gui.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
            this.gui.zLevel = 0.0F;
            this.gui.itemRender.zLevel = 0.0F;
        }
    }

    public static enum State {
        DEFAULT, ARTICLE_SELECT, ARTICLE
    }
}
