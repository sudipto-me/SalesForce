package app.salesforce.gnt.com.salesforce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by PC-05 on 5/10/2017.
 */

public class MyDB extends SQLiteOpenHelper {

    //SqliteDatabase name


    //database version
    private static final int DATABASE_VERSION = 1;
    //database name
    private static final String DATABASE_NAME = "inforce";
    //employee table name
    private static final String TABLE_NAME = "employee";
    //columns name
    private static final String COLOUMN_ONE = "id";
    private static final String COLOUMN_TWO = "value";

    //    //product table
    private static final String table_item = "table_item";
    //columns name
//    private static final String COLUMN_ONE = "id";
//    private static final String COLUMN_TWO = "value";
//    private static final String COLUMN_THREE = "pid";
//    private static final String COLUMN_FOUR = "quantity";
//    private static final String COLUMN_FIVE = "price";


    //table query
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLOUMN_ONE + " INTEGER PRIMARY KEY," +
                    COLOUMN_TWO + " TEXT)";
    //    //new table
//    private static final String SQL_CREATE_ITEMS = "CREATE TABLE " + TABLE_ITEMS + "(" +
//            COLUMN_ONE + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
//            COLUMN_TWO + " INTEGER, " +
//            COLUMN_THREE + " INTEGER, " +
//            COLUMN_FOUR + " INTEGER, " +
//            COLUMN_FIVE + " INTEGER)";
//
    private static final String SQL_CREATE_ITEMS = "CREATE TABLE table_item (id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " out_id INTEGER," +
            " product_id INTEGER," +
            " product_quantity INTEGER," +
            " product_price INTEGER)";


//    private static final String SQL_DELETE_ENTRIES =
//            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public MyDB(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //--------------Creating tables------------------
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + table_item);
        onCreate(db);

    }

//    public Cursor checkTable(SQLiteDatabase db) {
//
//        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_NAME + "'", null);
//        return cursor;
//    }


    //adding single employee id to the database
    public void insertData(String value) {

        SQLiteDatabase db = this.getWritableDatabase();


        // Create a new map of values, where column names are the keys
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLOUMN_ONE, 1);
        contentValues.put(COLOUMN_TWO, value);

        // Insert the new row, returning the primary key value of the new row
        db.insert(TABLE_NAME, null, contentValues);

    }

    //    //adding  information to the database
    public void insertITEMS(int value, int value1, int value2, int value3) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        try {
//            contentValues.put(COLUMN_ONE, pro_id);
            contentValues.put("out_id", value);
            contentValues.put("product_id", value1);
            contentValues.put("product_quantity", value2);
            contentValues.put("product_price", value3);
            db.insert(table_item, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //getting single employee id from the database
    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                COLOUMN_ONE,
                COLOUMN_TWO
        };

        Cursor cursor = db.query(
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

    //    //getting information from the database
    public Cursor getProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                "out_id",
                "product_id",
                " product_quantity",
                " product_price"

        };

        Cursor cursor = db.query(
                table_item,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        return cursor;
    }

    public Cursor DataChecking(int outletid) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM  table_item  WHERE out_id = " + outletid;
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;

    }



//        if (cursor != null) {
//           while( cursor.moveToFirst()){
//            String outlet_id = cursor.getString(0);
//            Log.d("Value", outlet_id);}
//
//            Log.d("Found", toString());
//            cursor.close();
//            return true;
//        } else {
//            Log.d("Value","not found");
//            Log.d("Not Found", toString());
//            return false;
//        }



    }



