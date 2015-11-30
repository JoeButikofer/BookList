package mobop.booklist.app.data.generic;

public interface IPersistentSearchManager<T> extends ISearchManager<T> {

    void filterOwn();
    void filterWish();
    void filterToRead();
    void filterFavorite();

}
