package mobop.booklist.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mobop.booklist.app.data.database.BookManager;
import mobop.booklist.app.data.generic.book.IApiBook;
import mobop.booklist.app.data.generic.book.IPersistentBook;
import mobop.booklist.app.data.generic.book.IPersistentBookManager;


public class BookDetailsActivity extends AppCompatActivity{

    private IPersistentBook mBook;
    private IPersistentBookManager mDatabaseManager;
    private EditText mEditNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabaseManager = new BookManager(this);
        setContentView(R.layout.activity_book_details);

        mDatabaseManager = new BookManager(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mBook = mDatabaseManager.loadInformation((IApiBook)intent.getSerializableExtra(BookListFragment.EXTRA_BOOK));

        TextView bookName = (TextView) findViewById(R.id.book_name);
        bookName.setText(mBook.getName());
        bookName.setSelected(true); //For the auto scroll

        TextView bookGenre = (TextView) findViewById(R.id.book_genre);
        bookGenre.setText(mBook.getGenre());
        bookGenre.setSelected(true); //For the auto scroll

        RatingBar bookRating = (RatingBar) findViewById(R.id.book_ratings);

        double rating = mBook.getRatings();
        if(rating > 0) {
            bookRating.setRating((float) rating);
        }
        TextView bookPages = (TextView) findViewById(R.id.book_pages);

        int pages = mBook.getPages();
        if(pages > 0)
            bookPages.setText(String.format(getString(R.string.book_pages), mBook.getPages()));

        ImageView bookImage = (ImageView) findViewById(R.id.book_image);
        // Load image with Picasso http://square.github.io/picasso/
        Picasso.with(this).load(mBook.getImagePath()).into(bookImage);

        CheckBox bookWish = (CheckBox) findViewById(R.id.book_wish);
        bookWish.setChecked(mBook.isWish());
        bookWish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBook.setWish(isChecked);
                saveBook();
            }
        });

        CheckBox bookHave = (CheckBox) findViewById(R.id.book_own);
        bookHave.setChecked(mBook.isOwn());
        bookHave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBook.setOwn(isChecked);
                saveBook();
            }
        });

        CheckBox bookToRead = (CheckBox) findViewById(R.id.book_to_read);
        bookToRead.setChecked(mBook.isToRead());
        bookToRead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBook.setToRead(isChecked);
                saveBook();
            }
        });

        CheckBox bookFavorite = (CheckBox) findViewById(R.id.book_favorite);
        bookFavorite.setChecked(mBook.isFavorite());
        bookFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBook.setFavorite(isChecked);
                saveBook();
            }
        });
        mEditNotes = (EditText) findViewById(R.id.edit_notes);
        mEditNotes.setText(mBook.getNotes());

        TextView bookAuthors = (TextView)findViewById(R.id.book_authors);
        List<String> authors = mBook.getAuthors();
        if (authors != null && authors.size() > 0) {
            bookAuthors.setText(TextUtils.join(", ", mBook.getAuthors()));
        } else {
            bookAuthors.setText("Auteur(s) non disponible(s)");
        }

        TextView bookSummary = (TextView)findViewById(R.id.book_summary);
        String summary = mBook.getDescription();

        if(summary == null || summary.length() < 0) {
            summary = getString(R.string.no_summary);
        }

        bookSummary.setText(summary);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_details, menu);
        return true;
    }

    private void saveBook() {
        mBook = mDatabaseManager.save(mBook);
    }

    private void saveNotes() {
        mBook.setNotes(mEditNotes.getText().toString());
        saveBook();
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveNotes();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveNotes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
