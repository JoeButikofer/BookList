package mobop.booklist.app.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.database.builder.Column;
import mobop.booklist.app.data.database.builder.ColumnType;
import mobop.booklist.app.data.database.builder.Table;
import mobop.booklist.app.data.generic.IAdatper;
import mobop.booklist.app.data.generic.IBook;
import mobop.booklist.app.data.generic.IPersistentManager;
import mobop.booklist.app.data.generic.ISearchManager;

import java.util.ArrayList;
import java.util.List;

public class BookManager implements IPersistentManager<IBook>, ISearchManager<IBook> {

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PAGES = "pages";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_READ = "read";
    public static final String SEARCH_WISH = "SEARCH_WISH";
    public static final String SEARCH_LIBRARY = "SEARCH_LIBRARY";
    public static final String SEARCH_TO_READ = "SEARCH_TO_READ";
    public static final String SEARCH_FAVORITES = "SEARCH_FAVORITES";

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final BookAdapter mAdapter;
    private final List<IBook> mListbook;

    public BookManager(Context context) {
        mDbHelper = new DbHelper(context);
        mContext = context;
        mListbook = new ArrayList<>();
        mAdapter = new BookAdapter(mContext, mListbook, "bookManager");
    }

    public static final Table TABLE = new Table("books"
            , new Column(COLUMN_NAME, ColumnType.Text)
            , new Column(COLUMN_GENRE, ColumnType.Text)
            , new Column(COLUMN_PAGES, ColumnType.Int)
            , new Column(COLUMN_RATING, ColumnType.Int)
            , new Column(COLUMN_READ, ColumnType.Bool)
    );

    @Override
    public void filter(String text) {
        mListbook.clear();
        Cursor c = mDbHelper.getReadableDatabase().query(
                TABLE.getName(),                // The table to query
                TABLE.getColumnsNames(),        // The columns to return
                null,                           // The columns for the WHERE clause
                null,                           // The values for the WHERE clause
                null,                           // don't group the rows
                null,                           // don't filter by row groups
                null                            // The sort order
        );
        while (c.moveToNext()) {
            Book book = new Book();
            Log.d("Database", "Read id = " + c.getLong(0));
            book.setDbId(c.getInt(0));
            book.setName(c.getString(1));
            book.setGenre(c.getString(2));
            book.setPages(c.getInt(3));
            book.setRatings(c.getInt(4));
            book.setRead(dbToBool(c.getInt(5)));
            mListbook.add(book);
        }
        mAdapter.notifyDataSetChanged();
    }

    private static boolean dbToBool(int value) {
        return value == 1;
    }

    private static int boolToDb(boolean value) {
        return value ? 1 : 0;
    }

    @Override
    public void clearFilter() {
        filter("");
    }

    @Override
    public IAdatper<IBook> adapter() {
        return mAdapter;
    }

    private void toContentValue(ContentValues values, IBook item) {
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_GENRE, item.getGenre());
        values.put(COLUMN_PAGES, item.getPages());
        values.put(COLUMN_RATING, item.getRatings());
        values.put(COLUMN_READ, boolToDb(item.isRead()));
    }

    private void add(Book book) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        toContentValue(values, book);

        long id = db.insert(TABLE.getName(), null, values);
        book.setDbId(id);
    }

    private void update(IBook item, long dbId) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        toContentValue(values, item);
        int nb = db.update(
                TABLE.getName(),
                values,
                Table.ID + "= ?",
                new String[]{
                        String.valueOf(dbId)
                }
        );
        if (nb != 1) {
            throw new IllegalArgumentException();
        }
    }

    private long getDbId(String id) {
        // TODO
        return -1;
    }

    @Override
    public IBook save(IBook item) {
        if (item instanceof Book) {
            Book book = (Book) item;
            if (book.getDbId() > 0) {
                update(book, book.getDbId());
                return book;
            }
        }
        long dbId = getDbId(item.getId());
        Book book = new Book(item);
        if (dbId > 0) {
            this.update(book, dbId);
        } else {
            this.add(book);
        }
        return book;
    }
}
