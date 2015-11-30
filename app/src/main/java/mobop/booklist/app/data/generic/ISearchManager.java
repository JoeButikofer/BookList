package mobop.booklist.app.data.generic;

public interface ISearchManager<T> {


    void clearFilter();

    IAdatper<T> adapter();
}
