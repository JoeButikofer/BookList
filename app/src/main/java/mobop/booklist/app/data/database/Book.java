package mobop.booklist.app.data.database;

import mobop.booklist.app.data.generic.book.IApiBook;
import mobop.booklist.app.data.generic.book.IPersistentBook;

public class Book implements IPersistentBook {
    private String id;
    private String name;
    private String genre;
    private int pages;
    private double ratings;
    private String imagePath;
    private String notes;
    private boolean read;
    private boolean own;

    private long dbId;

    public Book() {
        dbId = -1;
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
    public String getNotes() {
        return notes;
    }

    @Override
    public void setNotes(String value) {
        notes = value;
    }

    @Override
    public boolean isRead() {
        return read;
    }

    @Override
    public void setRead(boolean value) {
        read = value;
    }

    @Override
    public boolean isOwn() {
        return own;
    }

    @Override
    public void setOwn(boolean value) {
        own = value;
    }
}
