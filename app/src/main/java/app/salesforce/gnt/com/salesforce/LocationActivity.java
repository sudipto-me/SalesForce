package app.salesforce.gnt.com.salesforce;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    RecyclerView rv;
    LocationAdapter myAdapter;
    ArrayList<Location> locations = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DBHelper db = new DBHelper(this);

        rv = (RecyclerView)findViewById(R.id.rv_location_name);

        rv.setHasFixedSize(true);

        rv.setLayoutManager(new LinearLayoutManager(LocationActivity.this));

        //Inserting location
        Log.d("Inserting:","Inserting...");

        db.addData(new Location("Mohakhali"));
        db.addData(new Location("Gulshan1"));
        db.addData(new Location("Gulshan2"));
        db.addData(new Location("Motijhil"));

        //Reading all locations

        Log.d("Reading:","Reading....");
        List<Location>locations = db.getAllLocation();

        Log.e("Location",locations.toString());



        myAdapter = new LocationAdapter(LocationActivity.this,locations);
        rv.setAdapter(myAdapter);


    }






    }



