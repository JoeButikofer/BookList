package mobop.booklist.app.data.database;

import android.graphics.Bitmap;
import mobop.booklist.app.data.generic.IBook;

public class Book implements IBook {
    private String name;
    private String genre;
    private int pages;
    private int ratings;
    private Bitmap image;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String value) {
       name = value;
    }

    @Override
    public String getGenre() {
        return genre;
    }

    @Override
    public void setGenre(String value) {
        genre = value;
    }

    @Override
    public int getPages() {
        return pages;
    }

    @Override
    public void setPages(int value) {
        pages = value;
    }

    @Override
    public int getRatings() {
        return ratings;
    }

    @Override
    public void setRatings(int value) {
        ratings = value;
    }

    @Override
    public Bitmap getImage() {
        return image;
    }

    @Override
    public void setImage(Bitmap value) {
        image = Bitmap.createBitmap(value);
    }
}
