package mobop.booklist.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.*;
import android.widget.*;

import mobop.booklist.app.data.generic.book.IApiBook;
import mobop.booklist.app.data.generic.ISearchManager;


public class BookListFragment extends Fragment {

    public final static String EXTRA_BOOK = "mobop.booklist.app.BOOK";

    private ListView listViewBook;
    private ISearchManager<IApiBook> mManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        listViewBook = (ListView) view.findViewById(android.R.id.list);

        listViewBook.setAdapter(mManager.adapter());
        listViewBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IApiBook selectedBook = mManager.adapter().getItem(position);
                Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
                intent.putExtra(EXTRA_BOOK, selectedBook);
                startActivity(intent);
            }
        });
        listViewBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                IApiBook selectedBook = mManager.adapter().getItem(position);

                //TODO Long click : add/remove from lists
                return true; //true if the callback consumed the long click, false otherwise
            }
        });
        listViewBook.setAdapter(mManager.adapter());
        return view;
    }

    public void setManager(ISearchManager<IApiBook> manager) {
        this.mManager = manager;
        if (listViewBook != null) {
            listViewBook.setAdapter(manager.adapter());
        }
    }

    public ISearchManager<IApiBook> getManager()
    {
        return mManager;
    }
}
