package mobop.booklist.app.data.generic;

public interface IManager<T> {
    void filter(String text);
    void clearFilter();

    IAdatper<T> adapter();

    T add(T item);

    void update(T item);
}
