package mobop.booklist.app.data.generic;

import android.widget.ListAdapter;

public interface IAdapter<T> extends ListAdapter {
    @Override
    T getItem(int position);
}
