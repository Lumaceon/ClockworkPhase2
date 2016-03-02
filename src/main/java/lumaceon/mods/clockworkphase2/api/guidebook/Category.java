package lumaceon.mods.clockworkphase2.api.guidebook;

import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;

public class Category
{
    public String title;
    public ArrayList<Article> articles = new ArrayList<Article>(1);

    public Category(String title) {
        this.title = title;
    }

    public boolean isAvailableForPlayer(EntityPlayer player) {
        return true;
    }

    public void addArticle(Article article)
    {
        for(Article art : articles)
            if(art != null && art.equals(article))
                return;
        articles.add(article);
    }
}
