package mobop.booklist.app.data.generic;

public interface ISearchManager<T> {
    void filter(String text);
    void clearFilter();

    IAdatper<T> adapter();
}
