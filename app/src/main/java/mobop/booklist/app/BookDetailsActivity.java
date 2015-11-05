package mobop.booklist.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import mobop.booklist.app.data.generic.IBook;


public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        IBook book = (IBook)intent.getSerializableExtra(BookListFragment.EXTRA_BOOK);

        TextView bookName = (TextView) findViewById(R.id.book_name);
        bookName.setText(book.getName());

        TextView bookGenre = (TextView) findViewById(R.id.book_genre);
        bookGenre.setText(book.getGenre());

        TextView bookRating = (TextView) findViewById(R.id.book_ratings);
        bookRating.setText(""+book.getRatings());

        TextView bookPages = (TextView) findViewById(R.id.book_pages);
        bookPages.setText(book.getPages() + " pages");

        ImageView bookImage = (ImageView) findViewById(R.id.book_image);
        // Load image with Picasso http://square.github.io/picasso/
        Picasso.with(this).load(book.getImagePath()).into(bookImage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
