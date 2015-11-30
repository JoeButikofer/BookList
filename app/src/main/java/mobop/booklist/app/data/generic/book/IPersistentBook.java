package mobop.booklist.app.data.generic.book;

public interface IPersistentBook extends IApiBook {
    long getDbId();
    void setDbId(long value);

    String getNotes();
    void setNotes(String value);

    boolean isToRead();
    void setToRead(boolean value);

    boolean isOwn();
    void setOwn(boolean value);

    boolean isWish();
    void setWish(boolean value);

    boolean isFavorite();
    void setFavorite(boolean value);
}
