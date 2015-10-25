package mobop.booklist.app.data.generic;

import java.util.List;

public interface IManager<T> {
    List<T> list();
    List<T> search(String text);

    T add(T item);

    void update(T item);
}
