package pt.iscte.daam.basededados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cserrao on 15/03/16.
 */
public class ContactoDatasource {
    protected SQLiteDatabase db;
    protected DatabaseHelper dbhelper;

    public ContactoDatasource(Context c) {
        dbhelper = new DatabaseHelper(c);
    }

    public void open() {
        db = dbhelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    //CRUD

    // CREATE NEW CONTACT
    public Contacto create(String nome, String email, String telephone) {
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("email", email);
        values.put("telephone", telephone);

        long lastId = db.insert("contactos",null, values);

        return new Contacto(lastId, nome, email, telephone);
    }

    // OBTER UM CONTACTO
    public Contacto get(long id) {
        Cursor c = db.rawQuery("SELECT * FROM contactos WHERE id = " + id, null);

        if(c.getCount() == 0) {
            return null;
        } else {
            c.moveToFirst();
            Contacto ct = new Contacto(id,
                    c.getString(1),
                    c.getString(2),
                    c.getString(3));
            c.close();
            return ct;
        }
    }

    // OBTER TODOS OS CONTACTOS
    public List<Contacto> getAll() {
        Cursor c = db.rawQuery("SELECT * FROM contactos", null);

        if(c.getCount() == 0) {
            return null;
        } else {
            c.moveToFirst();

            List<Contacto> contactosList = new ArrayList<Contacto>();

            while(!c.isAfterLast()) {
                contactosList.add(new Contacto(c.getLong(0), c.getString(1), c.getString(2), c.getString(3)));
                c.moveToNext();
            }
            c.close();

            return contactosList;
        }
    }

    // APAGAR CONTACTO
    public void apagar(long id) {
        int result = db.delete("contactos", "id=?", new String[] { String.valueOf(id) });
    }

    // CONTAR REGISTOS
    public int count(){
        Cursor c = db.rawQuery("SELECT * FROM contactos", null);
        return c.getCount();
    }
}
