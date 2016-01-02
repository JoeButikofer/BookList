package mobop.booklist.app.data.generic;

public interface ISearchManager<T> {

    void reload();
    void clearFilter();

    IAdatper<T> adapter();
}
