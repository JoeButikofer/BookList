package mobop.booklist.app.data.generic.book;

public interface IPersistentBook extends IApiBook {
    long getDbId();
    void setDbId(long value);

    String getNotes();
    void setNotes(String value);

    boolean isRead();
    void setRead(boolean value);

    boolean isOwn();
    void setOwn(boolean value);
}
