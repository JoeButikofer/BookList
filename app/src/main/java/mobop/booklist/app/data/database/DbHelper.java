package mobop.booklist.app.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import mobop.booklist.app.data.database.builder.Table;

public class DbHelper extends SQLiteOpenHelper {
    private Context context;
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "Books_tmp_2.db";

    private Table[] tables;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        tables = new Table[] {
            BookManager.TABLE,
        };

    }

    private String getCreateSql() {
        StringBuilder builder = new StringBuilder();
        for(Table table : tables) {
            table.createTableQuery(builder);
        }
        return builder.toString();
    }

    private String getDropSql() {
        StringBuilder builder = new StringBuilder();
        for(Table table : tables) {
            table.deleteTableQuery(builder);
        }
        return builder.toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getCreateSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(getDropSql());
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
