package app.salesforce.gnt.com.salesforce;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC-05 on 5/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    //database version
    private   static final int DATABASE_VERSION = 1;

    //database name
    private static final String DATABASE_NAME = "locationDatabase";

    //table name
    private static final String TABLE_NAME = "location";

    //columns name
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";



    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try{
            String CREATE_TABLE="CREATE TABLE location(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT NOT NULL);";

            db.execSQL(CREATE_TABLE);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        onCreate(db);

    }

    //add new data
    public void addData(Location location){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_NAME,location.getLocation_name());

        //inserting rows
        db.insert(TABLE_NAME,null,values);
        db.close();

    }

    //getting all contacts
    public List<Location>getAllLocation(){
        List<Location>locationList = new ArrayList<Location>();

        //select all query
        String selectQuery = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);

        //looping through all rows and columns

        if(cursor.moveToFirst()){
            do{
                Location location = new Location();
                location.setLocation_id(Integer.parseInt(cursor.getString(0)));
                location.setLocation_name(cursor.getString(1));

                locationList.add(location);
            }while (cursor.moveToNext());
        }
        //return location list
        return locationList;
    }

}
