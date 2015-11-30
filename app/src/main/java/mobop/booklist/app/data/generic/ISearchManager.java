package mobop.booklist.app.data.generic;

public interface ISearchManager<T> {
    void filter(String text);
    void filterTitle(String text);
    void filterAuthor(String text);
    void filterIsbn(String text);

    void clearFilter();

    IAdatper<T> adapter();
}
