package mobop.booklist.app.data.generic;

import java.util.List;

public interface IManager<T> {
    @Deprecated
    List<T> list();
    @Deprecated
    List<T> search(String text);

    IAdatper<T> adapter();

    T add(T item);

    void update(T item);
}
