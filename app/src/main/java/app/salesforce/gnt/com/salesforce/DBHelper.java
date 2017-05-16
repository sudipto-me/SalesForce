package app.salesforce.gnt.com.salesforce;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.SyncStateContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC-05 on 5/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    //SqliteDatabase name


    //database version
    private static final int DATABASE_VERSION = 1;
    //database name
    private static final String DATABASE_NAME = "userdatabase";
    //table name
    private static final String TABLE_NAME = "employee";
    //columns name
    private static final String COLUMN_ID = "id";
    //table query
    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + "( " +
            COLUMN_ID + "STRING" + ");";




    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+TABLE_NAME+"'",null);
        Log.d("Checking:",cursor.toString());
        }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public Cursor checkTable(SQLiteDatabase db){

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+TABLE_NAME+"'",null);
        return cursor;
    }


    //adding single employee id to the database
    public void addEmployee(Employee employee) {

        SQLiteDatabase mydb = this.getWritableDatabase();
        mydb.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, employee.getId());
        mydb.insert(TABLE_NAME,null,contentValues);
        mydb.setTransactionSuccessful();
        mydb.endTransaction();
        mydb.close();





    }

    //getting single employee id from the database

    public Employee getEmployee(int id) {

        SQLiteDatabase mydb = this.getReadableDatabase();


        Cursor cursor =  mydb.query(TABLE_NAME, new String[] { COLUMN_ID
                        }, COLUMN_ID+ "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Employee employee = new Employee(Integer.parseInt(cursor.getString(0)));
        //return employee
        return employee;


    }

    public List<Employee>getAllId(){

    List<Employee> employeeList = new ArrayList<Employee>();
    // Select All Query
    String selectQuery = "SELECT  * FROM " + TABLE_NAME;

    SQLiteDatabase db = this.getWritableDatabase();
    Cursor cursor = db.rawQuery(selectQuery, null);

    // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
        do {
            Employee employee = new Employee();
            employee.setId(cursor.getString(1));

            // Adding contact to list
            employeeList.add(employee);
        } while (cursor.moveToNext());
    }

    // return contact list
        return employeeList;
}

    /*
    public static boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                                      String dbfield, String fieldValue) {
        SQLiteDatabase sqldb = MainActivity.S
        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = sqldb.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

     */

}
