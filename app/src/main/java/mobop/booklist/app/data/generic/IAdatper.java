package mobop.booklist.app.data.generic;

import android.widget.ListAdapter;

public interface IAdatper<T> extends ListAdapter {
    @Override
    T getItem(int position);
}
