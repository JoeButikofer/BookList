package mobop.booklist.app.data.database;

import android.graphics.Bitmap;

import mobop.booklist.app.data.generic.IBook;

public class Book implements IBook {
    private String id;
    private String name;
    private String genre;
    private int pages;
    private double ratings;
    private String imagePath;
    private Bitmap image;
    private boolean read;

    private long dbId;

    public Book() {
        dbId = -1;
    }

    public Book(IBook item) {
        this();
        this.id = item.getId();
        this.name = item.getName();
        this.genre = item.getGenre();
        this.pages = item.getPages();
        this.ratings = item.getRatings();
        this.read = item.isRead();
        //this.image = item.getImage();
        if (item instanceof Book) {
            Book book = (Book) item;
            this.dbId = book.dbId;
        }
    }

    public long getDbId() {
        return dbId;
    }

    public void setDbId(long value) {
        this.dbId = value;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

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
    public double getRatings() {
        return ratings;
    }

    @Override
    public void setRatings(double value) {
        ratings = value;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public void setImagePath(String value) {
        imagePath = value;
    }

    @Override
    public Bitmap getImage() {
        return image;
    }

    @Override
    public void setImage(Bitmap value) {
        image = value;
    }

    @Override
    public boolean isRead() {
        return read;
    }

    @Override
    public void setRead(boolean value) {
        read = value;
    }
}
