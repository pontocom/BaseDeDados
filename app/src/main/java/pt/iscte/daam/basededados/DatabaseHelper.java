package pt.iscte.daam.basededados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cserrao on 15/03/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context c) {
        super(c, "CONTACT_DATABASE", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contactos (" +
                "id INTEGER PRIMARY KEY," +
                "nome VARCHAR(128)," +
                "email VARCHAR(128)," +
                "telephone VARCHAR(32))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contactos");
    }
}
