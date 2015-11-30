package mobop.booklist.app.data.database;

import mobop.booklist.app.data.generic.book.IPersistentBook;

public class Book implements IPersistentBook {
    private String id;
    private String name;
    private String genre;
    private int pages;
    private double ratings;
    private String imagePath;
    private String notes;
    private boolean toRead;
    private boolean own;
    private boolean wish;
    private boolean favorite;

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
    public boolean isToRead() {
        return toRead;
    }

    @Override
    public void setToRead(boolean value) {
        toRead = value;
    }

    @Override
    public boolean isOwn() {
        return own;
    }

    @Override
    public void setOwn(boolean value) {
        own = value;
    }

    @Override
    public boolean isWish() {
        return wish;
    }

    @Override
    public void setWish(boolean value) {
        wish = value;
    }

    @Override
    public boolean isFavorite() {
        return favorite;
    }

    @Override
    public void setFavorite(boolean value) {
        favorite = value;
    }
}
