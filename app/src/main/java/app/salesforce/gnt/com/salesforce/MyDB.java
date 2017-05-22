package app.salesforce.gnt.com.salesforce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC-05 on 5/10/2017.
 */

public class MyDB extends SQLiteOpenHelper {

    //SqliteDatabase name


    //database version
    private static final int DATABASE_VERSION = 1;
    //database name
    private static final String DATABASE_NAME = "Inforce.db";
    //table name
    private static final String TABLE_NAME = "employee";
    //columns name
    private static final String COLOUMN_ONE = "id";
    private static final String COLOUMN_TWO = "value";

    //table query
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE "+TABLE_NAME+" (" +
                    COLOUMN_ONE + " INTEGER PRIMARY KEY," +
                    COLOUMN_TWO + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public MyDB(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    public Cursor checkTable(SQLiteDatabase db){

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+TABLE_NAME+"'",null);
        return cursor;
    }


    //adding single employee id to the database
    public void insertData(String value) {

        SQLiteDatabase db = this.getWritableDatabase();


        // Create a new map of values, where column names are the keys
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLOUMN_ONE,1);
        contentValues.put(COLOUMN_TWO,value);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, contentValues);

    }

    //getting single employee id from the database
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                COLOUMN_ONE,
                COLOUMN_TWO
        };

        Cursor cursor =  db.query(
                TABLE_NAME,   //The table to query
                projection,   //The columns to return
                null,//selecction      //The columns for the WHERE CLAUSE
                null,//selectionARGS   //The columns for the WHERE CLAUSE
                null,                  //don't group the row
                null,                  //don't filter the row group
                null                   //The sort order
        );
        return cursor;
    }


}
