package mobop.booklist.app.data.generic;

public interface IApiSearchManager<T> extends ISearchManager<T> {
    void filter(String text, boolean ignoreLanguage);
    void filterIsbn(String text);
    void setLanguage(String languageCode);
    String getLanguage();
}
