package mobop.booklist.app.data.generic;

import android.graphics.Bitmap;

public interface IBook {

    public String getName();
    public void setName(String value);

    public String getGenre(); //TODO peut etre mieux avec un enum nan ?, mais pas forcement compatible avec le webservice (et on fait quoi pour les multigenres ???)
    public void setGenre(String value);

    public int getPages();
    public void setPages(int value);

    public int getRatings(); //TODO pour l'instant, c'est un entier, a voir
    public void setRatings(int value);

    public Bitmap getImage();
    public void setImage(Bitmap value);
}
