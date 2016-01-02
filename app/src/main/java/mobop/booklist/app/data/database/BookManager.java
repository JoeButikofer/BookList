package mobop.booklist.app.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import mobop.booklist.app.adapter.BookAdapter;
import mobop.booklist.app.data.database.builder.Column;
import mobop.booklist.app.data.database.builder.ColumnType;
import mobop.booklist.app.data.database.builder.Table;
import mobop.booklist.app.data.generic.IAdapter;
import mobop.booklist.app.data.generic.IPersistentSearchManager;
import mobop.booklist.app.data.generic.book.IApiBook;
import mobop.booklist.app.data.generic.book.IPersistentBook;
import mobop.booklist.app.data.generic.book.IPersistentBookManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookManager implements IPersistentBookManager, IPersistentSearchManager<IApiBook> {

    private static final String COLUMN_API_ID = "api_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PAGES = "pages";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_IMAGE_PATH = "image_path";
    private static final String COLUMN_AUTHORS = "authors";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_NOTES = "notes";
    private static final String COLUMN_TO_READ = "to_read";
    private static final String COLUMN_OWN = "own";
    private static final String COLUMN_WISH = "wish";
    private static final String COLUMN_FAVORITE = "favorite";

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final BookAdapter mAdapter;
    private final List<IApiBook> mListbook;
    private String filterColumns = null;
    private String[] filterValues = null;


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
            , new Column(COLUMN_AUTHORS, ColumnType.Text)
            , new Column(COLUMN_DESCRIPTION, ColumnType.Text)
            , new Column(COLUMN_NOTES, ColumnType.Text)
            , new Column(COLUMN_TO_READ, ColumnType.Bool)
            , new Column(COLUMN_OWN, ColumnType.Bool)
            , new Column(COLUMN_WISH, ColumnType.Bool)
            , new Column(COLUMN_FAVORITE, ColumnType.Bool)
    );
    private void filterBool(String columnName, boolean value) {
        filter(columnName + "= ?", new String[]{String.valueOf(boolToDb(value))});
    }

    private void filter(String columnsWhere, String[] valuesWhere) {
        filterColumns = columnsWhere;
        filterValues = valuesWhere;
        reload();
    }

    @Override
    public void reload() {
        if (filterColumns == null || filterValues == null) {
            throw new IllegalStateException("Filters must no be null !");
        }
        mListbook.clear();
        Cursor c = mDbHelper.getReadableDatabase().query(
                TABLE.getName(),                // The table to query
                TABLE.getColumnsNames(),        // The columns to return
                filterColumns,                   // The columns for the WHERE clause
                filterValues,                    // The values for the WHERE clause
                null,                           // don't group the rows
                null,                           // don't filter by row groups
                null                            // The sort order
        );
        while (c.moveToNext()) {
            Book book = cursorToBook(c);
            mListbook.add(book);
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

        String[] authors = c.getString(7).split(";");
        book.setAuthors(Arrays.asList(authors));
        book.setDescription(c.getString(8));
        book.setNotes(c.getString(9));
        book.setToRead(dbToBool(c.getInt(10)));
        book.setOwn(dbToBool(c.getInt(11)));
        book.setWish(dbToBool(c.getInt(12)));
        book.setFavorite(dbToBool(c.getInt(13)));
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
        to.setAuthors(item.getAuthors());
        to.setDescription(item.getDescription());
    }

    private static void loadPersistentInformations(IPersistentBook to, IPersistentBook item) {
        to.setNotes(item.getNotes());
        to.setToRead(item.isToRead());
        to.setOwn(item.isOwn());
        to.setDbId(item.getDbId());
        to.setWish(item.isWish());
        to.setFavorite(item.isFavorite());
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
    public IAdapter<IApiBook> adapter() {
        return mAdapter;
    }

    private void toContentValue(ContentValues values, IPersistentBook item) {
        values.put(COLUMN_API_ID, item.getId());
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_GENRE, item.getGenre());
        values.put(COLUMN_PAGES, item.getPages());
        values.put(COLUMN_RATING, item.getRatings());
        values.put(COLUMN_IMAGE_PATH, item.getImagePath());
        List<String> authors = item.getAuthors();
        String stringAuthors = TextUtils.join(";", authors);
        values.put(COLUMN_AUTHORS, stringAuthors);
        values.put(COLUMN_DESCRIPTION, item.getDescription());
        values.put(COLUMN_NOTES, item.getNotes());
        values.put(COLUMN_TO_READ, boolToDb(item.isToRead()));
        values.put(COLUMN_OWN, boolToDb(item.isOwn()));
        values.put(COLUMN_WISH, boolToDb(item.isWish()));
        values.put(COLUMN_FAVORITE, boolToDb(item.isFavorite()));
    }

    private void add(IPersistentBook book) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        toContentValue(values, book);

        long id = db.insert(TABLE.getName(), null, values);
        book.setDbId(id);
    }

    private void update(IPersistentBook item) {
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
        filterBool(COLUMN_WISH, true);
    }

    @Override
    public void filterToRead() {
        filterBool(COLUMN_TO_READ, true);
    }

    @Override
    public void filterFavorite() {
        filterBool(COLUMN_FAVORITE, true);
    }
}
