package app.salesforce.gnt.com.salesforce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by PC-05 on 5/25/2017.
 */



public class ProductDB extends SQLiteOpenHelper {





    //database version
    private static final int DATABASE_VERSION = 1;
    //database name
    private static final String DATABASE_NAME = "product.db";
    //table name
    private static final String TABLE_NAME = "product";
    //column name
    private static final String COLUMN_ONE = "id";
    private static final String COLUMN_TWO = "product_id";
    private static final String COLUMN_THREE = "product_quantity";

    //table create query
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+TABLE_NAME+
            "("+COLUMN_ONE+"INTEGER PRIMARY KEY "+ COLUMN_TWO +
            "INTEGER  "+  COLUMN_THREE +"TEXT )";

    //table delete query
    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;

    public ProductDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(DELETE_TABLE);
        onCreate(db);

    }

    public Cursor checkTable(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_NAME + "'", null);
        return cursor;
    }

    //adding single product id into the database
    public void insertProduct(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        //create a new map for the values,where column names are the key
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ONE,1);
        contentValues.put(COLUMN_TWO,id);


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, contentValues);

    }

    //getting single product from the database
    public Cursor getProducts() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                COLUMN_ONE,
                COLUMN_TWO

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


}


