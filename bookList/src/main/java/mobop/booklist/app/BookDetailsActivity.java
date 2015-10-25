package mobop.booklist.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import mobop.booklist.app.data.generic.IBook;
import mobop.booklist.app.task.LoadImageTask;


public class BookDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        IBook book = (IBook)intent.getSerializableExtra(BookListActivity.EXTRA_BOOK);

        TextView bookName = (TextView) findViewById(R.id.book_detail_name);
        bookName.setText(book.getName());

        TextView bookGenre = (TextView) findViewById(R.id.book_detail_genre);
        bookGenre.setText(book.getGenre());

        TextView bookRating = (TextView) findViewById(R.id.book_detail_ratings);
        bookRating.setText(""+book.getRatings());

        TextView bookPages = (TextView) findViewById(R.id.book_detail_pages);
        bookPages.setText(book.getPages() + " pages");

        //TODO decommenter quand les images marcheront
        //ImageView bookImage = (ImageView) findViewById(R.id.book_detail_image);
        //new LoadImageTask(bookImage, 80,80).execute(book.getImagePath()); //TODO voir les valeurs de taille de l'image

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
