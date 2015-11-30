package mobop.booklist.app.data.generic;

public interface IPersistentManager<TApi, TPersistent extends TApi> {
    TPersistent save(TApi item);
    TPersistent loadInformation(TApi item);
}
