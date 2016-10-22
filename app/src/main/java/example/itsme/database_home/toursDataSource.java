package example.itsme.database_home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by itsme on 10/11/2016.
 */
public class toursDataSource {
    private static final String LOGTAG = "EXPLORECA";
    SQLiteOpenHelper dbhelper;
    SQLiteDatabase db;


    private static final String[] allColumns = {

            toursOpenHelper.COLUMN_ID,
            toursOpenHelper.COLUMN_TITLE,
            toursOpenHelper.COLUMN_DESC,
            toursOpenHelper.COLUMN_PRICE,
            toursOpenHelper.COLUMN_IMAGE
    };

    public toursDataSource(Context context) {

        dbhelper = new toursOpenHelper(context);
    }

    public void Open() {
        Log.i(LOGTAG, "Database Open");
        db = dbhelper.getWritableDatabase();
    }

    public void Close() {
        Log.i(LOGTAG, "Database Close");
        dbhelper.close();

    }

    public tours insertData(tours tour) {
        ContentValues values = new ContentValues();
        values.put(toursOpenHelper.COLUMN_TITLE, tour.getTitle());
        values.put(toursOpenHelper.COLUMN_DESC, tour.getDescription());
        values.put(toursOpenHelper.COLUMN_IMAGE, tour.getImage());
        values.put(toursOpenHelper.COLUMN_PRICE, tour.getPrice());
        long result = db.insert(toursOpenHelper.TABLE_TOUR, null, values);
        tour.setId(result);
        return tour;

    }

    public List<tours> findAll() {

        Cursor cursor = db.query(toursOpenHelper.TABLE_TOUR, allColumns, null, null, null, null, null, null);
        Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
        List<tours> tours = cursorToList(cursor);
        return tours;
    }

    public List<tours> findFiltered(String selection, String orderBy) {

        Cursor cursor = db.query(toursOpenHelper.TABLE_TOUR, allColumns, selection, null, null, null, null, orderBy);
        Log.i(LOGTAG, "Returned " + cursor.getCount() + " rows");
        List<tours> tours = cursorToList(cursor);

        return tours;
    }

    @NonNull
    private List<tours> cursorToList(Cursor cursor) {
        List<tours> tours = new ArrayList<tours>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                tours tour = new tours();
                tour.setId(cursor.getLong(cursor.getColumnIndex(toursOpenHelper.COLUMN_ID)));
                tour.setTitle(cursor.getString(cursor.getColumnIndex(toursOpenHelper.COLUMN_TITLE)));
                tour.setDescription(cursor.getString(cursor.getColumnIndex(toursOpenHelper.COLUMN_DESC)));
                tour.setImage(cursor.getString(cursor.getColumnIndex(toursOpenHelper.COLUMN_IMAGE)));
                tour.setPrice(cursor.getDouble(cursor.getColumnIndex(toursOpenHelper.COLUMN_PRICE)));
                tours.add(tour);
            }
        }
        return tours;
    }
}
