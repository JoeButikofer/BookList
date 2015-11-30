package mobop.booklist.app.data.generic.book;

import android.graphics.Bitmap;
import android.os.Parcelable;

import java.io.Serializable;

public interface IApiBook extends Serializable { //Parcelable would be more efficient but we don't need it for just one object

    String getId();
    void setId(String value);

    String getName();
    void setName(String value);

    String getGenre(); //TODO peut etre mieux avec un enum nan ?, mais pas forcement compatible avec le webservice (et on fait quoi pour les multigenres ???)
    void setGenre(String value);

    int getPages();
    void setPages(int value);

    double getRatings();
    void setRatings(double value);

    String getImagePath();
    void setImagePath(String value);
}
