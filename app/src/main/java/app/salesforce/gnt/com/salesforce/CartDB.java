package app.salesforce.gnt.com.salesforce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Android on 7/20/2017.
 */

public class CartDB extends SQLiteOpenHelper {

    //database version
    private static final int DATABASE_VERSION = 1;
    //database name
    private static final String DATABASE_NAME = "CARTDETAILS.DB";
    //table name
    private static final String TABLE_NAME = "cart";
    //column name
    private static final String COLUMN_ONE = "id";
    private static final String COLUMN_TWO = "shop_id";
    private static final String COLUMN_THREE = "product_id";
    private static final String COLUMN_FOUR = "product_quantity";
    private static final String COLUMN_FIVE = "product_price";

    ContentValues contentValues = new ContentValues();

    //table query
    private static final String SQL_CREATE_TABLE = "CREATE TABLE" + TABLE_NAME +
            "(" + COLUMN_ONE + "INTEGER PRIMARY KEY" +
            COLUMN_TWO + "TEXT" +COLUMN_THREE+"TEXT"+
            COLUMN_FOUR+"TEXT"+COLUMN_FIVE+"TEXT)";

    public CartDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(SQL_CREATE_TABLE);
        onCreate(db);

    }

    public Cursor checkTable(SQLiteDatabase db){
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '" + TABLE_NAME + "'", null);
        return cursor;
    }

    //adding  information to the database
    public void insertData(int value,ArrayList<Product> products) {

        int size  = products.size();

        SQLiteDatabase db = this.getWritableDatabase();

        contentValues.put(COLUMN_ONE,1);
        contentValues.put(COLUMN_TWO,value);

        try {

            for (int i = 0; i < size; i++) {
                contentValues.put(COLUMN_THREE, products.get(i).getId());
                contentValues.put(COLUMN_FOUR, products.get(i).getQuantity());
                contentValues.put(COLUMN_FIVE, products.get(i).getPrice());

                db.insertOrThrow(TABLE_NAME,null,contentValues);
            }
        }catch (Exception e){
            e.printStackTrace();
        }







    }

    //getting information from the database
    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        // Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                COLUMN_ONE,
                COLUMN_TWO,
                COLUMN_THREE,
                COLUMN_FOUR,
                COLUMN_FIVE
        };

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        return cursor;
    }
}
