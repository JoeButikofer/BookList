package mobop.booklist.app.data.generic;

public interface IApiSearchManager<T> extends ISearchManager<T> {
    void filter(String text);
    void filterTitle(String text);
    void filterAuthor(String text);
    void filterIsbn(String text);
}
