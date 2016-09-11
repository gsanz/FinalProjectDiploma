package audioguia.valencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.location.Location;
import android.content.Intent;
import android.os.Bundle;


import java.util.Vector;

/**
 * Created by gorsanmo on 11/11/15.
 */
public class LocationsVisited extends SQLiteOpenHelper {
    public LocationsVisited(Context context) {
        super(context, "Turistic_Valencia", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Turistic_Valencia (" + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "monument TEXT,lat TEXT,lon TEXT,imag TEXT,visited TEXT)");
    }

    public void insertar(SQLiteDatabase db,String monument,String lat, String lon, String image,String visited)
    {
        db.execSQL("INSERT INTO Turistic_Valencia VALUES (null,'"+monument+"' ,'"+lat+"','"+lon+"','"+image+"','"+visited+"')");
    }

    public void guardar()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM Turistic_Valencia");
        insertar(db,"Ciudad de las Artes y las Ciencias","39.454","-0.3545", "ciudad_ciencias","ciudadciencias");
        insertar(db,"Palacio del Marques de dos Aguas","39.47","-0.373", "marquesdosaguas","marquesdosaguas");
        insertar(db,"Palau de la Musica","39.466","-0.36038", "palau","palau");
        insertar(db,"Lonja","39.466","-0.37833", "lonja","lonja");
        db.close();
    }

    public Vector<String> listaPuntuaciones()
    {
        Vector<String> result = new Vector<String>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id,monument,lat,lon,imag,visited FROM Turistic_Valencia",null);
        while (cursor.moveToNext())
        {
            result.add(cursor.getInt(0) + "___" + cursor.getString(1)+"___" + cursor.getString(2)+"___" + cursor.getString(3)+"___" + cursor.getString(4)+"___" + cursor.getString(5));
        }
        cursor.close();
        db.close();
        return result;
    }

    public void hacer_update(int id_update)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("SELECT id FROM Turistic_Valencia WHERE monument=ciudad_ciencias");
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
