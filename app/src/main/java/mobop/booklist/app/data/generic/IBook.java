package mobop.booklist.app.data.generic;

import android.graphics.Bitmap;
import android.os.Parcelable;

import java.io.Serializable;

public interface IBook extends Serializable { //Parcelable would be more efficient but we don't need it for just one object

    public String getName();
    public void setName(String value);

    public String getGenre(); //TODO peut etre mieux avec un enum nan ?, mais pas forcement compatible avec le webservice (et on fait quoi pour les multigenres ???)
    public void setGenre(String value);

    public int getPages();
    public void setPages(int value);

    public double getRatings();
    public void setRatings(double value);

    public String getImagePath();
    public void setImagePath(String value);

    Bitmap getImage();
    void setImage(Bitmap value);
}
