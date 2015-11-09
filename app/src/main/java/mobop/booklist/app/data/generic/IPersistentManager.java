package mobop.booklist.app.data.generic;

public interface IPersistentManager<T> extends ISearchManager<T> {
    T add(T item);

    void update(T item);
}
