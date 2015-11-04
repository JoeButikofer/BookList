package mobop.booklist.app;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.*;
import android.widget.*;
import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.database.Book;
import mobop.booklist.app.data.database.BookManager;
import mobop.booklist.app.data.generic.IBook;
import mobop.booklist.app.data.generic.IManager;


public class BookListFragment extends Fragment {

    public final static String EXTRA_BOOK = "mobop.booklist.app.BOOK";

    private BookAdapter bookAdapter;
    IManager<IBook> databaseBookManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        Log.d("Book", "inflate");
        ListView listViewBook = (ListView)view.findViewById(android.R.id.list);
        databaseBookManager = new BookManager(getActivity());

        //TODO REMOVE THIS
        //TEST
        IBook testBook = new Book();
        testBook.setName("A really good book");
        testBook.setGenre("action");
        testBook.setPages(42);
        testBook.setRatings(12);

        bookAdapter = new BookAdapter(getActivity(), databaseBookManager.list());

        databaseBookManager.add(testBook);
        testBook.setPages(2);
        databaseBookManager.update(testBook);

        listViewBook.setAdapter(bookAdapter);
        listViewBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IBook selectedBook = databaseBookManager.list().get(position);
                Intent intent = new Intent(getActivity(), BookDetailsActivity.class);
                intent.putExtra(EXTRA_BOOK, selectedBook);
                startActivity(intent);
            }
        });
        listViewBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                IBook selectedBook = databaseBookManager.list().get(position);

                //TODO Long click : add/remove from lists
                return true; //true if the callback consumed the long click, false otherwise
            }
        });
        return view;
    }



}
