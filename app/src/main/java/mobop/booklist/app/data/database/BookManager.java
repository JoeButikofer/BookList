package mobop.booklist.app.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;

import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.database.builder.Column;
import mobop.booklist.app.data.database.builder.ColumnType;
import mobop.booklist.app.data.database.builder.Table;
import mobop.booklist.app.data.generic.IAdatper;
import mobop.booklist.app.data.generic.IPersistentSearchManager;
import mobop.booklist.app.data.generic.book.IApiBook;
import mobop.booklist.app.data.generic.book.IPersistentBook;
import mobop.booklist.app.data.generic.book.IPersistentBookManager;

import java.util.ArrayList;
import java.util.List;

public class BookManager implements IPersistentBookManager, IPersistentSearchManager<IApiBook> {

    private static final String COLUMN_API_ID = "api_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PAGES = "pages";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_IMAGE_PATH = "image_path";
    private static final String COLUMN_NOTES = "notes";
    private static final String COLUMN_READ = "read";
    private static final String COLUMN_OWN = "own";
    public static final String SEARCH_WISH = "SEARCH_WISH";
    public static final String SEARCH_LIBRARY = "SEARCH_LIBRARY";
    public static final String SEARCH_TO_READ = "SEARCH_TO_READ";
    public static final String SEARCH_FAVORITES = "SEARCH_FAVORITES";

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final BookAdapter mAdapter;
    private final List<IApiBook> mListbook;

    public BookManager(Context context) {
        mDbHelper = new DbHelper(context);
        mContext = context;
        mListbook = new ArrayList<>();
        mAdapter = new BookAdapter(mContext, mListbook, "bookManager");
    }

    public static final Table TABLE = new Table("books"
            , new Column(COLUMN_API_ID, ColumnType.Text)
            , new Column(COLUMN_NAME, ColumnType.Text)
            , new Column(COLUMN_GENRE, ColumnType.Text)
            , new Column(COLUMN_PAGES, ColumnType.Int)
            , new Column(COLUMN_RATING, ColumnType.Int)
            , new Column(COLUMN_IMAGE_PATH, ColumnType.Text)
            , new Column(COLUMN_NOTES, ColumnType.Text)
            , new Column(COLUMN_READ, ColumnType.Bool)
            , new Column(COLUMN_OWN, ColumnType.Bool)
    );
    private void filterBool(String columnName, boolean value) {
        filter(columnName + "= ?", new String[]{String.valueOf(boolToDb(value))});
    }

    private void filter(String columnsWhere, String[] valuesWhere) {
        mListbook.clear();
        Cursor c = mDbHelper.getReadableDatabase().query(
                TABLE.getName(),                // The table to query
                TABLE.getColumnsNames(),        // The columns to return
                columnsWhere,                   // The columns for the WHERE clause
                valuesWhere,                    // The values for the WHERE clause
                null,                           // don't group the rows
                null,                           // don't filter by row groups
                null                            // The sort order
        );
        while (c.moveToNext()) {
            Book book = cursorToBook(c);
            mListbook.add(book);
            Log.d("Database", "Read id = " + book.getId() + " / dbid = " + book.getDbId());
        }
        c.close();
        mAdapter.notifyDataSetChanged();
    }

    @NonNull
    private Book cursorToBook(Cursor c) {
        Book book = new Book();
        book.setDbId(c.getInt(0));
        book.setId(c.getString(1));
        book.setName(c.getString(2));
        book.setGenre(c.getString(3));
        book.setPages(c.getInt(4));
        book.setRatings(c.getInt(5));
        book.setImagePath(c.getString(6));
        book.setNotes(c.getString(7));
        book.setRead(dbToBool(c.getInt(8)));
        book.setOwn(dbToBool(c.getInt(9)));
        return book;
    }
    private static void loadAllInfomations(IPersistentBook to, IApiBook item) {
        loadApiInfomation(to, item);
        if (item instanceof IPersistentBook) {
            loadPersistentInformations(to, (IPersistentBook) item);
        }
    }
    private static void loadApiInfomation(IPersistentBook to, IApiBook item) {
        to.setId(item.getId());
        to.setName(item.getName());
        to.setGenre(item.getGenre());
        to.setPages(item.getPages());
        to.setRatings(item.getRatings());
        to.setImagePath(item.getImagePath());
    }

    private static void loadPersistentInformations(IPersistentBook to, IPersistentBook item) {
        to.setNotes(item.getNotes());
        to.setRead(item.isRead());
        to.setOwn(item.isOwn());
        to.setDbId(item.getDbId());
    }


    private static boolean dbToBool(int value) {
        return value == 1;
    }

    private static int boolToDb(boolean value) {
        return value ? 1 : 0;
    }

    @Override
    public void clearFilter() {

    }

    @Override
    public IAdatper<IApiBook> adapter() {
        return mAdapter;
    }

    private void toContentValue(ContentValues values, IPersistentBook item) {
        values.put(COLUMN_API_ID, item.getId());
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_GENRE, item.getGenre());
        values.put(COLUMN_PAGES, item.getPages());
        values.put(COLUMN_RATING, item.getRatings());
        values.put(COLUMN_IMAGE_PATH, item.getImagePath());
        values.put(COLUMN_NOTES, item.getNotes());
        values.put(COLUMN_READ, boolToDb(item.isRead()));
        values.put(COLUMN_OWN, boolToDb(item.isOwn()));
    }

    private void add(IPersistentBook book) {
        Log.d("Database", "insert book " + book.getId());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        toContentValue(values, book);

        long id = db.insert(TABLE.getName(), null, values);
        book.setDbId(id);
    }

    private void update(IPersistentBook item) {
        Log.d("Database", "update book " + item.getId());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        toContentValue(values, item);
        int nb = db.update(
                TABLE.getName(),
                values,
                Table.ID + "= ?",
                new String[]{
                        String.valueOf(item.getDbId())
                }
        );
        if (nb != 1) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public IPersistentBook save(IApiBook item) {
        IPersistentBook book = null;
        if (item instanceof IPersistentBook) {
            book = (IPersistentBook) item;
        } else {
            book = get(item.getId());
            if (book == null){
                book = new Book();
            }
            loadAllInfomations(book, item);
        }
        if (book.getDbId() > 0) {
            update(book);
        } else {
            add(book);
        }
        return book;
    }

    private IPersistentBook get(String id) {
        IPersistentBook book = null;
        Cursor c = mDbHelper.getReadableDatabase().query(
                TABLE.getName(),                // The table to query
                TABLE.getColumnsNames(),        // The columns to return
                COLUMN_API_ID + " = ?",         // The columns for the WHERE clause
                new String[]{id},                // The values for the WHERE clause
                null,                           // don't group the rows
                null,                           // don't filter by row groups
                null                            // The sort order
        );
        if (c.moveToNext()) {
            book = cursorToBook(c);
        }
        c.close();
        return book;
    }
    @Override
    public IPersistentBook loadInformation(IApiBook item) {
        IPersistentBook book =  get(item.getId());
        if (book != null) {
            loadApiInfomation(book, item);
            return save(book);
        } else {
            return save(item);
        }
    }

    @Override
    public void filterOwn() {
        filterBool(COLUMN_OWN, true);
    }

    @Override
    public void filterWish() {

    }

    @Override
    public void filterToRead() {

    }

    @Override
    public void filterFavorite() {

    }
}
