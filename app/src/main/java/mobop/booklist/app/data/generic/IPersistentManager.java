package mobop.booklist.app.data.generic;

public interface IPersistentManager<T> {
    T save(T item);
}
