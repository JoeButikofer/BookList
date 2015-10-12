package mobop.booklist.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.database.Book;
import mobop.booklist.app.data.generic.IBook;

import java.util.ArrayList;
import java.util.List;


public class BookListActivity extends Activity {


    private List<IBook> listBook;

    public final static String EXTRA_BOOK = "mobop.booklist.app.BOOK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        listBook = new ArrayList<IBook>();

        //TODO REMOVE THIS
        //TEST
        IBook testBook = new Book();
        testBook.setName("A really good book");
        testBook.setGenre("action");
        testBook.setPages(42);
        testBook.setRatings(12);

        listBook.add(testBook);
        //TEST

        BookAdapter adapter = new BookAdapter(this, listBook);

        ListView listViewBook = (ListView) findViewById(R.id.list_books);
        listViewBook.setAdapter(adapter);
        listViewBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IBook selectedBook = listBook.get(position);
                Intent intent = new Intent(BookListActivity.this, BookDetailsActivity.class);
                intent.putExtra(EXTRA_BOOK, selectedBook);
                startActivity(intent);
            }
        });
        listViewBook.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                IBook selectedBook = listBook.get(position);

                //TODO Long click : add/remove from lists
                return true; //true if the callback consumed the long click, false otherwise
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:

                return true;
            case R.id.action_scan:
                //TODO QR code scan
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
