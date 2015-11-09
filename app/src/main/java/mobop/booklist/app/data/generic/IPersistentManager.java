package mobop.booklist.app.data.generic;

public interface IPersistentManager<T> extends ISearchManager<T> {
    T save(T item);
}
