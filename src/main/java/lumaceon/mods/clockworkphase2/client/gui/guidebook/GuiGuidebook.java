package lumaceon.mods.clockworkphase2.client.gui.guidebook;

import lumaceon.mods.clockworkphase2.api.guidebook.*;
import lumaceon.mods.clockworkphase2.init.ModItems;
import lumaceon.mods.clockworkphase2.lib.Reference;
import lumaceon.mods.clockworkphase2.lib.Textures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiGuidebook extends GuiScreen
{
    private static final boolean DEVELOPING = true;

    protected State guiState = State.DEFAULT;
    protected Category selectedCategory;

    protected EntityPlayer player;
    private int bookImageWidth = 144;
    private int bookImageHeight = 178;
    private NextPageButton buttonNextPage;
    private NextPageButton buttonPreviousPage;

    private Article currentArticle;
    private int currPage; //Also shows currPage + 1 if there IS a currPage+1.
    private int bookTotalPages = 1;
    private int field_175387_B = -1;
    private List<IChatComponent> field_175386_A;
    private String bookTitle = "";
    private int updateCount;
    private boolean rightsideSelected = false;

    //Fixes a bug where actionPerformed is called twice if the state changes and adds a button at the clicked location
    private boolean stateChangedThisTick = false;

    public GuiGuidebook(EntityPlayer player)
    {
        super();
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
            if(currPage == 0 && bookTotalPages > 2 || DEVELOPING)
            {
                buttonNextPage.enabled = true;
                buttonNextPage.visible = true;
            }
            this.buttonList.add(new GuiButton(2, i + bookImageWidth / 2 - 25, bookImageHeight + 5, 50, 10, "Back"));
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
                            this.currentArticle.pages.appendTag(new NBTTagString(""));
                            this.currentArticle.pages.appendTag(new NBTTagString(""));
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
                        if(this.currPage < this.bookTotalPages - 2)
                        {
                            this.currPage += 2;
                            initGui();
                        }
                        else if(DEVELOPING)
                        {
                            this.addNewPage();
                            if(this.currPage < this.bookTotalPages - 2)
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
                    default:
                        if(DEVELOPING && currentArticle != null && currentArticle.displayName.equals("NEWARTICLE"))
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
        if(this.currentArticle != null && this.currentArticle.pages != null && this.currentArticle.pages.tagCount() < 50)
        {
            this.currentArticle.pages.appendTag(new NBTTagString(""));
            this.currentArticle.pages.appendTag(new NBTTagString(""));
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
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if(guiState == State.DEFAULT)
            this.mc.getTextureManager().bindTexture(Textures.GUI.BOOK_COVER);
        else if(guiState == State.ARTICLE_SELECT || guiState == State.ARTICLE)
            this.mc.getTextureManager().bindTexture(Textures.GUI.BOOK_INSIDE);

        int i = (this.width - this.bookImageWidth) / 2;
        int j = 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.bookImageWidth, this.bookImageHeight);
        super.drawScreen(mouseX, mouseY, partialTicks);
        drawText(true, mouseX, mouseY, partialTicks);
        drawText(false, mouseX, mouseY, partialTicks);

        if(stateChangedThisTick)
            stateChangedThisTick = false;
    }

    private void drawText(boolean rightSide, int mouseX, int mouseY, float partialTicks)
    {
        int i = (this.width - this.bookImageWidth) / 2;
        int j = 2;
        if(guiState == State.ARTICLE)
        {
            String s4;
            String s5 = "";

            if(this.currentArticle != null && this.currentArticle.pages != null && this.currPage >= 0 && this.currPage < this.currentArticle.pages.tagCount() - 1)
                s5 = rightSide ? this.currentArticle.pages.getStringTagAt(this.currPage + 1) : this.currentArticle.pages.getStringTagAt(this.currPage);

            if(DEVELOPING && rightSide == rightsideSelected)
            {
                if(this.fontRendererObj.getBidiFlag())
                    s5 = s5 + "_";
                else if(this.updateCount / 6 % 2 == 0)
                    s5 = s5 + "" + EnumChatFormatting.BLACK + "_";
                else
                    s5 = s5 + "" + EnumChatFormatting.GRAY + "_";
            }

            //PAGE NUMBERES
            int j1;
            /*if(rightSide)
            {
                s4 = I18n.format("book.pageIndicator", this.currPage + 2, this.bookTotalPages/2);
                j1 = this.fontRendererObj.getStringWidth(s4);
                this.fontRendererObj.drawString(s4, i - j1 + this.bookImageWidth - 42, j + 16, 0xFFFFFF);
            }
            else
            {*/
                s4 = I18n.format("book.pageIndicator", this.currPage/2 + 1, this.bookTotalPages/2);
                j1 = this.fontRendererObj.getStringWidth(s4);
                this.fontRendererObj.drawString(s4, i + (this.bookImageWidth / 2) - j1/2, j + bookImageHeight + 16, 0xFFFFFF);
            //}
            //PAGE NUMBERES

            int yPadding = 0;
            if(s5.startsWith("[IMAGE=") && s5.contains("]"))
            {
                String imageName = s5.substring(7, s5.indexOf(']'));
                GuidebookImageRegistry.GuidebookImage image = GuidebookImageRegistry.getImageFromID(imageName);
                if(image != null)
                {
                    this.mc.getTextureManager().bindTexture(image.texture);
                    if(rightSide)
                        this.drawTexturedModalRect((i + 150) + 132/2 - image.xSize/2, j+9, 0, 0, image.xSize, image.ySize);
                    else
                        this.drawTexturedModalRect((i + 6) + 132/2 - image.xSize/2, j+9, 0, 0, image.xSize, image.ySize);
                    yPadding = image.ySize + 5;
                }

                s5 = s5.substring(s5.indexOf(']') + 1);
            }

            float textScale = 1.0F;
            if(s5.startsWith("[SIZE=") && s5.contains("]"))
            {
                String sizeString = s5.substring(6, s5.indexOf(']'));
                try { textScale = Integer.parseInt(sizeString) * 0.1F; }
                catch(NumberFormatException ignored) {}

                s5 = s5.substring(s5.indexOf(']') + 1);
            }

            if(textScale <= 0.0F)
                textScale = 0.1F;
            GL11.glScalef(textScale, textScale, 1.0F);
            if(rightSide)
                this.fontRendererObj.drawSplitString(s5, (int) ((i + 10 + bookImageWidth / 2) / textScale), (int) ((j + 9 + yPadding) / textScale), (int) ((bookImageWidth/2 - 15) / textScale), 0);
            else
                this.fontRendererObj.drawSplitString(s5, (int) ((i + 9) / textScale), (int) ((j + 9 + yPadding) / textScale), (int) ((bookImageWidth/2 - 15) / textScale), 0);
            GL11.glScalef(1/textScale, 1/textScale, 1.0F);
            /*if(this.field_175386_A != null)
            {
                int k1 = Math.min(128 / this.fontRendererObj.FONT_HEIGHT, this.field_175386_A.size());

                for(int l1 = 0; l1 < k1; ++l1)
                {
                    IChatComponent ichatcomponent2 = this.field_175386_A.get(l1);
                    this.fontRendererObj.drawString(ichatcomponent2.getUnformattedText(), i + 36, j + 16 + 16 + l1 * this.fontRendererObj.FONT_HEIGHT, 0);
                }

                IChatComponent ichatcomponent1 = this.iDunnoWhatThisDoes(mouseX, mouseY);

                if(ichatcomponent1 != null)
                    this.handleComponentHover(ichatcomponent1, mouseX, mouseY);
            }*/
        }
    }

    //Originally func_176385_b
    private IChatComponent iDunnoWhatThisDoes(int mouseX, int mouseY)
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
                        IChatComponent ichatcomponent = this.field_175386_A.get(l);
                        int i1 = 0;

                        for (IChatComponent ichatcomponent1 : ichatcomponent)
                        {
                            if (ichatcomponent1 instanceof ChatComponentText)
                            {
                                i1 += this.mc.fontRendererObj.getStringWidth(((ChatComponentText)ichatcomponent1).getChatComponentText_TextValue());

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

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        if(DEVELOPING)
            this.keyTypedInBook(typedChar, keyCode);
    }

    private void keyTypedInBook(char typedChar, int keyCode)
    {
        if (GuiScreen.isKeyComboCtrlV(keyCode))
        {
            this.pageInsertIntoCurrent(GuiScreen.getClipboardString());
        }
        else
        {
            switch (keyCode)
            {
                case 14:
                    String s = this.pageGetCurrent();

                    if (s.length() > 0)
                    {
                        this.pageSetCurrent(s.substring(0, s.length() - 1));
                    }

                    return;
                case 28:
                case 156:
                    this.pageInsertIntoCurrent("\n");
                    return;
                default:

                    if (ChatAllowedCharacters.isAllowedCharacter(typedChar))
                    {
                        this.pageInsertIntoCurrent(Character.toString(typedChar));
                    }
            }
        }
    }

    /**
     * Processes keystrokes when editing the title of a book
     */
    private void keyTypedInTitle(char p_146460_1_, int p_146460_2_) throws IOException
    {
        switch (p_146460_2_)
        {
            case 14:

                if (!this.bookTitle.isEmpty())
                {
                    this.bookTitle = this.bookTitle.substring(0, this.bookTitle.length() - 1);
                    //this.updateButtons();
                }

                return;
            case 28:
            case 156:

                if (!this.bookTitle.isEmpty())
                {
                    //this.sendBookToServer(true);
                    this.mc.displayGuiScreen(null);
                }

                return;
            default:

                if (this.bookTitle.length() < 16 && ChatAllowedCharacters.isAllowedCharacter(p_146460_1_))
                {
                    this.bookTitle = this.bookTitle + Character.toString(p_146460_1_);
                    //this.updateButtons();
                }
        }
    }

    private String pageGetCurrent() {
        if(rightsideSelected)
            return this.currentArticle != null && this.currentArticle.pages != null && this.currPage+1 >= 0 && this.currPage+1 < this.currentArticle.pages.tagCount() ? this.currentArticle.pages.getStringTagAt(this.currPage+1) : "";
        else
            return this.currentArticle != null && this.currentArticle.pages != null && this.currPage >= 0 && this.currPage < this.currentArticle.pages.tagCount() ? this.currentArticle.pages.getStringTagAt(this.currPage) : "";
    }

    /**
     * Sets the text of the current page as determined by currPage
     */
    private void pageSetCurrent(String p_146457_1_) {
        if(this.currentArticle != null && this.currentArticle.pages != null && this.currPage >= 0 && this.currPage < this.currentArticle.pages.tagCount())
        {
            if(rightsideSelected)
                this.currentArticle.pages.set(this.currPage+1, new NBTTagString(p_146457_1_));
            else
                this.currentArticle.pages.set(this.currPage, new NBTTagString(p_146457_1_));
        }
    }

    private void pageInsertIntoCurrent(String p_146459_1_)
    {
        String s = this.pageGetCurrent();
        String s1 = s + p_146459_1_;
        int i = this.fontRendererObj.splitStringWidth(s1 + "" + EnumChatFormatting.BLACK + "_", 118);

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

            IChatComponent ichatcomponent = this.iDunnoWhatThisDoes(mouseX, mouseY);
            if(this.handleComponentClick(ichatcomponent))
                return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected boolean handleComponentClick(IChatComponent p_175276_1_)
    {
        ClickEvent clickevent = p_175276_1_ == null ? null : p_175276_1_.getChatStyle().getChatClickEvent();

        if(clickevent == null)
            return false;
        else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE)
        {
            String s = clickevent.getValue();
            try
            {
                int i = Integer.parseInt(s) - 1;
                if (i >= 0 && i < this.bookTotalPages && i != this.currPage)
                {
                    this.currPage = i;
                    //this.updateButtons();
                    return true;
                }
            }
            catch(Throwable ignored) {}
            return false;
        }
        else
        {
            boolean flag = super.handleComponentClick(p_175276_1_);

            if(flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
                this.mc.displayGuiScreen(null);

            return flag;
        }
    }

    private void savePages()
    {
        if(this.currentArticle != null && currentArticle.pages != null)
        {
            while(this.currentArticle.pages.tagCount() > 1)
            {
                String s = this.currentArticle.pages.getStringTagAt(this.currentArticle.pages.tagCount() - 1);

                if (s.length() != 0)
                {
                    break;
                }

                this.currentArticle.pages.removeTag(this.currentArticle.pages.tagCount() - 1);
            }

            for(int i = 0; i < this.currentArticle.pages.tagCount(); ++i)
            {
                String s1 = this.currentArticle.pages.getStringTagAt(i);
                IChatComponent ichatcomponent = new ChatComponentText(s1);
                s1 = IChatComponent.Serializer.componentToJson(ichatcomponent);
                this.currentArticle.pages.set(i, new NBTTagString(s1));
            }
        }
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
        WorldRenderer renderer = tessellator.getWorldRenderer();
        renderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        renderer.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex(0, 1).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex(1, 1).endVertex();
        renderer.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex(1, 0).endVertex();
        renderer.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex(0, 0).endVertex();
        tessellator.draw();
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
            WorldRenderer renderer = tessellator.getWorldRenderer();
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
            WorldRenderer renderer = tessellator.getWorldRenderer();
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
            FontRenderer font = null;
            if (stack != null) font = stack.getItem().getFontRenderer(stack);
            if (font == null) font = gui.fontRendererObj;
            this.gui.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
            //this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y - (this.draggedStack == null ? 0 : 8), altText);
            this.gui.zLevel = 0.0F;
            this.gui.itemRender.zLevel = 0.0F;
        }
    }

    public static enum State {
        DEFAULT, ARTICLE_SELECT, ARTICLE
    }
}
