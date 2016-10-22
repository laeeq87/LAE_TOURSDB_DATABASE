package example.itsme.database_home;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by itsme on 10/11/2016.
 */
public class toursOpenHelper extends SQLiteOpenHelper {

    private static final String LOGTAG="EXPLORECA";
    private static final String DATABASE_NAME="tours.db";
    private static final int DATABASE_VERSION=1;
    ////////////////////////////////////////////////////////////////////////////////
    public static final String TABLE_TOUR="tours_table";
    public static final String COLUMN_ID="ID";
    public static final String COLUMN_TITLE="TITLE";
    public static final String COLUMN_DESC="DESCRIPTION";
    public static final String COLUMN_IMAGE="IMAGE";
    public static final String COLUMN_PRICE="PRICE";
 ////////////////////////////////////////////////////////////////////////////////////////////////////
    private static final String TABLE_CREATE=" CREATE TABLE " + TABLE_TOUR + "("+
         COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT , "+
         COLUMN_TITLE +" TEXT,"+
         COLUMN_DESC+"TEXT,"+
         COLUMN_PRICE+"NUMERIC,"+
         COLUMN_IMAGE+"TEXT"+")";
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public toursOpenHelper(Context context) {

    super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//        sqLiteDatabase.execSQL(TABLE_CREATE);
       //sqLiteDatabase.execSQL("create table"+TABLE_TOUR+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,DESCRIPTION TEXT,PRICE NUMERIC,IMAGE TEXT)");
        sqLiteDatabase.execSQL("create table " + TABLE_TOUR + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT,DESCRIPTION TEXT,PRICE NUMERIC,IMAGE TEXT)");

        Log.i(LOGTAG,"Table has been Created");
 }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS"+TABLE_TOUR);
        onCreate(sqLiteDatabase);

    }
}
