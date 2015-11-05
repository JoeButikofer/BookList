package mobop.booklist.app.data.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import mobop.booklist.app.data.database.builder.Column;
import mobop.booklist.app.data.database.builder.ColumnType;
import mobop.booklist.app.data.database.builder.Table;
import mobop.booklist.app.data.generic.IBook;
import mobop.booklist.app.data.generic.IManager;

import java.util.ArrayList;
import java.util.List;

public class BookManager implements IManager<IBook> {

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PAGES = "pages";
    public static final String COLUMN_GENRE = "genre";
    public static final String COLUMN_RATING = "rating";

    // TODO get context
    private DbHelper dbHelper;
    public BookManager(Context context) {
        dbHelper = new DbHelper(context);
    }

    public static final Table TABLE = new Table("books"
            , new Column(BookManager.COLUMN_NAME, ColumnType.Text)
            , new Column(BookManager.COLUMN_GENRE, ColumnType.Text)
            , new Column(BookManager.COLUMN_PAGES, ColumnType.Int)
            , new Column(BookManager.COLUMN_RATING, ColumnType.Int)
    );

    @Override
    public List<IBook> list() {
        return search(null);
    }

    @Override
    public List<IBook> search(String text) {
        List<IBook> books = new ArrayList<IBook>();
        Cursor c = dbHelper.getReadableDatabase().query(
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
            books.add(book);
        }
        return books;
    }
    private void toContentValue(ContentValues values, IBook item) {
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_GENRE, item.getGenre());
        values.put(COLUMN_PAGES, item.getPages());
        values.put(COLUMN_RATING, item.getRatings());
    }
    @Override
    public IBook add(IBook item) {
        if (!(item instanceof Book)) {
            item = new Book(item);
        }
        Book book = (Book) item;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        toContentValue(values, book);


        long id = db.insert(TABLE.getName(), null, values);
        book.setDbId((int)id);
        return book;
    }

    @Override
    public void update(IBook item) {
        if (!(item instanceof Book)) {
            throw new IllegalArgumentException();
        }
        Book book = (Book) item;
        if (book.getDbId() <= 0) {
            throw new IllegalArgumentException();
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        toContentValue(values, item);
        int nb = db.update(
                TABLE.getName(),
                values,
                Table.ID + "= ?",
                new String[]{
                        String.valueOf(book.getDbId())
                }
        );
        if (nb != 1) {
            throw new IllegalArgumentException();
        }
    }
}